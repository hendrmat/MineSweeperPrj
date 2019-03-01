public class Cell {

    //instance variable that gives the number of mines
    private int mineCount;
    // instance variable that details if the cell is flagged
    private boolean isFlagged;
    //instance variable that details if a cell is exposed
    private boolean isExposed;
    //instance variable that details if a cell is a mine
    private boolean isMine;

    /******************************************************************
     * Constructor that sets the instance variables to their initial
     * state.
     *****************************************************************/
    public Cell(int mineCount,boolean isFlagged, boolean isExposed, boolean isMine){
        this.mineCount = mineCount;
        this.isFlagged = isFlagged;
        this.isExposed = isExposed;
        this.isMine = isMine;
    }

    /******************************************************************
     * Gets the current value of mineCount and returns it.
     * @return mineCount, An int that details the number of mines.
     *****************************************************************/
    public int getMineCount() {
        return mineCount;
    }

    /******************************************************************
     * Sets the number of mines in the game to the parameter.
     * @param mineCount an int that gives the number of mines.
     *****************************************************************/
    public void setMineCount(int mineCount) {
        this.mineCount = mineCount;
    }

    /******************************************************************
     * Gets the current state of isFlagged and returns.
     * @return isFlagged, A boolean that details if the cell
     * is flagged.
     *****************************************************************/
    public boolean isFlagged() {
        return isFlagged;
    }

    /******************************************************************
     * A method that sets the boolean value of isFlagged to
     * the parameter.
     * @param flagged a boolean that determines if the cell is flagged.
     ******************************************************************/
    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    /******************************************************************
     * Gets the current state of isExposed and returns it.
     * @return isExposed, A boolean that details if the cell
     * is exposed.
     *****************************************************************/
    public boolean isExposed() {
        return isExposed;
    }

    /******************************************************************
     * A method that accepts a boolean which changes the state of
     * IsExposed.
     * @param exposed a boolean that says whether a cell is exposed or
     * not.
     *****************************************************************/
    public void setExposed(boolean exposed) {
        isExposed = exposed;
    }

    /******************************************************************
     * Gets the current state of isMine and returns it.
     * @return isMine, A boolean that details if the cell is a mine.
     *****************************************************************/
    public boolean isMine() {
        return isMine;
    }

    /******************************************************************
     * Sets the current state of is mine equal to the parameter
     * @param mine a boolean that details if the cell is a mine
     *****************************************************************/
    public void setMine(boolean mine) {
        isMine = mine;
    }

    public void swapFlagged(){
        this.isFlagged = !this.isFlagged;
    }

    public void placeMine(){
        this.isMine = true;
    }
}
