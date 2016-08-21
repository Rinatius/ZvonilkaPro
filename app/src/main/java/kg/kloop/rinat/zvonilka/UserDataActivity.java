package kg.kloop.rinat.zvonilka;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
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
import com.backendless.persistence.QueryOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kg.kloop.rinat.zvonilka.adapters.CallAdapter;
import kg.kloop.rinat.zvonilka.adapters.EventsAdapter;
import kg.kloop.rinat.zvonilka.adapters.ToDoAdapter;
import kg.kloop.rinat.zvonilka.data.BackendAction;
import kg.kloop.rinat.zvonilka.data.Call;
import kg.kloop.rinat.zvonilka.data.Event;
import kg.kloop.rinat.zvonilka.data.EventUserStatus;
import kg.kloop.rinat.zvonilka.data.ToDo;
import kg.kloop.rinat.zvonilka.data.UserData;

public class UserDataActivity extends AppCompatActivity {

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
    private static OnScrollGetAllList toDoScroll;
    static String userId;
    private static UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        toDoAdapter = new ToDoAdapter(UserDataActivity.this, new ArrayList<ToDo>());
        eventsAdapter = new EventsAdapter(UserDataActivity.this, new ArrayList<Event>());
        callAdapter = new CallAdapter(UserDataActivity.this, new ArrayList<Call>());

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDataActivity.this, CallActivity.class);
                intent.putExtra(Resources.OBJECT_ID, userData.getObjectId());
                intent.putExtra(Resources.PHONE_NUMBER_KEY, userData.getPhoneNumber());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
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
        } else if (id == R.id.action_update_user_data){
            Intent intent = new Intent(UserDataActivity.this, AddUserDataActivity.class);
            intent.putExtra(Resources.ACTION, Resources.UPDATE_USER_DATA);
            intent.putExtra(Resources.OBJECT_ID, userId);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
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
        LoadUserData loadUserData;
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
            toDoList.setAdapter(toDoAdapter);
            toDoList.setOnItemClickListener(new OnItemClick(getContext(), ToDoActivity.class));
        }

        private void initUserData(View rootView) {
            if (loadUserData == null) {
                loadUserData = new LoadUserData(getContext(), userId);
                loadUserData.execute();
            }
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
            UserDataActivity.userData = userData;
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
            callListView.setAdapter(callAdapter);
//            callListView.setOnItemClickListener(new OnItemClick(getContext(), CallActivity.class));
//            callListView.setOnScrollListener(new OnScrollGetAllList(callAdapter, UserData.class));
        }


        private void initEvents(View rootView) {
            eventsList = (ListView) rootView.findViewById(R.id.fragment_list);
            eventsList.setAdapter(eventsAdapter);
            eventsList.setOnItemClickListener(new OnItemClick(getContext(), EventActivityDemo.class));
//            eventsList.setOnScrollListener(new OnScrollGetAllList(eventsAdapter, Event.class));
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

   static class LoadUserData extends AsyncTask<Long, Long, UserData>{
       Context context;
       String userId;
       ProgressDialog dialog;

       public LoadUserData(Context context, String userId) {
           this.context = context;
           this.userId = userId;
       }

       @Override
       protected void onPreExecute() {
           dialog = ProgressDialog.show(context, "", context.getString(R.string.loading), false);
           super.onPreExecute();
       }

       @Override
       protected UserData doInBackground(Long... longs) {
           BackendlessDataQuery query = new BackendlessDataQuery(Resources.OBJECT_ID +  " = '"+ userId + "'");
           QueryOptions queryOptions = new QueryOptions();
           queryOptions.setRelationsDepth(3);
           query.setQueryOptions(queryOptions);
           UserData userData = (UserData) BackendAction.getData(UserData.class, query).get(0);
           Log.d("UserCall", userData.getCall_ID().toString());
//           List<EventUserStatus> list = userData.getEventUserStatus_ID();
//           for (int i = 0; i < list.size(); i++) {
//               query = new BackendlessDataQuery(Resources.EVENT_USER_STATUS_ID_OBJECTID
//                       + " = '" + list.get(i).getObjectId() + "'");
//               Event event = (Event) BackendAction.getData(Event.class, query);
//               eventsAdapter.add((List) event);
//           }
           return userData;
       }

       @Override
       protected void onPostExecute(UserData userData) {
           PlaceholderFragment.loadUserData(userData);
           toDoAdapter.add(userData.getToDo_ID());
           toDoAdapter.notifyDataSetChanged();
           callAdapter.add(userData.getCall_ID());
           callAdapter.notifyDataSetChanged();
           eventsAdapter.notifyDataSetChanged();
           Log.d("Adapters", userData.getCall_ID().size() + " " + userData.getToDo_ID() + " " + userData.getEventUserStatus_ID());
           dialog.cancel();
           super.onPostExecute(userData);
       }
   }
}
