package kg.kloop.rinat.zvonilka;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import kg.kloop.rinat.zvonilka.data.Event;
import kg.kloop.rinat.zvonilka.data.ToDo;
import kg.kloop.rinat.zvonilka.data.UserData;
import kg.kloop.rinat.zvonilka.login.DefaultCallback;

public class ToDoActivity extends AppCompatActivity {
    int new_year, new_month, new_day;
    TextView deadline, name, note, eventName;
    String todoId;
    Date date;
    BackendlessDataQuery querry;
    ToDo toDo;
    UserData userData;
    Event event;
    Intent toUserDataActivity;
    Intent toEventActivity;
    SimpleDateFormat dateFormat;
    public static final String TAG = "ToDoActivity";
    static final int DIALOG_ID=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        toUserDataActivity = new Intent(this, UserDataActivity.class);
        toEventActivity = new Intent(this, EventActivity.class);
        initUi();
//        showDialogOnTextClick();
        programmButtons();

        todoId = getIntent().getExtras().getString(Resources.TODO_ID_KEY);
        querry = new BackendlessDataQuery();
        querry.setWhereClause(Resources.OBJECT_ID + "'" + todoId + "'");

        Backendless.Persistence.of(ToDo.class).find(querry, new DefaultCallback<BackendlessCollection<ToDo>>(this){
            @Override
            public void handleResponse(BackendlessCollection<ToDo> toDoBackendlessCollection) {
                toDo = toDoBackendlessCollection.getData().get(0);
//                userData = toDo.getUserData_ID_ToDo();
                String text;
                text = Resources.DEADLINE + ": " + toDo.getDeadline();
                deadline.setText(text);
                text = Resources.NAME + ": "+ userData.getFirstName() + userData.getSecondName();
                name.setText(text);
                text = Resources.DETAILS + ": " + toDo.getNote();
                note.setText(text);
                text = Resources.EVENT + ": " + event.getName();
                eventName.setText(text);
                super.handleResponse(toDoBackendlessCollection);
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                Log.d(TAG, "ToDo didn't recieved: " + backendlessFault.getDetail());
                super.handleFault(backendlessFault);
            }
        });

    }


  /*  private void showDialogOnTextClick(){
        DEADLINE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog(DIALOG_ID).show();
            }
        });
    }
*/


  /*  protected Dialog createDialog(int id){
        if (id==DIALOG_ID)
            return new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    new_year = i;
                    new_month = i1;
                    new_day = i2;
                    String text = new_day + "/" + new_month + "/" + new_year;
                    DEADLINE.setText(text);
                    toDo.setDeadline(text);
                }
            }, new_year, new_month, new_day);
        toDo.setDeadline( new_day + "/" + new_month + "/" + new_year);
        Backendless.Persistence.of(ToDo.class).save(toDo, new DefaultCallback<ToDo>(this)
        {
            @Override
            public void handleFault(BackendlessFault backendlessFault){
                Log.d(TAG, "Save failed " + backendlessFault.getDetail());
            }
        });
        return null;
    }
*/
    private void initUi(){
        deadline = (TextView)findViewById(R.id.toDoActivityDeadline);
        name = (TextView)findViewById(R.id.toDoActivityUserName);
        eventName = (TextView)findViewById(R.id.toDoActivityEventName);
        note = (TextView)findViewById(R.id.toDoActivityNote);
        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

        deadline.setClickable(true);
        name.setClickable(true);
        eventName.setClickable(true);
    }

    private void programmButtons(){
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toUserDataActivity.putExtra(Resources.USER_ID_KEY, userData.getObjectId());
                startActivity(toUserDataActivity);
            }
        });
        eventName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toEventActivity.putExtra(Resources.EVENT_ID_KEY, event.getObjectId());
                startActivity(toEventActivity);
            }
        });
    }

}

