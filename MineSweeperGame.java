package project2;

import javax.swing.*;
import java.util.Random;
import java.awt.*;
import java.util.Objects;

public class MineSweeperGame {


    private Cell[][] board;
    private GameStatus status;
    private int totalMineCount;
    private int numRows;
    private int numCols;
    private int wins;
    private int losses;
    private int totalSquares;
    private int exposedSquares = 0;

    public MineSweeperGame(int rows, int cols, int totalMineCount) {

        board = new Cell[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                board[row][col] = new Cell(0, false, false, false);
            }
        }
        Random random = new Random();
        int mineCount = 0;
        while (mineCount < totalMineCount) { // place the total number of mines
            int col = random.nextInt(cols);
            int row = random.nextInt(rows);
            if (!board[row][col].isMine()) {
                board[row][col].setMine(true);
                mineCount++;
            }
        }

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


        return status.NotOverYet;


    }

    public Cell getCell(int row, int col){
        return board[row][col];
    }


    public void select(int row, int col){
        //first we have to check if the cell is flagged.
        // if it is we want to do nothing.
        if(board[row][col].isFlagged()) {
            return;
        }

        board[row][col].setExposed(true);




    }

    public void reset(){

        exposedSquares = 0;
        totalSquares = 0;
        totalMineCount = 0;

        MineSweeperGame game = new MineSweeperGame(numRows, numCols, totalMineCount);

        //Allows the player to enter the number of rows on the board
        String rowHolder = JOptionPane.showInputDialog("Please" +
                        " enter the preferred number of rows(3-30: ",
                "10");

        //Converts the string to an integer
        numRows = Integer.parseInt(rowHolder);

        while (numRows < 3 || numRows > 30) {
            String newRowHolder = JOptionPane.showInputDialog("Please enter a valid number of rows");
            numRows = Integer.parseInt(newRowHolder);
        }

        //Allows the player to enter the number of rows on the board
        String colHolder = JOptionPane.showInputDialog("Please" +
                        " enter the preferred number of columns(3-30: ",
                "10");

        //Converts the string to an integer
        numCols = Integer.parseInt(colHolder);

        while (numCols < 3 || numCols > 30) {
            String newColHolder = JOptionPane.showInputDialog("Please enter a valid number of columns");
            numCols = Integer.parseInt(newColHolder);
        }

        //Allows the player to enter the number of mines on the board
        String mines = JOptionPane.showInputDialog("Please enter the number of mines.",
                10);

        //Converts the string to an integer
        totalMineCount = Integer.parseInt(mines);

        while (totalMineCount > (numRows * numCols) || (totalMineCount <= 0)) {
            String newMines = JOptionPane.showInputDialog("Too many or not enough mines" +
                    " for the board, please try again.");
            totalMineCount = Integer.parseInt(newMines);
        }

        board = new Cell[numRows][numCols];
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                board[row][col] = new Cell(0, false, false, false);
            }
        }
        Random random = new Random();
        int mineCount = 0;
        while (mineCount < totalMineCount) { // place the total number of mines
            int col = random.nextInt(numCols);
            int row = random.nextInt(numRows);
            if (!board[row][col].isMine()) {
                board[row][col].setMine(true);
                mineCount++;
            }
        }

        totalSquares = numRows * numCols;
    }

    public void incrementWins(int wins) {
        this.wins++;
    }

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

    public int getWins() {
        return wins;
    }
    public int getLosses(){
        return losses;
    }

    public void toggleFlag(int row, int column) {
        if (!board[row][column].isFlagged()) {
            board[row][column].setFlagged(true);
        }

        else if (board[row][column].isFlagged()) {
            board[row][column].setFlagged(false);
        }
    }

    public void exposeRecursive(int x, int y) {

        if (x < 0 || x > numRows || y < 0 || y > numCols) {
            return;
        }

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

        return;

    }



}