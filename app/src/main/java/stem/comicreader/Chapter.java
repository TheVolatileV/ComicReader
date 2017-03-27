package stem.comicreader;

import java.util.List;

/**
 * Created by elijahhursey on 2/17/17.
 */

public class Chapter
{
    private String mangaName;
    private List<Page> pageList;

    public Chapter(String mangaName)
    {
        this.mangaName = mangaName;
    }
    public Chapter(String mangaName, List<Page> pageList)
    {
        this.mangaName = mangaName;
        this.pageList = pageList;
    }

    public String getName()
    {
        return mangaName;
    }

    public List<Page> getPageList()
    {
        return pageList;
    }
}
