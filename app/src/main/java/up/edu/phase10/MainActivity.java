
package up.edu.phase10;

import java.util.ArrayList;

import up.edu.phase10.Framework.GameMainActivity;
import up.edu.phase10.Framework.GamePlayer;
import up.edu.phase10.Framework.LocalGame;
import up.edu.phase10.Framework.GameConfig;
import up.edu.phase10.Framework.GamePlayerType;

/**
 * @author Kirsten Foster, Alexis Molina, Emily Hoppe, Grace Penunuri
 * this is the primary activity for Phase10
 *
 * Final Release commentary:
 *
 * No known bugs
 * Fully functional GUI
 * Game plays to completion
 *
 * JUnit tests present for both the GameState and Phase classes
 *      These test the action handling in the GameState and the phase handling
 *      in the Phase class
 *
 * Smart AI plays game intelligently with a good strategy
 * Dumb AI plays the game poorly with a poor strategy, but will not necessarily lose
 * Both AIs have had their moves slowed down slightly to allow the human player to better see
 *      what actions they are taking
 *
 * Working quit, restart, and help (rules) buttons
 * 3 assistance buttons, one that shows the 10 phases, one that shows card scoring
 *      rules, and one that explains error flashing
 * There are different colors of error flashes that indicate different illegal moves, explained
 *      by pressing the "Flash Key" button
 *
 * Displays current score and phase of both players, as well as requirement for
 *      current human phase
 * Displays the actions that human is currently allowed to do (below the two draw piles),
 *      which updates with player does an action
 * Cards are visually highlighted and unhighlighted from the human hand when they are selected
 *
 * Working phase/round progression, scoring, as well as skip and wild cards
 * Game properly handles when a player draws the last card in either deck
 * All actions currently work when player follows action constrictions, and will recieve appropriate
 *      flash message to tell them their move was illegal
 * Working end game and winning
 *
 * Our alpha release grading indicated to us that we have some long methods that could be
 *      rectified by making greater use of arrays. We acknowledge this conceptually, but we did not have time
 *      rewrite our code to be structured in this way.
 *
 * The thread pausing used to slow down the AIs sometimes lags out when using the VM. We reccomend playing on a real tablet,
 *      but if that is not possible the threadpause lines at the top of the receiveInfo methods in the AIs can be removed to
 *      ensure smooth play.
 */
public class MainActivity extends GameMainActivity {

    // the port number that this game will use when playing over the network
    private static final int PORT_NUMBER = 2278;

    /**
     * Create the default configuration for this game:
     * - one human player vs. one computer player
     * - minimum of 1 player, maximum of 2
     *
     * @return
     * 		the new configuration object, representing the default configuration
     */
    @Override
    public GameConfig createDefaultConfig() {

        // Define the allowed player types
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

        // Pig has two player types:  human and computer
        playerTypes.add(new GamePlayerType("Local Human Player") {
            public GamePlayer createPlayer(String name) {
                return new Phase10HumanPlayer(name);
            }});
        playerTypes.add(new GamePlayerType("Smart Computer Player") {
            public GamePlayer createPlayer(String name) {
                return new SmartComputerPlayer(name);
            }});
        playerTypes.add(new GamePlayerType("Dumb Computer Player") {
            public GamePlayer createPlayer(String name) {
                return new DumbComputerPlayer(name);
            }});




        // Create a game configuration class for Pig:
        GameConfig defaultConfig = new GameConfig(playerTypes, 1, 2, "Pig", PORT_NUMBER);
        defaultConfig.addPlayer("Human", 0); // player 1: a human player
        defaultConfig.addPlayer("Computer", 1); // player 2: a computer player
        defaultConfig.setRemoteData("Remote Human Player", "", 0);

        return defaultConfig;
    }//createDefaultConfig

    /**
     * create a local game
     *
     * @return
     * 		the local game, a pig game
     */
    @Override
    public LocalGame createLocalGame() {
        return new Phase10LocalGame();
    }

}
