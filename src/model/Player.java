package model;

import ui.Interaction;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * A player of the game
 */
public class Player {

    private Game game;
    private int id;

    private Color colour;
    private List<Flavour> flavours;

    private int pos;
    private int money;
    private int stars;

    private static int SIZE = 25;

    // constructor
    public Player(Game g, int id, Color colour) {
        this.game = g;
        this.id = id;

        this.colour = colour;
        this.flavours = new ArrayList<Flavour>();

        this.pos = 0;
        this.money = 500;
        this.stars = 10;
    }


    // returns id of current player
    public int getId() {
        return id;
    }

    // returns id of opponent
    private int getOppId() {
        if (id == 0)
            return 1;
        else    // id == 1
            return 0;
    }


    // compares two players to see if they are the same
    // same if they have the same id
    public boolean samePlayer(Player p) {
        return (this.id == p.getId());
    }

    // returns team colour
    public Color getColour() {
        return colour;
    }


    // returns the current position of player
    public int getPos() {
        return pos;
    }

    // sets the position to a different value
    public void setPos(int p) {
        pos = p;
    }

    // move given number of steps
    // earn money and stars if start is passed
    // return the new position
    public int move(int p) {
        pos = pos + p;
        if (pos > 17) {
            pos = pos - 18;
            passStart();
        }
        return pos;
    }

    // get money and stars if START is passed
    public void passStart() {
        money = money + 150;
        stars = stars + 1;
        Interaction.get().updateScores();
    }


    // returns amount of money player has
    public int getMoney() {
        return money;
    }

    // player gains or loses money
    // game ends if negative, and player loses
    public void moneyAction(int c) {
        money = money + c;
        if (money < 0)
            game.setGameOver(getOppId());
    }

    // returns number of stars player has
    public int getStars() {
        return stars;
    }

    // player gains or loses stars
    // game ends if negative, and player loses
    // game ends if 30+, and player wins
    public void starAction(int c) {
        stars = stars + c;
        if (stars < 0)
            game.setGameOver(getOppId());
        if (stars >= 30)
            game.setGameOver(id);
    }



    // checks if the player owns the given flavour
    public boolean isOwnerOf(Flavour f) {
        //return (samePlayer(f.getOwner()));
        return flavours.contains(f);
    }

    // player becomes the new owner of the flavour
    public void gainFlavour(Flavour f) {
        flavours.add(f);
        f.setOwner(this);
    }

    // remove given flavour from list
    // called when another player buys flavour this owned previously
    public void loseFlavour(Flavour f) {
        flavours.remove(f);
        // if remove is called on an array list, everything shifts
    }

    // get the number of flavours player has
    public int getNumFlavours() {
        return flavours.size();
    }

    // returns flavour at index i
    public Flavour getFlavourByInd(int i) {
        if (i < flavours.size())
            return flavours.get(i);
        else return null;
    }



    public void drawPlayer(Graphics g) {
        g.setColor(colour);
        Box b = game.getBoxByInd(pos);
        int x = b.getX() + 95 - SIZE - (10*id);
        int y = b.getY() + 5;
        g.fillOval(x, y, SIZE, SIZE);
        g.setColor(Color.BLACK);
        g.drawOval(x, y, SIZE, SIZE);

        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.SERIF, Font.BOLD, 15));

        x = x + (SIZE/2) - 2;
        y = y + (SIZE/2) + 5;
        g.drawString((id+1) + "", x, y);
    }
}
