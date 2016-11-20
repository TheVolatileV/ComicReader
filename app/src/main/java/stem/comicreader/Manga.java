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
	private String seriesTitle;
    private String[] altTitles;
    private UUID uuid;
	private String id;
    private String seriesSynonyms;
    private int seriesChapters;
    private int seriesVolumes;
    private String seriesStartDate;
    private String seriesEndDate;
    private String seriesImage;
    private int userReadChapters;
    private int userReadVolumes;
    private String userSeriesStartDate;
    private String userSeriesEndDate;
    private int userScore;
    private boolean finished;



	public Manga(String seriesTitle, String[] altTitles)
	{
        this.uuid = UUID.randomUUID();
		this.seriesTitle = seriesTitle;
        this.altTitles = altTitles;
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

	public String getSeriesTitle()
	{
		return seriesTitle;
	}

    public String[] getAltTitles() {
        return altTitles;
    }

	public Map<Integer, Set<Page>> getManga() {
		return manga;
	}

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

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

    public String getUserSeriesEndDate() {
        return userSeriesEndDate;
    }

    public void setUserSeriesEndDate(String userSeriesEndDate) {
        this.userSeriesEndDate = userSeriesEndDate;
    }

    public int getUserReadVolumes() {
        return userReadVolumes;
    }

    public void setUserReadVolumes(int userReadVolumes) {
        this.userReadVolumes = userReadVolumes;
    }

    public String getUserSeriesStartDate() {
        return userSeriesStartDate;
    }

    public void setUserSeriesStartDate(String userSeriesStartDate) {
        this.userSeriesStartDate = userSeriesStartDate;
    }

    public String getSeriesSynonyms() {
        return seriesSynonyms;
    }

    public void setSeriesSynonyms(String seriesSynonyms) {
        this.seriesSynonyms = seriesSynonyms;
    }

    public int getSeriesChapters() {
        return seriesChapters;
    }

    public void setSeriesChapters(int seriesChapters) {
        this.seriesChapters = seriesChapters;
    }

    public int getSeriesVolumes() {
        return seriesVolumes;
    }

    public void setSeriesVolumes(int seriesVolumes) {
        this.seriesVolumes = seriesVolumes;
    }

    public String getSeriesStartDate() {
        return seriesStartDate;
    }

    public void setSeriesStartDate(String seriesStartDate) {
        this.seriesStartDate = seriesStartDate;
    }

    public String getSeriesEndDate() {
        return seriesEndDate;
    }

    public void setSeriesEndDate(String seriesEndDate) {
        this.seriesEndDate = seriesEndDate;
    }

    public String getSeriesImage() {
        return seriesImage;
    }

    public void setSeriesImage(String seriesImage) {
        this.seriesImage = seriesImage;
    }

    public int getUserReadChapters() {
        return userReadChapters;
    }

    public void setUserReadChapters(int userReadChapters) {
        this.userReadChapters = userReadChapters;
    }

    public void setSeriesTitle(String seriesTitle) {
        this.seriesTitle = seriesTitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean isFinished()
    {
        return finished;
    }
}
