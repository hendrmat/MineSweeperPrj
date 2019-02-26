package project2;

import javax.swing.*;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class MineSweeperGUI extends JFrame {

JLabel wins, losses, mine;

public MineSweeperGUI (int row, int col, int mines) {

    JFrame frame = new JFrame("MineSweeper");
    frame.setSize(1210,790);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(new GridLayout());
    frame.pack();
    frame.setVisible(true);
    JPanel panel = new MineSweeperPanel();

    GridBagConstraints loc = new GridBagConstraints();
    loc.gridx = 2;
    loc.gridy = 1;
    loc.gridwidth = 1;
    wins = new JLabel("Wins: 0");
    loc.gridx = 3;
    loc.gridy = 1;

    loc = new GridBagConstraints();
    loc.gridx = 2;
    loc.gridy = 5;
    loc.gridwidth = 1;
    losses = new JLabel("Losses: 0");
    loc.gridx = 3;
    loc.gridy = 5;

    loc = new GridBagConstraints();
    loc.gridx = 2;
    loc.gridy = 9;
    loc.gridwidth = 1;
    mine = new JLabel("Total Mine Count:");
    loc.gridx = 3;
    loc.gridy = 9;
}

    public static void main (String[] args) {
        int row = Integer.parseInt(JOptionPane.showInputDialog(null,
                "Please select the number of rows."));

        while (row < 2) {
            row = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "Please select a number of rows greater than one."));
        }

        int col = Integer.parseInt(JOptionPane.showInputDialog(null,
                "Please select the number of columns."));

        while (col < 2) {
            col = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "Please select a number of columns greater than 1."));
        }

        int mines = Integer.parseInt(JOptionPane.showInputDialog(null,
                "Please input the number of mines."));

        while (mines < 1) {
            mines = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "Please input a non-zero, non-negative number of mines."));
        }

        if (mines >= 1) {
            while (mines >= (row * col)){
                mines = Integer.parseInt(JOptionPane.showInputDialog(null,
                        "There are too many mines for a board of that size." +
                                " Please enter a valid number of mines."));
            }
        }
        
        MineSweeperGUI gui = new MineSweeperGUI(row, col, mines);
        gui.setVisible(true);
        //panel.setVisible(true);







    }


}
