package kg.kloop.rinat.zvonilka.data;

import android.os.Parcelable;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.BackendlessDataQuery;

import java.io.Serializable;
import java.util.ArrayList;

public class UserData extends BackendlessData {
    private String City;
    private String SecondName;
    private java.util.Date updated;
    private java.util.Date Birthday;
    private String Adress;
    private String FirstName;
    private String Family;
    private String Email;
    private String ownerId;
    private String Position;
    private String Interests;
    private String objectId;
    private java.util.Date created;
    private String Company;
    private String PhoneNumber;
    private java.util.List<AppUserStatus> AppUserStatus_ID;
    private java.util.List<Call> Call_ID;
    private BackendlessUser User_ID_UserData;
    private java.util.List<ToDo> ToDo_ID;
    private java.util.List<EventUserStatus> EventUserStatus_ID;

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getSecondName() {
        return SecondName;
    }

    public void setSecondName(String SecondName) {
        this.SecondName = SecondName;
    }

    public java.util.Date getUpdated() {
        return updated;
    }

    public java.util.Date getBirthday() {
        return Birthday;
    }

    public void setBirthday(java.util.Date Birthday) {
        this.Birthday = Birthday;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String Adress) {
        this.Adress = Adress;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getFamily() {
        return Family;
    }

    public void setFamily(String Family) {
        this.Family = Family;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String Position) {
        this.Position = Position;
    }

    public String getInterests() {
        return Interests;
    }

    public void setInterests(String Interests) {
        this.Interests = Interests;
    }

    public String getObjectId() {
        return objectId;
    }

    public java.util.Date getCreated() {
        return created;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String Company) {
        this.Company = Company;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public void addEventStatus(EventUserStatus e) {
        if (EventUserStatus_ID == null)
            EventUserStatus_ID = new ArrayList<>();
        EventUserStatus_ID.add(e);
    }

    public java.util.List getEventStatus() {
        return null;
    }

    public java.util.List<AppUserStatus> getAppUserStatus_ID() {
        return AppUserStatus_ID;
    }

    public void setAppUserStatus_ID(java.util.List<AppUserStatus> AppUserStatus_ID) {
        this.AppUserStatus_ID = AppUserStatus_ID;
    }

    public java.util.List<Call> getCall_ID() {
        return Call_ID;
    }

    public void setCall_ID(java.util.List<Call> Call_ID) {
        this.Call_ID = Call_ID;
    }

    public BackendlessUser getUser_ID_UserData() {
        return User_ID_UserData;
    }

    public void setUser_ID_UserData(BackendlessUser User_ID_UserData) {
        this.User_ID_UserData = User_ID_UserData;
    }

    public java.util.List<ToDo> getToDo_ID() {
        return ToDo_ID;
    }

    public void setToDo_ID(java.util.List<ToDo> ToDo_ID) {
        this.ToDo_ID = ToDo_ID;
    }

    public java.util.List<EventUserStatus> getEventUserStatus_ID() {
        return EventUserStatus_ID;
    }

    public void setEventUserStatus_ID(java.util.List<EventUserStatus> EventUserStatus_ID) {
        this.EventUserStatus_ID = EventUserStatus_ID;
    }


    public UserData save() {
        return Backendless.Data.of(UserData.class).save(this);
    }

    public Future<UserData> saveAsync() {
        if (Backendless.isAndroid()) {
            throw new UnsupportedOperationException("Using this method is restricted in Android");
        } else {
            Future<UserData> future = new Future<UserData>();
            Backendless.Data.of(UserData.class).save(this, future);

            return future;
        }
    }

    public void saveAsync(AsyncCallback<UserData> callback) {
        Backendless.Data.of(UserData.class).save(this, callback);
    }

    public Long remove() {
        return Backendless.Data.of(UserData.class).remove(this);
    }

    public Future<Long> removeAsync() {
        if (Backendless.isAndroid()) {
            throw new UnsupportedOperationException("Using this method is restricted in Android");
        } else {
            Future<Long> future = new Future<Long>();
            Backendless.Data.of(UserData.class).remove(this, future);

            return future;
        }
    }

    public void removeAsync(AsyncCallback<Long> callback) {
        Backendless.Data.of(UserData.class).remove(this, callback);
    }

    public static UserData findById(String id) {
        return Backendless.Data.of(UserData.class).findById(id);
    }

    public static Future<UserData> findByIdAsync(String id) {
        if (Backendless.isAndroid()) {
            throw new UnsupportedOperationException("Using this method is restricted in Android");
        } else {
            Future<UserData> future = new Future<UserData>();
            Backendless.Data.of(UserData.class).findById(id, future);

            return future;
        }
    }

    public static void findByIdAsync(String id, AsyncCallback<UserData> callback) {
        Backendless.Data.of(UserData.class).findById(id, callback);
    }

    public static UserData findFirst() {
        return Backendless.Data.of(UserData.class).findFirst();
    }

    public static Future<UserData> findFirstAsync() {
        if (Backendless.isAndroid()) {
            throw new UnsupportedOperationException("Using this method is restricted in Android");
        } else {
            Future<UserData> future = new Future<UserData>();
            Backendless.Data.of(UserData.class).findFirst(future);

            return future;
        }
    }

    public static void findFirstAsync(AsyncCallback<UserData> callback) {
        Backendless.Data.of(UserData.class).findFirst(callback);
    }

    public static UserData findLast() {
        return Backendless.Data.of(UserData.class).findLast();
    }

    public static Future<UserData> findLastAsync() {
        if (Backendless.isAndroid()) {
            throw new UnsupportedOperationException("Using this method is restricted in Android");
        } else {
            Future<UserData> future = new Future<UserData>();
            Backendless.Data.of(UserData.class).findLast(future);

            return future;
        }
    }

    public static void findLastAsync(AsyncCallback<UserData> callback) {
        Backendless.Data.of(UserData.class).findLast(callback);
    }

    public static BackendlessCollection<UserData> find(BackendlessDataQuery query) {
        return Backendless.Data.of(UserData.class).find(query);
    }

    public static Future<BackendlessCollection<UserData>> findAsync(BackendlessDataQuery query) {
        if (Backendless.isAndroid()) {
            throw new UnsupportedOperationException("Using this method is restricted in Android");
        } else {
            Future<BackendlessCollection<UserData>> future = new Future<BackendlessCollection<UserData>>();
            Backendless.Data.of(UserData.class).find(query, future);

            return future;
        }
    }

    public static void findAsync(BackendlessDataQuery query, AsyncCallback<BackendlessCollection<UserData>> callback) {
        Backendless.Data.of(UserData.class).find(query, callback);
    }
}