package kg.kloop.rinat.zvonilka;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import kg.kloop.rinat.zvonilka.data.UserData;

public class CallActivityDemo extends AppCompatActivity {

    UserData userData;
    String userId;
    String phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_activity_demo);

        Intent intent = getIntent();
        userId = intent.getStringExtra(Resources.OBJECT_ID);
        phoneNumber = intent.getStringExtra(Resources.PHONE_NUMBER_KEY);
        Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        startActivity(call);
    }

}
