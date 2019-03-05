package project2;

import javax.swing.*;
import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.Image;
import javax.imageio.*;
import java.io.*;
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
    //Variable used to tally the wins and losses
    private int win;
    private int lose;

    //Number of mines
    private int mines;

    //Shows whether or not the mines are revealed on the board
    private boolean minesExposed;

    //Displays wins and losses
    private JLabel lossesLabel;
    private JLabel winsLabel;

    //Image Icon for empty cells as well as the number of icons on the board
    private ImageIcon[] image;
    private final int NUM_ICONS = 12;

    /******************************************************************
     *A method that initializes all instance variables and creates new
     * JPanels one of which contains the board for the game
     * We will also set up the ImageIcon array for ease of
     * use in future methods, particularly in the method that determines the number
     * of surrounding mines.
     *
     * Credit to http://zetcode.com/tutorials/javagamestutorial/minesweeper/ for
     * the idea to use an array for the image icons.
     *****************************************************************/
    public MineSweeperPanel(int rows, int columns, int mines){
        //sets up the size of the game board
        this.rows = rows;
        this.columns = columns;
        //sets up the number of mines on the game board
        this.mines = mines;
        //sets up the board for the game
        board = new JButton[rows][columns];
        //make a MineSweeperGame for the Panels use
        this.game = new MineSweeperGame(this.rows, this.columns, this.mines);

        //create the center panel to be filled with cells/JButtons
        JPanel mineSweep = new JPanel();
        mineSweep.setPreferredSize(new Dimension(50 * rows,50 * columns));
        //create the bottom panel that will contain the labels/action buttons
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

        //Adding the ImageIcons to the array
        for (int i = 0; i < NUM_ICONS; i++) {
            String path = "src/project2/msimages/" + i + ".svg";
            image[i] = new ImageIcon(path);
        }


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

        //create a new button
        JButton cellButton = new JButton(image[11]);

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

            //This button will either show or not show the mines
            if (minesButton == e.getSource()) {
                toggleMines();
            }

            //This button will reset the game and allow entry of rows, columns,
            //and mines
            if (newGame == e.getSource()) {
                game.reset();
            }

            if (showCell == e.getSource()) {
                displayBoard();
            }

        }
    }

    /**
     * This button will allow the player to see what has already been clicked,
     * which will hopefully provide the player with enough clues to locate and
     * flag any mines that may be hidden on the board
     */
    public void displayBoard() {
        JButton button;

        //This loop will call for the position of a given cell
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.columns; col++) {
                iCell = this.game.getCell(row, col);
                button = this.board[row][col];

                //If the cell is exposed, disable the cell so clicking it
                //will have no effect
                if (iCell.isExposed()) {
                    board[row][col].setEnabled(false);
                }
                //If the cell is not exposed, set the cell to enabled and
                //able to be clicked.
                else {
                    board[row][col].setEnabled(true);
                    board[row][col].setIcon(image[11]);
                }

                //If the cell is flagged, set the cell to disabled and place
                //a flag image on the cell to indicate it is safe to click
                if ((iCell.isFlagged()) && (!iCell.isExposed())) {
                    board[row][col].setIcon(image[10]);
                    board[row][col].setEnabled(false);
                }

                //Shows the image of a mine if the cell is exposed and mines are
                //revealed
                if (iCell.isMine()) {
                    if (minesExposed == true) {
                        board[row][col].setIcon(image[9]);
                    }
                    else if(minesExposed == false) {
                        board[row][col].setIcon(image[11]);
                        //Prioritizes the image of a flag over that of a blank image
                        //if the cell was a mine.  Previous versions of the program
                        //tipped off the player by leaving a blank where the flag should be
                        if (iCell.isFlagged()) {
                            board[row][col].setIcon(image[10]);
                        }
                    }
                }

                //Sets the variable for any nearby mines to zero on a given square
                int nearbyMines = 0;
                //This loop will check the eight squares surrounding the selected cell
                //for mines.  If so, increment the variable by one for each mine.
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

                //This will set the image for the proper number of mines surrounding
                //the selected cell
                if (!iCell.isMine() && iCell.isExposed()) {
                        board[row][col].setIcon(image[nearbyMines]);
                        //This will reveal the squares surrounding mines that also
                        //do not have any nearby mines.  Repeat until there is a mine
                        //nearby.
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

        /*This will allow the game to continue on and also stop this method call
        //early.  Previous versions went through multiple method calls to
        //getGameStatus(), which caused a bug in calculating the win condition.
        */
        if (game.getGameStatus() == GameStatus.NotOverYet) {
            displayBoard();
            return;
        }

        //Sets the game status to lost as well as increments the number of losses.
        //This method is placed before the won condition to prioritize a loss in the
        //event of a player hitting a mine on what would be the last play of the game.
        if (game.getGameStatus() == GameStatus.Lost) {
            game.incrementLosses(lose);
            JOptionPane.showMessageDialog(null, "Wow! You Lose!");
            game.reset();
        }

        //Set the game status to won as well as increments the number of wins.
        else if (game.getGameStatus() == GameStatus.Won) {
            game.incrementWins(win);
            JOptionPane.showMessageDialog(null, "Bravo! You Win!");
            game.reset();
        }

        //Returns wins and losses
        this.win = this.game.getWins();
        this.lose = this.game.getLosses();

        //Sets the label to reflect the proper number of wins and losses
        winsLabel.setText("wins: " +win);
        lossesLabel.setText(("losses: "+lose));

        //Displays the current state of the board
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