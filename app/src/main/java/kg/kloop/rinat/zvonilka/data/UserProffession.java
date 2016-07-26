package kg.kloop.rinat.zvonilka.data;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.BackendlessDataQuery;

public class UserProffession
{
  private String Position;
  private String objectId;
  private String ownerId;
  private String Company;
  private java.util.Date updated;
  private java.util.Date created;
  private UserData UserData_ID;
  public String getPosition()
  {
    return Position;
  }

  public void setPosition( String Position )
  {
    this.Position = Position;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public String getCompany()
  {
    return Company;
  }

  public void setCompany( String Company )
  {
    this.Company = Company;
  }

  public java.util.Date getUpdated()
  {
    return updated;
  }

  public java.util.Date getCreated()
  {
    return created;
  }

  public UserData getUserData_ID()
  {
    return UserData_ID;
  }

  public void setUserData_ID( UserData UserData_ID )
  {
    this.UserData_ID = UserData_ID;
  }

                                                    
  public UserProffession save()
  {
    return Backendless.Data.of( UserProffession.class ).save( this );
  }

  public Future<UserProffession> saveAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<UserProffession> future = new Future<UserProffession>();
      Backendless.Data.of( UserProffession.class ).save( this, future );

      return future;
    }
  }

  public void saveAsync( AsyncCallback<UserProffession> callback )
  {
    Backendless.Data.of( UserProffession.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( UserProffession.class ).remove( this );
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
      Backendless.Data.of( UserProffession.class ).remove( this, future );

      return future;
    }
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( UserProffession.class ).remove( this, callback );
  }

  public static UserProffession findById( String id )
  {
    return Backendless.Data.of( UserProffession.class ).findById( id );
  }

  public static Future<UserProffession> findByIdAsync( String id )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<UserProffession> future = new Future<UserProffession>();
      Backendless.Data.of( UserProffession.class ).findById( id, future );

      return future;
    }
  }

  public static void findByIdAsync( String id, AsyncCallback<UserProffession> callback )
  {
    Backendless.Data.of( UserProffession.class ).findById( id, callback );
  }

  public static UserProffession findFirst()
  {
    return Backendless.Data.of( UserProffession.class ).findFirst();
  }

  public static Future<UserProffession> findFirstAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<UserProffession> future = new Future<UserProffession>();
      Backendless.Data.of( UserProffession.class ).findFirst( future );

      return future;
    }
  }

  public static void findFirstAsync( AsyncCallback<UserProffession> callback )
  {
    Backendless.Data.of( UserProffession.class ).findFirst( callback );
  }

  public static UserProffession findLast()
  {
    return Backendless.Data.of( UserProffession.class ).findLast();
  }

  public static Future<UserProffession> findLastAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<UserProffession> future = new Future<UserProffession>();
      Backendless.Data.of( UserProffession.class ).findLast( future );

      return future;
    }
  }

  public static void findLastAsync( AsyncCallback<UserProffession> callback )
  {
    Backendless.Data.of( UserProffession.class ).findLast( callback );
  }

  public static BackendlessCollection<UserProffession> find( BackendlessDataQuery query )
  {
    return Backendless.Data.of( UserProffession.class ).find( query );
  }

  public static Future<BackendlessCollection<UserProffession>> findAsync( BackendlessDataQuery query )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BackendlessCollection<UserProffession>> future = new Future<BackendlessCollection<UserProffession>>();
      Backendless.Data.of( UserProffession.class ).find( query, future );

      return future;
    }
  }

  public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<UserProffession>> callback )
  {
    Backendless.Data.of( UserProffession.class ).find( query, callback );
  }
}