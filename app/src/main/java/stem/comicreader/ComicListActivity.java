package stem.comicreader;

import android.support.v4.app.Fragment;

/**
 * Created by TAG on 11/4/2016.
 */

public class ComicListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new ComicListFragment();
    }
}
