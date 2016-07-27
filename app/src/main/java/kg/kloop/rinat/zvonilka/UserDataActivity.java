package kg.kloop.rinat.zvonilka;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import kg.kloop.rinat.zvonilka.data.Event;
import kg.kloop.rinat.zvonilka.data.EventUserStatus;
import kg.kloop.rinat.zvonilka.data.UserData;
import kg.kloop.rinat.zvonilka.login.DefaultCallback;

public class UserDataActivity extends AppCompatActivity {
    UserData userData;
    TextView name;
    TextView surname;
    TextView email;
    TextView phone;
    TextView city;
    TextView address;
    TextView interests;
    TextView birthday;
    TextView family, company, position, eventListText;
    String userId;
    BackendlessDataQuery querry;
    ImageButton callBtn;
    SimpleDateFormat dateFormat;
    List<EventUserStatus> listEvents;

    Uri number;

    private static final String TAG = "UserDataActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        initUi();
        userId = getIntent().getExtras().getString(Resources.userDataIdKey);
        querry = new BackendlessDataQuery();
        querry.setWhereClause(Resources.objectId + " = '" + userId + "'");

        Backendless.Persistence.of(UserData.class).find(querry, new DefaultCallback<BackendlessCollection<UserData>>(this){
            @Override
            public void handleResponse(BackendlessCollection<UserData> eventBackendlessCollection) {
                userData = eventBackendlessCollection.getData().get(0);
                String text;
                text = Resources.name + userData.getFirstName();
                name.setText(text);
                text = Resources.surName + ": " + userData.getSecondName();
                surname.setText(text);
                text = Resources.email + ": " + userData.getEmail();
                email.setText(text);
                text = Resources.phone + userData.getPhoneNumber();
                phone.setText(text);
                text = Resources.city + ": " + userData.getCity();
                city.setText(text);
                text = Resources.address + ": " + userData.getAdress();
                address.setText(text);
                text = Resources.interest + ": " + userData.getInterests();
                interests.setText(text);
                text = Resources.birthday + ": " + dateFormat.format(userData.getBirthday()) ;
                birthday.setText(text);
                text = Resources.family + ": " + userData.getFamily();
                family.setText(text);
                text = Resources.company + ": " + userData.getCompany();
                company.setText(text);
                text = Resources.position + ": " + userData.getPosition();
                position.setText(text);
                listEvents = userData.getEventUserStatus_ID();
                text = "";
                for (EventUserStatus status: listEvents) {
                    text+= status.getStatus();
                    text+= (status.getHasBeen() ? " " + Resources.participated : " " + Resources.notparticipated);
                }
                eventListText.setText(text);

                number = Uri.parse("tel:" + userData.getPhoneNumber());
                super.handleResponse(eventBackendlessCollection);
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                Log.d(TAG, "UserData didn't found: " + backendlessFault.getDetail());
                super.handleFault(backendlessFault);
            }
        });

        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
            }
        });

    }

    private void initUi(){
        name = (TextView)findViewById(R.id.userDataActivityName);
        surname = (TextView)findViewById(R.id.userDataActivitySurname);
        email = (TextView)findViewById(R.id.userDataActivityEmail);
        phone = (TextView)findViewById(R.id.userDataActivityPhone);
        city = (TextView)findViewById(R.id.userDataActivityCity);
        address = (TextView)findViewById(R.id.userDataActivityAdress);
        interests = (TextView)findViewById(R.id.userDataActivityInterests);
        birthday = (TextView)findViewById(R.id.userDataActivityBirthDay);
        family = (TextView)findViewById(R.id.userDataActivityFamily);
        company = (TextView)findViewById(R.id.eventActivityCompany);
        position = (TextView) findViewById(R.id.userDataActivityPosition);
        eventListText = (TextView) findViewById(R.id.userDataActivityEventList);

        callBtn = (ImageButton)findViewById(R.id.userDataActivityCallBtn);

        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
    }
}
