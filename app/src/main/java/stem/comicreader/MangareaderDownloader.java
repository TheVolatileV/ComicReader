package stem.comicreader;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class MangareaderDownloader {

	protected final static String SOURCE = "http://www.mangareader.net/";

	private String mangaName;
	private String url;
	private String path;

	public MangareaderDownloader(String mangaName, String path) throws IOException
	{
		this.mangaName = mangaName;
		this.url = SOURCE + mangaName;
		this.path = path;
		//manga.download(path);
	}

	/**
	 * Stores each page for each chapter into a Manga object.
	 *
	 * @return the manga object
	 * @throws IOException
	 */
	public Manga getManga() throws IOException
	{
		List<Integer> chapterList = getChapterList();
		Manga manga = new Manga(mangaName);
		for(Iterator it = chapterList.iterator(); it.hasNext(); )
		{
			int i = (int)it.next();
			manga.add(i, getChapterPages(i));
		}
		return manga;
	}

	/**
	 * Gets a list of each chapter for the specified manga.
	 *
	 * @return a list of each chapter
	 * @throws IOException
	 */
	public List<Integer> getChapterList() throws IOException
	{
		Document chapterPage = Jsoup.connect(url).get();
		Elements chapterElements = chapterPage.select("div[id=chapterlist] a[href*=" + mangaName + "]");
		List<Integer> chapterList = new ArrayList<>(500);
		Pattern p = Pattern.compile("\\d");
		for(Element link : chapterElements)
		{
			String s = link.attr("href");
			Matcher m = p.matcher(s);
			//finds a number of any length followed by closing a tag
			if(m.find())
			{
				s = m.group();
				chapterList.add(Integer.parseInt(s));
			}
		}
		return chapterList;
	}

	/**
	 * Gets the list of pages for a specified chapter.
	 *
	 * @param chapter
	 * @return a list of the pages.
	 * @throws IOException
	 */
	public List<Integer> getPagesList(int chapter) throws IOException
	{
		Document page = Jsoup.connect(url + "/" + chapter).get();
		Elements pageListElements = page.select("select[id=pagemenu] option");
		List<Integer> pageList = new ArrayList<>(60);
		for (Element link : pageListElements)
		{
			pageList.add(Integer.parseInt(link.text()));
		}
		return pageList;
	}

	/**
	 * Gets each page for a specified chapter of manga.
	 *
	 * @param chapter
	 * @return a set containing a Page object for each page in the chapter.
	 * @throws IOException
	 */
	public Set<Page> getChapterPages(int chapter) throws IOException {
		List<Integer> pageList = getPagesList(chapter);
		Document page;
		Elements pageImageElements;
		Set<Page> pages = new TreeSet<>();
		for (Iterator it = pageList.iterator(); it.hasNext(); ) {
			int i = (int) it.next();
			page = Jsoup.connect(url + "/" + chapter + "/" + i).get();
			pageImageElements = page.select("img[name=img]");
			pages.add(new Page(mangaName, chapter, i, pageImageElements.attr("src")));
		}
		return pages;
	}
}