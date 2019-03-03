package project2;

import javax.swing.*;
import java.util.Random;
import java.awt.*;
import java.util.Objects;

public class MineSweeperGame {


    private Cell[][] board;
    private GameStatus status;
    private int totalMineCount;
    private int rows;
    private int cols;
    private int wins;
    private int losses;


    public MineSweeperGame(int rows, int cols, int totalMineCount) {

        board = new Cell[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                board[row][col] = new Cell();
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


    }




    public GameStatus getGameStatus(){
        int covered = rows * cols;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (board[r][c].isExposed() && board[r][c].isMine()) {
                   return GameStatus.Lost;
                }
            }
        }
        //if (board[row][col].isExposed() && board[row][col].isMine()) {
         //   return GameStatus.Lost;
        //}
        if (board[rows][cols].isExposed()) {
            covered--;
            if (covered == totalMineCount) {
                return status.Lost;
            }
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

        if(board[row][col].isMine()) {
            //status = GameStatus.Lost;
            //losses++;
            //JOptionPane.showMessageDialog(null, "Wow! You Lose!");
            //reset();
        }

        board[row][col].setExposed(true);




    }

    public void reset(){
        MineSweeperGame game = new MineSweeperGame(rows, cols, totalMineCount);
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

        if (x < 0 || x > rows || y < 0 || y > cols) {
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