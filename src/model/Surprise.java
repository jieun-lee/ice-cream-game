package model;

import ui.Interaction;

/**
 * A surprise in the game
 */
public class Surprise {

    private int type;   // type 0 for money, type 1 for stars
    private int cost;   // how much is earned or lost
    private String msg;

    public Surprise(int type, int cost, String msg) {
        this.type = type;
        this.cost = cost;
        this.msg = msg;
    }

    public void doSurprise(Player p) {
        if (type == 0) {
            p.moneyAction(cost);
        }
        else {
            p.starAction(cost);
        }

        Interaction.get().updateScores();
        Interaction.get().waitForNext(msg + " Press SPACE to continue.");
    }
}
