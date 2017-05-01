package stem.comicreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * The ProcessMenu program implements an application that generates and displays a list of buttons
 * with click listeners on an Android device.
 *
 * @author Wilton Latham
 * @version 1.0.1
 * @since   2017-04-30
 */
public class ProcessMenu extends Activity implements OnClickListener{

    private static final int MY_BUTTON = 9000;

    /**
     * This method is used to initialize the activity
     * @param savedInstanceState object that permits activities to restore to a previous state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_menu);

        LinearLayout ll = (LinearLayout)findViewById(R.id.linearLayout2);

        Intent intent = getIntent();
        final String seriesTitle = intent.getStringExtra("seriesTitle"); //if it's a string you stored.

        //add buttons
        final Button[] b = new Button[5];
        for(int i=0; i<5; i++){
            b[i]  = new Button(this);
            b[i].setText("Chapter " + i);
            b[i].setId(i);
            final int id_ = b[i].getId();
            ll.addView(b[i]);
            Button btn = ((Button) findViewById(id_));
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),
                            "Button clicked index = " + id_, Toast.LENGTH_SHORT)
                            .show();
                    Intent myIntent = new Intent(ProcessMenu.this, ReaderActivity.class);
                    myIntent.putExtra("seriesTitle", seriesTitle);
                    myIntent.putExtra("chapterNum", id_);
                    startActivity(myIntent);
                }
            });
        }


    }

    /**
     * This method is used to specify an action when the back button is pressed.
     */
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(ProcessMenu.this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * This method is used to construct the values for this class.
     * @param v reference to a View object
     */
    public void onClick(View v) {
        Toast toast;
        Log.w("ANDROID DYNAMIC VIEWS:", "View Id: " + v.getId());
        switch (v.getId()) {
            case MY_BUTTON:
                toast = Toast.makeText(this, "Clicked on my dynamically added button!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
                break;
            // More buttons go here (if any) ...

        }
    }

}