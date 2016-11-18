package stem.comicreader;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by elijahhursey on 11/9/16.
 */

public class LoginActivity extends Activity {

    EditText username, password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.login_button);
     //   login.setOnClickListener(new View.OnClickListener() {
     //       @Override
     //       public void onClick(View v) {
     //           Intent intent = new Intent(LoginActivity.this, ComicListActivity.class);
     //           startActivity(intent);
     //       }
     //   });

        username = (EditText) findViewById(R.id.login_username);
        username.requestFocus();
        password = (EditText) findViewById(R.id.login_password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                //code to attempt logging into MAL
                MAL mal = MAL.getInstance();
                try {
                    if (mal.authenticate(username.getText().toString(), password.getText().toString())) {
                        Thread.sleep(500);
                        Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        Intent loginActivityIntent = new Intent(LoginActivity.this, ComicListActivity.class);
                        startActivity(loginActivityIntent);
                    } else {
                        Thread.sleep(500);
                        Toast.makeText(LoginActivity.this, "Login Failed, Try Again!", Toast.LENGTH_SHORT).show();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
