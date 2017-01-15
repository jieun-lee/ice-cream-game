package model;

import ui.IceCreamGame;
import ui.Interaction;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Main model of the game
 */
public class Game {

    private int curr;                   // index of current player
    private boolean gameOver;          // true if game is over
    private int winner;                 // index of player who won

    private List<Player> players;       // list of players (two)
    private List<Surprise> surprises;   // set of surprises to choose from
    private List<Box> boxes;            // the boxes that make up the game board


    // constructor
    public Game() {
        setUp();
    }


    // initializes variables
    // adds players, surprises, and boxes to the game
    private void setUp() {
        curr = 0;
        gameOver = false;
        winner = 2;

        addPlayers();
        addSurprises();
        addBoxes();
    }

    // adds two players to the game
    private void addPlayers() {
        players = new ArrayList<Player>();

        // set up player 1 (green) - index 0
        Player p = new Player(this, 0, new Color(0xA1D9FF));
        players.add(p);

        // set up player 2 (pink) - index 1
        p = new Player(this, 1, new Color(0xFFA7C8));
        players.add(p);
    }

    // adds surprises to the game
    // can earn(or lose) stars or money
    private void addSurprises() {
        surprises = new ArrayList<Surprise>();

        // earn money
        for (int i = 1; i < 6; i++) {
            int earning = i*100;
            Surprise s = new Surprise(0, earning, "Surprise! You got $" + earning + "!");
            surprises.add(s);
        }

        // lose money
        for (int i = 1; i < 4; i++) {
            int losing = i*100;
            Surprise s = new Surprise(0, -losing, "Oh no! You lost $" + losing + "!");
            surprises.add(s);
        }
        for (int i = 1; i < 3; i++) {
            int losing = i*100;
            Surprise s = new Surprise(0, -losing, "Oh no! You lost $" + losing + "!");
            surprises.add(s);
        }

        // earn stars
        for (int i = 1; i < 4; i++) {
            Surprise s = new Surprise(1, i, "Surprise! You got " + i + " stars!");
            surprises.add(s);
        }

        // lose stars
        for (int i = 1; i < 4; i++) {
            Surprise s = new Surprise(1, -i, "Oh no! You lost " + i + " stars!");
            surprises.add(s);
        }
    }

    // add boxes to the game
    private void addBoxes() {
        boxes = new ArrayList<Box>();

        Box b = new StartBox(0);
        boxes.add(b);
        b = new Flavour("Vanilla", 1, new Color(0xF2F2F2));
        boxes.add(b);
        b = new SurpriseBox(this, 2);
        boxes.add(b);
        b = new Flavour("Chocolate", 3, new Color(0xD8992C));
        boxes.add(b);
        b = new TeleportBox(4);
        boxes.add(b);
        b = new Flavour("Strawberry", 5, new Color(0xF97673));
        boxes.add(b);
        b = new FreeSwapBox(this, 6);
        boxes.add(b);
        b = new Flavour("Mango", 7, new Color(0xFFFF95));
        boxes.add(b);
        b = new SurpriseBox(this, 8);
        boxes.add(b);
        b = new Flavour("Coffee", 9, new Color(0x784817));
        boxes.add(b);
        b = new SurpriseBox(this, 10);
        boxes.add(b);
        b = new Flavour("Caramel", 11, new Color(0xEEB12D));
        boxes.add(b);
        b = new TeleportBox(12);
        boxes.add(b);
        b = new Flavour("Mint", 13, new Color(0x95F9C3));
        boxes.add(b);
        b = new SurpriseBox(this, 14);
        boxes.add(b);
        b = new Flavour("Green Tea", 15, new Color(0x2EE32F));
        boxes.add(b);
        b = new SurpriseBox(this, 16);
        boxes.add(b);
        b = new Flavour("Blueberry", 17, new Color(0x7DE9FF));
        boxes.add(b);
    }


    // what to do on player's turn
    private void turn() {

        Interaction.get().updateTurn();

        Interaction.get().clearLines();
        Interaction.get().waitForNext("Player " + (curr+1) + " - your turn! Press SPACE to roll.");

        Interaction.get().clearLines();

        int steps = rollDice();
        Player p = players.get(curr);
        int i = p.move(steps);
        Box b = boxes.get(i);

        Interaction.get().updateBoard();

        Interaction.get().writeMessage("You rolled a " + steps + " and landed on " + b.getName() + ".");

        b.takeAction(p);

        nextPlayer();
    }

    // change current player to the other player
    private void nextPlayer() {
        if (curr == 0)
            curr = 1;
        else
            curr = 0;
    }

    // roll the dice
    // picks a random number from 1-6
    private int rollDice() {
        int i = new Random().nextInt(6);
        return (i+1);
    }

    // picks a random surprise from the list of surprises
    // random picks a value from 0 to size (exclusive)
    // returns the surprise
    public Surprise pickSurprise() {
        int i = new Random().nextInt(surprises.size());
        return surprises.get(i);
    }

    // returns the player with the given id
    public Player getPlayerById(int i) {
        return players.get(i);
    }


    // starts the game
    public void startGame() {
        while (!gameOver) {
            turn();
        }
        gameOverAction();
    }

    // ends the game
    public void setGameOver(int winner) {
        gameOver = true;
        this.winner = winner;
    }

    public void gameOverAction() {
        // what to do when the game ends
        Interaction.get().clearLines();
        Interaction.get().writeMessage("Player " + (winner+1) + " wins!");
    }



    // what to draw
    public void drawGame(Graphics g) {
        drawBoxes(g);
        drawOutline(g);
        drawPlayers(g);
    }

    // draws each box
    public void drawBoxes(Graphics g) {
        // 100x100 boxes
        // then write names
        // these boxes don't change afterwards

        for (Box b : boxes) {
            b.drawBox(g);
        }

        drawOutline(g);
    }

    // draws outline of each box
    public void drawOutline(Graphics g) {
        g.setColor(Color.BLACK);

        int i = 25;
        while (i < 700) {
            g.drawRect(i, 25, 100, 100);
            i = i + 100;
        }

        i = 25;
        while (i < 700) {
            g.drawRect(i, 325, 100, 100);
            i = i + 100;
        }
        g.drawRect(25, 125, 100, 100);
        g.drawRect(25, 225, 100, 100);
        g.drawRect(625, 125, 100, 100);
        g.drawRect(625, 225, 100, 100);
    }

    // draws the players
    public void drawPlayers(Graphics g) {
        for (Player p : players) {
            p.drawPlayer(g);
        }
    }



    public Box getBoxByInd(int i) {
        return boxes.get(i);
    }

    public int getCurrent() {
        return curr;
    }
}
