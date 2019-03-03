package project2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

/**
 * This class creates the game board and the buttons that go along with
 * the game.  Includes a quit button, a button to expose the mines for testing
 * purposes, and a button to start a new game.
 */

public class MineSweeperPanel extends JPanel
{
    // a 2-dimensional array that represents the GUI board
    private JButton[][] board;
    // a single cell received from the game object
    private Cell iCell;
    //JButton that allows a user to quit the game
    private JButton quitButton;
    //instance variable for the game.
    private MineSweeperGame game;
    //instance variable for the mines
    private JButton minesButton;
    //instance variable for a button that reveals cells.
    private JButton showCell;
    //instance variable for a new game button
    private JButton newGame;
    //Size of board
    private int rows;
    private int columns;
    private int win;
    private int lose;

    //Keeps track of wins and losses
    private int mines;

    private boolean minesExposed;

    //Displays wins and losses
    private JLabel lossesLabel;
    private JLabel winsLabel;

    //Image Icon for empty cells
    private ImageIcon emptyIcon;

    /******************************************************************
     *A method that initializes all instance variables and creates new
     * JPanels one of which contains the board for the game
     *****************************************************************/
    public MineSweeperPanel(int rows, int columns, int mines){
        //sets up the size of the game board
        this.rows = rows;
        this.columns = columns;
        this.mines = mines;
        board = new JButton[rows][columns];
        //make a MineSweeperGame for the Panels use
        this.game = new MineSweeperGame(this.rows, this.columns, this.mines);

        //create the center panel to be filled with cells/JButtons
        JPanel mineSweep = new JPanel();
        mineSweep.setPreferredSize(new Dimension(50 * rows,50 * columns));
        //create the bottom panel that will contain the labels/action
        //buttons
        JPanel somePanel = new JPanel();
        somePanel.setPreferredSize(new Dimension(20 * rows,40 * columns));
        // A mouse listener for the board
        TheMouseListener mouseListener = new TheMouseListener();

        //instantiating the quit button
        this.quitButton = new JButton("Quit");

        // New Game button instantiation
        this.newGame = new JButton("New Game");

        //instantiating the mine hide button
        this.minesButton = new JButton("Mine Visibility On/Off");

        //Wins and losses label instantiation
        this.lossesLabel = new JLabel("Losses: ");
        this.winsLabel = new JLabel("Wins: ");

        //Showing whats inside a cell button instantiation
        this.showCell = new JButton("Show Contents of Cell");

        //reset win and lose to 0
        this.win = 0;
        this.lose = 0;

        // Using Grid Layout set to the inputted size
        mineSweep.setLayout(new GridLayout(this.rows, this.columns));
        add(mineSweep, BorderLayout.CENTER);

        //using Grid layout for overall panel as well
        somePanel.setLayout(new GridLayout(this.rows, this.columns));
        add(somePanel, BorderLayout.SOUTH);

        //Making a button listener so the buttons actually work
        ButtonListener listener = new ButtonListener();

        //Adding the button listener to the buttons
        minesButton.addActionListener(listener);
        showCell.addActionListener(listener);
        newGame.addActionListener(listener);
        quitButton.addActionListener(listener);

        //Adding it all to the panel
        somePanel.add(newGame);
        somePanel.add(quitButton);
        somePanel.add(minesButton);
        somePanel.add(showCell);
        somePanel.add(winsLabel);
        somePanel.add(lossesLabel);


        //Sets up the board for the game
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                board[r][c] = new JButton("");
                board[r][c].addMouseListener(mouseListener);
                mineSweep.add(board[r][c]);
            }
        }

        //displays board
        this.displayBoard();
    }

    public void gameButtons(TheMouseListener mouseListener,
                            JPanel mineSweep) {
        JButton newBut;
        this.board = new JButton[this.rows][this.columns];
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                newBut = buttonSetup(mouseListener);
                this.board[i][j] = newBut;
                mineSweep.add(newBut);
            }
        }

    }

    /** This method will set up the buttons and mouse functionality to implement
     *  the other functions of the game, such as flagging mines, starting a new game,
     *  and quitting the game.  This will also allow the player to expose cells and
     *  hopefully avoid the mines.
     *
     * @param mouseListener allows the mouse to perform functions such as flagging mines
     * @return returns the size of the button
     */
    public JButton buttonSetup(TheMouseListener mouseListener) {
        emptyIcon = new ImageIcon();
        //create a new button
        JButton cellButton = new JButton("" );

        //using Dimension, set the size of the button
        cellButton.setPreferredSize(new Dimension(2 * rows, 2 * columns));

        //add our MouseListener
        cellButton.addMouseListener(mouseListener);

        //return the blueprint
        return cellButton;
    }

    /**
     * This method will provide a quit button that will allow the user to end
     * the game right away.  This will have the same effect as clicking the X
     * on the upper right corner.
     */
    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            //if the button that is detected being clicked is the quit
            //button it closes everything associated with the program
            if (quitButton == e.getSource()) {
                System.exit(0);
            }

            if (minesButton == e.getSource()) {
                toggleMines();
            }

            if (newGame == e.getSource()) {
                game.reset();
            }

            if (showCell == e.getSource()) {
                displayBoard();
            }

        }
    }

    /**
     * This button will allow the player to see the board in its entirety, mines included.
     * This will be useful for those who want to test how the game works without leaving
     * everything to probability.
     */
    public void displayBoard() {
        JButton button;

        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.columns; col++) {
                iCell = this.game.getCell(row, col);
                button = this.board[row][col];

                if (iCell.isExposed()) {
                    board[row][col].setEnabled(false);
                }
                else {
                    board[row][col].setEnabled(true);
                    board[row][col].setText("");
                }

                if (iCell.isFlagged()) {
                    board[row][col].setText("F");
                    board[row][col].setEnabled(false);
                }

                if (iCell.isMine()) {
                    if (minesExposed == true) {
                        board[row][col].setText("M");
                    }
                    else if(minesExposed == false) {
                        board[row][col].setText("");
                        if (iCell.isFlagged()) {
                            board[row][col].setText("F");
                        }
                    }
                }

                int nearbyMines = 0;
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        if ((!iCell.isMine()) &&
                            (row + i >= 0 && row + i < rows) &&
                            (col + j >= 0 && col + j < columns)) {
                            if (game.getCell(row + i, col + j).isMine()) {
                                nearbyMines++;
                            }
                        }
                    }
                }

                if (!iCell.isMine() && iCell.isExposed()) {
                    if (nearbyMines != 0) {
                        board[row][col].setText("" + nearbyMines);
                    }
                    if (nearbyMines == 0) {
                        game.exposeRecursive(row, col);
                    }
                }
            }
        }
    }
    /**
     * This class provides mouse functionality.  This will be necessary for the
     * user as it governs flagging as well as exposing cells.
     */
    private class TheMouseListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e){

        }

        //This mouse method will allow the user to left click to select cells
        @Override
        public void mousePressed(MouseEvent e){
            if (e.getButton() == MouseEvent.BUTTON1) {
                selectCell(e);
            }
            //This mouse method will allow the user to right click to flag mines
            else if (e.getButton() == MouseEvent.BUTTON3){
                toggleCellFlag(e);
            }
        }
        @Override
        public void mouseReleased(MouseEvent e){

        }
        @Override
        public void mouseEntered(MouseEvent e){

        }

        @Override
        public void mouseExited(MouseEvent e){

        }
    }

    /******************************************************************
     * A class that selects a cell and updates wins/losses if that is
     * the result.
     * @param e a Mouse Event for when someone clicks.
     *****************************************************************/
    private void selectCell(MouseEvent e){
        int rowsAndColumns[] = getLocationOfEvent(e);
        int rows = Objects.requireNonNull(rowsAndColumns)[0];
        int columns = rowsAndColumns[1];
        this.game.select(rows,columns);

        if (game.getGameStatus() == GameStatus.Lost) {
            JOptionPane.showMessageDialog(null, "Wow! You Lose!");
            game.reset();
        }

        if (game.getGameStatus() == GameStatus.Won) {
            JOptionPane.showMessageDialog(null, "Bravo! You Win!");
            game.reset();
        }

        this.win = this.game.getWins();
        this.lose = this.game.getLosses();

        winsLabel.setText("wins: " +win);
        lossesLabel.setText(("losses: "+lose));

        displayBoard();
    }

    /******************************************************************
     * A class that gets the location of the clicked cell.
     * @param e a Mouse Event for when someone clicks.
     * @return cells an int array that contains cell locations
     *****************************************************************/
    private int[] getLocationOfEvent(MouseEvent e){
        int[] cells = {0,0};
        for(int i = 0; i <this.rows ;i++){
            for (int j = 0; j <this.columns ;j++)
                if(this.board[i][j] == e.getSource()){
                    cells[0]=i;
                    cells[1]=j;
                    return cells;
                }
        }
        //this should not occur if something triggers a listener
        return null;
    }
    private void toggleCellFlag(MouseEvent e) {
        //getting the location of the cell that we need flag/unflag
        int rowsAndColumns[]= getLocationOfEvent(e);
        int row = Objects.requireNonNull(rowsAndColumns)[0];
        int column = rowsAndColumns[1];

        this.game.toggleFlag(row,column);


        //recall the GUI so it can update

        displayBoard();
    }

    private void toggleMines(){

        //setting mines exposed to the opposite value

        if(minesExposed == false) {
            this.minesExposed = true;
        }
        else {
            this.minesExposed = false;
        }



        //swapping the Mines appearance
        if(minesExposed){
            minesButton.setText("Hide Mines");

        }
        else{
            minesButton.setText("Show Mines");

        }


        //displaying the GUI again to update it
        displayBoard();
    }
}