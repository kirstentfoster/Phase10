/**
 * @author Kirsten Foster, Alexis Molina, Emily Hoppe, Grace Penunuri
 *  this class extens the GameHumanPlayer and implements onClickListener so
 *  that the hands, images relative to the cards that will be played, the buttons
 *  the human player will implement and information of the human player is
 *  recieved and sent to the game
 */

package up.edu.phase10;

import up.edu.phase10.Framework.GameHumanPlayer;
import up.edu.phase10.Framework.GameMainActivity;
import up.edu.phase10.Framework.GameInfo;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *  @author Kirsten Foster, Alexis Molina, Emily Hoppe, Grace Penunuri
 */

public class Phase10HumanPlayer extends GameHumanPlayer implements OnClickListener {

    // the android activity that we are running
    private GameMainActivity myActivity;

    boolean flashed;
    final static boolean faceUp = false; //use to show AI cards

    /* instance variables */

    // These variables will reference widgets that will be modified during play
    private TextView    playerScoreTextView = null;
    private TextView    oppScoreTextView    = null;
    private TextView    phaseView     = null;
    private TextView turnPart = null;
    private Button      phaseButton         = null;
    private Button      hitButton           = null;
    private Button hitSelfButton = null;
    public Button discardButton = null;
    private Button quitButton = null;
    private Button restartButton = null;
    private Button helpButton = null;
    private Button phasesButton = null;
    private Button cardPoints = null;
    private Button troubleshoot = null;
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
    ArrayList<ImageButton> Hand = new ArrayList<ImageButton>();

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
    private ImageView AIPhase15 = null;
    private ImageView AIPhase16 = null;

    private ArrayList<ImageView> AIphase = new ArrayList<ImageView>();

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
    private ImageView PlayerPhase15 = null;
    private ImageView PlayerPhase16 = null;

    private ArrayList<ImageView> playerPhase = new ArrayList<ImageView>();

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
    private ArrayList<ImageView> AIDeck = new ArrayList<ImageView>();

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
        flashed = false;
        state = (Phase10GameState) info;
        createHand();
        createAIPhase();
        createPlayerPhase();
        if(faceUp == true) {
            createAIHandUp();
        }
        else{
            createAIHandDown();
        }
        state.drawDiscard((MainActivity) myActivity);

        //print info about phase and player turn
        if(playerNum == 0) {
            if(state.getTurnId()==0){
                switch(state.getTurnStage()){
                    case 1:
                        this.turnPart.setText("It's your turn! Draw a card!");
                        break;
                    case 2:
                        if(!state.getPlayer1HasPhased()) {
                            this.turnPart.setText("You drew a card! Now phase if you can or discard!");
                            break;
                        }
                    case 3:
                        if((this.playerNum==0&&state.getPlayer1HasPhased()) ||
                                this.playerNum==1&&state.getPlayer2HasPhased()) {
                            this.turnPart.setText("You've phased! Now hit if you can or discard!");
                        }
                        else{
                            state.setTurnStage(2);
                        }
                        break;
                    case 4:
                        this.turnPart.setText("Discard a card!");
                }
            }
            else{
                this.turnPart.setText("Wait for the AI to play!");
            }
            switch(state.getPlayer1Phase()){
                case 1:
                    phaseView.setText("Phase " + state.getPlayer1Phase() + "\n" + state.phase.getPhase1());
                    break;
                case 2:
                    phaseView.setText("Phase " + state.getPlayer1Phase() + "\n" + state.phase.getPhase2());
                    break;
                case 3:
                    phaseView.setText("Phase " + state.getPlayer1Phase() + "\n" + state.phase.getPhase3());
                    break;
                case 4:
                    phaseView.setText("Phase " + state.getPlayer1Phase() + "\n" + state.phase.getPhase4());
                    break;
                case 5:
                    phaseView.setText("Phase " + state.getPlayer1Phase() + "\n" + state.phase.getPhase5());
                    break;
                case 6:
                    phaseView.setText("Phase " + state.getPlayer1Phase() + "\n" + state.phase.getPhase6());
                    break;
                case 7:
                    phaseView.setText("Phase " + state.getPlayer1Phase() + "\n" + state.phase.getPhase7());
                    break;
                case 8:
                    phaseView.setText("Phase " + state.getPlayer1Phase() + "\n" + state.phase.getPhase8());
                    break;
                case 9:
                    phaseView.setText("Phase " + state.getPlayer1Phase() + "\n" + state.phase.getPhase9());
                    break;
                case 10:
                    phaseView.setText("Phase " + state.getPlayer1Phase() + "\n" + state.phase.getPhase10());
                    break;
            }
        }
        else if(playerNum==1){
            if(state.getTurnId()==1){
                switch(state.getTurnStage()){
                    case 1:
                        this.turnPart.setText("It's your turn! Draw a card!");
                        break;
                    case 2:
                        if(!state.getPlayer2HasPhased()) {
                            this.turnPart.setText("You drew a card! Now phase if you can or discard!");
                            break;
                        }
                    case 3:
                            this.turnPart.setText("You've phased! Now hit if you can or discard!");
                            break;
                    case 4:
                        this.turnPart.setText("Discard a card!");
                }
            }
            else{
                this.turnPart.setText("Wait for the AI to play!");
            }
            switch(state.getPlayer2Phase()){
                case 1:
                    phaseView.setText("Phase " + state.getPlayer2Phase() + "\n" + state.phase.getPhase1());
                    break;
                case 2:
                    phaseView.setText("Phase " + state.getPlayer2Phase() + "\n" + state.phase.getPhase2());
                    break;
                case 3:
                    phaseView.setText("Phase " + state.getPlayer2Phase() + "\n" + state.phase.getPhase3());
                    break;
                case 4:
                    phaseView.setText("Phase " + state.getPlayer2Phase() + "\n" + state.phase.getPhase4());
                    break;
                case 5:
                    phaseView.setText("Phase " + state.getPlayer2Phase() + "\n" + state.phase.getPhase5());
                    break;
                case 6:
                    phaseView.setText("Phase " + state.getPlayer2Phase() + "\n" + state.phase.getPhase6());
                    break;
                case 7:
                    phaseView.setText("Phase " + state.getPlayer2Phase() + "\n" + state.phase.getPhase7());
                    break;
                case 8:
                    phaseView.setText("Phase " + state.getPlayer2Phase() + "\n" + state.phase.getPhase8());
                    break;
                case 9:
                    phaseView.setText("Phase " + state.getPlayer2Phase() + "\n" + state.phase.getPhase9());
                    break;
                case 10:
                    phaseView.setText("Phase " + state.getPlayer2Phase() + "\n" + state.phase.getPhase10());
                    break;
            }
        }

        //print scores:
        if(playerNum==0){
            playerScoreTextView.setText("Your score is: " + state.getPlayer1Score() +
                    "\nYou are on phase: " + state.getPlayer1Phase());
            oppScoreTextView.setText("Your opponent's score is: " + state.getPlayer2Score() +
                    "\nThey are on phase: " + state.getPlayer2Phase());
        }
        else if(playerNum==1){
            playerScoreTextView.setText("Your score is:\n" + state.getPlayer2Score() +
                    "\nYou are on phase: " + state.getPlayer2Phase());
            oppScoreTextView.setText("Your opponent's score is:\n" + state.getPlayer1Score() +
                    "\nThey are on phase: " + state.getPlayer1Phase());
        }
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
            if(!state.getPlayerHasDrawn()){
                flash(Color.parseColor("#ffc0cb"), 100);
            }
            if(this.selected.size()>1) {
                PhaseAction p = new PhaseAction(this, this.selected);
                game.sendAction(p);
            }
            selected.clear();
            for(ImageButton b : Hand){
                b.setBackgroundColor(Color.parseColor("#00ffffff"));
            }

        }
        if(button.equals(quitButton)){//end program
            System.exit(0);
        }
        if(button.equals(restartButton)){ //back to game screen
            myActivity.recreate();
        }
        if(button.equals(hitButton)) {
            if(!state.getPlayerHasDrawn()){
                flash(Color.parseColor("#ffc0cb"), 100);
                flashed = true;
            }
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
            else{
                if(!flashed) {
                    flash(Color.parseColor("#ffff00"), 100);
                }
            }
            selected.clear();
            for(ImageButton b : Hand){
                b.setBackgroundColor(Color.parseColor("#00ffffff"));
            }
            flashed = false;
        }
        if(button.equals(hitSelfButton)){
            if(!state.getPlayerHasDrawn()){
                flash(Color.parseColor("#ffc0cb"), 100);
                flashed = true;
            }
            if(this.selected.size()==1){
                HitAction p = new HitAction(this, this.selected.get(0), this.playerNum);
                game.sendAction(p);
            }
            else{
                if(!flashed)
                flash(Color.parseColor("#ffff00"), 100);
            }
            selected.clear();
            for(ImageButton b : Hand){
                b.setBackgroundColor(Color.parseColor("#00ffffff"));
            }
            flashed = false;
        }

        if(button.equals(discardButton)) {
            if(!state.getPlayerHasDrawn()){
                flash(Color.parseColor("#ffc0cb"), 100);
                flashed = true;
            }
            if(this.selected.size()==1) {
                DiscardAction p = new DiscardAction(this, this.selected.get(0)); //need to get info of discard card from gui
                game.sendAction(p);
            }
            else{
                if(!flashed) {
                    flash(Color.parseColor("#ffff00"), 100);
                }
            }
            selected.clear();
            for(ImageButton b : Hand){
                b.setBackgroundColor(Color.parseColor("#00ffffff"));
            }
            flashed = false;
        }


        if(button.equals(drawFaceDownImageButton)) {
            DrawFaceDownAction p = new DrawFaceDownAction(this);
            if(state.getPlayerHasDrawn()){
                flash(Color.BLUE, 100);
            }
            game.sendAction(p);
        }
        if(button.equals(drawFaceUpImageButton)) {
            DrawFaceUpAction p = new DrawFaceUpAction(this);
            if(state.getPlayerHasDrawn()){
                flash(Color.BLUE, 100);
            }
            game.sendAction(p);
        }

        if(button.equals(helpButton)){
            this.displayHelp(getTopView());
        }
        if(button.equals(phasesButton)){
            this.displayPhases(getTopView());
        }
        if(button.equals(cardPoints)){
            this.displayPoints(getTopView());
        }
        if(button.equals(troubleshoot)){
            this.displayTroubleshooting(getTopView());
        }

        //select cards
        int i = 0;
        for(ImageButton b : Hand){
            if(button.equals(b)){
                selectCard(b,i,selected);
            }
            i++;
        }
    }// onClick

    /**
     * highlights or unhighlights card and adds to selected
     * @param cardNum passes which card is being selected
     * @param selected passes array list of all selected cards
     */
    public void selectCard(ImageButton b, int cardNum, ArrayList<Card> selected){
        if(playerNum==1) {
            if (!selected.contains(state.getPlayer2Hand().get(cardNum))) {
                selected.add(state.getPlayer2Hand().get(cardNum));
                b.setBackgroundColor(Color.parseColor("#FF9C27B0"));
            } else {
                selected.remove(state.getPlayer2Hand().get(cardNum));
                b.setBackgroundColor(Color.parseColor("#00000000"));
            }
        }
        else if(playerNum==0){
            if (!selected.contains(state.getPlayer1Hand().get(cardNum))) {
                selected.add(state.getPlayer1Hand().get(cardNum));
                b.setBackgroundColor(Color.parseColor("#FF9C27B0"));
            } else {
                selected.remove(state.getPlayer1Hand().get(cardNum));
                b.setBackgroundColor(Color.parseColor("#00000000"));
            }
        }
    }

    /**
     External Citation
     Date: 11/19/20
     Problem: Could not figure out how to create overlay

     Resource: https://howtodoinjava.com/java/collections/arraylist/arraylist-clone-deep-copy/
     Solution: I used the example from this link and adapted it to my code.
     */

    /**
     * displays help text over screen
     * @param view passes top view to method to allow it to display on screen
     */
    public void displayHelp(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                myActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.help_window, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    /**
     * Displays info about each phase
     * @param view passes top view for where to display overlay
     */
    public void displayPhases(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                myActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.phase_window, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    /**
     * Displays overlay for pont values of each card
     * @param view passes top view for where to display overlay
     */
    public void displayPoints(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                myActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.point_window, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    /**
     * Displays info about each phase
     * @param view passes top view for where to display overlay
     */
    public void displayTroubleshooting(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                myActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.troubleshooting_window, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    /**
     * callback method--our game has been chosen/rechosen to be the GUI,
     * called from the GUI thread
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
        this.quitButton = activity.findViewById(R.id.QuitButton);
        this.restartButton = activity.findViewById(R.id.restartButton);
        this.helpButton = activity.findViewById(R.id.helpButton);
        this.phasesButton = activity.findViewById(R.id.phasesButton);
        this.cardPoints = activity.findViewById(R.id.pointButton);
        this.troubleshoot = activity.findViewById(R.id.troubleshooting);
        this.playerScoreTextView = activity.findViewById(R.id.scoreView);
        this.oppScoreTextView = activity.findViewById(R.id.oppScoreView);
        this.phaseView = activity.findViewById(R.id.phaseDescription);
        this.turnPart = activity.findViewById(R.id.turnPart);

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

        Hand.add(Hand1);
        Hand.add(Hand2);
        Hand.add(Hand3);
        Hand.add(Hand4);
        Hand.add(Hand5);
        Hand.add(Hand6);
        Hand.add(Hand7);
        Hand.add(Hand8);
        Hand.add(Hand9);
        Hand.add(Hand10);
        Hand.add(Hand11);

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
        AIPhase15 = myActivity.findViewById(R.id.AIPhase15);
        AIPhase16 = myActivity.findViewById(R.id.AIPhase16);


        AIphase.add(AIPhase1);
        AIphase.add(AIPhase2);
        AIphase.add(AIPhase3);
        AIphase.add(AIPhase4);
        AIphase.add(AIPhase5);
        AIphase.add(AIPhase6);
        AIphase.add(AIPhase7);
        AIphase.add(AIPhase8);
        AIphase.add(AIPhase9);
        AIphase.add(AIPhase10);
        AIphase.add(AIPhase11);
        AIphase.add(AIPhase12);
        AIphase.add(AIPhase13);
        AIphase.add(AIPhase14);
        AIphase.add(AIPhase15);
        AIphase.add(AIPhase16);

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
        PlayerPhase15 = myActivity.findViewById(R.id.playerPhase15);
        PlayerPhase16 = myActivity.findViewById(R.id.playerPhase16);

        playerPhase.add(PlayerPhase1);
        playerPhase.add(PlayerPhase2);
        playerPhase.add(PlayerPhase3);
        playerPhase.add(PlayerPhase4);
        playerPhase.add(PlayerPhase5);
        playerPhase.add(PlayerPhase6);
        playerPhase.add(PlayerPhase7);
        playerPhase.add(PlayerPhase8);
        playerPhase.add(PlayerPhase9);
        playerPhase.add(PlayerPhase10);
        playerPhase.add(PlayerPhase11);
        playerPhase.add(PlayerPhase12);
        playerPhase.add(PlayerPhase13);
        playerPhase.add(PlayerPhase14);
        playerPhase.add(PlayerPhase15);
        playerPhase.add(PlayerPhase16);

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

        AIDeck.add(AIDeckCard1);
        AIDeck.add(AIDeckCard2);
        AIDeck.add(AIDeckCard3);
        AIDeck.add(AIDeckCard4);
        AIDeck.add(AIDeckCard5);
        AIDeck.add(AIDeckCard6);
        AIDeck.add(AIDeckCard7);
        AIDeck.add(AIDeckCard8);
        AIDeck.add(AIDeckCard9);
        AIDeck.add(AIDeckCard10);
        AIDeck.add(AIDeckCard11);

        //Listen for button presses
        drawFaceUpImageButton.setOnClickListener(this);
        drawFaceDownImageButton.setOnClickListener(this);
        hitButton.setOnClickListener(this);
        hitSelfButton.setOnClickListener(this);
        phaseButton.setOnClickListener(this);
        discardButton.setOnClickListener(this);
        quitButton.setOnClickListener(this);
        restartButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        phasesButton.setOnClickListener(this);
        cardPoints.setOnClickListener(this);
        troubleshoot.setOnClickListener(this);
        for(ImageButton b : Hand){
            b.setOnClickListener(this);
        }

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
        fixPhase();
        if(this.playerNum==1) {
            int i = 1;
            for (ImageView Im : AIphase) {
                if (state.getPlayer1PhaseContent().size() < i) {
                    Im.setImageResource(0);
                } else {
                    Im.setImageResource(state.testSlot(state.getPlayer1PhaseContent().get(i - 1)));
                }
                i++;
            }
        }
        else if(this.playerNum==0) {
            int i = 1;
            for (ImageView Im : AIphase) {
                if (state.getPlayer2PhaseContent().size() < i) {
                    Im.setImageResource(0);
                } else {
                    Im.setImageResource(state.testSlot(state.getPlayer2PhaseContent().get(i - 1)));
                }
                i++;
            }
        }
    }

    /**
     * adjusts the phase so it will be grouped by sets, runs, and color groups
     * even after hitting or when selecting cards in an unusual order
     */
    private void fixPhase(){
        //Displays phase in more user friendly order
        ArrayList<Card> fixPhase1 = new ArrayList<Card>();
        ArrayList<Card> fixPhase2 = new ArrayList<Card>();
        fixPhase1.clear();
        fixPhase2.clear();
        switch(state.getPlayer1Phase()) { //fix phase for player 1
            case 1:
            case 7:
            case 9:
            case 10: //phases 1, 7, 9, and 10 all have 2 sets
                if (state.getPhase().play1Set1 != null) {
                    fixPhase1.addAll(Arrays.asList(state.getPhase().play1Set1));
                    fixPhase1.addAll(Arrays.asList(state.getPhase().play1Set2));
                    state.setPlayer1PhaseContent(fixPhase1);
                }
                break;
            case 2:
            case 3://phases 2 and 3 both have 1 set and 1 run
                if (state.getPhase().play1Set1 != null) {
                    fixPhase1.addAll(Arrays.asList(state.getPhase().play1Set1));
                    fixPhase1.addAll(Arrays.asList(state.getPhase().play1Run));
                    state.setPlayer1PhaseContent(fixPhase1);
                }
                break;
            case 4:
            case 5:
            case 6: //phases 4-6 are all single runs
                if (state.getPhase().play1Run != null) {
                    fixPhase1.addAll(Arrays.asList(state.getPhase().play1Run));
                    state.setPlayer1PhaseContent(fixPhase1);
                }
                break;
            case 8: //phase 8 has a color group
                if (state.getPhase().play1Color != null) {
                    fixPhase1.addAll(Arrays.asList(state.getPhase().play1Color));
                    state.setPlayer1PhaseContent(fixPhase1);
                }
                break;
        }
        switch(state.getPlayer2Phase()) { //fix phase for player 2
            case 1:
            case 7:
            case 9:
            case 10: //phases 1, 7, 9, and 10 all have 2 sets
                if (state.getPhase().play2Set1 != null) {
                    fixPhase2.addAll(Arrays.asList(state.getPhase().play2Set1));
                    fixPhase2.addAll(Arrays.asList(state.getPhase().play2Set2));
                    state.setPlayer2PhaseContent(fixPhase2);
                }
                break;
            case 2:
            case 3://phases 2 and 3 both have 1 set and 1 run
                if (state.getPhase().play2Set1 != null) {
                    fixPhase2.addAll(Arrays.asList(state.getPhase().play2Set1));
                    fixPhase2.addAll(Arrays.asList(state.getPhase().play2Run));
                    state.setPlayer2PhaseContent(fixPhase2);
                }
                break;
            case 4:
            case 5:
            case 6: //phases 4-6 are all single runs
                if (state.getPhase().play2Run != null) {
                    fixPhase2.addAll(Arrays.asList(state.getPhase().play2Run));
                    state.setPlayer2PhaseContent(fixPhase2);
                }
                break;
            case 8: //phase 8 has a color group
                if (state.getPhase().play2Color != null) {
                    fixPhase2.addAll(Arrays.asList(state.getPhase().play2Color));
                    state.setPlayer2PhaseContent(fixPhase2);
                }
                break;
        }
    }

    /**
     * void method createPlayerPhase draws the player's selected phase (if valid)
     * and gets the information, to draw it to the GUI
     *
     */
    private void createPlayerPhase(){
        fixPhase();
        if(this.playerNum==0) {
            int i = 1;
            for (ImageView Im : playerPhase) {
                if (state.getPlayer1PhaseContent().size() < i) {
                    Im.setImageResource(0);
                } else {
                    Im.setImageResource(state.testSlot(state.getPlayer1PhaseContent().get(i - 1)));
                }
                i++;
            }
        }
        else if(this.playerNum==1) {
            int i = 1;
            for (ImageView Im : playerPhase) {
                if (state.getPlayer2PhaseContent().size() < i) {
                    Im.setImageResource(0);
                } else {
                    Im.setImageResource(state.testSlot(state.getPlayer2PhaseContent().get(i - 1)));
                }
                i++;
            }
        }

    } //create playerPhase end

    /**
     *  method that draws all AI card, face down, so that the human player cannot see such
     *  that the game is more realistic
     */
    private void createAIHandDown(){
        if(this.playerNum==1) {
            int i = 1;
            for (ImageView Im : AIDeck) {
                if (state.getPlayer1Hand().size() < i) {
                    Im.setImageResource(0);
                } else {
                    Im.setImageResource(R.drawable.cardback);
                }
                i++;
            }
        }
        else if(this.playerNum==0) {
            int i = 1;
            for (ImageView Im : AIDeck) {
                if (state.getPlayer2Hand().size() < i) {
                    Im.setImageResource(0);
                } else {
                    Im.setImageResource(R.drawable.cardback);
                }
                i++;
            }
        }
    }

    /**
     * Draws the AI's hand face up for testing purposes.
     */
    private void createAIHandUp(){
        if(this.playerNum==1) {
            int i = 1;
            for (ImageView Im : AIDeck) {
                if (state.getPlayer1Hand().size() < i) {
                    Im.setImageResource(0);
                } else {
                    Im.setImageResource(state.testSlot(state.getPlayer1Hand().get(i - 1)));
                }
                i++;
            }
        }
        else if(this.playerNum==0) {
            int i = 1;
            for (ImageView Im : AIDeck) {
                if (state.getPlayer2Hand().size() < i) {
                    Im.setImageResource(0);
                } else {
                    Im.setImageResource(state.testSlot(state.getPlayer2Hand().get(i - 1)));
                }
                i++;
            }
        }
    } //drawFaceUp cards end

    /**
     * this method creates the Hand of each player on the GUI
     * making sure that the imageResource (the cards) are displayed properly to
     * their respective information
     */
    private void createHand(){
        if(this.playerNum==0) {
            int i = 1;
            for (ImageButton b : Hand) {
                if (state.getPlayer1Hand().size() < i) {
                    b.setImageResource(0);
                    b.setClickable(false);
                    b.setEnabled(false);
                } else {
                    b.setImageResource(state.testSlot(state.getPlayer1Hand().get(i-1)));
                    b.setClickable(true);
                    b.setEnabled(true);
                }
                i++;
            }
        }
        else if(this.playerNum == 1) {
            int i = 1;
            for (ImageButton b : Hand) {
                if (state.getPlayer2Hand().size() < i) {
                    b.setImageResource(0);
                    b.setClickable(false);
                    b.setEnabled(false);
                } else {
                    b.setImageResource(state.testSlot(state.getPlayer2Hand().get(i-1)));
                    b.setClickable(true);
                    b.setEnabled(true);
                }
                i++;
            }
        }
    } // createHand end



}// class HumanPlayer