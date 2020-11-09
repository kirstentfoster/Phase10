/**
 * @author Kirsten Foster, Alexis Molina, Emily Hoppe, Grace Penunuri
 * Holds information about each card (particularly number and color)
 * Also includes getters/setters for card info
 * Includes a "to string" method to explain what the card is
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