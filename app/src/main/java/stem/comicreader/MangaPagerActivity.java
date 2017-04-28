package stem.comicreader;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static stem.comicreader.MangaFragment.EXTRA_COMIC_ID;

public class MangaPagerActivity extends ListActivity {

    List<Integer> mangaList = null;
    ListView listView;
    public static MangaPagerActivity mangaPagerActivity;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mangaPagerActivity = this;
        UUID mangaID = (UUID)getIntent().getSerializableExtra(EXTRA_COMIC_ID);
        Manga manga = MangaList.get().getManga(mangaID);
        mangaList = manga.getChapterList();
        setListAdapter(new ArrayAdapter<>(this, R.layout.fragment_chapters, mangaList));
        listView = getListView();
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Toast.makeText(getApplicationContext(),((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}













































//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.util.Log;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;

/**
 * Created by TAG on 11/13/2016.
 */

//public class MangaPagerActivity extends FragmentActivity {
//    private ViewPager mViewPager;
//    private List<Manga> mangas;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mViewPager = new ViewPager(this);
//        mViewPager.setId(R.id.viewPager);
//        setContentView(mViewPager);
//
//        mangas = MangaList.get().getMangas();
//
//        FragmentManager fm = getSupportFragmentManager();
//        mViewPager.setAdapter(new FragmentPagerAdapter(fm) {
//            @Override
//            public Fragment getItem(int position) {
//                Manga manga = mangas.get(position);
//                return MangaFragment.newInstance(manga.getUuid());
//            }
//
//            @Override
//            public int getCount() {
//                return mangas.size();
//            }
//        });
//
//        UUID mangaId = (UUID)getIntent().getSerializableExtra(MangaFragment.EXTRA_COMIC_ID);
//        for (int i = 0; i < mangas.size(); i++) {
//            if (mangas.get(i).getUuid().equals(mangaId)) {
//                //LOAD IN DATA HERE MAYBE???
//                mViewPager.setCurrentItem(i);
//                break;
//            }
//        }
//
//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
//            public void onPageScrollStateChanged(int state){}
//
//            public void onPageScrolled(int pos, float posOffset, int posOffsetPixels) {}
//
//            public void onPageSelected(int pos) {
//                Manga manga = mangas.get(pos);
//                if (manga.getSeriesTitle() != null) {
//                    //OR HERE???
//                    MAL mal = MAL.getInstance();
//                    mal.getMangaDetails(manga);
//                    setTitle(manga.getSeriesTitle());
//                }
//            }
//        });
//
//
//    }
//}
