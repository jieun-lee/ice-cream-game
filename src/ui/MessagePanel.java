package ui;

import model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Panel to display messages and options
 */
public class MessagePanel extends JPanel {

    private Game game;      // TODO: is this necessary?

    private String line1;
    private String line2;
    private String line3;
    private String line4;

    private int nextLine;   // indicates the next available line to write on

    private static final int WIDTH = 930;
    private static final int HEIGHT = 135;
    private static final Color BACKGROUND = new Color(0xFFFFC2);

    private static final String P_ONE = "Player 1";
    private static final String P_TWO = "Player 2";

    private static final String ROLL_DICE = " - your turn! Press SPACE to roll.";


    // the constructor
    public MessagePanel(Game g) {

        this.game = g;

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(BACKGROUND);

        // all strings are blank (all four lines are blank)
        line1 = "";
        line2 = "";
        line3 = "";
        line4 = "";

        nextLine = 1;
    }

    public void keyPressed(int keyCode) {

        // if it is waiting, set values
        // if it is not waiting, take no action
        if (Interaction.get().isWaiting()) {

            // if we want a choice, we want either 1 or 2 to be pressed
            if (Interaction.get().isWantChoice()) {

                if (keyCode == KeyEvent.VK_1) {
                    Interaction.get().setChoice(1);
                    Interaction.get().stopWaiting();
                }
                else if (keyCode == KeyEvent.VK_2) {
                    Interaction.get().setChoice(2);
                    Interaction.get().stopWaiting();
                }
            }

            // if we don't want a choice, we want space to be pressed
            else {
                if (keyCode == KeyEvent.VK_SPACE) {
                    Interaction.get().stopWaiting();
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawMessages(g);
    }

    private void drawMessages(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.MONOSPACED, 0, 18));
        g.drawString(line1, 15, 25);
        g.drawString(line2, 15, 55);
        g.drawString(line3, 15, 85);
        g.drawString(line4, 15, 115);
    }

    // you rolled a 5 and landed on vanilla
    // vanilla is owned by you you ahve 2 scoops so you get 2 stars
    // do you want to upgrade to 3 scoops for $100? yes or no
    // You just upgraded Vanilla to 3 scoops. Vanilla is now off the market


    // sets the next available line to the given string (message)
    public void writeMessage(String s) {
        if (nextLine == 1)
            line1 = s;
        else if (nextLine == 2)
            line2 = s;
        else if (nextLine == 3)
            line3 = s;
        else if (nextLine == 4)
            line4 = s;
        nextLine++;
    }

    // clears all the lines displayed and sets nextLine to 1
    public void clearLines() {
        line1 = "";
        line2 = "";
        line3 = "";
        line4 = "";
        nextLine = 1;
    }
}
