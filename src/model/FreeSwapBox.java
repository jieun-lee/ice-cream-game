package model;

import ui.Interaction;

import java.awt.*;
import java.util.Random;

/**
 * The Free Swap Box
 */
public class FreeSwapBox implements Box {

    private Game game;
    private int pos;

    private static int x = 625;
    private static int y = 25;

    private Player p0;
    private Player p1;

    public FreeSwapBox(Game g, int i) {
        this.game = g;
        this.pos = i;

        p0 = game.getPlayerById(0);
        p1 = game.getPlayerById(1);
    }

    // get name of the box
    public String getName() {
        return "Free Swap";
    }

    // gets x coordinate of box
    public int getX() {
        return x;
    }

    // gets y coordinate of box
    public int getY() {
        return y;
    }

    // what to do when a player lands on this box
    public void takeAction(Player p) {
        int n0 = p0.getNumFlavours();
        int n1 = p1.getNumFlavours();

        // at least one of the players has nothing to swap
        if ((n0 == 0) || (n1 == 0)) {
            // if either of them equal 0, do nothing
            // maybe return a message like, player X has nothing to exchange
            Interaction.get().waitForNext("Swap unsuccessful! Some player has nothing to swap. Press SPACE to continue.");
            return;
        }

        // both players have something to swap
        else {
            // pick a random index
            int r0 = new Random().nextInt(n0);
            int r1 = new Random().nextInt(n1);

            // get flavours at those indices
            Flavour f0 = p0.getFlavourByInd(r0);
            Flavour f1 = p1.getFlavourByInd(r1);

            // Offer the swap
            Interaction.get().writeMessage("Do you want to swap " + f0.getName() + " with " + f1.getName() + "?");

            if (Interaction.get().waitForChoice("Press 1 for YES, 2 for NO.")) {
                // IF OFFER IS ACCEPTED:
                // swap is executed, maintaining the same flavour states
                p0.gainFlavour(f1);
                p1.gainFlavour(f0);

                Interaction.get().updateBoard();
                Interaction.get().waitForNext("Swap successful! Press SPACE to continue.");
            }

            else {
                // IF OFFER IS DECLINED:
                // do nothing
                Interaction.get().waitForNext("Swap unsuccessful! Swap was declined. Press SPACE to continue.");
                return;
            }
        }
    }

    public void drawBox(Graphics g) {
        g.setColor(new Color(0xF0FFFC));
        g.fillRect(x, y, 100, 100);

        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));    // font, style, size
        FontMetrics fm = g.getFontMetrics();
        centreString("FREE", g, fm, y+45);
        centreString("SWAP", g, fm, y+70);
    }

    // used to put text at the center of a box
    public void centreString(String str, Graphics g, FontMetrics fm, int yPos) {
        int width = fm.stringWidth(str);
        g.drawString(str, ((100 - width)/2) + x, yPos);
    }
}
