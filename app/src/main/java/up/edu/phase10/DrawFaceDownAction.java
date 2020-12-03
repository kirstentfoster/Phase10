/**
 * @author Kirsten Foster, Alexis Molina, Emily Hoppe, Grace Penunuri
 * this action class extends Game action and draws whatever card face
 * down
 */

package up.edu.phase10;

import up.edu.phase10.Framework.Game;
import up.edu.phase10.Framework.GamePlayer;
import up.edu.phase10.Framework.GameAction;

public class DrawFaceDownAction extends GameAction {

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public DrawFaceDownAction(GamePlayer player) {
        super(player);
    }
}