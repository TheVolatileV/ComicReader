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

/**
 * Created by TAG on 11/2/2016.
 */

public class ComicFragment extends Fragment {
    private Comic mComic;
    private EditText mTitleField;
    private Button mChaptersButton;
    private CheckBox mFinishedCheckBox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mComic = new Comic();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_comic, parent, false);

        mTitleField = (EditText)v.findViewById(R.id.comic_title);
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
        mChaptersButton.setText(mComic.getChapters().toString());
        mChaptersButton.setEnabled(false);

        mFinishedCheckBox = (CheckBox)v.findViewById(R.id.comic_finished);
        mFinishedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mComic.setFinished(isChecked);
            }
        });

        return v;
    }
}
