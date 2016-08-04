package kg.kloop.rinat.zvonilka;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;

import java.util.ArrayList;
import java.util.List;

import kg.kloop.rinat.zvonilka.adapters.BaseListAdapter;
import kg.kloop.rinat.zvonilka.adapters.EventsAdapter;
import kg.kloop.rinat.zvonilka.adapters.ToDoAdapter;
import kg.kloop.rinat.zvonilka.adapters.UsersDataAdapter;
import kg.kloop.rinat.zvonilka.data.Event;
import kg.kloop.rinat.zvonilka.data.ToDo;
import kg.kloop.rinat.zvonilka.data.UserData;

public class MenuSelectActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private static boolean onBackground = false;
    static boolean[] allLoaded = new boolean[]{false, false, false};
    private static ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        /*
      The {@link ViewPager} that will host the section contents.
     */
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(1);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar_load_background);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_search) {
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_add_user_data) {
            Intent intent = new Intent(getApplicationContext(), AddUserDataActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_add_event) {
            Intent intent = new Intent(getApplicationContext(), AddEventActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = null;

            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 0:
                    rootView = inflater.inflate(R.layout.fragment_select_events, container, false);
                    initEventsFragment(rootView);
                    break;
                case 1:
                    rootView = inflater.inflate(R.layout.fragment_select_users, container, false);
                    initUsersFragment(rootView);
                    break;
                case 2:
                    rootView = inflater.inflate(R.layout.fragment_select_to_do, container, false);
                    initToDoFragment(rootView);
            }

            return rootView;

        }

        private void initEventsFragment(View view) {
            ListView eventsList = (ListView) view.findViewById(R.id.select_activity_list_events);
            EventsAdapter eventsAdapter = new EventsAdapter(getContext(), new ArrayList<Event>());
            eventsList.setAdapter(eventsAdapter);
            eventsList.setOnScrollListener(new OnScroll(eventsAdapter, Event.class, 0));
        }


        private void initUsersFragment(View view){
            ListView userDataList = (ListView) view.findViewById(R.id.select_activity_list_users);
            UsersDataAdapter usersDataAdapter = new UsersDataAdapter(getContext(), new ArrayList<UserData>());
            userDataList.setAdapter(usersDataAdapter);
            userDataList.setOnScrollListener(new OnScroll(usersDataAdapter, UserData.class, 1));
        }

        private void initToDoFragment(View view){
            ListView userToDoList = (ListView) view.findViewById(R.id.select_activity_list_to_do_list);
            ToDoAdapter toDoAdapter = new ToDoAdapter(getContext(), new ArrayList<ToDo>());
            userToDoList.setAdapter(toDoAdapter);
            userToDoList.setOnScrollListener(new OnScroll(toDoAdapter, ToDo.class, 2));
        }

        public static void updateState(boolean bool){
            onBackground = bool;
            if (bool){
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    }

    static class OnScroll implements AbsListView.OnScrollListener {
        BaseListAdapter adapter;
        Class type;
        int number;

        public OnScroll(BaseListAdapter adapter, Class type, int number) {
            this.adapter = adapter;
            this.type = type;
            this.number = number;
        }

        @Override
        public void onScrollStateChanged(AbsListView absListView, int i) {

        }

        @Override
        public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            if (!onBackground && !allLoaded[number] && (i + i1 * 2 >= i2 || i2 == 0)) {
                LoadData loadData = new LoadData(i1, i2, type, adapter);
                loadData.execute();
            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getString(R.string.fragment_select_events);
                case 1:
                    return getResources().getString(R.string.fragment_select_users);
                case 2:
                    return getResources().getString(R.string.fragment_select_to_do);
            }
            return null;
        }

    }
}



class LoadData extends AsyncTask<Integer, Integer, List> {
    int offset, pageSize;
    Class type;
    BaseListAdapter adapter;

    public LoadData(int pageSize, int offset, Class type, BaseListAdapter adapter) {
        this.offset = offset;
        this.pageSize = pageSize;
        this.type = type;
        this.adapter = adapter;
    }

    @Override
    protected void onPreExecute() {
        MenuSelectActivity.PlaceholderFragment.updateState(true);
        super.onPreExecute();
    }

    @Override
    protected List doInBackground(Integer... integers) {
        Log.d("Load on Async", "Loading... " + type.getSimpleName());
        BackendlessDataQuery dataQuery = new BackendlessDataQuery(new QueryOptions(pageSize, offset));
        return GetBackendlessData.getData(type,dataQuery);
    }

    @Override
    protected void onPostExecute(List list) {
        switch (type.getSimpleName()){
            case "Event":
                MenuSelectActivity.allLoaded[0] = list.size() == 0;
                break;
            case "UserData":
                MenuSelectActivity.allLoaded[1] = list.size() == 0;
                break;
            case "ToDo":
                MenuSelectActivity.allLoaded[2] = list.size() == 0;
                break;
        }
        adapter.add(list);
        adapter.notifyDataSetChanged();
        MenuSelectActivity.PlaceholderFragment.updateState(false);
        Log.d("Load on Async", "Loaded! " + type.getSimpleName());
        super.onPostExecute(list);
    }
}
