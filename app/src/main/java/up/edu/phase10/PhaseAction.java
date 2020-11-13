/**
 * @author Kirsten Foster, Alexis Molina, Emily Hoppe, Grace Penunuri
 * Holds all phase content and sends the action of phasing to GameAction
 *
 */

package up.edu.phase10;

/*
EXTERNAL CITATION https://beginnersbook.com/2013/12/java-arraylist-of-object-sort-example-comparable-and-comparator/
 */
import java.util.ArrayList;

import up.edu.phase10.Framework.GamePlayer;
import up.edu.phase10.Framework.GameAction;

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