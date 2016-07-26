package kg.kloop.rinat.zvonilka.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import kg.kloop.rinat.zvonilka.R;

public class PasswordRecoveryRequestedActivity extends Activity
{
  private Button loginButton;

  @Override
  public void onCreate( Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.password_recovery_requested );

    initUI();
  }

  private void initUI()
  {
    loginButton = (Button) findViewById( R.id.loginButton );

    loginButton.setOnClickListener( new View.OnClickListener()
    {
      @Override
      public void onClick( View view )
      {
        onLoginButtonClicked();
      }
    } );
  }

  public void onLoginButtonClicked()
  {
    startActivity( new Intent( this, LoginActivity.class ) );
    finish();
  }
}