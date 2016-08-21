package kg.kloop.rinat.zvonilka;

import android.app.ProgressDialog;
import android.content.Context;
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

import android.widget.ListView;
import android.widget.TextView;

import com.backendless.persistence.BackendlessDataQuery;

import java.util.ArrayList;
import java.util.List;

import kg.kloop.rinat.zvonilka.adapters.UsersDataAdapter;
import kg.kloop.rinat.zvonilka.data.BackendAction;
import kg.kloop.rinat.zvonilka.data.Event;
import kg.kloop.rinat.zvonilka.data.EventUserStatus;
import kg.kloop.rinat.zvonilka.data.UserData;

public class EventActivityDemo extends AppCompatActivity {

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
    private static UsersDataAdapter usersDataAdapter;
    private static ListView userList;
    private static String eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_activity_demo);

        eventId = getIntent().getExtras().getString(Resources.OBJECT_ID);
        usersDataAdapter = new UsersDataAdapter(EventActivityDemo.this, new ArrayList<UserData>());
        LoadEvent loadEvent = new LoadEvent(EventActivityDemo.this, usersDataAdapter, eventId);
        loadEvent.execute();

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

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_activity_demo, menu);
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
        static TextView name;
        static TextView date;
        static TextView city;
        static TextView notes;


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
                    rootView = inflater.inflate(R.layout.fragment_event_activity_demo, container, false);
                    initEventFragment(rootView);
                    break;
                case 1:
                    rootView = inflater.inflate(R.layout.fragment_list, container, false);
                    initUserListFragment(rootView);
                    break;
            }
            return rootView;
        }

        private void initEventFragment(View rootView) {
            name = (TextView) rootView.findViewById(R.id.event_activity_name);
            date = (TextView) rootView.findViewById(R.id.date);
            city = (TextView) rootView.findViewById(R.id.city);
            notes = (TextView) rootView.findViewById(R.id.notes);
        }

        private void initUserListFragment(View rootView) {
            userList = (ListView) rootView.findViewById(R.id.fragment_list);
            userList.setAdapter(usersDataAdapter);
            userList.setOnItemClickListener(new OnItemClick(getContext(), UserDataActivity.class));
        }

        protected static void updateEvent(Event event){
            name.setText(event.getName());
            city.setText(event.getCity());
            notes.setText(event.getNote());
            if (event.getDateOfEvent() != null)
                date.setText(event.getDateOfEvent().toString());
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
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.event);
                case 1:
                    return getString(R.string.users);
            }
            return null;
        }
    }

    static class LoadEvent extends AsyncTask<Long, Long, Long> {
        Context context;
        String eventId;
        Event event;
        UsersDataAdapter usersDataAdapter;
        ProgressDialog dialog;

        public LoadEvent(Context context, UsersDataAdapter usersDataAdapter, String eventId) {
            this.context = context;
            this.eventId = eventId;
            this.usersDataAdapter = usersDataAdapter;
        }

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(context, "", context.getString(R.string.loading), false);
            super.onPreExecute();
        }

        @Override
        protected Long doInBackground(Long... longs) {
            BackendlessDataQuery query = new BackendlessDataQuery(Resources.OBJECT_ID + " ='" + eventId + "'");
            Log.d("WhereClause", query.getWhereClause());
            event = (Event) BackendAction.getData(Event.class, query).get(0);
            List<EventUserStatus> eventUserStatuses = event.getEventUserStatus_ID_Event();
            for (int i = 0; i < eventUserStatuses.size(); i++) {
                query = new BackendlessDataQuery(Resources.EVENT_USER_STATUS_ID_OBJECTID
                        + " = '" + eventUserStatuses.get(i).getObjectId() + "'");
                usersDataAdapter.add(BackendAction.getData(UserData.class, query));
                Log.d("UsersDataAdapter", usersDataAdapter.getCount() + "");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            PlaceholderFragment.updateEvent(event);
            usersDataAdapter.notifyDataSetChanged();
            dialog.cancel();
            super.onPostExecute(aLong);
        }
    }
}
