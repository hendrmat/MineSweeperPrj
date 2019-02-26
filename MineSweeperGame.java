package project2;

import java.util.Random;
public class MineSweeperGame {

    private Cell[][] board;
    private GameStatus status;
    private int totalMineCount;
    private MineSweeperPanel gamePanel;

    public void setTotalMineCount (int totalMineCount){
        this.totalMineCount = totalMineCount;
    }

    public int getTotalMineCount() {
        return totalMineCount;
    }


    public MineSweeperGame() {

        board = new Cell[gamePanel.getNumRows()][gamePanel.getNumCols()];
        gamePanel = new MineSweeperPanel();

        for (int row = 0; row < gamePanel.getNumRows(); row++) {
            for (int col = 0; col < gamePanel.getNumCols(); col++) {
                board[row][col] = new Cell(0, false,
                        false, false);
            }
        }

        Random random = new Random();
        int mineCount = 0;
        while (mineCount < totalMineCount) {
            int row = random.nextInt(gamePanel.getNumRows());
            int col = random.nextInt(gamePanel.getNumCols());

            if (!board[row][col].isMine()) {
                board[row][col].setMine(true);
                mineCount++;
            }
        }
    }

    public void select (int row, int col) {
        if (board[row][col].isFlagged()) {
            return;
        }

        board[row][col].setExposed(true);
        getGameStatus();

        int nearbyMines = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((board[row + i][col + j].isMine()) &&
                (row + i >= 0 && row + i < gamePanel.getNumRows()) &&
                (col + j >= 0 && col + j < gamePanel.getNumCols())) {
                    nearbyMines++;
                }
            }
        }
        board[row][col].setMineCount(nearbyMines);

        if (nearbyMines == 0) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if ((row + i >= 0 && row + i < gamePanel.getNumRows()) &&
                            (col + j >= 0 && col + j < gamePanel.getNumCols())) {
                        board[row + i][col + j].setExposed(true);
                    }
                }
            }
        }
    }
    
     public void exposeRecursive(int x, int y, int m) {

        if (x < 0 || x > gamePanel.getNumRows() || y > 0 || y > gamePanel.getNumCols()) {
            return;
        }

        else if (m == 0 && !board[x][y].isExposed()) {
            board[x][y].isExposed();
            exposeRecursive(x - 1, y - 1, m);
            exposeRecursive(x, y - 1, m);
            exposeRecursive(x + 1, y - 1, m);
            exposeRecursive(x - 1, y, m);
            exposeRecursive(x + 1, y, m);
            exposeRecursive(x - 1, y + 1, m);
            exposeRecursive(x, y + 1, m);
            exposeRecursive(x + 1, y + 1, m);
        }

        return;

    }

    public void exposeNonRecursive(int x, int y, int m) {

        if (x < 0 || x > gamePanel.getNumRows() || y > 0 || y > gamePanel.getNumCols()) {
            return;
        }

        else if (m == 0 && !board[x][y].isExposed()) {
            while (m == 0) {
                for (int a = -1; a < 2; a++) {
                    for (int b = -1; b < 2; b++) {
                        board[x + a][y + b].isExposed();
                        if (m != 0) {
                            return;
                        }
                    }
                }
            }
        }
`   }

    public void reset() {

        for (int row = 0; row < gamePanel.getNumRows(); row++) {
            for (int col = 0; col < gamePanel.getNumCols(); col++) {
                board[row][col] = new Cell(0, false,
                        false, false);
            }
        }

    }

    public GameStatus getGameStatus() {

        int covered = 0;
        for (int row = 0; row < gamePanel.getNumRows(); row++) {
            for (int col = 0; col < gamePanel.getNumCols(); col++) {
                if (!board[row][col].isExposed()) {
                    covered++;
                }
            }
        }
        if (covered == totalMineCount) {
            return status.Won;
        }

        return status.NotOverYet;

    }

    public Cell getCell(int row, int col) {
        return board[row][col];
    }
}
