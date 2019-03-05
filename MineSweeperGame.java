package project2;

import javax.swing.*;
import java.util.Random;
import java.awt.*;
import java.util.Objects;

import static project2.GameStatus.NotOverYet;

/*****************************************
 * This class will contain the rules of the game as well as the win and loss
 * conditions.  Each turn, the game will check the status of whether the player
 * has won, lost, or is still continuing to play.  Created by Matt Hendrick and
 * Cameron Shearer.
 */
public class MineSweeperGame {
    //Creates a new board on which the game will be played
    private Cell[][] board;
    //This is the game status that can be set to Won, Lost, or Not Over Yet
    private GameStatus status;
    //This is the total number of mines to be used in the game
    private int totalMineCount;
    //Number of rows selected, used as an upper limit in loops
    private int numRows;
    //Number of columns selected, used as an upper limit in loops
    private int numCols;
    //The number of wins which will be incremented after each game
    private int wins;
    //The number of losses which will be incremented after each game
    private int losses;
    //The product of rows x columns, used in a calculation for whether the player has won
    private int totalSquares;
    //The number of exposed squares, used in a calculation for whether the player has won
    private int exposedSquares = 0;

    /**
     * This constructor will create a new game board each time the method is invoked.
     * The loops within will allow the board to be populated as needed for the game, as
     * well as allow for random placement of mines.
     *
     * @param rows  The number of rows in the game
     * @param cols  The number of columns in the game
     * @param totalMineCount  The total number of mines on the field of play
     */
    public MineSweeperGame(int rows, int cols, int totalMineCount) {

        //Creates a new board for the game
        board = new Cell[rows][cols];
        //This loop creates all the cells in their default state
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                board[row][col] = new Cell(0, false, false, false);
            }
        }
        //This is the randomizer that will place mines randomly throughout the board
        //with an amount equal to the user's mine input on the intro screens.
        Random random = new Random();
        int mineCount = 0;
        while (mineCount < totalMineCount) {
            int col = random.nextInt(cols);
            int row = random.nextInt(rows);
            if (!board[row][col].isMine()) {
                board[row][col].setMine(true);
                mineCount++;
            }
        }

        //These variables make up the calculation for the win condition in our program
        totalSquares = rows * cols;
        this.totalMineCount = mineCount;
    }




    public GameStatus getGameStatus(){

        exposedSquares = 0;
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (board[row][col].isExposed()) {
                    exposedSquares++;
                }
                if (board[row][col].isExposed() && board[row][col].isMine()) {
                    exposedSquares = 0;
                    totalSquares = 0;
                    totalMineCount = 0;
                    return status.Lost;
                }
            }
        }

        if (exposedSquares >= (totalSquares - totalMineCount)) {
            exposedSquares = 0;
            totalSquares = 0;
            totalMineCount = 0;
            return status.Won;
        }


        return NotOverYet;


    }

    /**
     * This method will gather the location of a current cell
     * @param row  Row of desired cell
     * @param col  Column of desired cell
     * @return  Returns the coordinates of the cell
     */
    public Cell getCell(int row, int col){
        return board[row][col];
    }


    /**
     * This method will trigger when a cell is selected.  If the selected cell is
     * flagged, we want to skip that cell.  Otherwise, this will expose a given cell.
     *
     * @param row  Row of selected cell
     * @param col  Column of selected cell
     */
    public void select(int row, int col){
        //first we have to check if the cell is flagged.
        // if it is we want to do nothing.
        if(board[row][col].isFlagged()) {
            return;
        }

        //Exposes the selected cell
        board[row][col].setExposed(true);




    }

    /**
     * This method will reset the board state upon request, victory, or loss.  At this
     * time, the game will clean up the board, rearrange the mines, and start up a new
     * game, while maintaining the win and loss records.
     *
     * NOTE: We had to repeat a significant amount of code from other methods, as it is
     * currently unknown why the initial draft of this method failed to reset the board
     * after a win or loss.
     *
     * CURRENT ISSUE: New game button calls this method but does not reset the board after
     * the prompt.
     */
    public void reset(){

        //This resets the numbers in the calculation to 0.  Without resetting these
        //variables, an error will be thrown.
        exposedSquares = 0;
        totalSquares = 0;
        totalMineCount = 0;

        //This creates a new instance of the game
        MineSweeperGame game = new MineSweeperGame(numRows, numCols, totalMineCount);

        //Allows the player to enter the number of rows on the board (default value 10)
        //Repeated the code from the previous method in case the player wants to change their mind
        String rowHolder = JOptionPane.showInputDialog("Please" +
                        " enter the preferred number of rows(3-30: ",
                "10");

        //Converts the string to an integer
        numRows = Integer.parseInt(rowHolder);

        //While loop forces the player to enter a proper number of rows if they entered
        //an invalid number earlier.
        while (numRows < 3 || numRows > 30) {
            String newRowHolder = JOptionPane.showInputDialog("Please enter a valid number of rows");
            numRows = Integer.parseInt(newRowHolder);
        }

        //Allows the player to enter the number of columns on the board
        String colHolder = JOptionPane.showInputDialog("Please" +
                        " enter the preferred number of columns(3-30: ",
                "10");

        //Converts the string to an integer
        numCols = Integer.parseInt(colHolder);

        //While loop forces the player to enter a proper number of columns if they entered
        //an invalid number earlier.
        while (numCols < 3 || numCols > 30) {
            String newColHolder = JOptionPane.showInputDialog("Please enter a valid number of columns");
            numCols = Integer.parseInt(newColHolder);
        }

        //Allows the player to enter the number of mines on the board (default 10)
        String mines = JOptionPane.showInputDialog("Please enter the number of mines.",
                10);

        //Converts the string to an integer
        totalMineCount = Integer.parseInt(mines);

        //This while loop forces the user to enter a number that won't place too many
        //mines on the board for a given game.
        while (totalMineCount > (numRows * numCols) || (totalMineCount <= 0)) {
            String newMines = JOptionPane.showInputDialog("Too many or not enough mines" +
                    " for the board, please try again.");
            totalMineCount = Integer.parseInt(newMines);
        }

        //This resets all the cells on the board, setting the board up for the next game.
        board = new Cell[numRows][numCols];
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                board[row][col] = new Cell(0, false, false, false);
            }
        }
        //This is the randomizer for the mines and will place as many mines on the board
        //as the user specifies.
        Random random = new Random();
        int mineCount = 0;
        while (mineCount < totalMineCount) {
            int col = random.nextInt(numCols);
            int row = random.nextInt(numRows);
            if (!board[row][col].isMine()) {
                board[row][col].setMine(true);
                mineCount++;
            }
        }
        //Sets up the calculation for the win condition later
        totalSquares = numRows * numCols;
    }

    /**
     * This method will increment the number of wins after the win condition
     * has been reached.
     *
     * @param wins  Number of total wins
     */

    public void incrementWins(int wins) {
        this.wins++;
    }

    /**
     * This method will increment the number of losses after the win condition
     * has been reached.
     *
     * @param losses  Number of total losses
     */

    public void incrementLosses(int losses) {
        this.losses++;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    public int getNumRows() {
        return this.numRows;
    }

    public int getNumCols(){
        return this.numCols;
    }

    /**
     * This method will get the number of wins to update the JLabel in the win column
     *
     * @return The win value to be gathered
     */

    public int getWins() {
        return wins;
    }

    /**
     * This method will get the number of losses to update the JLabel in the loss column.
     *
     * @return The loss value to be gathered
     */

    public int getLosses(){
        return losses;
    }

    /**
     * This method will toggle the flag on and off for a given cell.  A flagged
     * square is considered disabled and cannot be clicked.
     *
     * @param row  The row of a given cell
     * @param column  The column of a given cell
     */

    public void toggleFlag(int row, int column) {
        //If the cell is not flagged, set it to flagged
        if (!board[row][column].isFlagged()) {
            board[row][column].setFlagged(true);
        }
        //If the cell is flagged, set it to not flagged
        else if (board[row][column].isFlagged()) {
            board[row][column].setFlagged(false);
        }
    }

    /**
     * This method will expose the cells in a recursive manner if the cell clicked
     * has no surrounding mines.  The recursive nature of this functions means that
     * it will continue to call upon itself until the condition no longer applies.
     *
     * @param x  The row of the clicked cell
     * @param y  The column of the clicked cell
     */
    public void exposeRecursive(int x, int y) {

        //This if statement will make sure that the recursion does not go out of bounds
        //of the playfield
        if (x < 0 || x > 10 || y < 0 || y > 10) {
            return;
        }

        /*This statement will look at the eight other cells surrounding it and check to see
          if those cells also have no mines surrounding them.  If no mines, continue the
          process until there is a square with a mine around it.
        */
        else if (!board[x][y].isExposed()) {
            board[x][y].setExposed(true);
            exposeRecursive(x - 1, y - 1);
            exposeRecursive(x, y - 1);
            exposeRecursive(x + 1, y - 1);
            exposeRecursive(x - 1, y);
            exposeRecursive(x + 1, y);
            exposeRecursive(x - 1, y + 1);
            exposeRecursive(x, y + 1);
            exposeRecursive(x + 1, y + 1);
        }

        //Exits the method
        return;
    }

    /**
     * This method will perform the same function as the exposeRecursive function
     * directly above.  However, there is no recursion involved in this method and
     * we will be using strictly loops to achieve the same objective.
     *
     * @param x  The row of the clicked cell
     * @param y  The column of the clicked cell
     */

    public void exposeNonRecursive(int x, int y) {

    }

}