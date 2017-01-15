package model;

import ui.Interaction;

import java.awt.*;

/**
 * The start box
 */
public class StartBox implements Box {

    private int pos;

    private static int x = 25;
    private static int y = 25;

    public StartBox(int i) {
        this.pos = i;
    }

    // get name of box
    public String getName() {
        return "START";
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
    // do nothing; player already got the stars and money
    public void takeAction(Player p)  {
        Interaction.get().waitForNext("Press SPACE to continue.");
    }

    public void drawBox(Graphics g) {
        g.setColor(new Color(0xFFFEDA));
        g.fillRect(x, y, 100, 100);

        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));    // font, style, size
        FontMetrics fm = g.getFontMetrics();
        centreString("START", g, fm, y+40);

        g.setFont(new Font("SansSerif", 0, 12));    // font, style, size
        fm = g.getFontMetrics();
        centreString("Collect $150", g, fm, y+65);
        centreString("Collect 1 star", g, fm, y+80);
    }

    // used to put text at the center of a box
    public void centreString(String str, Graphics g, FontMetrics fm, int yPos) {
        int width = fm.stringWidth(str);
        g.drawString(str, ((100 - width)/2) + x, yPos);
    }
}
