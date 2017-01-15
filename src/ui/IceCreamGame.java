package ui;

import model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Ice Cream Game
 * by: Jieun Lee
 */
public class IceCreamGame extends JFrame {

    private Game game;

    private BoardPanel board;
    private ScorePanel score;
    private MessagePanel msg;

    // constructor
    private IceCreamGame() {
        super("The Ice Cream Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        game = new Game();

        board = new BoardPanel(game);
        score = new ScorePanel(game);
        msg = new MessagePanel(game);

        add(board, BorderLayout.WEST);
        add(score, BorderLayout.EAST);
        add(msg, BorderLayout.SOUTH);

        Interaction.get().setPanels(board, score, msg);

        pack();
        setVisible(true);

        addKeyListener(new KeyHandler());

        game.startGame();
    }

    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            msg.keyPressed(e.getKeyCode());
        }
    }


    public static void main(String[] args) {
        new IceCreamGame();
    }
}
