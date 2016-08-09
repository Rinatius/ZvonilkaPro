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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
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

    private static ProgressBar progressBar;
    private static EventsAdapter eventsAdapter;
    private static UsersDataAdapter usersDataAdapter;
    private static ToDoAdapter toDoAdapter;
    private static ListView eventsList;
    private static ListView userDataList;
    private static ListView userToDoList;

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
        } else if (id == R.id.action_add_company){
            Intent intent = new Intent(getApplicationContext(), CompanyActivity.class);
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
            eventsList = (ListView) view.findViewById(R.id.select_activity_list_events);
            if (eventsAdapter == null)
                eventsAdapter = new EventsAdapter(getContext(), new ArrayList<Event>());
            eventsList.setAdapter(eventsAdapter);
            eventsList.setOnItemClickListener(new OnItemClick(getContext(), EventActivity.class));

        }

        private void initUsersFragment(View view) {
            userDataList = (ListView) view.findViewById(R.id.select_activity_list_users);
            if (usersDataAdapter == null)
                usersDataAdapter = new UsersDataAdapter(getContext(), new ArrayList<UserData>());
            userDataList.setAdapter(usersDataAdapter);
            userDataList.setOnItemClickListener(new OnItemClick(getContext(), UserDataActivity.class));

        }

        private void initToDoFragment(View view) {
            userToDoList = (ListView) view.findViewById(R.id.select_activity_list_to_do_list);
            if (toDoAdapter == null)
                toDoAdapter = new ToDoAdapter(getContext(), new ArrayList<ToDo>());
            userToDoList.setAdapter(toDoAdapter);
            userToDoList.setOnItemClickListener(new OnItemClick(getContext(), ToDoActivity.class));


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


