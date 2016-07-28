package kg.kloop.rinat.zvonilka;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;

import java.util.List;

import kg.kloop.rinat.zvonilka.adapters.EventsAdapter;
import kg.kloop.rinat.zvonilka.adapters.ToDoAdapter;
import kg.kloop.rinat.zvonilka.adapters.UsersDataAdapter;
import kg.kloop.rinat.zvonilka.data.Event;
import kg.kloop.rinat.zvonilka.data.ToDo;
import kg.kloop.rinat.zvonilka.data.UserData;
import kg.kloop.rinat.zvonilka.login.DefaultCallback;


public class SearchActivity extends AppCompatActivity {
    EditText editSearchText;
    ImageButton search;
    Spinner spinner;
    String searchQuery;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initUI();




    }

    private void initUI() {
        editSearchText = (EditText) findViewById(R.id.search_activity_edit_text);
        search = (ImageButton) findViewById(R.id.search_activity_search);
        spinner = (Spinner) findViewById(R.id.search_activity_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.search_list_adapter, Resources.SPINNER_SEARCH);
        spinner.setAdapter(adapter);
        listView = (ListView) findViewById(R.id.search_activity_list);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchQuery = spinner.getSelectedItem().toString();
                Log.d("Spinner", searchQuery);
                switch (searchQuery) {
                    case "Users":
                        loadUsers();
                        break;
                    case "Events":
                        loadEvents();
                        break;
                    case "ToDos":
                        loadToDos();
                        break;
                }

            }


        });
    }


    private void loadEvents() {
        final BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        searchQuery = "name LIKE '%" + editSearchText.getText() + "%'";
        dataQuery.setWhereClause(searchQuery);
        Backendless.Persistence.of(Event.class).find(dataQuery, new DefaultCallback<BackendlessCollection<Event>>(SearchActivity.this) {
            @Override
            public void handleResponse(BackendlessCollection<Event> response) {
                List<Event> events = response.getData();
                EventsAdapter adapterEvents = new EventsAdapter(getApplicationContext(), events);
                Log.d("Data", response.getData().toString() + " " + searchQuery);
                listView.setAdapter(adapterEvents);
                super.handleResponse(response);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.d("Load Events error", fault.getMessage());
                super.handleFault(fault);
            }
        });
    }

    private void loadUsers() {
        final BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        searchQuery = "FirstName LIKE '%" + editSearchText.getText() + "%'";
        dataQuery.setWhereClause(searchQuery);

        Backendless.Persistence.of(UserData.class).find(dataQuery, new DefaultCallback<BackendlessCollection<UserData>>(SearchActivity.this) {
            @Override
            public void handleResponse(BackendlessCollection<UserData> response) {
                List<UserData> userDatas = response.getData();
                UsersDataAdapter userDatasAdapter = new UsersDataAdapter(getApplicationContext(), userDatas);
                Log.d("Data", response.getData().toString() + " " + searchQuery);
                listView.setAdapter(userDatasAdapter);
                super.handleResponse(response);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.d("Load Users error", fault.getMessage());
                super.handleFault(fault);
            }
        });
    }
    private void loadToDos() {
        final BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        searchQuery = Resources.NAME + "LIKE '%" + editSearchText.getText() + "%'";
        dataQuery.setWhereClause(searchQuery);
        Backendless.Persistence.of(ToDo.class).find(dataQuery, new DefaultCallback<BackendlessCollection<ToDo>>(SearchActivity.this){
            @Override
            public void handleResponse(BackendlessCollection<ToDo> response) {
                List<ToDo> toDos = response.getData();
                ToDoAdapter toDoAdapter = new ToDoAdapter(getApplicationContext(), toDos);
                Log.d("Data", response.getData().toString() + " " + searchQuery);
                listView.setAdapter(toDoAdapter);
                super.handleResponse(response);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                super.handleFault(fault);
            }
        });
    }
}
