package kg.kloop.rinat.zvonilka.data;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.BackendlessDataQuery;

public class AppCompany
{
  private String Name;
  private String ownerId;
  private String Email;
  private java.util.Date created;
  private java.util.Date updated;
  private String objectId;
  private String PhoneNumber;
  public String getName()
  {
    return Name;
  }

  public void setName( String Name )
  {
    this.Name = Name;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public String getEmail()
  {
    return Email;
  }

  public void setEmail( String Email )
  {
    this.Email = Email;
  }

  public java.util.Date getCreated()
  {
    return created;
  }

  public java.util.Date getUpdated()
  {
    return updated;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public String getPhoneNumber()
  {
    return PhoneNumber;
  }

  public void setPhoneNumber( String PhoneNumber )
  {
    this.PhoneNumber = PhoneNumber;
  }

                                                    
  public AppCompany save()
  {
    return Backendless.Data.of( AppCompany.class ).save( this );
  }

  public Future<AppCompany> saveAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<AppCompany> future = new Future<AppCompany>();
      Backendless.Data.of( AppCompany.class ).save( this, future );

      return future;
    }
  }

  public void saveAsync( AsyncCallback<AppCompany> callback )
  {
    Backendless.Data.of( AppCompany.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( AppCompany.class ).remove( this );
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
      Backendless.Data.of( AppCompany.class ).remove( this, future );

      return future;
    }
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( AppCompany.class ).remove( this, callback );
  }

  public static AppCompany findById( String id )
  {
    return Backendless.Data.of( AppCompany.class ).findById( id );
  }

  public static Future<AppCompany> findByIdAsync( String id )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<AppCompany> future = new Future<AppCompany>();
      Backendless.Data.of( AppCompany.class ).findById( id, future );

      return future;
    }
  }

  public static void findByIdAsync( String id, AsyncCallback<AppCompany> callback )
  {
    Backendless.Data.of( AppCompany.class ).findById( id, callback );
  }

  public static AppCompany findFirst()
  {
    return Backendless.Data.of( AppCompany.class ).findFirst();
  }

  public static Future<AppCompany> findFirstAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<AppCompany> future = new Future<AppCompany>();
      Backendless.Data.of( AppCompany.class ).findFirst( future );

      return future;
    }
  }

  public static void findFirstAsync( AsyncCallback<AppCompany> callback )
  {
    Backendless.Data.of( AppCompany.class ).findFirst( callback );
  }

  public static AppCompany findLast()
  {
    return Backendless.Data.of( AppCompany.class ).findLast();
  }

  public static Future<AppCompany> findLastAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<AppCompany> future = new Future<AppCompany>();
      Backendless.Data.of( AppCompany.class ).findLast( future );

      return future;
    }
  }

  public static void findLastAsync( AsyncCallback<AppCompany> callback )
  {
    Backendless.Data.of( AppCompany.class ).findLast( callback );
  }

  public static BackendlessCollection<AppCompany> find( BackendlessDataQuery query )
  {
    return Backendless.Data.of( AppCompany.class ).find( query );
  }

  public static Future<BackendlessCollection<AppCompany>> findAsync( BackendlessDataQuery query )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BackendlessCollection<AppCompany>> future = new Future<BackendlessCollection<AppCompany>>();
      Backendless.Data.of( AppCompany.class ).find( query, future );

      return future;
    }
  }

  public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<AppCompany>> callback )
  {
    Backendless.Data.of( AppCompany.class ).find( query, callback );
  }
}