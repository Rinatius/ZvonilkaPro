package kg.kloop.rinat.zvonilka;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
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

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.persistence.BackendlessDataQuery;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kg.kloop.rinat.zvonilka.adapters.BaseListAdapter;
import kg.kloop.rinat.zvonilka.adapters.CallAdapter;
import kg.kloop.rinat.zvonilka.adapters.EventsAdapter;
import kg.kloop.rinat.zvonilka.adapters.ToDoAdapter;
import kg.kloop.rinat.zvonilka.data.BackendAction;
import kg.kloop.rinat.zvonilka.data.Call;
import kg.kloop.rinat.zvonilka.data.Event;
import kg.kloop.rinat.zvonilka.data.ToDo;
import kg.kloop.rinat.zvonilka.data.UserData;

public class UserDataActivityDemo extends AppCompatActivity {

    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private static ToDoAdapter toDoAdapter;
    private static ListView toDoList;
    private static EventsAdapter eventsAdapter;
    private static ListView eventsList;
    private static CallAdapter callAdapter;
    private static ListView callListView;
    static String userId;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data_activity_demo);

        userId = getIntent().getStringExtra(Resources.OBJECT_ID);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(1);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_data_activity_demo, menu);
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

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "UserDataActivityDemo Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://kg.kloop.rinat.zvonilka/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "UserDataActivityDemo Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://kg.kloop.rinat.zvonilka/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        static EditText firstName;
        static EditText secondName;
        static EditText email;
        static EditText phone;
        static EditText address;
        static EditText city;
        static EditText interests;
        AlertDialog.Builder birthday;
        DatePicker datePicker;
        static EditText family;
        static EditText company;
        static EditText position;
        static TextView birthdayText;
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
                    initToDo(rootView);
                    break;
                case 1:
                    rootView = inflater.inflate(R.layout.activity_add_user, container, false);
                    initUserData(rootView);
                    break;
                case 2:
                    initHistory(rootView);
                    break;
                case 3:
                    initEvents(rootView);
                    break;
            }
            return rootView;
        }


        private void initToDo(View rootView) {
            toDoList = (ListView) rootView.findViewById(R.id.fragment_list);
            if (toDoAdapter == null) {
                toDoAdapter = new ToDoAdapter(getContext(), new ArrayList<ToDo>());
            }
            toDoList.setAdapter(toDoAdapter);
            toDoList.setOnItemClickListener(new OnItemClick(getContext(), ToDoActivity.class));
            toDoList.setOnScrollListener(new OnScrollGetAllList(toDoAdapter, ToDo.class));
        }

        private void initUserData(View rootView) {
            new LoadUserData(getContext(), userId, callAdapter).execute();
            firstName = (EditText) rootView.findViewById(R.id.activity_user_add_first_name);
            secondName = (EditText) rootView.findViewById(R.id.activity_user_add_second_name);
            email = (EditText) rootView.findViewById(R.id.activity_user_add_email);
            phone = (EditText) rootView.findViewById(R.id.activity_user_add_phone_number);
            city = (EditText) rootView.findViewById(R.id.activity_user_add_city);
            address = (EditText) rootView.findViewById(R.id.activity_user_add_address);
            interests = (EditText) rootView.findViewById(R.id.activity_user_add_interests);
            family = (EditText) rootView.findViewById(R.id.activity_user_add_family);
            company = (EditText) rootView.findViewById(R.id.activity_user_add_company);
            position = (EditText) rootView.findViewById(R.id.activity_user_add_position);
            birthdayText = (TextView) rootView.findViewById(R.id.activity_add_user_birthday_text);
            birthday = new AlertDialog.Builder(getContext());
            datePicker = new DatePicker(getContext());
            datePicker.setCalendarViewShown(false);
            birthday.setView(datePicker);
            birthday.setTitle(R.string.bithdaydate);
            birthday.setPositiveButton(R.string.set, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String date = datePicker.getDayOfMonth() + "." + datePicker.getMonth() + "." + datePicker.getYear();
                    birthdayText.setText(date);
                    datePicker = new DatePicker(getContext());
                    datePicker.setCalendarViewShown(false);
                    birthday.setView(datePicker);
                }
            });
            birthdayText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    birthday.show();
                }
            });
            birthday.setNegativeButton(R.string.cancel, null);
            setEnabledButtons(false);
        }

        private void setEnabledButtons(boolean b) {
            firstName.setEnabled(b);
            secondName.setEnabled(b);
            email.setEnabled(b);
            phone.setEnabled(b);
            city.setEnabled(b);
            address.setEnabled(b);
            family.setEnabled(b);
            interests.setEnabled(b);
            company.setEnabled(b);
            position.setEnabled(b);
            birthdayText.setEnabled(b);

        }

        protected static void loadUserData(UserData userData) {
            firstName.setText(userData.getFirstName());
            secondName.setText(userData.getSecondName());
            email.setText(userData.getEmail());
            phone.setText(userData.getPhoneNumber());
            city.setText(userData.getCity());
            address.setText(userData.getAdress());
            interests.setText(userData.getInterests());
            family.setText(userData.getFamily());
            company.setText(userData.getCompany());
            position.setText(userData.getPosition());
            if (userData.getBirthday() != null) {
                birthdayText.setText(userData.getBirthday().toString());
            }
        }

        protected void saveUserData() {
            String firstName = PlaceholderFragment.firstName.getText().toString();
            String secondName = PlaceholderFragment.secondName.getText().toString();
            String email = PlaceholderFragment.email.getText().toString();
            String phoneNumber = PlaceholderFragment.phone.getText().toString();
            String city = PlaceholderFragment.city.getText().toString();
            String address = PlaceholderFragment.address.getText().toString();
            String interests = PlaceholderFragment.interests.getText().toString();
            String family = PlaceholderFragment.family.getText().toString();
            String company = PlaceholderFragment.company.getText().toString();
            String position = PlaceholderFragment.position.getText().toString();

            if (firstName.equals("") || secondName.equals("") || phoneNumber.equals("")) {
                Toast.makeText(getContext(), R.string.error_didnt_write_field, Toast.LENGTH_LONG).show();
            } else {
                UserData userData = new UserData();
                userData.setFirstName(firstName);
                userData.setSecondName(secondName);
                userData.setEmail(email);
                userData.setPhoneNumber(phoneNumber);
                userData.setCity(city);
                userData.setAdress(address);
                userData.setInterests(interests);
                userData.setFamily(family);
                userData.setCompany(company);
                userData.setPosition(position);
                Date birthday = new Date(datePicker.getYear(),
                        datePicker.getMonth(),
                        datePicker.getDayOfMonth());
                userData.setBirthday(birthday);
                new SaveUserData(userData, getContext()).execute();
            }
        }

        private void initHistory(View rootView) {
            callListView = (ListView) rootView.findViewById(R.id.fragment_list);
            if (callAdapter == null) {
                callAdapter = new CallAdapter(getContext(), new ArrayList<Call>());
            }
            callListView.setAdapter(callAdapter);
            callListView.setOnItemClickListener(new OnItemClick(getContext(), CallActivity.class));
//            callListView.setOnScrollListener(new OnScrollGetAllList(callAdapter, UserData.class));
        }


        private void initEvents(View rootView) {
            eventsList = (ListView) rootView.findViewById(R.id.fragment_list);
            if (eventsAdapter == null) {
                eventsAdapter = new EventsAdapter(getContext(), new ArrayList<Event>());
            }
            eventsList.setAdapter(eventsAdapter);
            eventsList.setOnItemClickListener(new OnItemClick(getContext(), EventActivity.class));
            eventsList.setOnScrollListener(new OnScrollGetAllList(eventsAdapter, Event.class));
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
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.fragment_select_to_do);
                case 1:
                    return getString(R.string.user_data);
                case 2:
                    return getString(R.string.history);
                case 3:
                    return getString(R.string.event);
            }
            return null;
        }
    }

    private static class LoadUserData extends AsyncTask<Long, Long, List> {
        Context context;
        UserData data;
        String userId;
        BaseListAdapter adapter;
        ProgressDialog dialog;

        public LoadUserData(Context context, String userId, BaseListAdapter adapter) {
            this.context = context;
            this.userId = userId;
            this.adapter = adapter;
        }

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(context, "", context.getString(R.string.loading), false);
            super.onPreExecute();
        }

        @Override
        protected List doInBackground(Long... longs) {
            BackendlessDataQuery query = new BackendlessDataQuery(Resources.OBJECT_ID + " = '" + userId + "'");
            Log.d("LoadUserData", query.getWhereClause());
            data = (UserData) BackendAction.getData(UserData.class, query).get(0);
            return data.getCall_ID();
        }

        @Override
        protected void onPostExecute(List list) {
            PlaceholderFragment.loadUserData(data);
            if (list.size() != 0) {
                adapter.add(list);
                adapter.notifyDataSetChanged();
            }
            dialog.cancel();
            super.onPostExecute(list);
        }
    }
}
