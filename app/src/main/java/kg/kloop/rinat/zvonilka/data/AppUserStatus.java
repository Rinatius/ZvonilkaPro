package kg.kloop.rinat.zvonilka.data;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.BackendlessDataQuery;

public class AppUserStatus extends BackendlessData {
    private String ownerId;
    private java.util.Date created;
    private String Note;
    private String Status;
    private String objectId;
    private java.util.Date updated;

    public String getOwnerId() {
        return ownerId;
    }

    public java.util.Date getCreated() {
        return created;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String Note) {
        this.Note = Note;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getObjectId() {
        return objectId;
    }

    public java.util.Date getUpdated() {
        return updated;
    }


    public AppUserStatus save() {
        return Backendless.Data.of(AppUserStatus.class).save(this);
    }

    public Future<AppUserStatus> saveAsync() {
        if (Backendless.isAndroid()) {
            throw new UnsupportedOperationException("Using this method is restricted in Android");
        } else {
            Future<AppUserStatus> future = new Future<AppUserStatus>();
            Backendless.Data.of(AppUserStatus.class).save(this, future);

            return future;
        }
    }

    public void saveAsync(AsyncCallback<AppUserStatus> callback) {
        Backendless.Data.of(AppUserStatus.class).save(this, callback);
    }

    public Long remove() {
        return Backendless.Data.of(AppUserStatus.class).remove(this);
    }

    public Future<Long> removeAsync() {
        if (Backendless.isAndroid()) {
            throw new UnsupportedOperationException("Using this method is restricted in Android");
        } else {
            Future<Long> future = new Future<Long>();
            Backendless.Data.of(AppUserStatus.class).remove(this, future);

            return future;
        }
    }

    public void removeAsync(AsyncCallback<Long> callback) {
        Backendless.Data.of(AppUserStatus.class).remove(this, callback);
    }

    public static AppUserStatus findById(String id) {
        return Backendless.Data.of(AppUserStatus.class).findById(id);
    }

    public static Future<AppUserStatus> findByIdAsync(String id) {
        if (Backendless.isAndroid()) {
            throw new UnsupportedOperationException("Using this method is restricted in Android");
        } else {
            Future<AppUserStatus> future = new Future<AppUserStatus>();
            Backendless.Data.of(AppUserStatus.class).findById(id, future);

            return future;
        }
    }

    public static void findByIdAsync(String id, AsyncCallback<AppUserStatus> callback) {
        Backendless.Data.of(AppUserStatus.class).findById(id, callback);
    }

    public static AppUserStatus findFirst() {
        return Backendless.Data.of(AppUserStatus.class).findFirst();
    }

    public static Future<AppUserStatus> findFirstAsync() {
        if (Backendless.isAndroid()) {
            throw new UnsupportedOperationException("Using this method is restricted in Android");
        } else {
            Future<AppUserStatus> future = new Future<AppUserStatus>();
            Backendless.Data.of(AppUserStatus.class).findFirst(future);

            return future;
        }
    }

    public static void findFirstAsync(AsyncCallback<AppUserStatus> callback) {
        Backendless.Data.of(AppUserStatus.class).findFirst(callback);
    }

    public static AppUserStatus findLast() {
        return Backendless.Data.of(AppUserStatus.class).findLast();
    }

    public static Future<AppUserStatus> findLastAsync() {
        if (Backendless.isAndroid()) {
            throw new UnsupportedOperationException("Using this method is restricted in Android");
        } else {
            Future<AppUserStatus> future = new Future<AppUserStatus>();
            Backendless.Data.of(AppUserStatus.class).findLast(future);

            return future;
        }
    }

    public static void findLastAsync(AsyncCallback<AppUserStatus> callback) {
        Backendless.Data.of(AppUserStatus.class).findLast(callback);
    }

    public static BackendlessCollection<AppUserStatus> find(BackendlessDataQuery query) {
        return Backendless.Data.of(AppUserStatus.class).find(query);
    }

    public static Future<BackendlessCollection<AppUserStatus>> findAsync(BackendlessDataQuery query) {
        if (Backendless.isAndroid()) {
            throw new UnsupportedOperationException("Using this method is restricted in Android");
        } else {
            Future<BackendlessCollection<AppUserStatus>> future = new Future<BackendlessCollection<AppUserStatus>>();
            Backendless.Data.of(AppUserStatus.class).find(query, future);

            return future;
        }
    }

    public static void findAsync(BackendlessDataQuery query, AsyncCallback<BackendlessCollection<AppUserStatus>> callback) {
        Backendless.Data.of(AppUserStatus.class).find(query, callback);
    }
}