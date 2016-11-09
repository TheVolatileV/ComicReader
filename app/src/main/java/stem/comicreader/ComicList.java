package stem.comicreader;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by TAG on 11/4/2016.
 */

public class ComicList {
    private static ComicList sComicList;
    private Context mAppContext;
    private ArrayList<Comic> mComics;

    private ComicList(Context appContext) {
        mAppContext = appContext;
        mComics = new ArrayList<Comic>();
        for (int i = 0; i < 10; i++) {
            Comic c = new Comic();
            c.setTitle("Comic " + i);
            c.setFinished(i % 2 == 0);
            mComics.add(c);
        }
    }

    public static ComicList get(Context c) {
        if (sComicList == null) {
            sComicList = new ComicList(c.getApplicationContext());
        }
        return sComicList;
    }

    public ArrayList<Comic> getComics() {
        return mComics;
    }

    public Comic getComic(UUID id) {
        for (Comic c : mComics) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }
}
