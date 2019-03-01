package project2;

import javax.swing.*;
import java.util.Random;

public class MineSweeperGame {


    private int size;
    private Cell[][] board;
    private GameStatus status;
    private int totalMineCount = 10;
    private int wins;
    private int losses;


    public MineSweeperGame(int size) {

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
        return GameStatus.Lost;
    }

    public Cell getCell(int row, int col){
        return board[row][col];
    }


    public void select(int row, int col){
        //first we have to check if the cell is flagged.
        // if it is we want to do nothing.
        System.out.println("Ouch!");
        if(board[row][col].isFlagged()) {
            return;
        }

        if(board[row][col].isMine()) {

            status = GameStatus.Lost;
            losses++;
            JOptionPane.showMessageDialog(null, "Wow! You Lose!");
            reset();
        }

        board[row][col].setExposed(true);
        getGameStatus();

        int nearbyMines = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((board[row + i][col + j].isMine()) &&
                        (row + i >= 0 && row + i < size) &&
                        (col + j >= 0 && col + j < size)) {
                    nearbyMines++;
                }
            }
        }
        board[row][col].setMineCount(nearbyMines);

        if (nearbyMines == 0) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if ((row + i >= 0 && row + i < size) && col + j >= 0
                        && col + j < size) {
                        board[row + i][col + j].setExposed(true);
                    }
                }
            }
        }
    }

    public void reset(){

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                board[row][col] = new Cell();
                }
            }
    }

    public int getWins() {
        return wins;
    }
    public int getLosses(){

        return losses;
    }

    public void toggleFlag(int row, int column) {

    }



}