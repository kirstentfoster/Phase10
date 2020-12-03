
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
 * Game state commentary:
 * No known bugs
 * Fully functional GUI
 * Smart AI plays game intelligently with a good strategy
 * Dumb AI plays the game poorly with a poor strategy, but will not necessarily lose
 * Working quit, restart, and help (rules) buttons
 * Displays current score and phase of both players, as well as requirement for
 *  current human phase
 * Working phase/round progression, scoring, as well as skip and wild cards
 * All actions currently work when player follows action constrictions
 * Working end game (who wins)
 *
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
