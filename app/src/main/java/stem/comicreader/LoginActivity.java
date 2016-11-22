package stem.comicreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Created by elijahhursey on 11/9/16.
 */

public class LoginActivity extends Activity {

    EditText username, password;
    Button login;
    public static ProgressBar progressBar;
    private static LoginActivity loginActivity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginActivity = this;

        progressBar = (ProgressBar)findViewById(R.id.spinner);
        progressBar.setVisibility(View.GONE);
        login = (Button)findViewById(R.id.login_button);
        username = (EditText)findViewById(R.id.login_username);
        username.requestFocus();
        password = (EditText)findViewById(R.id.login_password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                //code to attempt logging into MAL
                MAL mal = MAL.getInstance();
                progressBar.setVisibility(View.VISIBLE);
                mal.authenticate(username.getText().toString(), password.getText().toString());
            }
        });
    }

    public static LoginActivity getLoginActivity() {
        return loginActivity;
    }

}
