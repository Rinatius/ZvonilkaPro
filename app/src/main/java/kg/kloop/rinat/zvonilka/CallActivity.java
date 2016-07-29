package kg.kloop.rinat.zvonilka;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.property.ObjectProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kg.kloop.rinat.zvonilka.data.Call;
import kg.kloop.rinat.zvonilka.data.Event;
import kg.kloop.rinat.zvonilka.data.EventUserStatus;
import kg.kloop.rinat.zvonilka.data.UserData;
import kg.kloop.rinat.zvonilka.login.DefaultCallback;

public class CallActivity extends AppCompatActivity {
    TextView name, date, duration;
    EditText note, status;
    Spinner eventSpinner;
    String userDataNumber;
    UserData userData;
    Date afterCall, beforeCall;
    List<Event> listEvent;
    List<String> list;
    Call call;
    EventUserStatus eventUserStatus;
    String text;
    Event event;

    private static final String TAG = "CallActivityDebug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        initUi();

        userDataNumber = getIntent().getData().toString().substring(4);
        Log.d(TAG, userDataNumber);

        BackendlessDataQuery querry = new BackendlessDataQuery();
        querry.setWhereClause(Resources.PHONE_NUMBER_KEY + " = '" + userDataNumber + "'");
        Backendless.Persistence.of(UserData.class).find(querry, new DefaultCallback<BackendlessCollection<UserData>>(CallActivity.this){
            @Override
            public void handleResponse(BackendlessCollection<UserData> userDataBackendlessCollection) {
                userData = userDataBackendlessCollection.getData().get(0);


                text = Resources.NAME + ": " + userData.getFirstName() +" " + userData.getSecondName();
                name.setText(text);

                text = Resources.DATE + ": " + Resources.DATE_FORMAT.format(afterCall);
                date.setText(text);

                Backendless.Persistence.of(Event.class).find(new DefaultCallback<BackendlessCollection<Event>>(CallActivity.this){
                    public void handleResponse(BackendlessCollection<Event> eventBackendlessCollection) {
                        listEvent = eventBackendlessCollection.getData();

                        list = new ArrayList<String>();

                        for (int i = 0; i < listEvent.size(); i++) {
                            list.add(listEvent.get(i).getName());
                            Log.d(TAG, listEvent.get(i).getName());
                        }

                        if(!list.isEmpty()) {

                            Log.d(TAG, list.get(0));

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(CallActivity.this, android.R.layout.simple_spinner_dropdown_item, list);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            eventSpinner.setAdapter(adapter);
                        }
                        else{
                            ViewGroup parent = (ViewGroup) eventSpinner.getParent();
                            parent.removeView(eventSpinner);
                        }
                        super.handleResponse(eventBackendlessCollection);
                    }
                });

                super.handleResponse(userDataBackendlessCollection);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_call_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_save:
                call = new Call();


               // Backendless.Persistence.describe( "EventUserStatus", new DefaultCallback<List<ObjectProperty>>(CallActivity.this)
               /* {
                    @Override
                    public void handleResponse( List<ObjectProperty> objectProperties )
                    {
                        for (int i = 0; i < objectProperties.size(); i++) {
                            Log.d("properties", objectProperties.get(i).getName());
                        }
                        super.handleResponse(objectProperties);
                    }
                } );*/Backendless.Persistence.describe( "Event", new DefaultCallback<List<ObjectProperty>>(CallActivity.this)
                {
                    @Override
                    public void handleResponse( List<ObjectProperty> objectProperties )
                    {
                        for (int i = 0; i < objectProperties.size(); i++) {
                            Log.d("properties", objectProperties.get(i).getName());
                        }
                        super.handleResponse(objectProperties);
                    }
                } );


                BackendlessDataQuery querry = new BackendlessDataQuery();

                event = listEvent.get((int) eventSpinner.getSelectedItemId());
                text = "Event[EventUserStatus_ID_Event].objectId = '" + event.getObjectId() + "'" +
                        " and UserData[EventUserStatus_ID].objectId = '" + userData.getObjectId() + "'";
                Log.d(TAG, text);
                querry.setWhereClause(text);
                Backendless.Persistence.of(EventUserStatus.class).find(querry,
                        new DefaultCallback<BackendlessCollection<EventUserStatus>>(CallActivity.this) {
                            @Override
                            public void handleResponse(BackendlessCollection<EventUserStatus> eventUserStatusBackendlessCollection) {
                                List<EventUserStatus> listEUS = eventUserStatusBackendlessCollection.getData();
                                Log.d(TAG, listEUS.size() + "");

                                if(eventUserStatusBackendlessCollection.getData().size() == 0) {
//                                    List<Call> callList = new ArrayList<Call>();
//                                    callList.add(call);
                                    eventUserStatus = new EventUserStatus();
//                                    eventUserStatus.setCall_ID(callList);
//                                    eventUserStatus.setUserData_ID_EventUserStatus(userData);
//                                    eventUserStatus.setEvent_ID(event);
//                                    text = status.getText().toString();
                                    eventUserStatus.setStatus(text);
//                                    call.setEventUserStatus_ID_Call(eventUserStatus);
                                    Backendless.Persistence.of(EventUserStatus.class)
                                            .save(eventUserStatus,
                                                    new DefaultCallback<EventUserStatus>(CallActivity.this));
                                }

                                text = note.getText().toString();
                                call.setNotes(text);
                                call.setDateCall(afterCall);
//                                call.setUserCaller_ID_Call(Backendless.UserService.CurrentUser());
//                                call.setUserDataClient_ID_Call(userData);
                                Backendless.Persistence.of(Call.class).save(call, new DefaultCallback<Call>(CallActivity.this));

                                super.handleResponse(eventUserStatusBackendlessCollection);
                            }
                        });
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initUi(){
        afterCall = new Date();
        date = (TextView)findViewById(R.id.callActivityCallDate);
        duration = (TextView)findViewById(R.id.callActivityCallDuration);
        name = (TextView)findViewById(R.id.callActivityClientName);
        note = (EditText) findViewById(R.id.callActivityNote);
        status = (EditText) findViewById(R.id.callActivityStatus);
        eventSpinner = (Spinner)findViewById(R.id.callActivityEventSpinner);
    }

    public void getUserEvents() {

    }
}
