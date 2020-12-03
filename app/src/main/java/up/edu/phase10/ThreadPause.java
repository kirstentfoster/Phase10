
package up.edu.phase10;

import android.util.Log;

/**
 External Citation
 Date: 12/1/20
 Problem: I wanted to slow down the AI's actions so that the human
    player can follow them more easily

 Resource: https://www.includehelp.com/java-programs/pause-the-execution.aspx
 Solution: I used the example from this link and adapted it to my code.
 */

/**
 * @author Kirsten Foster, Alexis Molina, Emily Hoppe, Grace Penunuri
 *
 * Manages pausing the AI to create more user-friendly play
 */
class ThreadPause {
    /**
     * pauses the thread for an amount of time
     *
     * @param sec multiplier for the timer to pause by, when
     *            sec = 1, it will pause for .3 seconds
     */
    public void wait(int sec) {
        Log.d("Thread Pause", "Attempting to sleep");
        try {
            Thread.currentThread().sleep(sec * 300);
        } catch (InterruptedException e) {
            Log.d("Thread Pause", "Sleep failed");
            e.printStackTrace();
        }
    }
}