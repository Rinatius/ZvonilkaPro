package kg.kloop.rinat.zvonilka;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;

import java.util.ArrayList;
import java.util.List;

import kg.kloop.rinat.zvonilka.data.BackendAction;
import kg.kloop.rinat.zvonilka.data.BackendlessData;
import kg.kloop.rinat.zvonilka.data.Event;
import kg.kloop.rinat.zvonilka.data.UserData;

public class UsersList extends AppCompatActivity {

    public static final int LOAD_USERS = 0;
    public static final int LOAD_EVENTS = 1;

    List<BackendlessData> backendlessDatas = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list);

        Log.d(this.getClass().getSimpleName(), getIntent().getIntExtra("requestCode", -1) + "");

        switch (getIntent().getIntExtra("requestCode", -1)) {
            case LOAD_USERS:
                new LoadEvents(UserData.class).execute();
                break;
            case LOAD_EVENTS:
                new LoadEvents(Event.class).execute();
                break;
        }

        listView = (ListView) findViewById(R.id.fragment_list);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_events_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        BaseAdapter adapter = (BaseAdapter) listView.getAdapter();
        SparseBooleanArray array = listView.getCheckedItemPositions();
        Log.d("Users List Checked", array.toString() + " " + array.size() + " " + adapter.getCount());
        intent.putExtra("backendlessDatas size", array.size());
        for (int i = 0; i < array.size(); i++) {
            int j = array.keyAt(i);
            Log.d("Users List", j + "");
            intent.putExtra("Checked_" + i, backendlessDatas.get(j).getObjectId());
            Log.d("Users List", backendlessDatas.get(j).getObjectId());
        }
        setResult(RESULT_OK, intent);
        finish();
        return super.onOptionsItemSelected(item);
    }

    class LoadEvents extends AsyncTask<Long, Long, Long> {
        Class type;
        ProgressDialog dialog;

        public LoadEvents(Class type) {
            this.type = type;
        }

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(UsersList.this, "", getString(R.string.loading), false);
        }

        @Override
        protected Long doInBackground(Long... longs) {
            BackendlessDataQuery query = new BackendlessDataQuery(new QueryOptions(50, 0));
            backendlessDatas.addAll(BackendAction.getData(type, query));
            for (int i = 0; i < backendlessDatas.size(); i++) {
                if (type == UserData.class) {
                    UserData userData = (UserData) backendlessDatas.get(i);
                    names.add(userData.getFirstName() + " " + userData.getSecondName());
                } else {
                    Event event = (Event) backendlessDatas.get(i);
                    names.add(event.getName());
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            listView.setAdapter(new ArrayAdapter<>(UsersList.this, android.R.layout.simple_list_item_multiple_choice, names));
            dialog.cancel();
            super.onPostExecute(aLong);
        }
    }
}
