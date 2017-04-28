/**
 * Write a description of class ReaderPageListener here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
private int currentPage;

public static class ReaderPageListener extends SimpleOnPageChangeListener
{
    // instance variables - replace the example below with your own
   public void onPageSelected(int position) {
        Log.i(TAG, "page selected " + position);
            currentPage = position;
     }

}
