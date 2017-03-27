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

/**
 * The ReaderActivity program implements and application that
 * simply displays a collection of images from an Android device.
 *
 * @author Wilton Latham
 * @version 0.9
 * @since   2017-03-10
 */
public class ReaderActivity extends Activity {

    private static int[] mResources = {
            R.drawable.clannad_1399758,
            R.drawable.clannad_1399759,
            R.drawable.clannad_1399760,
            R.drawable.clannad_1399761,
            R.drawable.clannad_1399762};


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
     * @version 1.0
     * @since   2017-03-10
     */
    class ReaderPagerAdapter extends PagerAdapter {

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
            return mResources.length;
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
            View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

            // We may simply need to replace the next two lines to ensure functionality with Picasso
            ImageView imageView = (ImageView) itemView.findViewById(R.id.pager);
            imageView.setImageResource(mResources[position]);

            container.addView(itemView);

            return itemView;
        }

        /**
         * This method is used to remove View objects from a ViewGroup object.
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }
}
