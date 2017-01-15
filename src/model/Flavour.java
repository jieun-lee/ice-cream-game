package model;

import ui.Interaction;

import java.awt.*;

/**
 * A flavour of the game
 */
public class Flavour implements Box {

    private String name;
    private int pos;
    private Color colour;

    private int x;
    private int y;

    private Player owner;   // null if unowned
    private int scoops;     // max is 3
    private int price;      // 0 if not on market
    private boolean forSale;


    public Flavour(String name, int pos, Color colour) {
        this.name = name;
        this.pos = pos;
        this.colour = colour;

        this.x = getXDim();
        this.y = getYDim();

        this.owner = null;
        this.scoops = 0;
        this.price = 100;
        this.forSale = true;
    }


    // returns the flavour name
    public String getName() {
        return name;
    }

    // returns the flavour's location on the board
    public int getPos() {
        return pos;
    }

    // returns the display colour of the flavour
    public Color getColour() {
        return colour;
    }

    // gets x coordinate of box
    public int getX() {
        return x;
    }

    // gets y coordinate of box
    public int getY() {
        return y;
    }


    // returns true if there is an owner
    public boolean isOwned() {
        return (owner != null);
    }

    // returns the owner; null if unowned
    public Player getOwner() {
        return owner;
    }

    // removes the old owner and sets the new owner to p
    // happens when someone buys a flavour
    public void setOwner(Player p) {
        if (isOwned())
            owner.loseFlavour(this);
        owner = p;
    }


    // returns the number of scoops
    public int numScoops() {
        return scoops;
    }

    // returns true if we can upgrade the flavour
    public boolean isUpgradable() {
        return (scoops < 3);
    }

    // upgrades the flavour
    // takes the flavour off the market if max scoops is reached
    public void upgrade() {
        scoops++;
        if (scoops == 3)
            forSale = false;
    }

    // returns the market price of the flavour
    public int marketPrice() {
        return price;
    }

    // returns true if flavour is for sale
    public boolean isOnMarket() {
        return forSale;
    }

    // increases the market price of the flavour
    // takes the flavour off the market if max price is reached
    public void increasePrice() {
        if (price == 500)
            forSale = false;
        else
            price = price + 100;
    }


    // code to be executed when player lands on a flavour box
    public void takeAction(Player p) {

        // if flavour is unowned
        if (!this.isOwned()) {
            // ask player if they want to buy the flavour
            Interaction.get().writeMessage(name + " is unowned. Do you want to buy " + name + " for $100?");

            if (Interaction.get().waitForChoice("Press 1 for YES, 2 for NO.")) {
                // IF THEY WANT TO:
                p.moneyAction(-price);
                p.gainFlavour(this);
                increasePrice();
                scoops = 1;     // start with 1 scoop

                Interaction.get().updateBoth();
                Interaction.get().waitForNext("You just bought " + name + ". Press SPACE to continue.");
            }

            else {
                // IF NOT:
                // just end
                Interaction.get().waitForNext("You chose not to buy " + name + ". Press SPACE to continue.");
            }
        }


        // if player is owner of flavour
        // give stars to player
        // if upgradable, ask if they want to upgrade
        else if (p.isOwnerOf(this)) {

            // get stars
            p.starAction(this.scoops);
            Interaction.get().updateScores();
            Interaction.get().writeMessage("You own " + name + ", so you got " + scoops + " stars!");

            if (isUpgradable()) {

                if (Interaction.get().waitForChoice("Do you want to upgrade for $100? Press 1 for YES, 2 for NO.")) {
                    // IF THEY WANT TO:
                    p.moneyAction(-100);
                    this.upgrade();

                    Interaction.get().updateBoth();
                    Interaction.get().waitForNext("You upgraded " + name + " to " + scoops + " scoops. Press SPACE to continue.");
                }
                else {
                    // IF NOT:
                    // just end
                    Interaction.get().waitForNext("You chose not to upgrade " + name + ". Press SPACE to continue.");
                }
            }
            else {
                // not upgradable
                Interaction.get().waitForNext("You already reached the maximum number of scoops. Press SPACE to continue.");
            }
        }


        // if player is not the owner of flavour
        // if flavour is on sale, ask if they want to buy
        // if flavour is not on sale or if they don't want to buy, pay stars
        else {

            if (forSale) {
                // ask player if they want to buy the flavour
                Interaction.get().writeMessage("Do you want to buy " + name + " for " + price + ", or pay the owner " + scoops + " stars?");

                if (Interaction.get().waitForChoice("Press 1 to BUY, 2 for PAY STARS.")) {
                    // IF THEY WANT TO:
                    p.moneyAction(-price);
                    owner.moneyAction(price);
                    increasePrice();
                    scoops = 1;     // reset number of scoops
                    p.gainFlavour(this);

                    Interaction.get().updateBoth();
                    Interaction.get().waitForNext("You just bought " + name + ". Press SPACE to continue.");
                    return;
                }

                else {
                    // IF THEY DON'T WANT TO:
                    // pay stars
                    p.starAction(-scoops);
                    owner.starAction(scoops);

                    Interaction.get().updateScores();
                    Interaction.get().waitForNext("You chose to pay by stars instead. Press SPACE to continue.");
                }
            }
            else {
                // if not for sale, p must pay stars
                p.starAction(-scoops);
                owner.starAction(scoops);

                Interaction.get().updateScores();
                Interaction.get().waitForNext(name + " is not for sale, so you pay " + scoops + " stars. Press SPACE to continue.");
            }
        }
    }



    public void drawBox(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, 100, 100);

        // set up text
        g.setColor(Color.BLACK);
        g.setFont(new Font("Serif", 0, 20));    // font, style, size
        FontMetrics fm = g.getFontMetrics();

        // draw name
        centreString(name, g, fm, y+95);

        // draw price, scoops, owner
        drawUpdates(g);
    }

    public void drawUpdates(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Serif", 0, 16));    // font, style, size
        FontMetrics fm = g.getFontMetrics();

        // draw price
        if (forSale)
            centreString("$" + price, g, fm, y+75);

        // draw scoops
        g.setColor(colour);
        g.setFont(new Font("Monospaced", Font.BOLD, 35));
        fm = g.getFontMetrics();
        centreString("" + scoops, g, fm, y+50);

        // draw owner labels
        int[] xp = {x, x+25, x};
        int[] yp = {y, y, y+25};
        if (isOwned()) {
            g.setColor(owner.getColour());
            g.fillPolygon(xp, yp, 3);
        }
        else {
            g.setColor(Color.BLACK);
            g.drawPolygon(xp, yp, 3);
        }
    }

    // used to put text at the center of a box
    public void centreString(String str, Graphics g, FontMetrics fm, int yPos) {
        int width = fm.stringWidth(str);
        g.drawString(str, ((100 - width)/2) + x, yPos);
    }


    private int getXDim() {
        switch (pos) {
            case 1:
                return 125;
            case 3:
                return 325;
            case 5:
                return 525;
            case 7:
                return 625;
            case 9:
                return 625;
            case 11:
                return 425;
            case 13:
                return 225;
            default:
                return 25;
        }
    }

    private int getYDim() {
        if (pos < 6)
            return 25;
        else if (pos == 7 || pos == 17)
            return 125;
        else
            return 325;
    }
}
