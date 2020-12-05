
package up.edu.phase10;

import up.edu.phase10.Framework.GamePlayer;
import up.edu.phase10.Framework.GameAction;

/**
 * @author Kirsten Foster, Alexis Molina, Emily Hoppe, Grace Penunuri
 * this action class extends Game action and passes through the player
 * that is attempting to hit, the card the player is trying to hit with
 * and the other player the current player is trying to hit
 */
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