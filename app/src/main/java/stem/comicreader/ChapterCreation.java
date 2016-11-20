package stem.comicreader;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Evangeline on 11/13/2016.
 */

public class ChapterCreation extends ListActivity {


    public ChapterCreation() throws IOException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chapters);
        getChapterL();


    }


    public void getChapterL() {
        new ThreadedGetChapterList().execute();
    }

    //String[] ex1 = {"Label 1", "Label 2", "Label 3", "Label 4"};



    //List<Integer> mdList = new ArrayList<Integer>(Arrays.asList(1, 2, 3));

    //String[] ex3 = new String[mdList.size()];
    //for (int i = 0; i < mdList.size(); i++) {
    //    ex3[i] = mdList.get(i).toString();
    //Log.d("example1", "Test - Beginning");
    //Log.d("example2", mdList.get(i).toString());
    //}

    private class ThreadedGetChapterList extends AsyncTask<Void, Void, List<Integer>> {

        @Override
        protected List<Integer> doInBackground(Void... params) {
            List<Integer> mdList = null;
            List<Manga> mangaList = null;
            try {
                mangaList = MAL.getInstance().getUserMangaList();
                MangareaderDownloader md = new MangareaderDownloader(mangaList.get(0), "/");
                mdList = md.getChapterList();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return mdList;
        }

        protected void onPostExecute(List<Integer> mdList) {
            try {
                Thread.sleep(5000);
            } catch (Exception e) {}
            ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getListView().getContext(), android.R.layout.simple_list_item_1, mdList);
            getListView().setAdapter(adapter);
        }

    }

}
