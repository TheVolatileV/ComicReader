package stem.comicreader;

import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by TAG on 11/4/2016.
 */

public class ComicListFragment extends ListFragment {
    private ArrayList<Comic> mComics;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.comics_title);
        mComics = ComicList.get(getActivity()).getComics();

        ArrayAdapter<Comic> adapter =
                new ArrayAdapter<Comic>(getActivity(),
                        android.R.layout.simple_list_item_1,
                        mComics);
        setListAdapter(adapter);
    }

}
