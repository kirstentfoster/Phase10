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
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 *  @author Kirsten Foster, Alexis Molina, Emily Hoppe, Grace Penunuri
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
    private Button hitSelfButton = null;
    public Button discardButton = null;
    private Button quitButton = null;
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

    private ImageView AIPhase1 = null;
    private ImageView AIPhase2 = null;
    private ImageView AIPhase3 = null;
    private ImageView AIPhase4 = null;
    private ImageView AIPhase5 = null;
    private ImageView AIPhase6 = null;
    private ImageView AIPhase7 = null;
    private ImageView AIPhase8 = null;
    private ImageView AIPhase9 = null;
    private ImageView AIPhase10 = null;
    private ImageView AIPhase11 = null;
    private ImageView AIPhase12 = null;
    private ImageView AIPhase13 = null;
    private ImageView AIPhase14 = null;

    private ImageView PlayerPhase1 = null;
    private ImageView PlayerPhase2 = null;
    private ImageView PlayerPhase3 = null;
    private ImageView PlayerPhase4 = null;
    private ImageView PlayerPhase5 = null;
    private ImageView PlayerPhase6 = null;
    private ImageView PlayerPhase7 = null;
    private ImageView PlayerPhase8 = null;
    private ImageView PlayerPhase9 = null;
    private ImageView PlayerPhase10 = null;
    private ImageView PlayerPhase11 = null;
    private ImageView PlayerPhase12 = null;
    private ImageView PlayerPhase13 = null;
    private ImageView PlayerPhase14 = null;

    private ImageView AIDeckCard1 = null;
    private ImageView AIDeckCard2 = null;
    private ImageView AIDeckCard3 = null;
    private ImageView AIDeckCard4 = null;
    private ImageView AIDeckCard5 = null;
    private ImageView AIDeckCard6 = null;
    private ImageView AIDeckCard7 = null;
    private ImageView AIDeckCard8 = null;
    private ImageView AIDeckCard9 = null;
    private ImageView AIDeckCard10 = null;
    private ImageView AIDeckCard11 = null;

    private ArrayList<Card> selected = new ArrayList<Card>();

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
        state = (Phase10GameState) info;
        createHand();
        createAIPhase();
        createPlayerPhase();
        createAIHand();
        state.drawDiscard((MainActivity) myActivity);
        //TO DO should phase counters be here??
    }//receiveInfo


    /**
     * this method gets called when the user clicks the phase button, the hit button, the
     * discard button, and draws the hands as each hand is created and drawn to the GUI. It
     * creates a new phaseAction, hitAction, discardAction and sends it to the game.
     *
     * @param button
     * 		the button that was clicked
     */
    public void onClick(View button) {

        if(state==null){return;}
        if(button.equals(phaseButton)) {
            if(this.selected.size()>1) {
                PhaseAction p = new PhaseAction(this, this.selected);
                game.sendAction(p);
            }
            selected.clear();
        }
        if(button.equals(hitButton)) {
            if(this.selected.size()==1) {
                if(this.playerNum==0) {
                    HitAction p = new HitAction(this, this.selected.get(0), 1);
                    game.sendAction(p);
                }
                else if(this.playerNum==1) {
                    HitAction p = new HitAction(this, this.selected.get(0), 0);
                    game.sendAction(p);
                }
            }
            selected.clear();
        }
        if(button.equals(hitSelfButton)){
            if(this.selected.size()==1){
                HitAction p = new HitAction(this, this.selected.get(0), this.playerNum);
                game.sendAction(p);
            }
            selected.clear();
        }

        if(button.equals(discardButton)) {
            if(this.selected.size()==1) {
                DiscardAction p = new DiscardAction(this, this.selected.get(0)); //need to get info of discard card from gui
                game.sendAction(p);
            }
            selected.clear();
        }

        if(button.equals(quitButton)){
            //?????????????????????????????
        }


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
        if(button.equals(Hand11)){
            selected.add(state.getPlayer1Hand().get(10));
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
        this.hitSelfButton = (Button) activity.findViewById(R.id.hitOnMe);
        this.phaseButton= (Button)activity.findViewById(R.id.PlayButton);
        this.discardButton = activity.findViewById(R.id.DiscardButton);

        //hands for each player
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
        this.quitButton = activity.findViewById(R.id.QuitButton);

        //AI Phase and drawables
        AIPhase1 = myActivity.findViewById(R.id.AIPhase1);
        AIPhase2 = myActivity.findViewById(R.id.AIPhase2);
        AIPhase3 = myActivity.findViewById(R.id.AIPhase3);
        AIPhase4 = myActivity.findViewById(R.id.AIPhase4);
        AIPhase5 = myActivity.findViewById(R.id.AIPhase5);
        AIPhase6 = myActivity.findViewById(R.id.AIPhase6);
        AIPhase7 = myActivity.findViewById(R.id.AIPhase7);
        AIPhase8 = myActivity.findViewById(R.id.AIPhase8);
        AIPhase9 = myActivity.findViewById(R.id.AIPhase9);
        AIPhase10 = myActivity.findViewById(R.id.AIPhase10);
        AIPhase11 = myActivity.findViewById(R.id.AIPhase11);
        AIPhase12 = myActivity.findViewById(R.id.AIPhase12);
        AIPhase13 = myActivity.findViewById(R.id.AIPhase13);
        AIPhase14 = myActivity.findViewById(R.id.AIPhase14);

        //playerPhases and it's drawables
        PlayerPhase1 = myActivity.findViewById(R.id.playerPhase1);
        PlayerPhase2 = myActivity.findViewById(R.id.playerPhase2);
        PlayerPhase3 = myActivity.findViewById(R.id.playerPhase3);
        PlayerPhase4 = myActivity.findViewById(R.id.playerPhase4);
        PlayerPhase5 = myActivity.findViewById(R.id.playerPhase5);
        PlayerPhase6 = myActivity.findViewById(R.id.playerPhase6);
        PlayerPhase7 = myActivity.findViewById(R.id.playerPhase7);
        PlayerPhase8 = myActivity.findViewById(R.id.playerPhase8);
        PlayerPhase9 = myActivity.findViewById(R.id.playerPhase9);
        PlayerPhase10 = myActivity.findViewById(R.id.playerPhase10);
        PlayerPhase11 = myActivity.findViewById(R.id.playerPhase11);
        PlayerPhase12 = myActivity.findViewById(R.id.playerPhase12);
        PlayerPhase13 = myActivity.findViewById(R.id.playerPhase13);
        PlayerPhase14 = myActivity.findViewById(R.id.playerPhase14);

        //AI decks and their drawables
        AIDeckCard1 = myActivity.findViewById(R.id.AIDeckCard1);
        AIDeckCard2 = myActivity.findViewById(R.id.AIDeckCard2);
        AIDeckCard3 = myActivity.findViewById(R.id.AIDeckCard3);
        AIDeckCard4 = myActivity.findViewById(R.id.AIDeckCard4);
        AIDeckCard5 = myActivity.findViewById(R.id.AIDeckCard5);
        AIDeckCard6 = myActivity.findViewById(R.id.AIDeckCard6);
        AIDeckCard7 = myActivity.findViewById(R.id.AIDeckCard7);
        AIDeckCard8 = myActivity.findViewById(R.id.AIDeckCard8);
        AIDeckCard9 = myActivity.findViewById(R.id.AIDeckCard9);
        AIDeckCard10 = myActivity.findViewById(R.id.AIDeckCard10);
        AIDeckCard11 = myActivity.findViewById(R.id.AIDeckCard11);

        //Listen for button presses
        drawFaceUpImageButton.setOnClickListener(this);
        drawFaceDownImageButton.setOnClickListener(this);
        hitButton.setOnClickListener(this);
        hitSelfButton.setOnClickListener(this);
        phaseButton.setOnClickListener(this);
        discardButton.setOnClickListener(this);
        quitButton.setOnClickListener(this);
        Hand1.setOnClickListener(this);
        Hand2.setOnClickListener(this);
        Hand3.setOnClickListener(this);
        Hand4.setOnClickListener(this);
        Hand5.setOnClickListener(this);
        Hand6.setOnClickListener(this);
        Hand7.setOnClickListener(this);
        Hand8.setOnClickListener(this);
        Hand9.setOnClickListener(this);
        Hand10.setOnClickListener(this);
        Hand11.setOnClickListener(this);

    }//setAsGui


    /**
     *
     * void method that creates the Phase (i.e. the new phase) for
     * the AI, and sets the Phase slot in the GUI to cards the AI
     * has contributed
     *
     *
     */
    private void createAIPhase(){

        if(this.playerNum+1==2) {
            if(state.getPlayer1PhaseContent().size()<1){
                AIPhase1.setImageResource(0);
            }
            else {
                AIPhase1.setImageResource(state.testSlot(state.getPlayer1PhaseContent().get(0)));
            }
            if(state.getPlayer1PhaseContent().size()<2){
                AIPhase2.setImageResource(0);
            }
            else {
                AIPhase2.setImageResource(state.testSlot(state.getPlayer1PhaseContent().get(1)));
            }
            if(state.getPlayer1PhaseContent().size()<3){
                AIPhase3.setImageResource(0);
            }
            else {
                AIPhase3.setImageResource(state.testSlot(state.getPlayer1PhaseContent().get(2)));
            }
            if(state.getPlayer1PhaseContent().size()<4){
                AIPhase4.setImageResource(0);
            }
            else {
                AIPhase4.setImageResource(state.testSlot(state.getPlayer1PhaseContent().get(3)));
            }
            if(state.getPlayer1PhaseContent().size()<5){
                AIPhase5.setImageResource(0);
            }
            else {
                AIPhase5.setImageResource(state.testSlot(state.getPlayer1PhaseContent().get(4)));
            }
            if(state.getPlayer1PhaseContent().size()<6){
                AIPhase6.setImageResource(0);
            }
            else {
                AIPhase6.setImageResource(state.testSlot(state.getPlayer1PhaseContent().get(5)));
            }
            if(state.getPlayer1PhaseContent().size()<7){
                AIPhase7.setImageResource(0);
            }
            else {
                AIPhase7.setImageResource(state.testSlot(state.getPlayer1PhaseContent().get(6)));
            }
            if(state.getPlayer1PhaseContent().size()<8){
                AIPhase8.setImageResource(0);
            }
            else {
                AIPhase8.setImageResource(state.testSlot(state.getPlayer1PhaseContent().get(7)));
            }
            if(state.getPlayer1PhaseContent().size()<9){
                AIPhase9.setImageResource(0);
            }
            else {
                AIPhase9.setImageResource(state.testSlot(state.getPlayer1PhaseContent().get(8)));
            }
            if(state.getPlayer1PhaseContent().size()<10){
                AIPhase10.setImageResource(0);
            }
            else {
                AIPhase10.setImageResource(state.testSlot(state.getPlayer1PhaseContent().get(9)));
            }
            if(state.getPlayer1PhaseContent().size()<11){
                AIPhase11.setImageResource(0);
            }
            else {
                AIPhase11.setImageResource(state.testSlot(state.getPlayer1PhaseContent().get(10)));
            }
            if(state.getPlayer1PhaseContent().size()<12){
                AIPhase12.setImageResource(0);
            }
            else {
                AIPhase12.setImageResource(state.testSlot(state.getPlayer1PhaseContent().get(11)));
            }
            if(state.getPlayer1PhaseContent().size()<13){
                AIPhase13.setImageResource(0);
            }
            else {
                AIPhase13.setImageResource(state.testSlot(state.getPlayer1PhaseContent().get(12)));
            }
            if(state.getPlayer1PhaseContent().size()<14){
                AIPhase14.setImageResource(0);
            }
            else {
                AIPhase14.setImageResource(state.testSlot(state.getPlayer1PhaseContent().get(13)));
            }
        }
        else{
            if(state.getPlayer2PhaseContent().size()<1){
                AIPhase1.setImageResource(0);
            }
            else {
                AIPhase1.setImageResource(state.testSlot(state.getPlayer2PhaseContent().get(0)));
            }
            if(state.getPlayer2PhaseContent().size()<2){
                AIPhase2.setImageResource(0);
            }
            else {
                AIPhase2.setImageResource(state.testSlot(state.getPlayer2PhaseContent().get(1)));
            }
            if(state.getPlayer2PhaseContent().size()<3){
                AIPhase3.setImageResource(0);
            }
            else {
                AIPhase3.setImageResource(state.testSlot(state.getPlayer2PhaseContent().get(2)));
            }
            if(state.getPlayer2PhaseContent().size()<4){
                AIPhase4.setImageResource(0);
            }
            else {
                AIPhase4.setImageResource(state.testSlot(state.getPlayer2PhaseContent().get(3)));
            }
            if(state.getPlayer2PhaseContent().size()<5){
                AIPhase5.setImageResource(0);
            }
            else {
                AIPhase5.setImageResource(state.testSlot(state.getPlayer2PhaseContent().get(4)));
            }
            if(state.getPlayer2PhaseContent().size()<6){
                AIPhase6.setImageResource(0);
            }
            else {
                AIPhase6.setImageResource(state.testSlot(state.getPlayer2PhaseContent().get(5)));
            }
            if(state.getPlayer2PhaseContent().size()<7){
                AIPhase7.setImageResource(0);
            }
            else {
                AIPhase7.setImageResource(state.testSlot(state.getPlayer2PhaseContent().get(6)));
            }
            if(state.getPlayer2PhaseContent().size()<8){
                AIPhase8.setImageResource(0);
            }
            else {
                AIPhase8.setImageResource(state.testSlot(state.getPlayer2PhaseContent().get(7)));
            }
            if(state.getPlayer2PhaseContent().size()<9){
                AIPhase9.setImageResource(0);
            }
            else {
                AIPhase9.setImageResource(state.testSlot(state.getPlayer2PhaseContent().get(8)));
            }
            if(state.getPlayer2PhaseContent().size()<10){
                AIPhase10.setImageResource(0);
            }
            else {
                AIPhase10.setImageResource(state.testSlot(state.getPlayer2PhaseContent().get(9)));
            }
            if(state.getPlayer2PhaseContent().size()<11){
                AIPhase11.setImageResource(0);
            }
            else {
                AIPhase11.setImageResource(state.testSlot(state.getPlayer2PhaseContent().get(10)));
            }
            if(state.getPlayer2PhaseContent().size()<12){
                AIPhase12.setImageResource(0);
            }
            else {
                AIPhase12.setImageResource(state.testSlot(state.getPlayer2PhaseContent().get(11)));
            }
            if(state.getPlayer2PhaseContent().size()<13){
                AIPhase13.setImageResource(0);
            }
            else {
                AIPhase13.setImageResource(state.testSlot(state.getPlayer2PhaseContent().get(12)));
            }
            if(state.getPlayer2PhaseContent().size()<14){
                AIPhase14.setImageResource(0);
            }
            else {
                AIPhase14.setImageResource(state.testSlot(state.getPlayer2PhaseContent().get(13)));
            }
        }
    }

    /**
     * void method createPlayerPhase draws the player's selected phase (if valid)
     * and gets the information, to draw it to the GUI
     *
     */
    private void createPlayerPhase(){

        if(this.playerNum+1==1) {
            if(state.getPlayer1PhaseContent().size()<1){
                PlayerPhase1.setImageResource(0);
            }
            else {
                PlayerPhase1.setImageResource(state.testSlot(state.getPlayer1PhaseContent().get(0)));
            }
            if(state.getPlayer1PhaseContent().size()<2){
                PlayerPhase2.setImageResource(0);
            }
            else {
                PlayerPhase2.setImageResource(state.testSlot(state.getPlayer1PhaseContent().get(1)));
            }
            if(state.getPlayer1PhaseContent().size()<3){
                PlayerPhase3.setImageResource(0);
            }
            else {
                PlayerPhase3.setImageResource(state.testSlot(state.getPlayer1PhaseContent().get(2)));
            }
            if(state.getPlayer1PhaseContent().size()<4){
                PlayerPhase4.setImageResource(0);
            }
            else {
                PlayerPhase4.setImageResource(state.testSlot(state.getPlayer1PhaseContent().get(3)));
            }
            if(state.getPlayer1PhaseContent().size()<5){
                PlayerPhase5.setImageResource(0);
            }
            else {
                PlayerPhase5.setImageResource(state.testSlot(state.getPlayer1PhaseContent().get(4)));
            }
            if(state.getPlayer1PhaseContent().size()<6){
                PlayerPhase6.setImageResource(0);
            }
            else {
                PlayerPhase6.setImageResource(state.testSlot(state.getPlayer1PhaseContent().get(5)));
            }
            if(state.getPlayer1PhaseContent().size()<7){
                PlayerPhase7.setImageResource(0);
            }
            else {
                PlayerPhase7.setImageResource(state.testSlot(state.getPlayer1PhaseContent().get(6)));
            }
            if(state.getPlayer1PhaseContent().size()<8){
                PlayerPhase8.setImageResource(0);
            }
            else {
                PlayerPhase8.setImageResource(state.testSlot(state.getPlayer1PhaseContent().get(7)));
            }
            if(state.getPlayer1PhaseContent().size()<9){
                PlayerPhase9.setImageResource(0);
            }
            else {
                PlayerPhase9.setImageResource(state.testSlot(state.getPlayer1PhaseContent().get(8)));
            }
            if(state.getPlayer1PhaseContent().size()<10){
                PlayerPhase10.setImageResource(0);
            }
            else {
                PlayerPhase10.setImageResource(state.testSlot(state.getPlayer1PhaseContent().get(9)));
            }
            if(state.getPlayer1PhaseContent().size()<11){
                PlayerPhase11.setImageResource(0);
            }
            else {
                PlayerPhase11.setImageResource(state.testSlot(state.getPlayer1PhaseContent().get(10)));
            }
            if(state.getPlayer1PhaseContent().size()<12){
                PlayerPhase12.setImageResource(0);
            }
            else {
                PlayerPhase12.setImageResource(state.testSlot(state.getPlayer1PhaseContent().get(11)));
            }
            if(state.getPlayer1PhaseContent().size()<13){
                PlayerPhase13.setImageResource(0);
            }
            else {
                PlayerPhase13.setImageResource(state.testSlot(state.getPlayer1PhaseContent().get(12)));
            }
            if(state.getPlayer1PhaseContent().size()<14){
                PlayerPhase14.setImageResource(0);
            }
            else {
                PlayerPhase14.setImageResource(state.testSlot(state.getPlayer1PhaseContent().get(13)));
            }
        }
        else{
            if(state.getPlayer2PhaseContent().size()<1){
                PlayerPhase1.setImageResource(0);
            }
            else {
                PlayerPhase1.setImageResource(state.testSlot(state.getPlayer2PhaseContent().get(0)));
            }
            if(state.getPlayer2PhaseContent().size()<2){
                PlayerPhase2.setImageResource(0);
            }
            else {
                PlayerPhase2.setImageResource(state.testSlot(state.getPlayer2PhaseContent().get(1)));
            }
            if(state.getPlayer2PhaseContent().size()<3){
                PlayerPhase3.setImageResource(0);
            }
            else {
                PlayerPhase3.setImageResource(state.testSlot(state.getPlayer2PhaseContent().get(2)));
            }
            if(state.getPlayer2PhaseContent().size()<4){
                PlayerPhase4.setImageResource(0);
            }
            else {
                PlayerPhase4.setImageResource(state.testSlot(state.getPlayer2PhaseContent().get(3)));
            }
            if(state.getPlayer2PhaseContent().size()<5){
                PlayerPhase5.setImageResource(0);
            }
            else {
                PlayerPhase5.setImageResource(state.testSlot(state.getPlayer2PhaseContent().get(4)));
            }
            if(state.getPlayer2PhaseContent().size()<6){
                PlayerPhase6.setImageResource(0);
            }
            else {
                PlayerPhase6.setImageResource(state.testSlot(state.getPlayer2PhaseContent().get(5)));
            }
            if(state.getPlayer2PhaseContent().size()<7){
                PlayerPhase7.setImageResource(0);
            }
            else {
                PlayerPhase7.setImageResource(state.testSlot(state.getPlayer2PhaseContent().get(6)));
            }
            if(state.getPlayer2PhaseContent().size()<8){
                PlayerPhase8.setImageResource(0);
            }
            else {
                PlayerPhase8.setImageResource(state.testSlot(state.getPlayer2PhaseContent().get(7)));
            }
            if(state.getPlayer2PhaseContent().size()<9){
                PlayerPhase9.setImageResource(0);
            }
            else {
                PlayerPhase9.setImageResource(state.testSlot(state.getPlayer2PhaseContent().get(8)));
            }
            if(state.getPlayer2PhaseContent().size()<10){
                PlayerPhase10.setImageResource(0);
            }
            else {
                PlayerPhase10.setImageResource(state.testSlot(state.getPlayer2PhaseContent().get(9)));
            }
            if(state.getPlayer2PhaseContent().size()<11){
                PlayerPhase11.setImageResource(0);
            }
            else {
                PlayerPhase11.setImageResource(state.testSlot(state.getPlayer2PhaseContent().get(10)));
            }
            if(state.getPlayer2PhaseContent().size()<12){
                PlayerPhase12.setImageResource(0);
            }
            else {
                PlayerPhase12.setImageResource(state.testSlot(state.getPlayer2PhaseContent().get(11)));
            }
            if(state.getPlayer2PhaseContent().size()<13){
                PlayerPhase13.setImageResource(0);
            }
            else {
                PlayerPhase13.setImageResource(state.testSlot(state.getPlayer2PhaseContent().get(12)));
            }
            if(state.getPlayer2PhaseContent().size()<14){
                PlayerPhase14.setImageResource(0);
            }
            else {
                PlayerPhase14.setImageResource(state.testSlot(state.getPlayer2PhaseContent().get(13)));
            }
        }
    } //create playerPhaseHand

    /**
     *  method that draws all AI card, face down, so that the human player cannot see such
     *  that the game is more realistic
     */
//    Face down AI hand
//    private void createAIHand(){
//        if(this.playerNum+1==2) {
//            if(state.getPlayer1Hand().size()<1){
//                AIDeckCard1.setImageResource(0);
//            }
//            else {
//                AIDeckCard1.setImageResource(R.drawable.cardback);
//            }
//            if(state.getPlayer1Hand().size()<2){
//                AIDeckCard2.setImageResource(0);
//            }
//            else {
//                AIDeckCard2.setImageResource(R.drawable.cardback);
//            }
//            if(state.getPlayer1Hand().size()<3){
//                AIDeckCard3.setImageResource(0);
//            }
//            else {
//                AIDeckCard3.setImageResource(R.drawable.cardback);
//            }
//            if(state.getPlayer1Hand().size()<4){
//                AIDeckCard4.setImageResource(0);
//            }
//            else {
//                AIDeckCard4.setImageResource(R.drawable.cardback);
//            }
//            if(state.getPlayer1Hand().size()<5){
//                AIDeckCard5.setImageResource(0);
//            }
//            else {
//                AIDeckCard5.setImageResource(R.drawable.cardback);
//            }
//            if(state.getPlayer1Hand().size()<6){
//                AIDeckCard6.setImageResource(0);
//            }
//            else {
//                AIDeckCard6.setImageResource(R.drawable.cardback);
//            }
//            if(state.getPlayer1Hand().size()<7){
//                AIDeckCard7.setImageResource(0);
//            }
//            else {
//                AIDeckCard7.setImageResource(R.drawable.cardback);
//            }
//            if(state.getPlayer1Hand().size()<8){
//                AIDeckCard8.setImageResource(0);
//            }
//            else {
//                AIDeckCard8.setImageResource(R.drawable.cardback);
//            }
//            if(state.getPlayer1Hand().size()<9){
//                AIDeckCard9.setImageResource(0);
//            }
//            else {
//                AIDeckCard9.setImageResource(R.drawable.cardback);
//            }
//            if(state.getPlayer1Hand().size()<10){
//                AIDeckCard10.setImageResource(0);
//            }
//            else {
//                AIDeckCard10.setImageResource(R.drawable.cardback);
//            }
//            if(state.getPlayer1Hand().size()<11){
//                AIDeckCard11.setImageResource(0);
//            }
//            else {
//                AIDeckCard11.setImageResource(R.drawable.cardback);
//            }
//        }
//        else{
//            if(state.getPlayer2Hand().size()<1){
//                AIDeckCard1.setImageResource(0);
//            }
//            else {
//                AIDeckCard1.setImageResource(R.drawable.cardback);
//            }
//            if(state.getPlayer2Hand().size()<2){
//                AIDeckCard2.setImageResource(0);
//            }
//            else {
//                AIDeckCard2.setImageResource(R.drawable.cardback);
//            }
//            if(state.getPlayer2Hand().size()<3){
//                AIDeckCard3.setImageResource(0);
//            }
//            else {
//                AIDeckCard3.setImageResource(R.drawable.cardback);
//            }
//            if(state.getPlayer2Hand().size()<4){
//                AIDeckCard4.setImageResource(0);
//            }
//            else {
//                AIDeckCard4.setImageResource(R.drawable.cardback);
//            }
//            if(state.getPlayer2Hand().size()<5){
//                AIDeckCard5.setImageResource(0);
//            }
//            else {
//                AIDeckCard5.setImageResource(R.drawable.cardback);
//            }
//            if(state.getPlayer2Hand().size()<6){
//                AIDeckCard6.setImageResource(0);
//            }
//            else {
//                AIDeckCard6.setImageResource(R.drawable.cardback);
//            }
//            if(state.getPlayer2Hand().size()<7){
//                AIDeckCard7.setImageResource(0);
//            }
//            else {
//                AIDeckCard7.setImageResource(R.drawable.cardback);
//            }
//            if(state.getPlayer2Hand().size()<8){
//                AIDeckCard8.setImageResource(0);
//            }
//            else {
//                AIDeckCard8.setImageResource(R.drawable.cardback);
//            }
//            if(state.getPlayer2Hand().size()<9){
//                AIDeckCard9.setImageResource(0);
//            }
//            else {
//                AIDeckCard9.setImageResource(R.drawable.cardback);
//            }
//            if(state.getPlayer2Hand().size()<10){
//                AIDeckCard10.setImageResource(0);
//            }
//            else {
//                AIDeckCard10.setImageResource(R.drawable.cardback);
//            }
//            if(state.getPlayer2Hand().size()<11){
//                AIDeckCard11.setImageResource(0);
//            }
//            else {
//                AIDeckCard11.setImageResource(R.drawable.cardback);
//            }
//        }
//    }

//    Shows face up AI hand
    private void createAIHand(){
        if(this.playerNum+1==2) {
            if(state.getPlayer1Hand().size()<1){
                AIDeckCard1.setImageResource(0);
            }
            else {
                AIDeckCard1.setImageResource(state.testSlot(state.getPlayer1Hand().get(0)));
            }
            if(state.getPlayer1Hand().size()<2){
                AIDeckCard2.setImageResource(0);
            }
            else {
                AIDeckCard2.setImageResource(state.testSlot(state.getPlayer1Hand().get(1)));
            }
            if(state.getPlayer1Hand().size()<3){
                AIDeckCard3.setImageResource(0);
            }
            else {
                AIDeckCard3.setImageResource(state.testSlot(state.getPlayer1Hand().get(2)));
            }
            if(state.getPlayer1Hand().size()<4){
                AIDeckCard4.setImageResource(0);
            }
            else {
                AIDeckCard4.setImageResource(state.testSlot(state.getPlayer1Hand().get(3)));
            }
            if(state.getPlayer1Hand().size()<5){
                AIDeckCard5.setImageResource(0);
            }
            else {
                AIDeckCard5.setImageResource(state.testSlot(state.getPlayer1Hand().get(4)));
            }
            if(state.getPlayer1Hand().size()<6){
                AIDeckCard6.setImageResource(0);
            }
            else {
                AIDeckCard6.setImageResource(state.testSlot(state.getPlayer1Hand().get(5)));
            }
            if(state.getPlayer1Hand().size()<7){
                AIDeckCard7.setImageResource(0);
            }
            else {
                AIDeckCard7.setImageResource(state.testSlot(state.getPlayer1Hand().get(6)));
            }
            if(state.getPlayer1Hand().size()<8){
                AIDeckCard8.setImageResource(0);
            }
            else {
                AIDeckCard8.setImageResource(state.testSlot(state.getPlayer1Hand().get(7)));
            }
            if(state.getPlayer1Hand().size()<9){
                AIDeckCard9.setImageResource(0);
            }
            else {
                AIDeckCard9.setImageResource(state.testSlot(state.getPlayer1Hand().get(8)));
            }
            if(state.getPlayer1Hand().size()<10){
                AIDeckCard10.setImageResource(0);
            }
            else {
                AIDeckCard10.setImageResource(state.testSlot(state.getPlayer1Hand().get(9)));
            }
            if(state.getPlayer1Hand().size()<11){
                AIDeckCard11.setImageResource(0);
            }
            else {
                AIDeckCard11.setImageResource(state.testSlot(state.getPlayer1Hand().get(10)));
            }
        }
        else{
            if(state.getPlayer2Hand().size()<1){
                AIDeckCard1.setImageResource(0);
            }
            else {
                AIDeckCard1.setImageResource(state.testSlot(state.getPlayer2Hand().get(0)));
            }
            if(state.getPlayer2Hand().size()<2){
                AIDeckCard2.setImageResource(0);
            }
            else {
                AIDeckCard2.setImageResource(state.testSlot(state.getPlayer2Hand().get(1)));
            }
            if(state.getPlayer2Hand().size()<3){
                AIDeckCard3.setImageResource(0);
            }
            else {
                AIDeckCard3.setImageResource(state.testSlot(state.getPlayer2Hand().get(2)));
            }
            if(state.getPlayer2Hand().size()<4){
                AIDeckCard4.setImageResource(0);
            }
            else {
                AIDeckCard4.setImageResource(state.testSlot(state.getPlayer2Hand().get(3)));
            }
            if(state.getPlayer2Hand().size()<5){
                AIDeckCard5.setImageResource(0);
            }
            else {
                AIDeckCard5.setImageResource(state.testSlot(state.getPlayer2Hand().get(4)));
            }
            if(state.getPlayer2Hand().size()<6){
                AIDeckCard6.setImageResource(0);
            }
            else {
                AIDeckCard6.setImageResource(state.testSlot(state.getPlayer2Hand().get(5)));
            }
            if(state.getPlayer2Hand().size()<7){
                AIDeckCard7.setImageResource(0);
            }
            else {
                AIDeckCard7.setImageResource(state.testSlot(state.getPlayer2Hand().get(6)));
            }
            if(state.getPlayer2Hand().size()<8){
                AIDeckCard8.setImageResource(0);
            }
            else {
                AIDeckCard8.setImageResource(state.testSlot(state.getPlayer2Hand().get(7)));
            }
            if(state.getPlayer2Hand().size()<9){
                AIDeckCard9.setImageResource(0);
            }
            else {
                AIDeckCard9.setImageResource(state.testSlot(state.getPlayer2Hand().get(8)));
            }
            if(state.getPlayer2Hand().size()<10){
                AIDeckCard10.setImageResource(0);
            }
            else {
                AIDeckCard10.setImageResource(state.testSlot(state.getPlayer2Hand().get(9)));
            }
            if(state.getPlayer2Hand().size()<11){
                AIDeckCard11.setImageResource(0);
            }
            else {
                AIDeckCard11.setImageResource(state.testSlot(state.getPlayer2Hand().get(10)));
            }
        }
    } //drawFaceUp cards end

    /**
     * this method creates the Hand of each player on the GUI
     * making sure that the imageResource (the cards) are displayed properly to
     * their respective information
     *
     *
     */
    private void createHand(){

        if(this.playerNum+1==1) {
            if(state.getPlayer1Hand().size()<1){
                Hand1.setImageResource(0);
            }
            else {
                Hand1.setImageResource(state.testSlot(state.getPlayer1Hand().get(0)));
            }
            if(state.getPlayer1Hand().size()<2){
                Hand2.setImageResource(0);
            }
            else {
                Hand2.setImageResource(state.testSlot(state.getPlayer1Hand().get(1)));
            }
            if(state.getPlayer1Hand().size()<3){
                Hand3.setImageResource(0);
            }
            else {
                Hand3.setImageResource(state.testSlot(state.getPlayer1Hand().get(2)));
            }
            if(state.getPlayer1Hand().size()<4){
                Hand4.setImageResource(0);
            }
            else {
                Hand4.setImageResource(state.testSlot(state.getPlayer1Hand().get(3)));
            }
            if(state.getPlayer1Hand().size()<5){
                Hand5.setImageResource(0);
            }
            else {
                Hand5.setImageResource(state.testSlot(state.getPlayer1Hand().get(4)));
            }
            if(state.getPlayer1Hand().size()<6){
                Hand6.setImageResource(0);
            }
            else {
                Hand6.setImageResource(state.testSlot(state.getPlayer1Hand().get(5)));
            }
            if(state.getPlayer1Hand().size()<7){
                Hand7.setImageResource(0);
            }
            else {
                Hand7.setImageResource(state.testSlot(state.getPlayer1Hand().get(6)));
            }
            if(state.getPlayer1Hand().size()<8){
                Hand8.setImageResource(0);
            }
            else {
                Hand8.setImageResource(state.testSlot(state.getPlayer1Hand().get(7)));
            }
            if(state.getPlayer1Hand().size()<9){
                Hand9.setImageResource(0);
            }
            else {
                Hand9.setImageResource(state.testSlot(state.getPlayer1Hand().get(8)));
            }
            if(state.getPlayer1Hand().size()<10){
                Hand10.setImageResource(0);
            }
            else {
                Hand10.setImageResource(state.testSlot(state.getPlayer1Hand().get(9)));
            }
            if(state.getPlayer1Hand().size()<11){
                Hand11.setImageResource(0);
            }
            else {
                Hand11.setImageResource(state.testSlot(state.getPlayer1Hand().get(10)));
            }
        }
        else{
            if(state.getPlayer2Hand().size()<1){
                Hand1.setImageResource(0);
            }
            else {
                Hand1.setImageResource(state.testSlot(state.getPlayer2Hand().get(0)));
            }
            if(state.getPlayer2Hand().size()<2){
                Hand2.setImageResource(0);
            }
            else {
                Hand2.setImageResource(state.testSlot(state.getPlayer2Hand().get(1)));
            }
            if(state.getPlayer2Hand().size()<3){
                Hand3.setImageResource(0);
            }
            else {
                Hand3.setImageResource(state.testSlot(state.getPlayer2Hand().get(2)));
            }
            if(state.getPlayer2Hand().size()<4){
                Hand4.setImageResource(0);
            }
            else {
                Hand4.setImageResource(state.testSlot(state.getPlayer2Hand().get(3)));
            }
            if(state.getPlayer2Hand().size()<5){
                Hand5.setImageResource(0);
            }
            else {
                Hand5.setImageResource(state.testSlot(state.getPlayer2Hand().get(4)));
            }
            if(state.getPlayer2Hand().size()<6){
                Hand6.setImageResource(0);
            }
            else {
                Hand6.setImageResource(state.testSlot(state.getPlayer2Hand().get(5)));
            }
            if(state.getPlayer2Hand().size()<7){
                Hand7.setImageResource(0);
            }
            else {
                Hand7.setImageResource(state.testSlot(state.getPlayer2Hand().get(6)));
            }
            if(state.getPlayer2Hand().size()<8){
                Hand8.setImageResource(0);
            }
            else {
                Hand8.setImageResource(state.testSlot(state.getPlayer2Hand().get(7)));
            }
            if(state.getPlayer2Hand().size()<9){
                Hand9.setImageResource(0);
            }
            else {
                Hand9.setImageResource(state.testSlot(state.getPlayer2Hand().get(8)));
            }
            if(state.getPlayer2Hand().size()<10){
                Hand10.setImageResource(0);
            }
            else {
                Hand10.setImageResource(state.testSlot(state.getPlayer2Hand().get(9)));
            }
            if(state.getPlayer2Hand().size()<11){
                Hand11.setImageResource(0);
            }
            else {
                Hand11.setImageResource(state.testSlot(state.getPlayer2Hand().get(10)));
            }
        }


    } // createHand end



}// class HumanPlayer