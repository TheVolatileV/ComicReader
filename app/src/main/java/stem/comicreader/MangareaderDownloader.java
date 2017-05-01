package stem.comicreader;

import android.util.Log;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import stem.comicreader.exceptions.MangareaderUnavailable;
import stem.comicreader.exceptions.MangaDoesNotExist;


public class MangareaderDownloader {

	protected final static String BASEURL = "http://www.mangareader.net";

	private Manga manga;
	private String mangaName;
	public String url;
	private String path;

	public MangareaderDownloader(Manga manga, String path) throws IOException
	{
		this.manga = manga;
		this.mangaName = this.manga.getSeriesTitle();
		try {
			this.url = BASEURL + findMangaRelativeURL();
        } catch(MangareaderUnavailable e) {
			// notify user that mangareader is down currently
		} catch (MangaDoesNotExist e) {
			// notify user that manga is not on mangareader
		} catch (Exception e) {
			// unhandled exception, something went horribly wrong
		}
		this.path = path;
		//manga.download(path);
	}

	/**
	 * Returns the Manga object associated with this MangaReader instance
	 *
	 * @return the manga object
	 * @throws IOException
	 */
	public Manga getManga() throws IOException
	{
		return this.manga;
	}

	/**
	 * Gets a list of each chapter for the specified manga.
	 *
	 * @return a list of each chapter
	 * @throws IOException
	 */
	public List<Integer> getChapterList() throws IOException
	{
        Log.i("MangaList", url);
		Document chapterPage = Jsoup.connect(url).get();
		Elements chapterElements = chapterPage.select("div[id=chapterlist] a[href]"); //a[href*=/" + this.mangaName.substring(0,mangaName.length() - 2) +"/]");
		List<Integer> chapterList = new ArrayList<>(500);
		Pattern p = Pattern.compile("\\/[0-9]+");
        Log.i("MangaList", chapterElements.toString() + " ");
		for(Element link : chapterElements)
		{
			String s = link.attr("href");
			Log.i("MangaList", s + " Number");
			Matcher m = p.matcher(s);
			//finds a number of any length followed by closing a tag
			if(m.find())
			{
				s = m.group();
				s = s.substring(1,s.length());
				Log.i("MangaList", s + " Parsed integer");
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

	/**
	 * Returns the relative URL for the matching manga in MangaReader from the user's MAL list.
	 *
	 * Note: This function utilises Mangareader's internal search API, which returns
	 * a newline (\n) delimited string of each possible result.  Each result is a
	 * pipe (|) delimited string that contains metadata about each result.
	 * The metadata is as follows: position 0 is the first 40 characters of the title,
	 * 1 is the image url, 2 is the full title, 3 is the author/artist, 4 is the url
	 * extension, and 5 is the internal ID of the manga
	 *
	 * @return
     */
	private String findMangaRelativeURL() throws Exception {
		String[] altTitles = this.manga.getAltTitles();

		String baseSearchUrl = String.format("%s/actions/search/", BASEURL);

		int attempts = 0;

		String title = this.manga.getSeriesTitle();

		while (attempts < altTitles.length) {

			try {
				String searchUrl = baseSearchUrl + "?q=" + title;
				Element content = Jsoup.connect(searchUrl).get().body();
				if (content.text().equals("")) { // No match found for title

					if (attempts + 1 == altTitles.length) { // ran out of attempts, manga not found
						break;
					}
					title = altTitles[attempts];
					attempts++;
				} else {
					return content.text().split("\\n")[0].split("\\|")[4];
				}
			} catch (HttpStatusException e) {
				if (e.getStatusCode() >= 500) { // server-side error
					throw new MangareaderUnavailable("Mangareader appears to be down");
				}
			}
		}

		throw new MangaDoesNotExist("Could not find manga " + this.manga.getSeriesTitle());
	}

}
