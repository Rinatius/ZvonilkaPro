package kg.kloop.rinat.zvonilka;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import kg.kloop.rinat.zvonilka.data.EventUserStatus;
import kg.kloop.rinat.zvonilka.data.UserData;
import kg.kloop.rinat.zvonilka.login.DefaultCallback;

public class UserDataActivity extends AppCompatActivity {
    UserData userData;
    EditText name;
    EditText surname;
    EditText email;
    EditText phone;
    EditText city;
    EditText address;
    EditText interests;
    EditText birthday;
    EditText company;
    EditText family, position;
    TextView eventListText;

    String userId;
    BackendlessDataQuery querry;
    ImageButton callBtn, sendEmailBtn, editBtn, saveBtn;
    SimpleDateFormat dateFormat;
    List<EventUserStatus> listEvents;

//    Uri number;

    private static final String TAG = "UserDataActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        initUi();
        programmButtons();
        userId = getIntent().getExtras().getString(Resources.USER_DATA_ID_KEY);
        querry = new BackendlessDataQuery();
        querry.setWhereClause(Resources.OBJECTID + " = '" + userId + "'");

        Backendless.Persistence.of(UserData.class).find(querry, new DefaultCallback<BackendlessCollection<UserData>>(this){
            @Override
            public void handleResponse(BackendlessCollection<UserData> eventBackendlessCollection) {
                userData = eventBackendlessCollection.getData().get(0);
                String text;
                text = Resources.NAME + ": " + userData.getFirstName();
                name.setText(text);
                text = Resources.SUR_NAME + ": " + userData.getSecondName();
                surname.setText(text);
                text = Resources.EMAIL + ": " + userData.getEmail();
                email.setText(text);
                text = Resources.PHONE + ": " + userData.getPhoneNumber();
                phone.setText(text);
                text = Resources.CITY + ": " + userData.getCity();
                city.setText(text);
                text = Resources.ADDRESS + ": " + userData.getAdress();
                address.setText(text);
                text = Resources.INTERESTS + ": " + userData.getInterests();
                interests.setText(text);
                text = Resources.BIRTHDAY_DATE + ": " + dateFormat.format(userData.getBirthday()) ;
                birthday.setText(text);
                text = Resources.FAMILY + ": " + userData.getFamily();
                family.setText(text);
                text = Resources.COMPANY + ": " + userData.getCompany();
                company.setText(text);
                text = Resources.POSITION + ": " + userData.getPosition();
                position.setText(text);
                listEvents = userData.getEventUserStatus_ID();
                text = "";
                for (EventUserStatus status: listEvents) {
                    text+= status.getStatus();
                    text+= (status.getHasBeen() ? " " + Resources.PARTICIPATED : " " + Resources.NOT_PARTICIPATED);
                }
                eventListText.setText(text);
                Log.d(TAG, userData.getPhoneNumber());


                super.handleResponse(eventBackendlessCollection);
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                Log.d(TAG, "UserData didn't found: " + backendlessFault.getDetail());
                super.handleFault(backendlessFault);
            }
        });



    }

    private void initUi(){
        name = (EditText)findViewById(R.id.userDataActivityName);
        surname = (EditText)findViewById(R.id.userDataActivitySurname);
        email = (EditText)findViewById(R.id.userDataActivityEmail);
        phone = (EditText)findViewById(R.id.userDataActivityPhone);
        city = (EditText)findViewById(R.id.userDataActivityCity);
        address = (EditText)findViewById(R.id.userDataActivityAdress);
        interests = (EditText)findViewById(R.id.userDataActivityInterests);
        birthday = (EditText)findViewById(R.id.userDataActivityBirthDay);
        family = (EditText)findViewById(R.id.userDataActivityFamily);
        company = (EditText)findViewById(R.id.userDataActivityCompany);
        position = (EditText) findViewById(R.id.userDataActivityPosition);
        eventListText = (TextView) findViewById(R.id.userDataActivityEventList);

        callBtn = (ImageButton)findViewById(R.id.userDataActivityCallBtn);
        sendEmailBtn = (ImageButton)findViewById(R.id.userDataActivitySendEmailBtn);
        editBtn = (ImageButton)findViewById(R.id.userDataActivityEditBtn);
        saveBtn = (ImageButton)findViewById(R.id.userDataActivitySaveBtn);

        callBtn.setEnabled(true);
        callBtn.setClickable(true);

        sendEmailBtn.setEnabled(true);
        sendEmailBtn.setClickable(true);

        name.setEnabled(false);
        surname.setEnabled(false);
        email.setEnabled(false);
        phone.setEnabled(false);
        city.setEnabled(false);
        address.setEnabled(false);
        interests.setEnabled(false);
        birthday.setEnabled(false);
        family.setEnabled(false);
        company.setEnabled(false);
        position.setEnabled(false);
        saveBtn.setEnabled(false);



        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
    }

    private void programmButtons(){
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri number = Uri.parse("tel:" + userData.getPhoneNumber());
                Log.d(TAG, userData.getPhoneNumber());
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
            }
        });
        sendEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto",userData.getEmail(), null));
                startActivity(Intent.createChooser(emailIntent, Resources.SEND_EMAIL ));
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setEnabled(true);
                surname.setEnabled(true);
                email.setEnabled(true);
                phone.setEnabled(true);
                city.setEnabled(true);
                address.setEnabled(true);
                interests.setEnabled(true);
//                birthday.setEnabled(true);
                family.setEnabled(true);
                company.setEnabled(true);
                position.setEnabled(true);
                saveBtn.setEnabled(true);
                editBtn.setEnabled(false);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setEnabled(false);
                surname.setEnabled(false);
                email.setEnabled(false);
                phone.setEnabled(false);
                city.setEnabled(false);
                address.setEnabled(false);
                interests.setEnabled(false);
//                birthday.setEnabled(false);
                family.setEnabled(false);
                company.setEnabled(false);
                position.setEnabled(false);
                saveBtn.setEnabled(false);
                editBtn.setEnabled(true);

                String text;
                text = name.getText().toString().substring(6);
                userData.setFirstName(text);
                text = surname.getText().toString().substring(9);
                userData.setSecondName(text);
                text = email.getText().toString().substring(7);
                userData.setEmail(text);
                text = phone.getText().toString().substring(7);
                userData.setPhoneNumber(text);
                text = city.getText().toString().substring(6);
                userData.setCity(text);
                text = address.getText().toString().substring(10);
                userData.setAdress(text);
//                text = birthday.getText().toString().substring(12);
//                userData.setBirthday(text);
                text = family.getText().toString().substring(9);
                userData.setFirstName(text);
                text = company.getText().toString().substring(10);
                userData.setPosition(text);
                text = position.getText().toString().substring(11);
                userData.setPosition(text);
                Backendless.Persistence.of(UserData.class).save(userData, new DefaultCallback<UserData>(UserDataActivity.this));
            }
        });

    }
}
