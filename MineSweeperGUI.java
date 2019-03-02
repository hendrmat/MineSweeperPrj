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
        //set the title of the game to the name variable

        //make sure the frame will close
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Allows the player to enter his/her name
        String playerName = JOptionPane.showInputDialog("Please enter your name: ");
        frame.setTitle(playerName + "'s Minesweeper");

        //Allows the player to enter the size of the board
        String sizeHolder = JOptionPane.showInputDialog("Please" +
                        " enter the preferred size of the board (3-30: ",
                "10");

        //Allows the player to enter the number of mines on the board
        String mines = JOptionPane.showInputDialog("Please enter the number of mines.",
                10);

        //Converts the string to an integer
        int size = Integer.parseInt(sizeHolder);
        int numMines = Integer.parseInt(mines);

        //Creates the frame and sets up the components for the rest of the GUI
        frame.getContentPane().add(new MineSweeperPanel(size, numMines));
        frame.setSize(10 * size, 10 * size);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main (String[]args){

        MineSweeperGUI gui = new MineSweeperGUI();
    }
}