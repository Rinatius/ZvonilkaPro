package kg.kloop.rinat.zvonilka;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

    private static EventsAdapter eventsAdapter;
    private static UsersDataAdapter usersDataAdapter;
    private static ToDoAdapter toDoAdapter;
    private static ListView eventsList;
    private static ListView userDataList;
    private static ListView userToDoList;
    private static OnScrollGetAllList eventScroll;
    private static OnScrollGetAllList userScroll;
    private static OnScrollGetAllList toDoScroll;
    private static FloatingActionButton fab;


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

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuSelectActivity.this, AddUserDataActivity.class);
                intent.putExtra(Resources.ACTION, Resources.ADD_USER_DATA);
                startActivity(intent);
            }
        });

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
            Intent intent = new Intent(MenuSelectActivity.this, SearchActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_add_user_data) {
            Intent intent = new Intent(MenuSelectActivity.this, AddUserDataActivity.class);
            intent.putExtra(Resources.ACTION, Resources.ADD_USER_DATA);
            startActivity(intent);
        } else if (id == R.id.action_add_event) {
            Intent intent = new Intent(MenuSelectActivity.this, AddEventActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_add_company) {
            Intent intent = new Intent(MenuSelectActivity.this, CompanyActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_update) {
            OnRefresh refresh = new OnRefresh(MenuSelectActivity.this, eventsAdapter, Event.class, null);
            refresh.Refresh();
            refresh = new OnRefresh(MenuSelectActivity.this, usersDataAdapter, UserData.class, null);
            refresh.Refresh();
            refresh = new OnRefresh(MenuSelectActivity.this, toDoAdapter, ToDo.class, null);
            refresh.Refresh();
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
            View rootView = inflater.inflate(R.layout.fragment_list, container, false);
            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 0:
                    initEventsFragment(rootView);
//                    rootView.setOnRefreshListener(new OnRefresh(eventsAdapter, Event.class, null));
                    break;
                case 1:
                    initUsersFragment(rootView);
                    break;
                case 2:
                    initToDoFragment(rootView);
                    break;
            }
            return rootView;

        }

        private void initEventsFragment(View view) {
            eventsList = (ListView) view.findViewById(R.id.fragment_list);
            if (eventsAdapter == null)
                eventsAdapter = new EventsAdapter(getContext(), new ArrayList<Event>());
            if (eventScroll == null)
                eventScroll = new OnScrollGetAllList(eventsAdapter, Event.class);
            eventsList.setAdapter(eventsAdapter);
            eventsList.setOnItemClickListener(new OnItemClick(getContext(), EventActivityDemo.class));
            eventsList.setOnScrollListener(eventScroll);
        }

        private void initUsersFragment(View view) {
            userDataList = (ListView) view.findViewById(R.id.fragment_list);
            if (usersDataAdapter == null)
                usersDataAdapter = new UsersDataAdapter(getContext(), new ArrayList<UserData>());
            if (userScroll == null)
                userScroll = new OnScrollGetAllList(usersDataAdapter, UserData.class);
            userDataList.setAdapter(usersDataAdapter);
            userDataList.setOnItemClickListener(new OnItemClick(getContext(), UserDataActivity.class));
            userDataList.setOnScrollListener(userScroll);
        }

        private void initToDoFragment(View view) {
            userToDoList = (ListView) view.findViewById(R.id.fragment_list);
            if (toDoAdapter == null)
                toDoAdapter = new ToDoAdapter(getContext(), new ArrayList<ToDo>());
            if (toDoScroll == null)
                toDoScroll = new OnScrollGetAllList(toDoAdapter, ToDo.class);
            userToDoList.setAdapter(toDoAdapter);
            userToDoList.setOnItemClickListener(new OnItemClick(getContext(), ToDoActivity.class));
            userToDoList.setOnScrollListener(toDoScroll);
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


