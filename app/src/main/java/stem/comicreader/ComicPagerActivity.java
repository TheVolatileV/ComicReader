package stem.comicreader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by TAG on 11/13/2016.
 */

public class ComicPagerActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private ArrayList<Comic> mComics;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        mComics = ComicList.get(this).getComics();

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Comic comic = mComics.get(position);
                return ComicFragment.newInstance(comic.getId());
            }

            @Override
            public int getCount() {
                return mComics.size();
            }
        });

        UUID comicId = (UUID)getIntent().getSerializableExtra(ComicFragment.EXTRA_COMIC_ID);
        for (int i = 0; i < mComics.size(); i++) {
            if (mComics.get(i).getId().equals(comicId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            public void onPageScrollStateChanged(int state){}

            public void onPageScrolled(int pos, float posOffset, int posOffsetPixels) {}

            public void onPageSelected(int pos) {
                Comic comic = mComics.get(pos);
                if (comic.getTitle() != null) {
                    setTitle(comic.getTitle());
                }
            }
        });


    }
}
