/**
 * @author Kirsten Foster, Alexis Molina, Emily Hoppe, Grace Penunuri
 * Manages pausing the AI to create more user-friendly play
 */
package up.edu.phase10;
/**
 External Citation
 Date: 12/1/20
 Problem: I wanted to slow down the AI's actions so that the human
    player can follow them more easily

 Resource: https://www.includehelp.com/java-programs/pause-the-execution.aspx
 Solution: I used the example from this link and adapted it to my code.
 */
class ThreadPause {
    /**
     * pauses the thread for an amount of time
     *
     * @param sec multiplier for the timer to pause by, when
     *            sec = 1, it will pause for .3 seconds
     */
    public void wait(int sec) {
        try {
            Thread.currentThread().sleep(sec * 300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}