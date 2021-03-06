
package up.edu.phase10;

import java.util.ArrayList;

import up.edu.phase10.Framework.GamePlayer;
import up.edu.phase10.Framework.GameAction;

/**
 * @author Kirsten Foster, Alexis Molina, Emily Hoppe, Grace Penunuri
 * this action class extends Game action and passes through the game player,
 * the phaseContent of that game player to play in the game
 */
public class PhaseAction extends GameAction {
    private ArrayList<Card> phaseContent;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     * @param phaseContent stores the phase content
     */
    public PhaseAction(GamePlayer player, ArrayList<Card> phaseContent) {
        super(player);
        this.phaseContent=phaseContent;
    }

    /**
     * an array list to store the phase content
     *
     * @return returns phase content
     */
    public ArrayList<Card>  getPhaseContent() {
        return phaseContent;
    }
}