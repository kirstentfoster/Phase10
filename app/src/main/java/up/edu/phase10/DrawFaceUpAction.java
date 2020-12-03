

package up.edu.phase10;

import up.edu.phase10.Framework.Game;
import up.edu.phase10.Framework.GamePlayer;
import up.edu.phase10.Framework.GameAction;

/**
 * @author Kirsten Foster, Alexis Molina, Emily Hoppe, Grace Penunuri
 * this action class extends Game action and draws the card
 * face up dependent on the player attempting to do so
 */
public class DrawFaceUpAction extends GameAction {

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public DrawFaceUpAction(GamePlayer player) {
        super(player);
    }
}