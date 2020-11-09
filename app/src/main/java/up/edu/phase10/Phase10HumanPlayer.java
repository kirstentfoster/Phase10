/**
 * @author Kirsten Foster, Alexis Molina, Emily Hoppe, Grace Penunuri
 * Holds information about each card (particularly number and color)
 * Also includes getters/setters for card info
 * Includes a "to string" method to explain what the card is
 */

package up.edu.phase10;

import up.edu.phase10.Framework.GameHumanPlayer;
import up.edu.phase10.Framework.GameMainActivity;
import up.edu.phase10.R;
import up.edu.phase10.Framework.GameInfo;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View.OnClickListener;

/** @author Kirsten Foster, Alexis Molina, Emily Hoppe, Grace Penunuri
 */

public class Phase10HumanPlayer extends GameHumanPlayer implements OnClickListener {

    /* instance variables */

    // These variables will reference widgets that will be modified during play
    private TextView    playerScoreTextView = null;
    private TextView    oppScoreTextView    = null;
    private TextView    turnTotalTextView   = null;
    private TextView    messageTextView     = null;
    private Button      phaseButton         = null; // do we want this to be an image button?
    private Button      hitButton           = null;
    private Button      discardButton       = null;
    private ImageButton drawFaceUpImageButton =null;
    private ImageButton drawFaceDownImageButton = null;


    // the android activity that we are running
    private GameMainActivity myActivity;

    /**
     * constructor does nothing extra
     */
    public Phase10HumanPlayer(String name) {
        super(name);
    }

    /**
     * Returns the GUI's top view object
     *
     * @return
     * 		the top object in the GUI's view heirarchy
     */
    public View getTopView() {
        return myActivity.findViewById(R.id.top_gui_layout);
    }

    /**
     * callback method when we get a message (e.g., from the game)
     *
     * @param info
     * 	the message
     */
    @Override
    public void receiveInfo(GameInfo info) {
        if(!(info instanceof Phase10GameState)) {
            flash(Color.RED, 500);
            return;
        }
        //TO DO should phase counters be here??
    }//receiveInfo


    /**
     * this method gets called when the user clicks the die or hold button. It
     * creates a new phaseAction or hitAction and sends it to the game.
     *
     * @param button
     * 		the button that was clicked
     */
    public void onClick(View button) {
        if(button.equals(phaseButton)) {
            PhaseAction p = new PhaseAction(this); //Need to get info of phaseContent from gui
            game.sendAction(p);
        }
        if(button.equals(hitButton)) {
            HitAction p = new HitAction(this); //need to get info of hit card and player to hit from gui
            game.sendAction(p);
        }
        if(button.equals(discardButton)) {
            DiscardAction p = new DiscardAction(this); //need to get info of discard card from gui
            game.sendAction(p);
        }

        // for the drawFaceDown && drawFace up : click a card, in a specific area (i.e. should the
        // onClick area be fixed like drawPile area is??

        if(button.equals(drawFaceDownImageButton)) {
            DrawFaceDownAction p = new DrawFaceDownAction(this);
            game.sendAction(p);
        }
        if(button.equals(drawFaceUpImageButton)) {
            DrawFaceUpAction p = new DrawFaceUpAction(this);
            game.sendAction(p);
        }
    }// onClick

    /**
     * callback method--our game has been chosen/rechosen to be the GUI,
     * called from the GUI thread
     *
     * @param activity
     * 		the activity under which we are running
     */
    public void setAsGui(GameMainActivity activity) {

        // remember the activity
        myActivity = activity;

        // Load the layout resource for our GUI
        activity.setContentView(R.layout.phase10_layout);

        //Initialize the widget reference member variables
        this.drawFaceUpImageButton= (ImageButton)activity.findViewById(R.id.DiscardPile);
        this.drawFaceDownImageButton= (ImageButton)activity.findViewById(R.id.DrawPile);
        this.hitButton= (Button)activity.findViewById(R.id.HitButton);
        this.phaseButton= (Button)activity.findViewById(R.id.PlayButton);
        this.discardButton= (Button)activity.findViewById(R.id.DiscardButton);

        //Listen for button presses
        drawFaceUpImageButton.setOnClickListener(this);
        drawFaceDownImageButton.setOnClickListener(this);
        hitButton.setOnClickListener(this);
        phaseButton.setOnClickListener(this);
        discardButton.setOnClickListener(this);

    }//setAsGui

}// class HumanPlayer