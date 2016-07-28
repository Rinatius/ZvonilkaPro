package kg.kloop.rinat.zvonilka;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.persistence.BackendlessDataQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kg.kloop.rinat.zvonilka.data.UserData;
import kg.kloop.rinat.zvonilka.login.DefaultCallback;

public class CallActivity extends AppCompatActivity {
    TextView name, date, duration;
    EditText note;
    Spinner eventSpinner;
    String userDataNumber;
    UserData userData;
    Date afterCall, beforeCall;
    List<String> list;
    private static final String TAG = "CallActivityDebug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        initUi();

        userDataNumber = getIntent().getData().toString().substring(4);
        Log.d(TAG, userDataNumber);


        BackendlessDataQuery querry = new BackendlessDataQuery();


        querry.setWhereClause(Resources.PHONE_NUMBER_KEY + " = '" + userDataNumber + "'");
        Backendless.Persistence.of(UserData.class).find(querry, new DefaultCallback<BackendlessCollection<UserData>>(CallActivity.this){
            @Override
            public void handleResponse(BackendlessCollection<UserData> userDataBackendlessCollection) {
                userData = userDataBackendlessCollection.getData().get(0);
                String text;

                text = Resources.NAME + ": " + userData.getFirstName() +" " + userData.getSecondName();
                name.setText(text);

                text = Resources.DATE + ": " + Resources.DATE_FORMAT.format(afterCall);
                date.setText(text);

                list = new ArrayList<String>();
                for (int i = 0; i < userData.getEventUserStatus_ID().size(); i++) {
                    list.add(userData.getEventUserStatus_ID().get(i).getEvent_ID().getName());
                }

                if(!list.isEmpty()) {

                    Log.d(TAG, list.get(0));

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(CallActivity.this, android.R.layout.simple_spinner_dropdown_item, list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    eventSpinner.setAdapter(adapter);
                }
                else{
                    ViewGroup parent = (ViewGroup) eventSpinner.getParent();
                    parent.removeView(eventSpinner);
                }
                super.handleResponse(userDataBackendlessCollection);
            }
        });

    }

    private void getUserData(String userDataID) {

    }

    private void initUi(){
        afterCall = new Date();
        date = (TextView)findViewById(R.id.callActivityCallDate);
        duration = (TextView)findViewById(R.id.callActivityCallDuration);
        name = (TextView)findViewById(R.id.callActivityClientName);
        note = (EditText) findViewById(R.id.callActivityNote);
        eventSpinner = (Spinner)findViewById(R.id.callActivityEventSpinner);

    }

    public void getUserEvents() {

    }
}
