package stem.comicreader;
import java.util.*;

/**
 * This class represents from a single chapter to the entirety of a manga.
 * For each key in the Map there exists a chapter with a set of pages.
 *
 * Created by elijahhursey on 10/14/16.
 */
public class Manga
{
	private Map<Integer, Set<Page>> manga;
	private String name;


	public Manga(String name)
	{
		this.name = name;
		this.manga = new TreeMap<>();
	}

	/**
	 * Adds an element to the Map.
	 *
	 * @param chapterNum
	 * @param pages
	 */
	public void add(int chapterNum, Set<Page> pages)
	{
		if (!manga.containsKey(chapterNum))
		{
			manga.put(chapterNum, pages);
		}
	}

	public String getName()
	{
		return name;
	}

	public Map<Integer, Set<Page>> getManga() {
		return manga;
	}

	/**
	 * Downloads all chapters that are currently stored in the Map.
	 */
//	public void download(String path)
//	{
//		for(Map.Entry<Integer, Set<Page>> entry : manga.entrySet())
//		{
//			Integer chapter = entry.getKey();
//			Set<Page> pages = entry.getValue();
//			for (Page page : pages)
//			{
//				FileMaker.storeImageIntoFS(path, page);
//			}
//		}
//	}

	public String toString()
	{
		String s = "";
		for (Map.Entry<Integer, Set<Page>> entry : manga.entrySet())
		{
			Integer key = entry.getKey();
			s += "Chapter: " + key + " \n";
			Set<Page> pages = entry.getValue();
			for (Page page : pages)
			{
				s += page.toString();
			}
		}
		return s;
	}
}
