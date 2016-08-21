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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.io.BackendlessUserFactory;

import java.util.Date;

import kg.kloop.rinat.zvonilka.data.BackendAction;
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
            new SaveUserData(userData, AddUserDataActivity.this, this).execute();

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

}


class SaveUserData extends AsyncTask<Integer, Integer, Integer> {

    UserData userData;
    Context context;
    AppCompatActivity appCompatActivity;
    ProgressDialog dialog;


    public SaveUserData(UserData userData, Context context, AppCompatActivity appCompatActivity) {
        this.userData = userData;
        this.context = context;
        this.appCompatActivity = appCompatActivity;
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
        userData.save();
        return 0;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        dialog.cancel();
        Toast.makeText(context, R.string.saved, Toast.LENGTH_SHORT).show();
        Log.d("Save", "Saved!");
        if (appCompatActivity != null)
            appCompatActivity.finish();
        super.onPostExecute(integer);
    }
}

