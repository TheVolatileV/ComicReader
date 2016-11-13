package stem.comicreader;

/**
 * This Class represents a single page of a manga.
 * It implements comparable so that the objects can be sorted correctly when they are put into a TreeSet
 *
 * Created by elijahhursey on 10/14/16.
 */
public class Page implements Comparable<Page>
{
	private int pageNum;
	private String url;
	private String mangaName;
	private int chapterNum;

	public Page(String mangaName, int chapterNum, int pageNum, String url)
	{
		this.chapterNum = chapterNum;
		this.mangaName = mangaName;
		this.pageNum = pageNum;
		this.url = url;
	}

	public int getChapterNum()
	{
		return chapterNum;
	}

	public int getPage()
	{
		return pageNum;
	}

	public String getMangaName() {
		return mangaName;
	}

	public void setPage(int pageNum)
	{
		this.pageNum = pageNum;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public int compareTo(Page page)
	{
		if (page.getPage() < pageNum)
			return 1;
		else if (page.getPage() > pageNum)
			return -1;
		else
			return 0;
	}

	public String toString()
	{
		return "Page Number: " + pageNum + " Link: " + url + " \n";
	}
}