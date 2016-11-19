package stem.comicreader;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Singleton for interfacing with MAL.
 *
 * Created by elijahhursey on 11/13/16.
 */

public class MAL { //Params, Progress, Result
    public boolean userpassIsValid;
    private String encodedUserPass;
    private String username;
    private String password;


    private static MAL mal = new MAL();

    private MAL() {
        userpassIsValid = false;
        encodedUserPass = "";
        username = "";
        password = "";
    }

    public static MAL getInstance() {
        return mal;
    }


    public List<Manga> getUserMangaList() {
        new ThreadedListGetter().execute(encodedUserPass);

        return null;
    }

    private class ThreadedListGetter extends AsyncTask<String, Void, List<Manga>> {

        @Override
        protected List<Manga> doInBackground(String... params) {
            List<Manga> list = new ArrayList<>();

            try {
                String url = "https://myanimelist.net/malappinfo.php?u=" + username + "&status=all&type=manga";
                Document doc = Jsoup.connect(url).get();
                Elements elem = doc.select("series_title");
                for (Element link : elem) {
                    list.add(new Manga(link.text()));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return list;
        }
    }

    public void authenticate(String username, String password) {
        if (username.compareTo("") == 0 && password.compareTo("") == 0) {
            this.username = "testeraccount";
            this.password = "redorangeispurpl";
        }
        else {
            this.username = username;
            this.password = password;
        }
        new ThreadedAuthenticator().execute(this.username, this.password);
    }
    private class ThreadedAuthenticator extends AsyncTask<Object, Object, Boolean> {

        private LoginActivity loginActivity;

        @Override
        protected Boolean doInBackground(Object... params) {
            try {
                if (!userpassIsValid) {
                    URL url = new URL("https://myanimelist.net/api/account/verify_credentials.xml");
                    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

                    conn.setRequestMethod("GET");

                    String userpass = params[0] + ":" + params[1];
                    encodedUserPass = "Basic "
                            + Base64.encodeToString(userpass.getBytes(), Base64.DEFAULT);
                    conn.setRequestProperty("Authorization", encodedUserPass);

                    if (conn.getResponseCode() != 200) {
                        return false;
                    }
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            loginActivity = LoginActivity.getLoginActivity();
            loginActivity.isValid(result);
        }
    }

}
