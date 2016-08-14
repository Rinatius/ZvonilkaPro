package kg.kloop.rinat.zvonilka;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import kg.kloop.rinat.zvonilka.data.Event;
import kg.kloop.rinat.zvonilka.data.BackendAction;

public class AddEventActivity extends AppCompatActivity {

    EditText name;
    EditText city;
    EditText notes;
    TextView date;
    AlertDialog.Builder builder;
    DatePicker datePicker;


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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_call_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
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
        if (name.equals("") || city.equals("")){
            Toast.makeText(this, R.string.write_name_city, Toast.LENGTH_LONG).show();
        } else {
            Event event = new Event();
            event.setName(name);
            event.setCity(city);
            event.setNote(notes);
            Date date = new Date(datePicker.getYear()-2000, datePicker.getMonth(), datePicker.getDayOfMonth());
            event.setDateOfEvent(date);
            new SaveEvent(this, event).execute();
        }
    }
}

class SaveEvent extends AsyncTask<Integer, Integer, Integer>{
    Context context;
    Event event;
    ProgressDialog progressDialog;
    public SaveEvent(Context context, Event event) {
        this.context = context;
        this.event = event;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(context, "", context.getString(R.string.saving), true);
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(Integer... integers) {
        BackendAction.saveData(Event.class, event);
        return null;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        progressDialog.cancel();
        Toast.makeText(context, R.string.saved, Toast.LENGTH_LONG).show();
        super.onPostExecute(integer);
    }
}
