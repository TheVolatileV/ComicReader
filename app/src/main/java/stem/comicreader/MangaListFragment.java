package stem.comicreader;

import android.content.Intent;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TAG on 11/4/2016.
 */

public class MangaListFragment extends ListFragment {
    private List<Manga> mangas;
    private static final String TAG = "MangaListFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.comics_title);
        mangas = MangaList.get().getMangas();

        MangaAdapter adapter = new MangaAdapter(mangas);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        //Comic c = (Comic)(getListAdapter()).getItem(pos);
        Manga m = ((MangaAdapter)getListAdapter()).getItem(pos);
        //Log.d(TAG, c.getTitle() + " was clicked");
        //Intent i = new Intent(getActivity(), MangaActivity.class);
        Intent i = new Intent(getActivity(), MangaPagerActivity.class);
        i.putExtra(MangaFragment.EXTRA_COMIC_ID, m.getId());
        startActivity(i);
    }

    private class MangaAdapter extends ArrayAdapter<Manga> {
        public MangaAdapter(List<Manga> mangas) {
            super(getActivity(), 0, mangas);
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_comic, null);
            }

            Manga m = getItem(pos);

            TextView titleTextView =
                    (TextView)convertView.findViewById(R.id.comic_list_item_titleTextView);
            titleTextView.setText(m.getSeriesTitle());
            CheckBox finishedCheckBox =
                    (CheckBox)convertView.findViewById(R.id.comic_list_item_finishedCheckBox);
            finishedCheckBox.setChecked(m.isFinished());

            return convertView;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MangaAdapter)getListAdapter()).notifyDataSetChanged();
    }

}
