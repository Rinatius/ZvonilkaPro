package kg.kloop.rinat.zvonilka.data;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.BackendlessDataQuery;

public class ToDo
{
  private java.util.Date Deadline;
  private String objectId;
  private java.util.Date updated;
  private java.util.Date created;
  private Boolean IsDone;
  private String Note;
  private String ownerId;
  private AppCompany AppCompany_ID_ToDo;
  private UserData UserData_ID_ToDo;
  public java.util.Date getDeadline()
  {
    return Deadline;
  }

  public void setDeadline( java.util.Date Deadline )
  {
    this.Deadline = Deadline;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public java.util.Date getUpdated()
  {
    return updated;
  }

  public java.util.Date getCreated()
  {
    return created;
  }

  public Boolean getIsDone()
  {
    return IsDone;
  }

  public void setIsDone( Boolean IsDone )
  {
    this.IsDone = IsDone;
  }

  public String getNote()
  {
    return Note;
  }

  public void setNote( String Note )
  {
    this.Note = Note;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public AppCompany getAppCompany_ID_ToDo()
  {
    return AppCompany_ID_ToDo;
  }

  public void setAppCompany_ID_ToDo( AppCompany AppCompany_ID_ToDo )
  {
    this.AppCompany_ID_ToDo = AppCompany_ID_ToDo;
  }

  public UserData getUserData_ID_ToDo()
  {
    return UserData_ID_ToDo;
  }

  public void setUserData_ID_ToDo( UserData UserData_ID_ToDo )
  {
    this.UserData_ID_ToDo = UserData_ID_ToDo;
  }

                                                    
  public ToDo save()
  {
    return Backendless.Data.of( ToDo.class ).save( this );
  }

  public Future<ToDo> saveAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<ToDo> future = new Future<ToDo>();
      Backendless.Data.of( ToDo.class ).save( this, future );

      return future;
    }
  }

  public void saveAsync( AsyncCallback<ToDo> callback )
  {
    Backendless.Data.of( ToDo.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( ToDo.class ).remove( this );
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
      Backendless.Data.of( ToDo.class ).remove( this, future );

      return future;
    }
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( ToDo.class ).remove( this, callback );
  }

  public static ToDo findById( String id )
  {
    return Backendless.Data.of( ToDo.class ).findById( id );
  }

  public static Future<ToDo> findByIdAsync( String id )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<ToDo> future = new Future<ToDo>();
      Backendless.Data.of( ToDo.class ).findById( id, future );

      return future;
    }
  }

  public static void findByIdAsync( String id, AsyncCallback<ToDo> callback )
  {
    Backendless.Data.of( ToDo.class ).findById( id, callback );
  }

  public static ToDo findFirst()
  {
    return Backendless.Data.of( ToDo.class ).findFirst();
  }

  public static Future<ToDo> findFirstAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<ToDo> future = new Future<ToDo>();
      Backendless.Data.of( ToDo.class ).findFirst( future );

      return future;
    }
  }

  public static void findFirstAsync( AsyncCallback<ToDo> callback )
  {
    Backendless.Data.of( ToDo.class ).findFirst( callback );
  }

  public static ToDo findLast()
  {
    return Backendless.Data.of( ToDo.class ).findLast();
  }

  public static Future<ToDo> findLastAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<ToDo> future = new Future<ToDo>();
      Backendless.Data.of( ToDo.class ).findLast( future );

      return future;
    }
  }

  public static void findLastAsync( AsyncCallback<ToDo> callback )
  {
    Backendless.Data.of( ToDo.class ).findLast( callback );
  }

  public static BackendlessCollection<ToDo> find( BackendlessDataQuery query )
  {
    return Backendless.Data.of( ToDo.class ).find( query );
  }

  public static Future<BackendlessCollection<ToDo>> findAsync( BackendlessDataQuery query )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BackendlessCollection<ToDo>> future = new Future<BackendlessCollection<ToDo>>();
      Backendless.Data.of( ToDo.class ).find( query, future );

      return future;
    }
  }

  public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<ToDo>> callback )
  {
    Backendless.Data.of( ToDo.class ).find( query, callback );
  }
}