/**
 * @author Kirsten Foster, Alexis Molina, Emily Hoppe, Grace Penunuri
 * Holds information about each card (particularly number and color)
 * Also includes getters/setters for card info
 * Includes a "to string" method to explain what the card is
 */

package up.edu.phase10;

/*
EXTERNAL CITATION https://beginnersbook.com/2013/12/java-arraylist-of-object-sort-example-comparable-and-comparator/
 */
import up.edu.phase10.Framework.Game;
import up.edu.phase10.Framework.GamePlayer;
import up.edu.phase10.Framework.GameAction;

public class HitAction extends GameAction {
    private Card card;
    private int hitPlayer;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     * @param card the card being hit
     * @param hitPlayer the player being hit on
     */
    public HitAction(GamePlayer player, Card card, int hitPlayer) {
        super(player);
        this.card = card;
        this.hitPlayer = hitPlayer;
    }

    public Card getCard() {
        return card;
    }
    public int getHitPlayer() { return hitPlayer; }
}