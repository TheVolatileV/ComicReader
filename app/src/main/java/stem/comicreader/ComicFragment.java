package stem.comicreader;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.UUID;

/**
 * Created by TAG on 11/2/2016.
 */

public class ComicFragment extends Fragment {
    public static final String EXTRA_COMIC_ID = "stem.comicreader";

    private Comic mComic;
    private TextView mTitleField;
    private Button mChaptersButton;
    private CheckBox mFinishedCheckBox;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mComic = new Comic();
        //UUID comicId = (UUID)getActivity().getIntent().getSerializableExtra(EXTRA_COMIC_ID);
        UUID comicId = (UUID)getArguments().getSerializable(EXTRA_COMIC_ID);
        mComic = ComicList.get(getActivity()).getComic(comicId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_comic, parent, false);

        mTitleField = (TextView)v.findViewById(R.id.comic_title);
        mTitleField.setText(mComic.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mComic.setTitle(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mChaptersButton = (Button)v.findViewById(R.id.comic_chapters);
        //mChaptersButton.setText(mComic.getChapters());
        //mChaptersButton.setEnabled(false);

        mFinishedCheckBox = (CheckBox)v.findViewById(R.id.comic_finished);
        mFinishedCheckBox.setChecked(mComic.isFinished());
        mFinishedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mComic.setFinished(isChecked);
            }
        });

        return v;
    }

    public static ComicFragment newInstance(UUID comicId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_COMIC_ID, comicId);

        ComicFragment fragment = new ComicFragment();
        fragment.setArguments(args);

        return fragment;
    }
}
