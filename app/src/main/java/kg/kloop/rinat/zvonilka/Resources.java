package kg.kloop.rinat.zvonilka;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Resources {
//      KEYS
    public static final String TODO_ID_KEY = "todoId";
    public static final String USER_ID_KEY = "userId";
    public static final String USER_DATA_ID_KEY = "userDataId";
    public static final String PHONE_NUMBER_KEY = "PhoneNumber";
    //     Other strings
    public static final String OBJECT_ID = "objectId";
    public static final String EVENTUSERSTATUS_CALL_ID_OBJECTID = "EventUserStatus[Call_ID].objectId";
    public static final String EVENT_USER_STATUS_ID_OBJECTID = "EventUserStatus_ID.objectId";
    public static final String EVENT_EVENTUSERSTATUS_ID_OBJECTID = "Event[EventUserStatus_ID].objectId";
    public static final String USER_CALL_ID = "UserData[Call_ID].objectId";
    public static final String EVENT_ID_KEY = "eventId";
    public static final String NAME = "Name";
    public static final String CITY = "City";
    public static final String DETAILS = "Details";
    public static final String DEADLINE = "Deadline";
    public static final String EVENT = "Event";
    public static final String DATE = "Date";
    public static final String DESCRIPTION = "Description";
    public static final String[] SPINNER_SEARCH = {"Users", "Events"};

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

    public static final String PARTICIPANTS = "Participants";
    public static final String USERS = "Users";
    public static final String ACTION = "APP_ACTION";
    public static final String ADD_USER_DATA = "APP_ACTION_ADD_USER_DATA";
    public static final String UPDATE_USER_DATA = "APP_ACTION_UPDATE_USER_DATA";
}
