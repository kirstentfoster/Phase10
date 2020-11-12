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

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

/** @author Kirsten Foster, Alexis Molina, Emily Hoppe, Grace Penunuri
 */

public class Phase10HumanPlayer extends GameHumanPlayer implements OnClickListener {

    // the android activity that we are running
    private GameMainActivity myActivity;

    /* instance variables */

    // These variables will reference widgets that will be modified during play
    private TextView    playerScoreTextView = null;
    private TextView    oppScoreTextView    = null;
    private TextView    turnTotalTextView   = null;
    private TextView    messageTextView     = null;
    private Button      phaseButton         = null; // do we want this to be an image button?
    private Button      hitButton           = null;
    public Button discardButton       = null;
    private ImageButton drawFaceUpImageButton = null;
    private ImageButton drawFaceDownImageButton = null;
    private ImageButton Hand1 = null;
    private ImageButton Hand2 = null;
    private ImageButton Hand3 = null;
    private ImageButton Hand4 = null;
    private ImageButton Hand5 = null;
    private ImageButton Hand6 = null;
    private ImageButton Hand7 = null;
    private ImageButton Hand8 = null;
    private ImageButton Hand9 = null;
    private ImageButton Hand10 = null;
    private ImageButton Hand11 = null;
    private ArrayList<Card> selected = null;

    private Phase10GameState state;







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
        createHand((Phase10GameState) info);
        ((Phase10GameState) info).drawDiscard((MainActivity) myActivity);
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
            PhaseAction p = new PhaseAction(this, this.selected); //Need to get info of phaseContent from gui
            selected.clear();
            game.sendAction(p);
        }
        if(button.equals(hitButton)) {
            HitAction p = new HitAction(this, this.selected.get(0), 1); //need to get info of hit card and player to hit from gui
            selected.clear();
            game.sendAction(p);
        }
        if(button.equals(discardButton)) {
            DiscardAction p = new DiscardAction(this, this.selected.get(0)); //need to get info of discard card from gui
            selected.clear();
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

        //select cards
        if(button.equals(Hand1)){
            selected.add(state.getPlayer1Hand().get(0));
        }
        if(button.equals(Hand2)){
            selected.add(state.getPlayer1Hand().get(1));
        }
        if(button.equals(Hand3)){
            selected.add(state.getPlayer1Hand().get(2));
        }
        if(button.equals(Hand4)){
            selected.add(state.getPlayer1Hand().get(3));
        }
        if(button.equals(Hand5)){
            selected.add(state.getPlayer1Hand().get(4));
        }
        if(button.equals(Hand6)){
            selected.add(state.getPlayer1Hand().get(5));
        }
        if(button.equals(Hand7)){
            selected.add(state.getPlayer1Hand().get(6));
        }
        if(button.equals(Hand8)){
            selected.add(state.getPlayer1Hand().get(7));
        }
        if(button.equals(Hand9)){
            selected.add(state.getPlayer1Hand().get(8));
        }
        if(button.equals(Hand10)){
            selected.add(state.getPlayer1Hand().get(9));
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
        this.discardButton = activity.findViewById(R.id.DiscardButton);


        //Listen for button presses
        drawFaceUpImageButton.setOnClickListener(this);
        drawFaceDownImageButton.setOnClickListener(this);
        hitButton.setOnClickListener(this);
        phaseButton.setOnClickListener(this);
        discardButton.setOnClickListener(this);

    }//setAsGui

    private void createHand(Phase10GameState gs){
        Hand1 = myActivity.findViewById(R.id.PlayerHand1);
        Hand2 = myActivity.findViewById(R.id.PlayerHand2);
        Hand3 = myActivity.findViewById(R.id.PlayerHand3);
        Hand4 = myActivity.findViewById(R.id.PlayerHand4);
        Hand5 = myActivity.findViewById(R.id.PlayerHand5);
        Hand6 = myActivity.findViewById(R.id.PlayerHand6);
        Hand7 = myActivity.findViewById(R.id.PlayerHand7);
        Hand8 = myActivity.findViewById(R.id.PlayerHand8);
        Hand9 = myActivity.findViewById(R.id.PlayerHand9);
        Hand10 = myActivity.findViewById(R.id.PlayerHand10);
        Hand11 = myActivity.findViewById(R.id.PlayerHand11);
        if(this.playerNum+1==1) {
            Hand1.setImageResource(gs.testSlot(gs.getPlayer1Hand().get(0)));
            Hand2.setImageResource(gs.testSlot(gs.getPlayer1Hand().get(1)));
            Hand3.setImageResource(gs.testSlot(gs.getPlayer1Hand().get(2)));
            Hand4.setImageResource(gs.testSlot(gs.getPlayer1Hand().get(3)));
            Hand5.setImageResource(gs.testSlot(gs.getPlayer1Hand().get(4)));
            Hand6.setImageResource(gs.testSlot(gs.getPlayer1Hand().get(5)));
            Hand7.setImageResource(gs.testSlot(gs.getPlayer1Hand().get(6)));
            Hand8.setImageResource(gs.testSlot(gs.getPlayer1Hand().get(7)));
            Hand9.setImageResource(gs.testSlot(gs.getPlayer1Hand().get(8)));
            Hand10.setImageResource(gs.testSlot(gs.getPlayer1Hand().get(9)));
        }
        else{
            Hand1.setImageResource(gs.testSlot(gs.getPlayer2Hand().get(0)));
            Hand2.setImageResource(gs.testSlot(gs.getPlayer2Hand().get(1)));
            Hand3.setImageResource(gs.testSlot(gs.getPlayer2Hand().get(2)));
            Hand4.setImageResource(gs.testSlot(gs.getPlayer2Hand().get(3)));
            Hand5.setImageResource(gs.testSlot(gs.getPlayer2Hand().get(4)));
            Hand6.setImageResource(gs.testSlot(gs.getPlayer2Hand().get(5)));
            Hand7.setImageResource(gs.testSlot(gs.getPlayer2Hand().get(6)));
            Hand8.setImageResource(gs.testSlot(gs.getPlayer2Hand().get(7)));
            Hand9.setImageResource(gs.testSlot(gs.getPlayer2Hand().get(8)));
            Hand10.setImageResource(gs.testSlot(gs.getPlayer2Hand().get(9)));
        }
        Hand11.setImageResource(0);

    }



}// class HumanPlayer