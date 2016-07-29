package kg.kloop.rinat.zvonilka;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
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
    Button search;
    Spinner spinner;
    String searchQuery;
    ListView listView;
    boolean onBackground = false;
    boolean allLoaded[]= new boolean[2];
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initUI();




    }

    private void initUI() {
        editSearchText = (EditText) findViewById(R.id.search_activity_edit_text);
        search = (Button) findViewById(R.id.search_activity_search);
        spinner = (Spinner) findViewById(R.id.search_activity_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.search_list_adapter, Resources.SPINNER_SEARCH);
        spinner.setAdapter(adapter);
        listView = (ListView) findViewById(R.id.search_activity_list);
        progressBar = (ProgressBar) findViewById(R.id.search_activity_progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        allLoaded[0] = false;
        allLoaded[1] = false;

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
            public void handleResponse(final BackendlessCollection<Event> response) {
                List<Event> events = response.getData();
                final EventsAdapter adapterEvents = new EventsAdapter(getApplicationContext(), events);
                Log.d("Data", response.getData().toString() + " " + searchQuery);
                listView.setAdapter(adapterEvents);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(SearchActivity.this, EventActivity.class);
                        Event event = (Event) adapterView.getItemAtPosition(i);
                        intent.putExtra(Resources.EVENT_ID_KEY, event.getObjectId());
                        startActivity(intent);
                    }
                });
                listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView absListView, int i) {

                    }

                    @Override
                    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                        if (!onBackground && !allLoaded[0] && i+i1*2 >= i2){
                            onBackground = true;
                            progressBar.setVisibility(View.VISIBLE);
                            response.getPage(10, i2, new AsyncCallback<BackendlessCollection<Event>>() {
                                @Override
                                public void handleResponse(BackendlessCollection<Event> eventBackendlessCollection) {
                                    adapterEvents.add(eventBackendlessCollection.getData());
                                    adapterEvents.notifyDataSetChanged();
                                    onBackground = false;
                                    progressBar.setVisibility(View.INVISIBLE);
                                    allLoaded[0] = eventBackendlessCollection.getData().size() == 0;
                                }

                                @Override
                                public void handleFault(BackendlessFault backendlessFault) {

                                }
                            });
                        }
                    }
                });
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
            public void handleResponse(final BackendlessCollection<UserData> response) {
                final List<UserData> userDatas = response.getData();
                final UsersDataAdapter userDatasAdapter = new UsersDataAdapter(getApplicationContext(), userDatas);
                Log.d("Data", response.getData().toString() + " " + searchQuery);
                listView.setAdapter(userDatasAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(SearchActivity.this, UserDataActivity.class);
                        UserData userData = (UserData) adapterView.getItemAtPosition(i);
                        Log.d("User ID", userData.getObjectId());
                        intent.putExtra(Resources.USER_DATA_ID_KEY, userData.getObjectId());
                        startActivity(intent);
                    }
                });
                listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView absListView, int i) {

                    }

                    @Override
                    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                        progressBar.setVisibility(View.VISIBLE);
                        if(!onBackground && !allLoaded[0] && i+i1*2 >= i2){
                            onBackground = true;
                            response.getPage(10, i2, new AsyncCallback<BackendlessCollection<UserData>>() {
                                @Override
                                public void handleResponse(BackendlessCollection<UserData> userDataBackendlessCollection) {
                                    userDatasAdapter.add(userDataBackendlessCollection.getData());
                                    userDatasAdapter.notifyDataSetChanged();
                                    onBackground = false;
                                    progressBar.setVisibility(View.INVISIBLE);
                                    allLoaded[0] = userDataBackendlessCollection.getData().size() == 0;
                                }

                                @Override
                                public void handleFault(BackendlessFault backendlessFault) {

                                }
                            });
                        }
                    }
                });
                super.handleResponse(response);
//                while (true){
//                    if()
//                }
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
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(SearchActivity.this, ToDoActivity.class);
                        ToDo toDo = (ToDo) adapterView.getItemAtPosition(i);
                        intent.putExtra(Resources.TODO_ID_KEY, toDo.getObjectId());
                        startActivity(intent);
                    }
                });
                super.handleResponse(response);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                super.handleFault(fault);
            }
        });
    }
}