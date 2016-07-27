package kg.kloop.rinat.zvonilka;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;

import java.util.List;

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
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        initUI();
        eventId = getIntent().getExtras().getString(getResources().getString(R.string.eventIdkey));
        querry = new BackendlessDataQuery();
        querry.setWhereClause(getResources().getString(R.string.objectidquerry) + " = '" + eventId + "'");
        Log.d("query", eventId);

        Backendless.Persistence.of(Event.class).find(querry, new DefaultCallback<BackendlessCollection<Event>>(this) {
            @Override
            public void handleResponse(BackendlessCollection<Event> eventBackendlessCollection) {
                List<Event> event = eventBackendlessCollection.getData();
                Log.d("Event", event.size() + "");
                for (int i = 0; i < event.size(); i++) {
                    String text;
                    text = R.string.date + event.get(i).getDateOfEvent().toString();
                    date.setText(text);
                    text = R.string.city + event.get(i).getCity();
                    city.setText(text);
                    if (event.get(i).getAppCompany_ID() != null) {
                        text = R.string.company + event.get(i).getAppCompany_ID().getName();
                        company.setText(text);
                    }
                    text = R.string.eventDescription + event.get(i).getNote();
                    notes.setText(text);
                    super.handleResponse(eventBackendlessCollection);
                }

            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                Log.w("Error", backendlessFault.getMessage() + " " + backendlessFault.getDetail());
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
