/**
 * Write a description of class ReaderPageListener here.
 *
 * @author Tyler McFadden
 * @version 0.1
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
