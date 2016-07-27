package kg.kloop.rinat.zvonilka;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.UserService;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.ArrayList;
import java.util.List;

import kg.kloop.rinat.zvonilka.adapters.SelectActivityAdapterEvents;
import kg.kloop.rinat.zvonilka.adapters.SelectActivityAdapterUserDatas;
import kg.kloop.rinat.zvonilka.data.Event;
import kg.kloop.rinat.zvonilka.data.UserData;
import kg.kloop.rinat.zvonilka.login.DefaultCallback;

public class SelectActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private static SelectActivityAdapterUserDatas userDatasAdapter;
    private static SelectActivityAdapterEvents eventsAdapter;
    private static SearchView searchView;
    static ListView userDataList;
    static ListView eventsList;

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
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        Log.d("Data", "Some message");




//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

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

            switch (getArguments().getInt(ARG_SECTION_NUMBER)){
                case 0:
                    rootView = inflater.inflate(R.layout.fragment_select_events, container, false);
                    initEventsFragment(rootView);
                    break;
                case 1:
                    rootView = inflater.inflate(R.layout.fragment_select_users, container, false);
                    initUsersFragment(rootView);
                    break;
                case 2:
                    rootView = inflater.inflate(R.layout.fragment_select_search, container, false);
                    initSearchFragment(rootView);
                    break;
            }

            return rootView;

        }

//////********* Initial Event List *****************//////////////////
        private void initEventsFragment(View view) {

            eventsList = (ListView) view.findViewById(R.id.select_activity_list_events);

            if(eventsAdapter == null) {

                Backendless.Persistence.of(Event.class).find(new DefaultCallback<BackendlessCollection<Event>>(getContext()) {
                    @Override
                    public void handleResponse(BackendlessCollection<Event> eventBackendlessCollection) {
                        final List<Event> events = eventBackendlessCollection.getData();
                        eventsAdapter = new SelectActivityAdapterEvents(getContext(), events);
                        eventsList.setAdapter(eventsAdapter);

                        Toast.makeText(getContext(), "Events Loaded!", Toast.LENGTH_SHORT).show();

                        Log.d("Events", eventsAdapter.hashCode() + " " + eventsList.hashCode());


                        super.handleResponse(eventBackendlessCollection);
                    }

                    @Override
                    public void handleFault(BackendlessFault backendlessFault) {
                        Log.w("Error", backendlessFault.getMessage());
                    }
                });

            } else {
                eventsList.setAdapter(eventsAdapter);
            }


            eventsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getContext(), EventActivity.class);
                    Event event = (Event) adapterView.getItemAtPosition(i);
                    intent.putExtra(getResources().getString(R.string.eventIdkey), event.getObjectId());
                    startActivity(intent);
                }
            });

        }



//////********* Initial User List *****************//////////////////
        private void initUsersFragment(View view) {

            userDataList = (ListView) view.findViewById(R.id.select_activity_list_users);
            if (userDatasAdapter == null) {
                Backendless.Persistence.of(UserData.class).find(new DefaultCallback<BackendlessCollection<UserData>>(getContext()) {
                    @Override
                    public void handleResponse(final BackendlessCollection<UserData> userDataBackendlessCollection) {
                        List<UserData> userData = userDataBackendlessCollection.getData();
                        userDatasAdapter = new SelectActivityAdapterUserDatas(getContext(), userData);
                        Toast.makeText(getContext(), "Users Loaded!", Toast.LENGTH_SHORT).show();
                        eventsList.setAdapter(eventsAdapter);

                        Log.d("Data", userDatasAdapter.hashCode() + " " + userDataList.hashCode());

                        super.handleResponse(userDataBackendlessCollection);
                    }

                    @Override
                    public void handleFault(BackendlessFault backendlessFault) {
                        Log.w("Error", backendlessFault.getMessage());
                    }
                });
            } else {
                eventsList.setAdapter(userDatasAdapter);
            }
            userDataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.d("Item Click", "clicked" + i);
                    Intent intent = new Intent(getContext(), UserDataActivity.class);
                    UserData userData = (UserData) adapterView.getItemAtPosition(i);
                    intent.putExtra(getResources().getString(R.string.userDataIdkey), userData.getObjectId());
                    startActivity(intent);
                }
            });
        }


/////********** Initial Search Fragment ************/////////////////
        private void initSearchFragment(View view) {
            if(searchView == null){
                searchView = (SearchView) view.findViewById(R.id.select_activity_search);
//                searchView.setq
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
                    return getResources().getString(R.string.fragment_select_search);
            }
            return null;
        }
    }
}
