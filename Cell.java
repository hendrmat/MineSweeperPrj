package project2;

public class Cell {
    private int mineCount;
    private boolean isFlagged;
    private boolean isExposed;
    private boolean isMine;

    public Cell() {
        mineCount = 0;
        isFlagged = false;
        isExposed = false;
        isMine = false;
    }

    public Cell(int mineCount, boolean isFlagged,
                boolean isExposed, boolean isMine) {

        this.mineCount = mineCount;
        this.isFlagged = false;
        this.isExposed = false;
        this.isMine = false;

    }

    public void setMineCount(int mineCount) {
        this.mineCount = mineCount;
    }

    public int getMineCount() {
        return mineCount;
    }

    public void setFlagged(boolean isFlagged) {
        this.isFlagged = isFlagged;
    }

    public boolean isFlagged() {
        return this.isFlagged;
    }

    public void setExposed(boolean isExposed) {
        this.isExposed = isExposed;
    }

    public boolean isExposed() {
        return this.isExposed;
    }

    public void setMine(boolean isMine) {
        this.isMine = isMine;
    }

    public boolean isMine() {
        return this.isMine;
    }



}
