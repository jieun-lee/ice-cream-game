package ui;

import model.Game;

/**
 * class that deals with Game - Message Interactions
 */
public class Interaction {

    private static Interaction theInteraction;
    BoardPanel board;
    ScorePanel score;
    MessagePanel msg;

    private static int WAIT = 1500;

    private boolean waiting;
    private boolean wantChoice;
    private int choice;         // choice = 0, choice not made; choice = 1 is yes, choice = 2 is no

    private Interaction() {
        this.waiting = false;
        this.wantChoice = false;
        this.choice = 0;
    }

    public static Interaction get() {
        if (theInteraction == null)
            theInteraction = new Interaction();
        return theInteraction;
    }

    public void setPanels(BoardPanel bp, ScorePanel sp, MessagePanel mp) {
        this.board = bp;
        this.score = sp;
        this.msg = mp;
    }


    // returns true if currently waiting for user response
    public boolean isWaiting() {
        return waiting;
    }

    // starts waiting for user response
    public void startWaiting() {
        waiting = true;
    }

    // stops waiting for user response
    public void stopWaiting() {
        waiting = false;
    }


    // returns true if we are looking for a choice
    public boolean isWantChoice() {
        return wantChoice;
    }

    // sets value of wantChoice to true
    public void startWantingChoice() {
        wantChoice = true;
    }

    // sets value of wantChoice to false
    public void stopWantingChoice() {
        wantChoice = false;
    }

    // sets choice to the choice made
    public void setChoice(int i) {
        choice = i;
        // i is one of 1 or 2
    }

    // resets the choice after it has been used
    public void resetChoice() {
        choice = 0;
        stopWantingChoice();
    }



    public void waitForNext(String s) {
        writeMessage(s);
        startWaiting();

        while (waiting) {   // try to find a different way to wait
            // do nothing
            // returns when key is pressed
            try {
                Thread.sleep(WAIT);
            } catch (InterruptedException e) {
                // do nothing
            }
        }
    }

    public boolean waitForChoice(String s) {
        writeMessage(s);
        startWantingChoice();
        startWaiting();

        while (waiting) {   // try to find a different way to wait
            // do nothing
            // stops when waiting stops
            // when either 1 or 2 is pressed
            try {
                Thread.sleep(WAIT);
            }
            catch (InterruptedException e) {
                // do nothing
            }
        }

        if (choice == 1) {
            resetChoice();
            return true;
        }
        else { // choice == 2
            resetChoice();
            return false;
        }
    }



    // calls writeMessage on the MessagePanel
    public void writeMessage(String s) {
        msg.writeMessage(s);
        updateMessages();
    }

    // calls clearLines on the MessagePanel
    public void clearLines() {
        msg.clearLines();
    }

    public void updateMessages() {
        msg.repaint();
    }


    public void updateBoard() {
        board.repaint();
    }

    public void updateScores() {
        score.repaint();
    }

    public void updateTurn() {
        score.updateTurn();
    }

    public void updateBoth() {
        updateBoard();
        updateScores();
    }
}
