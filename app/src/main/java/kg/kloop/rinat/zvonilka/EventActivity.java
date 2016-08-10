package kg.kloop.rinat.zvonilka;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessException;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.social.BackendlessSocialJSInterface;

import org.xml.sax.DTDHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import kg.kloop.rinat.zvonilka.data.Event;
import kg.kloop.rinat.zvonilka.data.EventUserStatus;
import kg.kloop.rinat.zvonilka.data.UserData;
import kg.kloop.rinat.zvonilka.login.DefaultCallback;


public class EventActivity extends AppCompatActivity {


    Event event;
    String eventId;
    EditText  city, company, notes, name;

    TextView date,participants;
    BackendlessDataQuery querry;
    String text;
    List<UserData> userDataList;
    AlertDialog.Builder builder;

    private static final String TAG = "EventActivityDebug";

    int editCount = 0;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        initUI();
        setOnClick();

        eventId = getIntent().getExtras().getString(Resources.OBJECT_ID);
        querry = new BackendlessDataQuery();
        querry.setWhereClause(Resources.OBJECT_ID + " = '" + eventId + "'");
        Log.d("query", eventId);

        Backendless.Persistence.of(Event.class).find(querry, new DefaultCallback<BackendlessCollection<Event>>(this) {
            @Override
            public void handleResponse(BackendlessCollection<Event> eventBackendlessCollection) {
                event = eventBackendlessCollection.getData().get(0);
                LoadUsers loadUsers = new LoadUsers(event, EventActivity.this);
                loadUsers.execute();
                text = Resources.NAME + ": " + event.getName();
                name.setText(text);
                text = Resources.DATE + ": " + Resources.DATE_FORMAT.format(event.getDateOfEvent());
                date.setText(text);
                text = Resources.CITY + ": " + event.getCity();
                city.setText(text);
                if (event.getAppCompany_ID_Event() != null) {
//                        text = Resources.COMPANY + ": " + event.getAppCompany_ID_Event().getName();
                    company.setText(text);
                }
                text = Resources.DESCRIPTION + ": " + event.getNote();
                notes.setText(text);
                text = "";
                BackendlessDataQuery query = new BackendlessDataQuery();
                Log.d("userData", event.getEventUserStatus_ID_Event().size() + "");

                try {
                    userDataList = loadUsers.get();
                    builder = new AlertDialog.Builder(EventActivity.this);
                    builder.setTitle(Resources.USERS + ":");
                    CharSequence[] sequence;
                    ArrayList<String> arrayList = new ArrayList<>();
                    for (int i = 0; i < userDataList.size(); i++) {
                        arrayList.add(userDataList.get(i).getFirstName() + " " + userDataList.get(i).getSecondName());
                    }
                    sequence = arrayList.toArray(new CharSequence[arrayList.size()]);
                    builder.setItems(sequence, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(EventActivity.this, UserDataActivity.class);
                            UserData userData = userDataList.get(i);
                            intent.putExtra(Resources.OBJECT_ID, userData.getObjectId());
                            startActivity(intent);
                        }
                    });
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

                text = Resources.PARTICIPANTS;

                participants.setText(text);



                super.handleResponse(eventBackendlessCollection);


            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                Log.w("Error", backendlessFault.getMessage() + " " + backendlessFault.getDetail());
                super.handleFault(backendlessFault);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_data_activity, menu);
//        menu.add("Edit");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_edit:
                if(editCount ==0 ){

//                date.setEnabled(true);
                    notes.setEnabled(true);
                    city.setEnabled(true);
                    name.setEnabled(true);
                    editCount++;
                    item.setIcon(getResources().getDrawable(android.R.drawable.ic_menu_save));
                }
                else{
                    date.setEnabled(false);
                    notes.setEnabled(false);
                    city.setEnabled(false);
                    name.setEnabled(false);

                    String text;
                    text =notes.getText().toString().substring(13);
                    event.setNote(text);
                    text = name.getText().toString().substring(6);
                    event.setName(text);
                    text = city.getText().toString().substring(6);
                    event.setCity(text);
                    Backendless.Persistence.of(Event.class).save(event, new DefaultCallback<Event>(EventActivity.this));
                    item.setIcon(android.R.drawable.ic_menu_edit);
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initUI(){
        date = (TextView) findViewById(R.id.eventActivityDate);
        notes = (EditText) findViewById(R.id.eventActivityNotes);
        city = (EditText) findViewById(R.id.eventActivityCity);
        company = (EditText) findViewById(R.id.eventActivityCompany);
        participants = (TextView) findViewById(R.id.eventActivityParticipants);
        name = (EditText) findViewById(R.id.eventActivityName);



        date.setEnabled(false);
        city.setEnabled(false);
        notes.setEnabled(false);
        company.setEnabled(false);
        name.setEnabled(false);
    }

    private void setOnClick(){
        participants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.show();
            }
        });
    }


}

class LoadUsers extends AsyncTask<Event, Integer , List<UserData>>{

    Event event;
    Context context;

    public LoadUsers(Event event, Context context) {
        this.event = event;
        this.context = context;
    }

    @Override
    protected List<UserData> doInBackground(Event... events) {
        List<EventUserStatus> listEUSAs = event.getEventUserStatus_ID_Event();
        List<UserData> listUserData = new ArrayList<>();
        BackendlessDataQuery query = new BackendlessDataQuery();
        Log.d("userData", listEUSAs.size() + "");

        for (int i = 0; i < listEUSAs.size(); i++) {

            query.setWhereClause("EventUserStatus_ID.objectId = '" + listEUSAs.get(i).getObjectId()+ "'");
            Log.d("userData", query.getWhereClause());
            List<UserData> newList;
            try {
                newList = Backendless.Persistence.of(UserData.class).find(query).getData();
                if(newList.size()!=0){
                    listUserData.add(newList.get(0));
                }
            } catch (BackendlessException e) {
                e.printStackTrace();
            }

        }
        return listUserData;

    }

}