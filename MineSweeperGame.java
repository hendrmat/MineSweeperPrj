package project2;

import javax.swing.*;
import java.util.Random;
import java.awt.*;
import java.util.Objects;

public class MineSweeperGame {


    private int size;
    private Cell[][] board;
    private GameStatus status;
    private int totalMineCount;
    private int wins;
    private int losses;


    public MineSweeperGame(int size, int totalMineCount) {

        board = new Cell[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                board[row][col] = new Cell();
            }
        }
        Random random = new Random();
        int mineCount = 0;
        while (mineCount < totalMineCount) { // place the total number of mines
            int col = random.nextInt(size);
            int row = random.nextInt(size);
            if (!board[row][col].isMine()) {
                board[row][col].setMine(true);
                mineCount++;
            }
        }


    }




    public GameStatus getGameStatus(){
        int covered = size * size;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board[row][col].isExposed()) {
                    //System.out.println("ouch");
                    return GameStatus.Lost;
                }
                if (board[row][col].isExposed()) {
                    covered--;
                }
            }
        }
        if (covered == totalMineCount) {
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

        if(board[row][col].isMine()) {
            //status = GameStatus.Lost;
            //losses++;
            //JOptionPane.showMessageDialog(null, "Wow! You Lose!");
            //reset();
        }

        board[row][col].setExposed(true);




    }

    public void reset(){
        totalMineCount = 0;
        MineSweeperGame game = new MineSweeperGame(size, totalMineCount);
    }

    public int getWins() {
        return wins;
    }
    public int getLosses(){
        return losses;
    }

    public void toggleFlag(int row, int column) {
        board[row][column].isFlagged();
    }

    public void exposeRecursive(int x, int y) {

        if (x < 0 || x > size || y < 0 || y > size) {
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