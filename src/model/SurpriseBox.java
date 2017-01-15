package model;

import ui.Interaction;

import java.awt.*;
import java.util.Random;

/**
 * The surprise boxes
 */
public class SurpriseBox implements Box {

    private Game game;
    private int pos;

    private int x;
    private int y;

    public SurpriseBox(Game g, int i) {
        this.game = g;
        this.pos = i;

        this.x = getXDim();
        this.y = getYDim();
    }

    // get name of box
    public String getName() {
        return "Surprise";
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

        if (Interaction.get().waitForChoice("Do you want to pick a surprise card? Press 1 for YES, 2 for NO.")) {
            // IF THEY WANT TO:
            Surprise s = game.pickSurprise();
            s.doSurprise(p);
        }

        else {
            // else
            // IF THEY DON'T WANT TO:
            // do nothing
            Interaction.get().waitForNext("You have chosen to not pick a surprise card. Press SPACE to continue.");
        }
    }


    public void drawBox(Graphics g) {
        g.setColor(new Color(0xFFF3EE));
        g.fillRect(x, y, 100, 100);

        g.setColor(Color.BLACK);
        g.setFont(new Font("SansSerif", Font.BOLD|Font.ITALIC, 15));    // font, style, size
        FontMetrics fm = g.getFontMetrics();
        centreString("SURPRISE!", g, fm, y+50);
    }

    // used to put text at the center of a box
    public void centreString(String str, Graphics g, FontMetrics fm, int yPos) {
        int width = fm.stringWidth(str);
        g.drawString(str, ((100 - width)/2) + x, yPos);
    }

    // get x dimension of box
    private int getXDim() {
        switch (pos) {
            case 2:
                return 225;
            case 8:
                return 625;
            case 10:
                return 525;
            case 14:
                return 125;
            default:    // case 16
                return 25;
        }
    }

    // get y dimension of box
    private int getYDim() {
        switch (pos) {
            case 2:
                return 25;
            case 8:
                return 225;
            case 10:
                return 325;
            case 14:
                return 325;
            default:    // case 16
                return 225;
        }
    }

}
