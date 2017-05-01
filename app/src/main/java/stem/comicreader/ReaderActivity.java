package stem.comicreader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;


/**
 * The ReaderActivity program implements an application that
 * simply displays a collection of images on an Android device.
 *
 * @author Wilton Latham
 * @version 3.1
 * @since   2017-03-10
 */
public class ReaderActivity extends Activity {

    public static ArrayList<Page> pages = new ArrayList<Page>();
    public static AsyncTask<Void, Void, Void> mTask;

    /**
     * This method is used to initialize the activity
     * @param savedInstanceState object that permits activities to restore to a previous state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);

        Intent intent = getIntent();
        String seriesTitle = intent.getStringExtra("seriesTitle"); //if it's a string you stored.
        int chapterNum = intent.getIntExtra("chapterNum",0); //if it's a string you stored.


        mTask = new MyAsyncTask(this, seriesTitle, chapterNum).execute(); // will return KeyVal


    }

    /**
     * This method is used to specify an action when the back button is pressed.
     */
    @Override
    public void onBackPressed(){
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * The MyAsyncTask program prepares a separate thread for intensive operations.
     *
     * @author Wilton Latham
     * @version 1.0
     * @since   2017-04-30
     */
    private class MyAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private Context mContext;
        private String seriesTitle;
        private int chapterNum;

        /**
         * This method is used to construct the values for this class.
         * @param mContext reference to object that allows access to application-specific resources
         * @param seriesTitle reference to a specific Manga title
         * @param chapterNum reference to a specific Manga chapter number
         */
        MyAsyncTask(Context mContext, String seriesTitle, int chapterNum){
            this.mContext = mContext;
            this.seriesTitle = seriesTitle;
            this.chapterNum = chapterNum;

        }

        public Context getmContext(){return this.mContext;}
        public String getSeriesTitle(){return this.seriesTitle;}
        public int getChapterNum(){return this.chapterNum;}

        /**
         * This method is used invoke a background thread (perform a background computation)
         * immediately
         */
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Manga mnga = new Manga(getSeriesTitle(),new String[]{""});

                MangareaderDownloader manga = new MangareaderDownloader(mnga, "");

                pages.addAll(manga.getChapterPages(getChapterNum()));
            } catch (IOException e) {
                // unhandled exception, something went horribly wrong
            }
            return null;
        }
        /**
         * This method runs the UI thread after doInBackground(Void...
         */
        @Override
        protected void onPostExecute(Void result) {
            ReaderPagerAdapter mReaderPagerActivity = new ReaderPagerAdapter(getmContext());
            ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
            mViewPager.setAdapter(mReaderPagerActivity);

        }
    }

    /**
     * The ReaderPagerAdapter program populates pages inside of a ViewPager
     *
     * @author Wilton Latham
     * @version 2.0
     * @since   2017-03-10
     */
    private class ReaderPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;

        /**
         * This method is used to construct the values for this class.
         * @param context reference to object that allows access to application-specific resources
         */
        ReaderPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        /**
         * This method is used to discern the number of resources
         * in the mResources data structure.
         * @return int This returns the length of mResources.
         */
        @Override
        public int getCount() {
            return pages.size();
        }

        /**
         * This method is used to discern whether a page View is associated
         * with a specific key object.
         * @return boolean This returns the state of a page View.
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        /**
         * This method is used populate View objects into a ViewGroup object.
         * @return Object This returns a View object.
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Context gContext = getApplicationContext();

            // We may simply need to replace the next two lines to ensure functionality with Picasso4
            ImageView imageView = new ImageView(ReaderActivity.this);
            Picasso.with(gContext).load(pages.get(position).getUrl()).fit()
                    .into(imageView);;
            container.addView(imageView);

            return imageView;
        }

        /**
         * This method is used to remove View objects from a ViewGroup object.
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
