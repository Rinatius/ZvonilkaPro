package kg.kloop.rinat.zvonilka;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;


import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;

import java.util.Date;

import kg.kloop.rinat.zvonilka.data.ToDo;
import kg.kloop.rinat.zvonilka.data.UserData;

public class ToDoActivity extends AppCompatActivity {
    int new_year, new_month, new_day;
    TextView deadline, name,note;
    String todoId;
    Date date;
    BackendlessDataQuery querry;
    ToDo toDo;
    UserData userData;
    public static final String TAG = "ToDoActivity";
    static final int DIALOG_ID=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        initUi();
        showDialogOnTextClick();
        todoId = getIntent().getExtras().getString(Resources.todoIdKey);
        querry = new BackendlessDataQuery();
        querry.setWhereClause(Resources.objectId + "'" + todoId + "'");

        Backendless.Persistence.of(ToDo.class).find(querry, new AsyncCallback<BackendlessCollection<ToDo>>() {
            @Override
            public void handleResponse(BackendlessCollection<ToDo> toDoBackendlessCollection) {
                toDo = toDoBackendlessCollection.getData().get(0);
                querry.setWhereClause(Resources.userDataIdKey + "'"+ toDo.getUserData_ID());
                Backendless.Persistence.of(UserData.class).find(querry, new AsyncCallback<BackendlessCollection<UserData>>() {
                    @Override
                    public void handleResponse(BackendlessCollection<UserData> userDataBackendlessCollection) {
                        userData = userDataBackendlessCollection.getData().get(0);
                    }

                    @Override
                    public void handleFault(BackendlessFault backendlessFault) {
                        Log.d(TAG, "UserData didn't recieved: " + backendlessFault.getDetail());
                    }
                });
                String text;
                text = Resources.deadline + ": " + toDo.getDeadline();
                deadline.setText(text);
                text = Resources.name + ": "+ userData.getFirstName() + userData.getSecondName();
                name.setText(text);
                text = Resources.details + ": " + toDo.getNote();
                note.setText(text);
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                Log.d(TAG, "ToDo didn't recieved: " + backendlessFault.getDetail());
            }
        });


    }
    public void showDialogOnTextClick(){
        deadline.setClickable(true);
        deadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog(DIALOG_ID).show();
            }
        });
    }

    protected Dialog createDialog(int id){
        if (id==DIALOG_ID)
            return new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    new_year = i;
                    new_month = i1;
                    new_day = i2;
                    String text = new_day + "/" + new_month + "/" + new_year;
                    deadline.setText(text);
                    toDo.setDeadline(text);
                }
            }, new_year, new_month, new_day);
        return null;
    }

    private void initUi(){
        deadline = (TextView)findViewById(R.id.toDoActivityDeadline);
    }
}

