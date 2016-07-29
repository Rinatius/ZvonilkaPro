package kg.kloop.rinat.zvonilka.data;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.BackendlessDataQuery;

public class Event
{
  private String ownerId;
  private java.util.Date created;
  private String City;
  private java.util.Date DateOfEvent;
  private java.util.Date updated;
  private String Name;
  private String Note;
  private String objectId;
  private String appCompany_ID_Event;
  private java.util.List<EventUserStatus> EventUserStatus_ID_Event;
  public String getOwnerId()
  {
    return ownerId;
  }

  public java.util.Date getCreated()
  {
    return created;
  }

  public String getCity()
  {
    return City;
  }

  public void setCity( String City )
  {
    this.City = City;
  }

  public java.util.Date getDateOfEvent()
  {
    return DateOfEvent;
  }

  public void setDateOfEvent( java.util.Date DateOfEvent )
  {
    this.DateOfEvent = DateOfEvent;
  }

  public java.util.Date getUpdated()
  {
    return updated;
  }

  public String getName()
  {
    return Name;
  }

  public void setName( String Name )
  {
    this.Name = Name;
  }

  public String getNote()
  {
    return Note;
  }

  public void setNote( String Note )
  {
    this.Note = Note;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public String getAppCompany_ID_Event()
  {
    return appCompany_ID_Event;
  }

  public void setAppCompany_ID_Event( String appCompany_ID_Event )
  {
    this.appCompany_ID_Event = appCompany_ID_Event;
  }

  public java.util.List<EventUserStatus> getEventUserStatus_ID_Event()
  {
    return EventUserStatus_ID_Event;
  }

  public void setEventUserStatus_ID_Event( java.util.List<EventUserStatus> EventUserStatus_ID_Event )
  {
    this.EventUserStatus_ID_Event = EventUserStatus_ID_Event;
  }

                                                    
  public Event save()
  {
    return Backendless.Data.of( Event.class ).save( this );
  }

  public Future<Event> saveAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Event> future = new Future<Event>();
      Backendless.Data.of( Event.class ).save( this, future );

      return future;
    }
  }

  public void saveAsync( AsyncCallback<Event> callback )
  {
    Backendless.Data.of( Event.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( Event.class ).remove( this );
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
      Backendless.Data.of( Event.class ).remove( this, future );

      return future;
    }
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( Event.class ).remove( this, callback );
  }

  public static Event findById( String id )
  {
    return Backendless.Data.of( Event.class ).findById( id );
  }

  public static Future<Event> findByIdAsync( String id )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Event> future = new Future<Event>();
      Backendless.Data.of( Event.class ).findById( id, future );

      return future;
    }
  }

  public static void findByIdAsync( String id, AsyncCallback<Event> callback )
  {
    Backendless.Data.of( Event.class ).findById( id, callback );
  }

  public static Event findFirst()
  {
    return Backendless.Data.of( Event.class ).findFirst();
  }

  public static Future<Event> findFirstAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Event> future = new Future<Event>();
      Backendless.Data.of( Event.class ).findFirst( future );

      return future;
    }
  }

  public static void findFirstAsync( AsyncCallback<Event> callback )
  {
    Backendless.Data.of( Event.class ).findFirst( callback );
  }

  public static Event findLast()
  {
    return Backendless.Data.of( Event.class ).findLast();
  }

  public static Future<Event> findLastAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Event> future = new Future<Event>();
      Backendless.Data.of( Event.class ).findLast( future );

      return future;
    }
  }

  public static void findLastAsync( AsyncCallback<Event> callback )
  {
    Backendless.Data.of( Event.class ).findLast( callback );
  }

  public static BackendlessCollection<Event> find( BackendlessDataQuery query )
  {
    return Backendless.Data.of( Event.class ).find( query );
  }

  public static Future<BackendlessCollection<Event>> findAsync( BackendlessDataQuery query )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BackendlessCollection<Event>> future = new Future<BackendlessCollection<Event>>();
      Backendless.Data.of( Event.class ).find( query, future );

      return future;
    }
  }

  public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<Event>> callback )
  {
    Backendless.Data.of( Event.class ).find( query, callback );
  }
}