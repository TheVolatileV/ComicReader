package stem.comicreader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;

import static stem.comicreader.MangaFragment.EXTRA_COMIC_ID;



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
        UUID mangaID = (UUID) getIntent().getSerializableExtra(EXTRA_COMIC_ID);
        Manga manga = MangaList.get().getManga(mangaID);
        Log.d("pages", manga.getPages().toString() + "");
        pages = (ArrayList) manga.getPages();

        ReaderPagerAdapter mReaderPagerActivity = new ReaderPagerAdapter(this);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mReaderPagerActivity);
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
