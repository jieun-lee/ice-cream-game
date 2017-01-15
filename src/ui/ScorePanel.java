package ui;

import model.Game;
import model.Player;

import javax.swing.*;
import java.awt.*;

/**
 * Panel to display scores
 */
public class ScorePanel extends JPanel {

    private Game game;

    private static final int WIDTH =180;
    private static final int HEIGHT = 450;
    private static final Color BACKGROUND = new Color(0xE8FAFF);

    private int money1;
    private int money2;
    private int stars1;
    private int stars2;

    private int turn;

    // the constructor
    public ScorePanel(Game g) {

        this.game = g;
        this.turn = 0;

        money1 = 500;
        money2 = 500;
        stars1 = 10;
        stars2 = 10;

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(BACKGROUND);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        updateScores();
        drawScoreBoard(g);
    }

    // updates the scores
    private void updateScores() {
        Player p = game.getPlayerById(0);
        money1 = p.getMoney();
        stars1 = p.getStars();

        p = game.getPlayerById(1);
        money2 = p.getMoney();
        stars2 = p.getStars();
    }

    // draws teh scoreboard
    private void drawScoreBoard(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("SansSerif", Font.BOLD, 20));
        g.drawString("Player 1:", 20, 100);
        g.drawString("Player 2:", 20, 250);

        g.setFont(new Font("SansSerif", 0, 17));
        g.drawString("Money: $" + money1, 40, 130);
        g.drawString("Stars: " + stars1, 40, 160);
        g.drawString("Money: $" + money2, 40, 280);
        g.drawString("Stars: " + stars2, 40, 310);

        int y;

        if (turn == 0)
            y = 101;
        else
            y = 251;

        g.drawLine(20, y, 94, y);
    }

    // updates the current player
    // repaints the scoreboard after
    public void updateTurn() {
        turn = game.getCurrent();
        repaint();
    }
}
