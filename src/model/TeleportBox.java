package model;

import ui.Interaction;

import java.awt.*;

/**
 * The teleport boxes
 */
public class TeleportBox implements Box {

    private int pos;

    private int x;
    private int y;

    public TeleportBox(int i) {
        this.pos = i;

        if (pos == 4) {
            x = 425;
            y = 25;
        }
        else {
            x = 325;
            y = 325;
        }
    }

    // get name of box
    public String getName() {
        int loc;
        if (pos == 4) {
            loc = 1;
        }
        else {
            loc = 2;
        }
        return "Teleport Hub #" + loc;
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
    // just teleport; no further actions
    public void takeAction(Player p) {

        // wait for 1 second, then move
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // do nothing
        }

        // if on box 4, move to box 12
        if (this.pos == 4) {
            p.setPos(12);
        }

        // if on box 12, move to box 4
        else {
            p.setPos(4);
        }

        Interaction.get().updateBoard();
        Interaction.get().waitForNext("You just teleported to a different box! Press SPACE to continue.");
    }

    public void drawBox(Graphics g) {
        g.setColor(new Color(0xECFFDF));
        g.fillRect(x, y, 100, 100);

        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 15));    // font, style, size
        FontMetrics fm = g.getFontMetrics();
        centreString("teleport", g, fm, y+45);
    }
    // used to put text at the center of a box
    public void centreString(String str, Graphics g, FontMetrics fm, int yPos) {
        int width = fm.stringWidth(str);
        g.drawString(str, ((100 - width)/2) + x, yPos);
    }
}
