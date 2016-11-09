package stem.comicreader;

import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by TAG on 11/4/2016.
 */

public class ComicListFragment extends ListFragment {
    private ArrayList<Comic> mComics;
    private static final String TAG = "ComicListFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.comics_title);
        mComics = ComicList.get(getActivity()).getComics();

        ComicAdapter adapter = new ComicAdapter(mComics);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        //Comic c = (Comic)(getListAdapter()).getItem(pos);
        Comic c = ((ComicAdapter)getListAdapter()).getItem(pos);
        Log.d(TAG, c.getTitle() + " was clicked");
    }

    private class ComicAdapter extends ArrayAdapter<Comic> {
        public ComicAdapter(ArrayList<Comic> comics) {
            super(getActivity(), 0, comics);
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_comic, null);
            }

            Comic c = getItem(pos);

            TextView titleTextView =
                    (TextView)convertView.findViewById(R.id.comic_list_item_titleTextView);
            titleTextView.setText(c.getTitle());
            CheckBox finishedCheckBox =
                    (CheckBox)convertView.findViewById(R.id.comic_list_item_finishedCheckBox);
            finishedCheckBox.setChecked(c.isFinished());

            return convertView;
        }
    }

}
