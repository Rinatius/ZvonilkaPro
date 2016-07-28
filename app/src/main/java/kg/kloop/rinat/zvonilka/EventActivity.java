package kg.kloop.rinat.zvonilka;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import kg.kloop.rinat.zvonilka.data.Event;
import kg.kloop.rinat.zvonilka.data.EventUserStatus;
import kg.kloop.rinat.zvonilka.login.DefaultCallback;


public class EventActivity extends AppCompatActivity {


    Event event;
    String eventId;
    EditText date, city, company, notes, participants, name;
    ImageButton editBtn, saveBtn;
    BackendlessDataQuery querry;
    SimpleDateFormat dateFormat;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        initUI();
        setOnClick();

        eventId = getIntent().getExtras().getString(getResources().getString(R.string.eventIdkey));
        querry = new BackendlessDataQuery();
        querry.setWhereClause(Resources.OBJECTID + " = '" + eventId + "'");
        Log.d("query", eventId);

        Backendless.Persistence.of(Event.class).find(querry, new DefaultCallback<BackendlessCollection<Event>>(this) {
            @Override
            public void handleResponse(BackendlessCollection<Event> eventBackendlessCollection) {
                List<Event> event = eventBackendlessCollection.getData();
                Log.d("Event", event.size() + "");
                for (int i = 0; i < event.size(); i++) {
                    String text;
                    text = Resources.NAME + ": " + event.get(i).getName();
                    name.setText(text);
                    text = Resources.DATE + ": " + dateFormat.format(event.get(i).getDateOfEvent());
                    date.setText(text);
                    text = Resources.CITY + ": " + event.get(i).getCity();
                    city.setText(text);
                    if (event.get(i).getAppCompany_ID_Event() != null) {
                        text = Resources.COMPANY + ": " + event.get(i).getAppCompany_ID_Event().getName();
                        company.setText(text);
                    }
                    text = Resources.DESCRIPTION + ": " + event.get(i).getNote();
                    notes.setText(text);
                    text = "";
                    for (int j = 0; j < event.get(i).getEventUserStatus_ID_Event().size(); j++) {
                        EventUserStatus eventUserStatus;
                        eventUserStatus = event.get(i).getEventUserStatus_ID_Event().get(j);
                        text = eventUserStatus.getUserData_ID_EventUserStatus().getFirstName() +
                                " " + eventUserStatus.getUserData_ID_EventUserStatus().getSecondName() + "\n";
                    }
                    participants.setText(text);

                    super.handleResponse(eventBackendlessCollection);
                }

            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                Log.w("Error", backendlessFault.getMessage() + " " + backendlessFault.getDetail());
                super.handleFault(backendlessFault);
            }
        });
    }

    private void initUI(){
        date = (EditText) findViewById(R.id.eventActivityDate);
        notes = (EditText) findViewById(R.id.eventActivityNotes);
        city = (EditText) findViewById(R.id.eventActivityCity);
        company = (EditText) findViewById(R.id.eventActivityCompany);
        participants = (EditText) findViewById(R.id.eventActivityParticipants);
        name = (EditText) findViewById(R.id.eventActivityName);
        editBtn = (ImageButton) findViewById(R.id.eventActivityEditBtn);
        saveBtn = (ImageButton) findViewById(R.id.eventActivitySaveBtn);

        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

        saveBtn.setEnabled(false);

        participants.setEnabled(false);
        date.setEnabled(false);
        city.setEnabled(false);
        notes.setEnabled(false);
        company.setEnabled(false);
        name.setEnabled(false);
    }

    private void setOnClick(){
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date.setEnabled(true);
                notes.setEnabled(true);
                city.setEnabled(true);
                company.setEnabled(true);
                participants.setEnabled(true);
                name.setEnabled(true);
                saveBtn.setEnabled(true);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                date.setEnabled(false);
                notes.setEnabled(false);
                city.setEnabled(false);
                company.setEnabled(false);
                participants.setEnabled(false);
                name.setEnabled(false);
                saveBtn.setEnabled(false);

                String text;
                text =notes.getText().toString();
                event.setNote(text);
                re
            }
        });
    }


}
