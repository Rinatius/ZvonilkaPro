package kg.kloop.rinat.zvonilka;

import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.persistence.BackendlessDataQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kg.kloop.rinat.zvonilka.data.Event;
import kg.kloop.rinat.zvonilka.data.BackendAction;
import kg.kloop.rinat.zvonilka.data.EventUserStatus;
import kg.kloop.rinat.zvonilka.data.UserData;

public class AddEventActivity extends AppCompatActivity {

    EditText name;
    EditText city;
    EditText notes;
    TextView date;
    AlertDialog.Builder builder;
    DatePicker datePicker;
    BackendlessDataQuery query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        name = (EditText) findViewById(R.id.activity_add_event_name);
        city = (EditText) findViewById(R.id.activity_add_event_city);
        notes = (EditText) findViewById(R.id.activity_add_event_note);
        date = (TextView) findViewById(R.id.activity_add_event_date);
        builder = new AlertDialog.Builder(this);
        builder.setNegativeButton(R.string.cancel, null);
        builder.setPositiveButton(R.string.set, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String dateStr = datePicker.getDayOfMonth() + "."
                        + datePicker.getMonth() + "."
                        + datePicker.getYear();
                date.setText(dateStr);
            }
        });

        datePicker = new DatePicker(AddEventActivity.this);
        datePicker.setCalendarViewShown(false);
        builder.setView(datePicker);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.show();
                datePicker = new DatePicker(AddEventActivity.this);
                datePicker.setCalendarViewShown(false);
                builder.setView(datePicker);
            }
        });

        Button addClients = (Button) findViewById(R.id.add_clients);
        addClients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEventActivity.this, UsersList.class);
                startActivityForResult(intent, UsersList.LOAD_USERS);
            }
        });

    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        intent.putExtra("requestCode", requestCode);
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            query = new BackendlessDataQuery();
            String whereClause = "";
            for (int i = 0; i < data.getIntExtra("backendlessDatas size", 0); i++) {
                if (i == 0){
                    whereClause = Resources.OBJECT_ID + " = '" + data.getStringExtra("Checked_" + i) + "'";
                } else {
                    whereClause = whereClause.concat(" or " + Resources.OBJECT_ID + " = '" + data.getStringExtra("Checked_" + i) + "'");
                }
            }
            Log.d("WhereClause", whereClause);
            query.setWhereClause(whereClause);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_call_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveEvent();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveEvent() {
        String name = this.name.getText().toString();
        String city = this.city.getText().toString();
        String notes = this.notes.getText().toString();
        if (name.equals("") || city.equals("")) {
            Toast.makeText(this, R.string.write_name_city, Toast.LENGTH_LONG).show();
        } else {
            Event event = new Event();
            event.setName(name);
            event.setCity(city);
            event.setNote(notes);
            Date date = new Date(datePicker.getYear() - 2000, datePicker.getMonth(), datePicker.getDayOfMonth());
            event.setDateOfEvent(date);
            new SaveEvent(this, event, query).execute();
        }
    }

    class SaveEvent extends AsyncTask<Integer, Integer, Integer> {
        Context context;
        Event event;
        BackendlessDataQuery query;
        ProgressDialog progressDialog;

        public SaveEvent(Context context, Event event, BackendlessDataQuery query) {
            this.context = context;
            this.event = event;
            this.query = query;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(context, "", context.getString(R.string.saving), true);
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            List<UserData> userDatas = BackendAction.getData(UserData.class, query);
            for (int i = 0; i < userDatas.size(); i++) {
                EventUserStatus eventUserStatus = new EventUserStatus();
                eventUserStatus.setHasBeen(false);
                eventUserStatus.setStatus("Recorded!");
                eventUserStatus.save();
                Log.d("AddEvent", "EventUserStatus Saved!" + eventUserStatus.getObjectId());

                userDatas.get(i).addEventStatus(eventUserStatus);
                userDatas.get(i).save();
                Log.d("AddEvent", "UserData Updated!");
                event.addEventStatus_ID_Event(eventUserStatus);
            }
            BackendAction.saveData(Event.class, event);
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            progressDialog.cancel();
            Toast.makeText(context, R.string.saved, Toast.LENGTH_LONG).show();
            finish();
            super.onPostExecute(integer);
        }
    }
}


