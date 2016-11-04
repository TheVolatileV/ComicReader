package stem.comicreader;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by TAG on 11/2/2016.
 */

public class Comic {
    private UUID mId;
    private String mTitle;
    private boolean mFinished;
    private String mChapters;

    public Comic() {
        //Generates unique identifier
        mId = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return mTitle;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public boolean isFinished() {
        return mFinished;
    }

    public void setFinished(boolean finished) {
        mFinished = finished;
    }

    public String getChapters() {
        return mChapters;
    }

    public void setChapters(String chapters) {
        mChapters = chapters;
    }

}
