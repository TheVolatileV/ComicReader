package stem.comicreader;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.os.StrictMode;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.net.ssl.HttpsURLConnection;

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
                try {
                    String rawData;
                    URL url = new URL("https://myanimelist.net/api/account/verify_credentials.xml");
                    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

                    conn.setRequestMethod("GET");

                    String usernameTxt = username.getText().toString();
                    String passwordTxt = password.getText().toString();
                    String userpass = usernameTxt + ":" + passwordTxt;
                    String basicAuth = "Basic "
                            + Base64.encodeToString(userpass.getBytes(), Base64.DEFAULT);

                    conn.setRequestProperty("Authorization", basicAuth);

                    if (conn.getResponseCode() != 200) {
                        Toast.makeText(LoginActivity.this, basicAuth, Toast.LENGTH_SHORT).show();
                        throw new IOException(conn.getResponseMessage());
                    }

                    Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
