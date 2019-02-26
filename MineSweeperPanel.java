package project2;

import javax.swing.*;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.AbstractList;


public class MineSweeperPanel extends JPanel {

    private JButton[][] board;
    private Cell iCell;
    private JButton quitButton;
    private JButton cheatButton;
    private JButton defaultButton;
    private int numRows;
    private int numCols;
    public int losses;
    public int wins;
    private MineSweeperGame game;


    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public int getNumRows() {
        return numRows;
    }

    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    public int getNumCols() {
        return numCols;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }


    public MineSweeperPanel() {
        game = new MineSweeperGame();
        JPanel buttons = new JPanel();
        quitButton = new JButton("Quit", new ImageIcon(""));
        quitButton.add(buttons);
        cheatButton = new JButton("Cheat!", new ImageIcon(""));
        cheatButton.add(buttons);
        defaultButton = new JButton("Default Setting", new ImageIcon(""));
        defaultButton.add(buttons);
        buttons.setBackground(Color.GRAY);
        buttons.setVisible(true);

        JPanel gamePanel = new JPanel();
        GridLayout layout = new GridLayout(numRows, numCols);
        gamePanel.setLayout(layout);
        board = new JButton[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                board[i][j] = new JButton("", new ImageIcon(""));
                //board[i][j].addActionListener(this);
                gamePanel.add(board[i][j]);
            }
        }
    }
    private void displayBoard() {

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                iCell = game.getCell(i, j);
                if (iCell.isExposed()) {
                    board[i][j].setEnabled(false);
                } else {
                    board[i][j].setEnabled(true);
                }

                if (iCell.isExposed()) {
                    board[i][j].setText("" + iCell.getMineCount());
                }
            }
        }
    }

    private class ButtonListener implements MouseListener {

        private void actionPerformed(ActionEvent e) {

            JPanel buttons = new JPanel();
            quitButton = new JButton("Quit", new ImageIcon(""));
            quitButton.add(buttons);
//            //quitButton.addActionListener(this);
//
//            if (SwingUtilities.isRightMouseButton()) {
//                for (int row = 0; row < numRows; row++)
//                    for (int col = 0; col < numCols; col++) {
//
//                    }
//            }

            if (game.getGameStatus() == GameStatus.Lost) {
                JOptionPane.showMessageDialog(null,
                        "Wow! You Lose!");
                losses++;
                game.reset();
            } else if (game.getGameStatus() == GameStatus.Won) {
                JOptionPane.showMessageDialog(null,
                        "Bravo! You Win!");
                wins++;
                game.reset();
            }
        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }
    }
    }

