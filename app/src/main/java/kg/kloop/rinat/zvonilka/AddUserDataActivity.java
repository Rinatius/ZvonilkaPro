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

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.io.BackendlessUserFactory;
import com.backendless.persistence.BackendlessDataQuery;

import java.util.Date;
import java.util.List;

import kg.kloop.rinat.zvonilka.data.BackendAction;
import kg.kloop.rinat.zvonilka.data.Event;
import kg.kloop.rinat.zvonilka.data.EventUserStatus;
import kg.kloop.rinat.zvonilka.data.UserData;

public class AddUserDataActivity extends AppCompatActivity {

    static EditText firstName;
    static EditText secondName;
    static EditText email;
    static EditText phone;
    static EditText address;
    static EditText city;
    static EditText interests;
    AlertDialog.Builder birthday;
    DatePicker datePicker;
    static EditText family;
    static EditText company;
    static EditText position;
    static TextView birthdayText;
    static Button addEvents;
    static BackendlessDataQuery query;
    String action;
    UserData userData;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        Intent intent = getIntent();
        action = intent.getStringExtra(Resources.ACTION);
        if (action.equals(Resources.UPDATE_USER_DATA)) {
            userId = intent.getStringExtra(Resources.OBJECT_ID);

        }
        initUI();
    }

    private void initUI() {
        firstName = (EditText) findViewById(R.id.activity_user_add_first_name);
        secondName = (EditText) findViewById(R.id.activity_user_add_second_name);
        email = (EditText) findViewById(R.id.activity_user_add_email);
        phone = (EditText) findViewById(R.id.activity_user_add_phone_number);
        city = (EditText) findViewById(R.id.activity_user_add_city);
        address = (EditText) findViewById(R.id.activity_user_add_address);
        interests = (EditText) findViewById(R.id.activity_user_add_interests);
        family = (EditText) findViewById(R.id.activity_user_add_family);
        company = (EditText) findViewById(R.id.activity_user_add_company);
        position = (EditText) findViewById(R.id.activity_user_add_position);
        birthdayText = (TextView) findViewById(R.id.activity_add_user_birthday_text);
        birthdayText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                birthday.show();
            }
        });
        birthday = new AlertDialog.Builder(this);
        datePicker = new DatePicker(this);
        datePicker.setCalendarViewShown(false);
        birthday.setView(datePicker);
        birthday.setTitle(R.string.bithdaydate);
        birthday.setPositiveButton(R.string.set, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String date = datePicker.getDayOfMonth() + "." + datePicker.getMonth() + "." + datePicker.getYear();
                birthdayText.setText(date);
                datePicker = new DatePicker(AddUserDataActivity.this);
                datePicker.setCalendarViewShown(false);
                birthday.setView(datePicker);
            }
        });
        birthday.setNegativeButton(R.string.cancel, null);
        addEvents = (Button) findViewById(R.id.add_events);
        addEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddUserDataActivity.this, UsersList.class);
                startActivityForResult(intent, UsersList.LOAD_EVENTS);
            }
        });

        if (action.equals(Resources.UPDATE_USER_DATA)) {
            new LoadUserData(AddUserDataActivity.this, userId, userData).execute();
        }


    }

    protected void updateFields(UserData userData) {
        this.userData = userData;
        firstName.setText(userData.getFirstName());
        secondName.setText(userData.getSecondName());
        email.setText(userData.getEmail());
        phone.setText(userData.getPhoneNumber());
        city.setText(userData.getCity());
        address.setText(userData.getAdress());
        interests.setText(userData.getInterests());
        family.setText(userData.getFamily());
        company.setText(userData.getCompany());
        position.setText(userData.getPosition());
        if (userData.getBirthday() != null)
            birthdayText.setText(userData.getBirthday().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_call_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        intent.putExtra("requestCode", requestCode);
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            query = new BackendlessDataQuery();
            String whereClause = "";
            for (int i = 0; i < data.getIntExtra("backendlessDatas size", 0); i++) {
                if (i == 0) {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveUserdata();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveUserdata() {
        String firstName = this.firstName.getText().toString();
        String secondName = this.secondName.getText().toString();
        String email = this.email.getText().toString();
        String phoneNumber = this.phone.getText().toString();
        String city = this.city.getText().toString();
        String address = this.address.getText().toString();
        String interests = this.interests.getText().toString();
        String family = this.family.getText().toString();
        String company = this.company.getText().toString();
        String position = this.position.getText().toString();

        if (firstName.equals("") || secondName.equals("") || phoneNumber.equals("")) {
            Toast.makeText(getApplicationContext(), R.string.error_didnt_write_field, Toast.LENGTH_LONG).show();
        } else {
            if (action.equals(Resources.ADD_USER_DATA)) {
                userData = new UserData();
            }
            userData.setFirstName(firstName);
            userData.setSecondName(secondName);
            userData.setEmail(email);
            userData.setPhoneNumber(phoneNumber);
            userData.setCity(city);
            userData.setAdress(address);
            userData.setInterests(interests);
            userData.setFamily(family);
            userData.setCompany(company);
            userData.setPosition(position);
            Log.d("Date", datePicker.getYear() + " " + datePicker.getMonth() + " " + datePicker.getDayOfMonth());
            Date birthday = new Date(datePicker.getYear(),
                    datePicker.getMonth(),
                    datePicker.getDayOfMonth());
            userData.setBirthday(birthday);
            new SaveUserData(userData, AddUserDataActivity.this, query).execute();

        }

    }

    class LoadUserData extends AsyncTask<Long, Long, UserData> {
        String userId;
        UserData userData;
        Context context;
        ProgressDialog dialog;

        public LoadUserData(Context context, String userId, UserData userData) {
            this.userData = userData;
            this.userId = userId;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(context, "", context.getString(R.string.loading), false);
            super.onPreExecute();
        }

        @Override
        protected UserData doInBackground(Long... longs) {
            return Backendless.Persistence.of(UserData.class).findById(userId);
        }

        @Override
        protected void onPostExecute(UserData userData) {
            updateFields(userData);
            dialog.cancel();
            super.onPostExecute(userData);
        }
    }


    public static class SaveUserData extends AsyncTask<Integer, Integer, Integer> {

        UserData userData;
        Context context;
        BackendlessDataQuery query;
        ProgressDialog dialog;


        public SaveUserData(UserData userData, Context context, BackendlessDataQuery query) {
            this.userData = userData;
            this.context = context;
            this.query = query;
        }

        public SaveUserData(UserData userData, Context context) {
            this.userData = userData;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            Log.d("Save", "Saving...");
            dialog = ProgressDialog.show(context, "", context.getString(R.string.saving), true);
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            List<Event> events = BackendAction.getData(Event.class, query);
            for (int i = 0; i < events.size(); i++) {
                EventUserStatus eventUserStatus = new EventUserStatus();
                eventUserStatus.setHasBeen(false);
                eventUserStatus.setStatus("Recorded!");
                eventUserStatus.save();
                Log.d("AddEvent", "EventUserStatus Saved!" + eventUserStatus.getObjectId());

                events.get(i).addEventStatus_ID_Event(eventUserStatus);
                events.get(i).save();
                Log.d("AddEvent", "Event Updated!");
                userData.addEventStatus(eventUserStatus);
            }
            userData.save();
            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            dialog.cancel();
            Toast.makeText(context, R.string.saved, Toast.LENGTH_SHORT).show();
            Log.d("Save", "Saved!");
            super.onPostExecute(integer);
        }
    }

}


