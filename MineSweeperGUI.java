/******************************************************
 * Written by Cameron Shearer and Matt Hendrick
 * CIS 163
 * MWF 11-12
 *****************************************************/

package project2;

import javax.swing.*;

/**
 * This is the GUI for MineSweeper, it can range in size from 3x3 to 30x30.
 * There is also an option to personalize it by adding your name, and there
 * will also be a record of your wins and losses.  GUI written by Cameron
 * Shearer.
 *
 */
public class MineSweeperGUI extends JFrame {
    public MineSweeperGUI() {
        JFrame frame = new JFrame();


        //make sure the frame will close
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Allows the player to enter his/her name
        //and set the title of the game to the name variable
        String playerName = JOptionPane.showInputDialog("Please enter your name: ");
        frame.setTitle(playerName + "'s Minesweeper");

        //Allows the player to enter the number of rows on the board (defaults to 10)
        String rowHolder = JOptionPane.showInputDialog("Please" +
                        " enter the preferred number of rows(3-30: ",
                "10");

        //Converts the string to an integer
        int row = Integer.parseInt(rowHolder);

        //The while loop will force the player to enter a valid number of rows and contains
        //a separate string from the original message
        while (row < 3 || row > 30) {
            String newRowHolder = JOptionPane.showInputDialog("Please enter a valid number of rows");
            row = Integer.parseInt(newRowHolder);
        }

        //Allows the player to enter the number of columns on the board (defaults to 10)
        String colHolder = JOptionPane.showInputDialog("Please" +
                        " enter the preferred number of columns(3-30: ",
                "10");

        //Converts the string to an integer
        int col = Integer.parseInt(colHolder);

        //The while loop will force the player to enter a valid number of columns and contains
        //a separate string from the original message
        while (col < 3 || col > 30) {
            String newColHolder = JOptionPane.showInputDialog("Please enter a valid number of columns");
            col = Integer.parseInt(newColHolder);
        }

        //Allows the player to enter the number of mines on the board
        String mines = JOptionPane.showInputDialog("Please enter the number of mines.",
                10);

        //Converts the string to an integer
        int numMines = Integer.parseInt(mines);

        //The while loop will force the player to select a number of mines that can fit the board
        //and contains a separate string from the original message
        while (numMines > (row * col) || (numMines <= 0)) {
            String newMines = JOptionPane.showInputDialog("Too many or not enough mines" +
                    " for the board, please try again.");
            numMines = Integer.parseInt(newMines);
        }

        //Creates the frame and sets up the components for the rest of the GUI
        frame.getContentPane().add(new MineSweeperPanel(row, col, numMines));
        frame.setSize(row * row, col * col);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main (String[] args){
        MineSweeperGUI gui = new MineSweeperGUI();
    }
}