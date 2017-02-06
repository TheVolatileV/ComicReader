package stem.comicreader;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by TAG on 11/4/2016.
 */

public class MangaList
{
    private static MangaList mangaList;
    private List<Manga> mangas;

    private MangaList() {
        mangas = new ArrayList<>();
    }

    public void setMangas(List<Manga> mangas) {
        this.mangas = mangas;
    }

    public static MangaList get() {
        if (mangaList == null) {
            mangaList = new MangaList();
        }
        return mangaList;
    }

    public List<Manga> getMangas() {
        return mangas;
    }

    public Manga getManga(UUID id) {
        for (Manga m : mangas) {
            if (m.getUuid().equals(id)) {
                return m;
            }
        }
        return null;
    }
}
