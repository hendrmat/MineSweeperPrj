import java.util.Random;

public class MineSweeperGame {


    private int size;
    private Cell[][] board;
    private GameStatus status;
    private int totalMineCount = 10;
    private int wins;
    private int losses;


    public MineSweeperGame(int size) {

        board = new Cell[10][10];
        for (int row = 0; row < 10; row++)
            for (int col = 0; col < 10; col++)
// this constructor creates a new cell with
// minCount = 0, flagged = false, exposed = false
// isMine = false.
                board[row][col] = new Cell(0, false, false, false);
        Random random = new Random();
        int mineCount = 0;
        while (mineCount < totalMineCount) { // place 10 mines.
            int col = random.nextInt(10);
            int row = random.nextInt(10);
            if (!board[row][col].isMine()) {
                board[row][col].setMine(true);
                mineCount++;
            }
        }


            }




    public GameStatus getGameStatus(){
        return GameStatus.LOST;
    }

    public Cell getCell(int row, int col){
        return board[row][col];
    }


    public void select(int row, int col){
        //first we have to check if the cell is flagged.
        // if it is we want to do nothing.

        if(board[row][col].isFlagged())
            return;

        board[row][col].setExposed(true);
    }

    public void reset(){

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