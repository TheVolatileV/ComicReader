package stem.comicreader;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.UUID;
import static stem.comicreader.MangaFragment.UserStatusCodes.COMPLETED;
import static stem.comicreader.MangaFragment.UserStatusCodes.DROPPED;
import static stem.comicreader.MangaFragment.UserStatusCodes.INVALID;
import static stem.comicreader.MangaFragment.UserStatusCodes.ON_HOLD;
import static stem.comicreader.MangaFragment.UserStatusCodes.PLAN_TO_READ;
import static stem.comicreader.MangaFragment.UserStatusCodes.READING;

/**
 * Created by TAG on 11/2/2016.
 */

public class MangaFragment extends Fragment {
    public static final String EXTRA_COMIC_ID = "stem.comicreader";

    private Manga manga;
    private TextView mTitleField;
    private ImageView mImageBox;
    private TextView mDateStarted;
    private TextView mDateFinished;
    private TextView mStatus;
    private TextView mChaptersRead;
    private TextView mTotalChapters;
    private Button mChaptersButton;
    private MAL mal;
    private static MangaFragment mangaFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mal = MAL.getInstance();
        mangaFragment = this;

        //mComic = new Comic();
        //UUID comicId = (UUID)getActivity().getIntent().getSerializableExtra(EXTRA_COMIC_ID);
        UUID mangaId = (UUID)getArguments().getSerializable(EXTRA_COMIC_ID);
        manga = MangaList.get().getManga(mangaId);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_comic, parent, false);

        initialize(v);


//        mTitleField.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                manga.setSeriesTitle(s.toString());
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        //mChaptersButton.setText(mComic.getChapters());
        //mChaptersButton.setEnabled(false);

//        mFinishedCheckBox = (CheckBox)v.findViewById(R.id.comic_finished);
//        mFinishedCheckBox.setChecked(mComic.isFinished());
//        mFinishedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                mComic.setFinished(isChecked);
//            }
//        });

        return v;
    }


    /**
     * Status codes for user
     *  1: Reading
     *  2: Completed
     *  3: On-Hold
     *  4: Dropped
     *  6: Plan to read
     */
    protected enum UserStatusCodes {
        READING("reading", 1),
        COMPLETED("completed", 2),
        ON_HOLD("on hold", 3),
        DROPPED("dropped", 4),
        PLAN_TO_READ("plan to read", 6),
        INVALID("invalid", -1);

        protected final String string;
        protected final int code;

        UserStatusCodes(String string, int code) {
            this.string = string;
            this.code = code;
        }
    }

    private void initialize(View view) {

        mTitleField = (TextView)view.findViewById(R.id.comic_title);
        mTitleField.setText(manga.getSeriesTitle());
        mImageBox = (ImageView)view.findViewById(R.id.comic_img);
        mImageBox.setImageBitmap(manga.getSeriesImage());
        mDateStarted = (TextView)view.findViewById(R.id.start_date);
        mDateStarted.setText(manga.getUserStartDate());
        mDateFinished = (TextView)view.findViewById(R.id.finish_date);
        mDateFinished.setText(manga.getUserEndDate());
        mStatus = (TextView)view.findViewById(R.id.status);
        switch(manga.getUserStatus()) {
            case 1:
                mStatus.setText(READING.string);
                break;
            case 2:
                mStatus.setText(COMPLETED.string);
                break;
            case 3:
                mStatus.setText(ON_HOLD.string);
                break;
            case 4:
                mStatus.setText(DROPPED.string);
                break;
            case 6:
                mStatus.setText(PLAN_TO_READ.string);
                break;
            default:
                mStatus.setText(INVALID.string);
                break;
        }
        mChaptersRead = (TextView)view.findViewById(R.id.chapters_read);
        mChaptersRead.setText(manga.getUserReadChapters() + "");
        mTotalChapters = (TextView)view.findViewById(R.id.total_chapters);
        mTotalChapters.setText(manga.getSeriesChapters() + "");
        mChaptersButton = (Button)view.findViewById(R.id.comic_chapters);
        mChaptersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(mangaFragment.getActivity(), MangaPagerActivity.class);
                intent.putExtra(EXTRA_COMIC_ID, manga.getUuid());
                mangaFragment.startActivity(intent);
            }


        });



    }

    public static MangaFragment getMangaFragment() {
        return mangaFragment;
    }

    public static MangaFragment newInstance(UUID comicId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_COMIC_ID, comicId);
        MangaFragment fragment = new MangaFragment();
        fragment.setArguments(args);

        return fragment;
    }
}
