/**
 * @author Kirsten Foster, Alexis Molina, Emily Hoppe, Grace Penunuri
 *  this action class extends Game action and passes through the card
 *  the player is trying to discard
 */

package up.edu.phase10;

import up.edu.phase10.Framework.Game;
import up.edu.phase10.Framework.GamePlayer;
import up.edu.phase10.Framework.GameAction;

public class DiscardAction extends GameAction {
    private Card card;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public DiscardAction(GamePlayer player, Card card) {
        super(player);
        this.card = card;
    }

    public Card getCard() {
        return card;
    }
}