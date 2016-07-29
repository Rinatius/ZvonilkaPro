package kg.kloop.rinat.zvonilka;

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
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import kg.kloop.rinat.zvonilka.data.Event;
import kg.kloop.rinat.zvonilka.data.EventUserStatus;
import kg.kloop.rinat.zvonilka.data.UserData;
import kg.kloop.rinat.zvonilka.login.DefaultCallback;


public class EventActivity extends AppCompatActivity {


    Event event;
    String eventId;
    EditText  city, company, notes, participants, name;
    TextView date;
    BackendlessDataQuery querry;

    int editCount = 0;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        initUI();
        setOnClick();

        eventId = getIntent().getExtras().getString(getResources().getString(R.string.eventIdkey));
        querry = new BackendlessDataQuery();
        querry.setWhereClause(Resources.OBJECT_ID + " = '" + eventId + "'");
        Log.d("query", eventId);

        Backendless.Persistence.of(Event.class).find(querry, new DefaultCallback<BackendlessCollection<Event>>(this) {
            @Override
            public void handleResponse(BackendlessCollection<Event> eventBackendlessCollection) {
                event = eventBackendlessCollection.getData().get(0);
//                Log.d("Event", event.size() + "");
//                for (int i = 0; i < event.size(); i++) {
                    String text;
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
                    for (int j = 0; j < event.getEventUserStatus_ID_Event().size(); j++) {
                        EventUserStatus eventUserStatus;
                        eventUserStatus = event.getEventUserStatus_ID_Event().get(j);
//                        text = eventUserStatus.getUserData_ID_EventUserStatus().getFirstName() +
//                                " " + eventUserStatus.getUserData_ID_EventUserStatus().getSecondName() + "\n";
                    }
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
        participants = (EditText) findViewById(R.id.eventActivityParticipants);
        name = (EditText) findViewById(R.id.eventActivityName);



        date.setEnabled(false);
        city.setEnabled(false);
        notes.setEnabled(false);
        company.setEnabled(false);
        name.setEnabled(false);
    }

    private void setOnClick(){

    }


}
