package stem.comicreader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by TAG on 11/13/2016.
 */

public class MangaPagerActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private List<Manga> mangas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        mangas = MangaList.get().getMangas();

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Manga manga = mangas.get(position);
                return MangaFragment.newInstance(manga.getUuid());
            }

            @Override
            public int getCount() {
                return mangas.size();
            }
        });

        UUID comicId = (UUID)getIntent().getSerializableExtra(MangaFragment.EXTRA_COMIC_ID);
        for (int i = 0; i < mangas.size(); i++) {
            if (mangas.get(i).getUuid().equals(comicId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            public void onPageScrollStateChanged(int state){}

            public void onPageScrolled(int pos, float posOffset, int posOffsetPixels) {}

            public void onPageSelected(int pos) {
                Manga manga = mangas.get(pos);
                if (manga.getSeriesTitle() != null) {
                    setTitle(manga.getSeriesTitle());
                }
            }
        });


    }
}
