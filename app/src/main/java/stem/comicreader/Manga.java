package stem.comicreader;
import android.graphics.Bitmap;

import java.util.*;

import static android.R.id.list;

/**
 * This class represents from a single chapter to the entirety of a manga.
 * For each key in the Map there exists a chapter with a set of pages.
 *
 * Created by elijahhursey on 10/14/16.
 */
public class Manga
{
//    public List<Chapter> getChapterList() {
//        return chapterList;
//    }
//
//    public void setChapterList(List<Chapter> chapterList) {
//        this.chapterList = chapterList;
//    }

    //	private Map<Integer, Set<Page>> manga;


    private List<Integer> chapterList;
    private int workingChapter;
    private List<Page> chaptersPages;
    private UUID uuid;
	private String seriesTitle;
    private String[] altTitles;
	private String seriesId;
    private String seriesSynonyms;
    private int seriesChapters;
    private int seriesVolumes;
    private int seriesStatus;
    private String seriesStartDate;
    private String seriesEndDate;
    private Bitmap seriesImage;
    private String userId;
    private int userReadChapters;
    private int userReadVolumes;
    private String userSeriesStartDate;
    private String userSeriesEndDate;
    private int userScore;
    private int userStatus;



	public Manga(String seriesTitle, String[] altTitles)
	{
        this.uuid = UUID.randomUUID();
		this.seriesTitle = seriesTitle;
        this.altTitles = altTitles;
        chapterList = new ArrayList<>();
        workingChapter = 1;
        chaptersPages = new ArrayList<>();
	}

//	public void add(int chapterNum, Set<Page> pages)
//	{
//		if (!manga.containsKey(chapterNum))
//		{
//			manga.put(chapterNum, pages);
//		}
//	}


    public void setWorkingChapter(int workingChapter) {
        this.workingChapter = workingChapter;
    }

    public int getWorkingChapter() {
        return workingChapter;
    }
    public List<Page> getPages() {
        return chaptersPages;
    }
    public void setChapterList(List<Integer> chapterList) {
        this.chapterList = chapterList;
    }
    public List<Integer> getChapterList() {
        return chapterList;
    }

    public void addChapterPages(List<Page> pages) {
        chaptersPages = pages;
    }
	public String getSeriesTitle()
	{
		return seriesTitle;
	}

    public String[] getAltTitles() {
        return altTitles;
    }

	public String toString()
	{
		String s = "";
//		for (Map.Entry<Integer, Set<Page>> entry : manga.entrySet())
//		{
//			Integer key = entry.getKey();
//			s += "Chapter: " + key + " \n";
//			Set<Page> pages = entry.getValue();
//			for (Page page : pages)
//			{
//				s += page.toString();
//			}
//		}
		return s;
	}

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

    public String getUserEndDate() {
        return userSeriesEndDate;
    }

    public void setUserEndDate(String userSeriesEndDate) {
        this.userSeriesEndDate = userSeriesEndDate;
    }

    public int getUserReadVolumes() {
        return userReadVolumes;
    }

    public void setUserReadVolumes(int userReadVolumes) {
        this.userReadVolumes = userReadVolumes;
    }

    public String getUserStartDate() {
        return userSeriesStartDate;
    }

    public void setUserStartDate(String userSeriesStartDate) {
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

    public int getSeriesStatus() {
        return seriesStatus;
    }

    public void setSeriesStatus(int seriesStatus) {
        this.seriesStatus = seriesStatus;
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

    public Bitmap getSeriesImage() {
        return seriesImage;
    }

    public void setSeriesImage(Bitmap seriesImage) {
        this.seriesImage = seriesImage;
    }

    public String getUserId() {
        return  userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public UUID getUuid() {
        return uuid;
    }

    public List<Integer> getChapterIntegerList() {
        List<Integer> intList = new ArrayList<>(100);
        for (int i = 0; i < chapterList.size(); i++) {
            intList.add(i, i);
        }
        return null;
    }
}
