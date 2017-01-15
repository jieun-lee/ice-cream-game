package ui;

import model.Game;

import javax.swing.*;
import java.awt.*;

/**
 * Panel for the main board
 */
public class BoardPanel extends JPanel {

    private Game game;

    private static final int WIDTH = 750;
    private static final int HEIGHT = 450;
    private static final Color BACKGROUND = new Color(0xFFBCAF);

    // the constructor
    public BoardPanel(Game g) {

        this.game = g;

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(BACKGROUND);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
    }

    private void drawBoard(Graphics g) {
        game.drawGame(g);
    }
}
