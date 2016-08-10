package kg.kloop.rinat.zvonilka.data;

import java.util.Date;

public abstract class BackendlessData {
    private String ownerId;
    private java.util.Date created;
    private java.util.Date updated;
    private String objectId;

    public String getOwnerId() {
        return ownerId;
    }

    public Date getCreated() {
        return created;
    }


    public Date getUpdated() {
        return updated;
    }


    public String getObjectId() {
        return objectId;
    }

}
