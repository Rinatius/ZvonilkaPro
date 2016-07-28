package kg.kloop.rinat.zvonilka.data;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.BackendlessDataQuery;

public class EventUserStatus
{
  private Boolean HasBeen;
  private String Status;
  private java.util.Date updated;
  private String ownerId;
  private java.util.Date created;
  private String objectId;
  private java.util.List<Call> Call_ID;
  private Event Event_ID;
  private UserData UserData_ID_EventUserStatus;
  public Boolean getHasBeen()
  {
    return HasBeen;
  }

  public void setHasBeen( Boolean HasBeen )
  {
    this.HasBeen = HasBeen;
  }

  public String getStatus()
  {
    return Status;
  }

  public void setStatus( String Status )
  {
    this.Status = Status;
  }

  public java.util.Date getUpdated()
  {
    return updated;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public java.util.Date getCreated()
  {
    return created;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public java.util.List<Call> getCall_ID()
  {
    return Call_ID;
  }

  public void setCall_ID( java.util.List<Call> Call_ID )
  {
    this.Call_ID = Call_ID;
  }

  public Event getEvent_ID()
  {
    return Event_ID;
  }

  public void setEvent_ID( Event Event_ID )
  {
    this.Event_ID = Event_ID;
  }

  public UserData getUserData_ID_EventUserStatus()
  {
    return UserData_ID_EventUserStatus;
  }

  public void setUserData_ID_EventUserStatus( UserData UserData_ID_EventUserStatus )
  {
    this.UserData_ID_EventUserStatus = UserData_ID_EventUserStatus;
  }

                                                    
  public EventUserStatus save()
  {
    return Backendless.Data.of( EventUserStatus.class ).save( this );
  }

  public Future<EventUserStatus> saveAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<EventUserStatus> future = new Future<EventUserStatus>();
      Backendless.Data.of( EventUserStatus.class ).save( this, future );

      return future;
    }
  }

  public void saveAsync( AsyncCallback<EventUserStatus> callback )
  {
    Backendless.Data.of( EventUserStatus.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( EventUserStatus.class ).remove( this );
  }

  public Future<Long> removeAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Long> future = new Future<Long>();
      Backendless.Data.of( EventUserStatus.class ).remove( this, future );

      return future;
    }
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( EventUserStatus.class ).remove( this, callback );
  }

  public static EventUserStatus findById( String id )
  {
    return Backendless.Data.of( EventUserStatus.class ).findById( id );
  }

  public static Future<EventUserStatus> findByIdAsync( String id )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<EventUserStatus> future = new Future<EventUserStatus>();
      Backendless.Data.of( EventUserStatus.class ).findById( id, future );

      return future;
    }
  }

  public static void findByIdAsync( String id, AsyncCallback<EventUserStatus> callback )
  {
    Backendless.Data.of( EventUserStatus.class ).findById( id, callback );
  }

  public static EventUserStatus findFirst()
  {
    return Backendless.Data.of( EventUserStatus.class ).findFirst();
  }

  public static Future<EventUserStatus> findFirstAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<EventUserStatus> future = new Future<EventUserStatus>();
      Backendless.Data.of( EventUserStatus.class ).findFirst( future );

      return future;
    }
  }

  public static void findFirstAsync( AsyncCallback<EventUserStatus> callback )
  {
    Backendless.Data.of( EventUserStatus.class ).findFirst( callback );
  }

  public static EventUserStatus findLast()
  {
    return Backendless.Data.of( EventUserStatus.class ).findLast();
  }

  public static Future<EventUserStatus> findLastAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<EventUserStatus> future = new Future<EventUserStatus>();
      Backendless.Data.of( EventUserStatus.class ).findLast( future );

      return future;
    }
  }

  public static void findLastAsync( AsyncCallback<EventUserStatus> callback )
  {
    Backendless.Data.of( EventUserStatus.class ).findLast( callback );
  }

  public static BackendlessCollection<EventUserStatus> find( BackendlessDataQuery query )
  {
    return Backendless.Data.of( EventUserStatus.class ).find( query );
  }

  public static Future<BackendlessCollection<EventUserStatus>> findAsync( BackendlessDataQuery query )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BackendlessCollection<EventUserStatus>> future = new Future<BackendlessCollection<EventUserStatus>>();
      Backendless.Data.of( EventUserStatus.class ).find( query, future );

      return future;
    }
  }

  public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<EventUserStatus>> callback )
  {
    Backendless.Data.of( EventUserStatus.class ).find( query, callback );
  }
}