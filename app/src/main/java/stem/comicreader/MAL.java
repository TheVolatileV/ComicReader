package stem.comicreader;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.RequiresPermission;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.net.ssl.HttpsURLConnection;

import static stem.comicreader.MangaFragment.EXTRA_COMIC_ID;

/**
 * The MAL program implements an application that
 * serves as a Singleton for interfacing with MAL.
 *
 * @author Elijah Hursey
 * @version 1.0
 * @date   2016-11-13
 */
public class MAL { //Params, Progress, Result
    public boolean userpassIsValid;
    private String encodedUserPass;
    private String username;
    private String password;
    private Document userList;

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

    public void getChapterPages(Manga manga) {
        new ThreadedPageGetter().execute(manga);
    }

    /**
     * The ThreadedPageGetter program prepares a separate thread for intensive operations.
     *
     * @author Elijah Hursey
     * @author Wilt Latham
     * @version 1.0
     * @date   2017-05-2
     */
    private class ThreadedPageGetter extends AsyncTask<Manga, Void, Void>
    {
        /**
         * This method is used invoke a background thread (perform a background computation)
         * immediately
         */
        @Override
        protected Void doInBackground(Manga... params) {
            try {
                MangareaderDownloader mangareaderDownloader = new MangareaderDownloader(params[0], "");
                List<Page> pages = new ArrayList<>();

                pages.addAll(mangareaderDownloader.getChapterPages(params[0].getWorkingChapter()));
                Log.d("pages", pages.toString() + "");
                Log.d("pages", params[0].getWorkingChapter() + "");
                params[0].addChapterPages(pages);

                Intent intent = new Intent(MangaPagerActivity.mangaPagerActivity, ReaderActivity.class);
                intent.putExtra(EXTRA_COMIC_ID, params[0].getUuid());
                MangaPagerActivity.mangaPagerActivity.startActivity(intent);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        /**
         * This method runs the UI thread after doInBackground(Void...
         */
        @Override
        protected void onPostExecute(Void result) {

        }
    }



    public void getMangaChapterList(Manga manga) {
        new ThreadedGetChapterList().execute(manga);
    }

    private class ThreadedGetChapterList extends AsyncTask<Manga, Void, Void>
    {
        MangaFragment mangaFragment = MangaFragment.getMangaFragment();
        Intent intent;
        @Override
        protected Void doInBackground(Manga... params) {
            List<Integer> temp = null;
            try {
                MangareaderDownloader mdl = new MangareaderDownloader(params[0], "");
                temp = mdl.getChapterList();
                params[0].setChapterList(temp);
            } catch(IOException e) {
                Log.e("IOException", "Failed to get values from hosting website.");
            }

            intent = new Intent(mangaFragment.getActivity(), MangaPagerActivity.class);
            intent.putExtra(EXTRA_COMIC_ID, params[0].getUuid());
            mangaFragment.startActivity(intent);
            return null;
        }
        @Override
        protected void onPostExecute(Void param) {
        }
    }


    public void getMangaDetails(Manga manga) {
        new ThreadedDetailsGetter().execute(manga);
    }

    /**
    * Retrieves title, id, chapters, volumes, status, start date, end date, and image from database.
    * Also retrieves the mal id, read chapters, read volumes, user start date, user finish date, user score, and user status.
    **/
    private class ThreadedDetailsGetter extends AsyncTask<Manga, Void, Manga>
    {

        @Override
        protected Manga doInBackground(Manga... params) {
            String TAG = "Elements";

            //DB variables
            Elements elem = userList.select("manga:has(series_title:contains(" + params[0].getSeriesTitle() + "))");
            params[0].setSeriesId(elem.select("series_mangadb_id").text());
            params[0].setSeriesChapters(Integer.parseInt(elem.select("series_chapters").text()));
            params[0].setSeriesVolumes(Integer.parseInt(elem.select("series_volumes").text()));
            params[0].setSeriesStatus(Integer.parseInt(elem.select("series_status").text()));
            params[0].setSeriesStartDate(elem.select("series_start").text());
            params[0].setSeriesEndDate(elem.select("series_end").text());
            URL url;
            try {
                url = new URL(elem.select("series_image").text());
                params[0].setSeriesImage(BitmapFactory.decodeStream(url.openStream()));
            } catch (Exception e)
            {
                Log.e(TAG, e.toString());
            }
            //userVariables
            params[0].setUserId(elem.select("my_id").text());
            params[0].setUserReadChapters(Integer.parseInt(elem.select("my_read_chapters").text()));
            params[0].setUserReadVolumes(Integer.parseInt(elem.select("my_read_volumes").text()));
            params[0].setUserStartDate(elem.select("my_start_date").text());
            params[0].setUserEndDate(elem.select("my_finish_date").text());
            params[0].setUserScore(Integer.parseInt(elem.select("my_score").text()));
            params[0].setUserStatus(Integer.parseInt(elem.select("my_status").text()));

            //Tests DB Variables
            Log.d(TAG, "Series ID: " + params[0].getSeriesId());
            Log.d(TAG, "Series Chapters: " + params[0].getSeriesChapters());
            Log.d(TAG, "Series Volumes: " + params[0].getSeriesVolumes());
            Log.d(TAG, "Series Status: " + params[0].getSeriesStatus());
            Log.d(TAG, "Series StartDate: " + params[0].getSeriesStartDate());
            Log.d(TAG, "Series EndDate: " + params[0].getSeriesEndDate());
            Log.d(TAG, "Series ImageLink: " + params[0].getSeriesImage());

            //Tests User Variables
            Log.d(TAG, "User ID: " + params[0].getUserId());
            Log.d(TAG, "User Chapters: " + params[0].getUserReadChapters());
            Log.d(TAG, "User Volumes: " + params[0].getUserReadVolumes());
            Log.d(TAG, "User Status: " + params[0].getUserStatus());
            Log.d(TAG, "User StartDate: " + params[0].getUserStartDate());
            Log.d(TAG, "User EndDate: " + params[0].getUserEndDate());

//            try {
//                MangareaderDownloader mdl = new MangareaderDownloader(params[0], "");
//                List<Integer> temp = mdl.getChapterList();
//                List<Chapter> chapterList = new ArrayList<>();
//                for (Integer i :temp) {
//                    chapterList.add(new Chapter(params[0].getSeriesTitle()));
//                }
//                params[0].setChapterList(chapterList);
//            } catch (IOException e) {
//                Log.e("IOException", "Failed to get values from hosting website.");
//            }
            return params[0];
        }

        @Override
        protected void onPostExecute(Manga result) {
            MangaListFragment mangaListFragment = MangaListFragment.mangaListFragment;
            Intent intent = new Intent(mangaListFragment.getActivity(), MangaActivity.class);
            intent.putExtra(EXTRA_COMIC_ID, result.getUuid());
            mangaListFragment.startActivity(intent);
        }
    }


    /*
    private class ThreadedChapterGetter extends AsyncTask<Manga, Void, List<Chapter>> {

        private Manga myManga;

        protected List<Chapter> doInBackground(Manga... params) {
            List<Integer> chapterList = new ArrayList<>();
            MangareaderDownloader mrd = new MangareaderDownloader(, "test");
            chapterList = params[0].getChapterList();

        }
    }
    */



    public void getUserMangaList() {
        new ThreadedListGetter().execute(encodedUserPass);
    }
    
    /**
    *  Retrieves each manga on the user's list
    **/
    private class ThreadedListGetter extends AsyncTask<String, Void, List<Manga>> {

        private MangaList mangaList;

        @Override
        protected List<Manga> doInBackground(String... params) {
            List<Manga> list = new ArrayList<>();

            try {
                String url = "https://myanimelist.net/malappinfo.php?u=" + username + "&status=all&type=manga";
                userList= Jsoup.connect(url).get();
                Elements elem = userList.select("series_title");
                Elements altTitles = userList.select("series_synonyms");
                for (int i = 0; i < elem.size(); i++) {
                    list.add(new Manga(elem.get(i).text(), altTitles.get(i).text().split(";")));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return list;
        }

        @Override
        protected void onPostExecute(List<Manga> result) {
            LoginActivity loginActivity = LoginActivity.getLoginActivity();
            mangaList = MangaList.get();
            mangaList.setMangas(result);
            LoginActivity.progressBar.setVisibility(View.GONE);
            Intent intent = new Intent(loginActivity, MangaListActivity.class);
            loginActivity.startActivity(intent);
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
    /**
    *  Authenticates the user's provided username and password with myanimelist.com
    **/
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
            if(result) {
                Toast.makeText(loginActivity, "Login Successful", Toast.LENGTH_SHORT).show();
                getUserMangaList();
            } else {
                Toast.makeText(loginActivity, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
