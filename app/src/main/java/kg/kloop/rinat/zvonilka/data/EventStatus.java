package kg.kloop.rinat.zvonilka.data;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.BackendlessDataQuery;

public class EventStatus
{
  private String ownerId;
  private Boolean HasBeen;
  private java.util.Date updated;
  private String Status;
  private String objectId;
  private java.util.Date created;
  private Call Call_ID;
  private Event Event_ID;
  private UserData UserData_ID;
  public String getOwnerId()
  {
    return ownerId;
  }

  public Boolean getHasBeen()
  {
    return HasBeen;
  }

  public void setHasBeen( Boolean HasBeen )
  {
    this.HasBeen = HasBeen;
  }

  public java.util.Date getUpdated()
  {
    return updated;
  }

  public String getStatus()
  {
    return Status;
  }

  public void setStatus( String Status )
  {
    this.Status = Status;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public java.util.Date getCreated()
  {
    return created;
  }

  public Call getCall_ID()
  {
    return Call_ID;
  }

  public void setCall_ID( Call Call_ID )
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

  public UserData getUserData_ID()
  {
    return UserData_ID;
  }

  public void setUserData_ID( UserData UserData_ID )
  {
    this.UserData_ID = UserData_ID;
  }

                                                    
  public EventStatus save()
  {
    return Backendless.Data.of( EventStatus.class ).save( this );
  }

  public Future<EventStatus> saveAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<EventStatus> future = new Future<EventStatus>();
      Backendless.Data.of( EventStatus.class ).save( this, future );

      return future;
    }
  }

  public void saveAsync( AsyncCallback<EventStatus> callback )
  {
    Backendless.Data.of( EventStatus.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( EventStatus.class ).remove( this );
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
      Backendless.Data.of( EventStatus.class ).remove( this, future );

      return future;
    }
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( EventStatus.class ).remove( this, callback );
  }

  public static EventStatus findById( String id )
  {
    return Backendless.Data.of( EventStatus.class ).findById( id );
  }

  public static Future<EventStatus> findByIdAsync( String id )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<EventStatus> future = new Future<EventStatus>();
      Backendless.Data.of( EventStatus.class ).findById( id, future );

      return future;
    }
  }

  public static void findByIdAsync( String id, AsyncCallback<EventStatus> callback )
  {
    Backendless.Data.of( EventStatus.class ).findById( id, callback );
  }

  public static EventStatus findFirst()
  {
    return Backendless.Data.of( EventStatus.class ).findFirst();
  }

  public static Future<EventStatus> findFirstAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<EventStatus> future = new Future<EventStatus>();
      Backendless.Data.of( EventStatus.class ).findFirst( future );

      return future;
    }
  }

  public static void findFirstAsync( AsyncCallback<EventStatus> callback )
  {
    Backendless.Data.of( EventStatus.class ).findFirst( callback );
  }

  public static EventStatus findLast()
  {
    return Backendless.Data.of( EventStatus.class ).findLast();
  }

  public static Future<EventStatus> findLastAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<EventStatus> future = new Future<EventStatus>();
      Backendless.Data.of( EventStatus.class ).findLast( future );

      return future;
    }
  }

  public static void findLastAsync( AsyncCallback<EventStatus> callback )
  {
    Backendless.Data.of( EventStatus.class ).findLast( callback );
  }

  public static BackendlessCollection<EventStatus> find( BackendlessDataQuery query )
  {
    return Backendless.Data.of( EventStatus.class ).find( query );
  }

  public static Future<BackendlessCollection<EventStatus>> findAsync( BackendlessDataQuery query )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BackendlessCollection<EventStatus>> future = new Future<BackendlessCollection<EventStatus>>();
      Backendless.Data.of( EventStatus.class ).find( query, future );

      return future;
    }
  }

  public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<EventStatus>> callback )
  {
    Backendless.Data.of( EventStatus.class ).find( query, callback );
  }
}