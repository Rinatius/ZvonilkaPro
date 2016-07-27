package kg.kloop.rinat.zvonilka;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;

import kg.kloop.rinat.zvonilka.data.Event;
import kg.kloop.rinat.zvonilka.login.DefaultCallback;


public class EventActivity extends AppCompatActivity {


    Event event;
    String eventId;
    TextView date;
    TextView city;
    TextView company;
    TextView notes;
    BackendlessDataQuery querry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        initUI();
        eventId = getIntent().getExtras().getString(getResources().getString(R.string.eventIdkey));
        querry = new BackendlessDataQuery();
        querry.setWhereClause(R.string.objectidquerry + eventId + "'");

        Backendless.Persistence.of(Event.class).find(querry, new DefaultCallback<BackendlessCollection<Event>>(this) {
            @Override
            public void handleResponse(BackendlessCollection<Event> eventBackendlessCollection) {
                event = eventBackendlessCollection.getData().get(0);
                String text;
                text = R.string.date + event.getDateOfEvent().toString();
                date.setText(text);
                text = R.string.city + event.getCity();
                city.setText(text);
                text = R.string.company + event.getAppCompany_ID().getName();
                company.setText(text);
                text = R.string.eventDescription + event.getNote();
                notes.setText(text);
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {

            }
        });
    }

    private void initUI(){
        date = (TextView) findViewById(R.id.eventActivityDate);
        notes = (TextView) findViewById(R.id.eventActivityNotes);
        city = (TextView) findViewById(R.id.eventActivityCity);
        company = (TextView) findViewById(R.id.eventActivityCompany);
    }

}
