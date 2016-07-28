package kg.kloop.rinat.zvonilka;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

    int editCount = 0;

    String userId;
    BackendlessDataQuery querry;
    ImageButton callBtn, sendEmailBtn;
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
        querry.setWhereClause(Resources.OBJECT_ID + " = '" + userId + "'");

        Backendless.Persistence.of(UserData.class).find(querry, new DefaultCallback<BackendlessCollection<UserData>>(this){
            @Override
            public void handleResponse(BackendlessCollection<UserData> userDataBackendlessCollection) {
                userData = userDataBackendlessCollection.getData().get(0);
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
//                text = Resources.BIRTHDAY_DATE + ": " + Resources.DATE_FORMAT.format(userData.getBirthday()) ;
//                birthday.setText(text);
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
//                Log.d(TAG, userData.getPhoneNumber());


                super.handleResponse(userDataBackendlessCollection);
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                Log.d(TAG, "UserData didn't found: " + backendlessFault.getDetail());
                super.handleFault(backendlessFault);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_data_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_edit:
                if(editCount==0) {
                    name.setEnabled(true);
                    surname.setEnabled(true);
                    email.setEnabled(true);
                    phone.setEnabled(true);
                    city.setEnabled(true);
                    address.setEnabled(true);
                    interests.setEnabled(true);
//            birthday.setEnabled(true);
                    family.setEnabled(true);
                    company.setEnabled(true);
                    position.setEnabled(true);
                    item.setIcon(getResources().getDrawable(android.R.drawable.ic_menu_save));
                    editCount++;
                }
                else{
                    name.setEnabled(false);
                    surname.setEnabled(false);
                    email.setEnabled(false);
                    phone.setEnabled(false);
                    city.setEnabled(false);
                    address.setEnabled(false);
                    interests.setEnabled(false);
//            birthday.setEnabled(false);
                    family.setEnabled(false);
                    company.setEnabled(false);
                    position.setEnabled(false);
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
                    text = address.getText().toString().substring(9);
                    userData.setAdress(text);
//                text = birthday.getText().toString().substring(12);
//                userData.setBirthday(text);
                    text = family.getText().toString().substring(8);
                    userData.setFamily(text);
                    text = company.getText().toString().substring(9);
                    userData.setCompany(text);
                    text = position.getText().toString().substring(10);
                    userData.setPosition(text);
                    Backendless.Persistence.of(UserData.class).save(userData, new DefaultCallback<UserData>(UserDataActivity.this));
                    item.setIcon(getResources().getDrawable(android.R.drawable.ic_menu_edit));
                    editCount--;
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
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

    }

    private void programmButtons(){
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri number = Uri.parse("tel:" + userData.getPhoneNumber());
                Intent callIntent;
                callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
                callIntent.setClass(getApplicationContext(), CallActivity.class);
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

    }
}
