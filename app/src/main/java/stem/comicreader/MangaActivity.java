package stem.comicreader;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.UUID;

public class MangaActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {

        UUID comicId = (UUID)getIntent().getSerializableExtra(MangaFragment.EXTRA_COMIC_ID);

        return MangaFragment.newInstance(comicId);
    }
}
