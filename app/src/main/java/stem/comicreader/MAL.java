package stem.comicreader;

import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Singleton for interfacing with MAL.
 *
 * Created by elijahhursey on 11/13/16.
 */

public class MAL { //Params, Progress, Result
    private boolean userpassIsValid;
    private String encodedUserPass = "";

    private static MAL mal = new MAL();

    private MAL() {
        userpassIsValid = false;
    }

    public static MAL getInstance() {
        return mal;
    }



    public boolean authenticate(String username, String password) {
        new ThreadedAuthenticator().execute(username, password);
        return userpassIsValid;
    }
    private class ThreadedAuthenticator extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                if(!userpassIsValid) {
                    URL url = new URL("https://myanimelist.net/api/account/verify_credentials.xml");
                    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

                    conn.setRequestMethod("GET");

                    String userpass = params[0] + ":" + params[1];
                    encodedUserPass = "Basic "
                            + Base64.encodeToString(userpass.getBytes(), Base64.DEFAULT);

                    conn.setRequestProperty("Authorization", encodedUserPass);

                    if (conn.getResponseCode() != 200) {
                        userpassIsValid = false;
                        return false;
                    }

                    userpassIsValid = true;
                    return true;
                } else {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            userpassIsValid = result;
        }
    }

}
