package model;

import java.awt.*;

/**
 * A box on the board
 */
public interface Box {

    // get X coordinate of box
    public int getX();

    // get Y coordinate of box
    public int getY();

    // get name of the box
    public String getName();

    // what to do when a player lands on this box
    public void takeAction(Player p);

    // draw a box
    public void drawBox(Graphics g);

}