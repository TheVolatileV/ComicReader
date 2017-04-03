package stem.comicreader;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

/**
 * The ReaderActivity program implements and application that
 * simply displays a collection of images from an Android device.
 *
 * @author Wilton Latham
 * @version 0.9
 * @since   2017-03-10
 */
public class ReaderActivity extends Activity {

    public static final String[] URLS = {
            "http://i10.mangareader.net/clannad/28/clannad-1399758.jpg",
            "http://i10.mangareader.net/clannad/28/clannad-1399759.jpg",
            "http://i3.mangareader.net/clannad/28/clannad-1399760.jpg",
            "http://i3.mangareader.net/clannad/28/clannad-1399761.jpg",
            "http://i3.mangareader.net/clannad/28/clannad-1399762.jpg"};


    /**
     * This method is used to initialize the activity
     * @param savedInstanceState object that permits activities to restore to a previous state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);

        ReaderPagerAdapter mReaderPagerAdapter = new ReaderPagerAdapter(this);

        ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mReaderPagerAdapter);
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
            return URLS.length;
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
            Picasso.with(gContext).load(URLS[position]).fit()
                    .into(imageView);

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
