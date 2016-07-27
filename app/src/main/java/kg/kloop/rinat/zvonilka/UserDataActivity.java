package kg.kloop.rinat.zvonilka;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;

import java.util.HashSet;

import kg.kloop.rinat.zvonilka.data.Event;
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
    TextView family;
    String userId;
    BackendlessDataQuery querry;
    ImageButton callBtn;
    Uri number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        initUi();
        userId = getIntent().getExtras().getString(getResources().getString(R.string.userDataIdkey));
        querry = new BackendlessDataQuery();
        querry.setWhereClause(getResources().getString(R.string.objectidquerry) + " = '" + userId + "'");

        Backendless.Persistence.of(UserData.class).find(querry, new DefaultCallback<BackendlessCollection<UserData>>(this){
            @Override
            public void handleResponse(BackendlessCollection<UserData> eventBackendlessCollection) {
                userData = eventBackendlessCollection.getData().get(0);
                String text;
                text = getResources().getString(R.string.name) + userData.getFirstName();
                name.setText(text);
                text = getResources().getString(R.string.surname) + userData.getSecondName();
                surname.setText(text);
                text = getResources().getString(R.string.email) + userData.getEmail();
                email.setText(text);
                text = getResources().getString(R.string.phone) + userData.getPhoneNumber();
                phone.setText(text);
                text = getResources().getString(R.string.city) + userData.getCity();
                city.setText(text);
                text = getResources().getString(R.string.address) + userData.getAdress();
                address.setText(text);
                text = getResources().getString(R.string.interests) + userData.getInterests();
                interests.setText(text);
                text = getResources().getString(R.string.bithdaydate) + userData.getBirthday().toString();
                birthday.setText(text);
                text = getResources().getString(R.string.family) + userData.getFamily();
                family.setText(text);

                number = Uri.parse("tel:"+userData.getPhoneNumber());
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {

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
        callBtn = (ImageButton)findViewById(R.id.userDataActivityCallBtn);
    }
}
