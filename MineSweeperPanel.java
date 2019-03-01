
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

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
    private int size;

    //Keeps track of wins and losses
    private int win;
    private int lose;

    private boolean minesExposed;

    //Displays wins and losses
    private JLabel lossesLabel;
    private JLabel winsLabel;

    //Image Icon for empty cells
    private ImageIcon emptyIcon;

    /******************************************************************
     *A method that initializes all instance variables and creates new
     * JPanels one of which contains the board for the game
     *****************************************************************/
    public MineSweeperPanel(int size){
        this.size = size;
        board = new JButton[size][size];
        //make a MineSweeperGame for the Panels use
        this.game = new MineSweeperGame(this.size);

        //create the center panel to be filled with cells/JButtons
        JPanel mineSweep = new JPanel();
        mineSweep.setPreferredSize(new Dimension(200,400));
        //create the bottom panel that will contain the labels/action
        //buttons
        JPanel somePanel = new JPanel();
        somePanel.setPreferredSize(new Dimension(600,900));
        // A mouse listener for the board
        theMouseListener mouseListener = new theMouseListener();

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
        mineSweep.setLayout(new GridLayout(this.size, this.size));
        add(mineSweep, BorderLayout.CENTER);

        //using Grid layout for overall panel as well
        somePanel.setLayout(new GridLayout(1, 6));
        add(somePanel, BorderLayout.SOUTH);

        //Making a button listener so the buttons actually work
        buttonListener listener = new buttonListener();

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


        //Sets up the board for the game
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                board[row][col] = new JButton("");
                board[row][col].addActionListener(listener);
                somePanel.add(board[row][col]);
            }
        }

        //displays board
        this.displayBoard();
    }

    public void gameButtons(theMouseListener mouseListener,
                            JPanel mineSweep) {
        JButton newBut;
        this.board = new JButton[this.size][this.size];
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                newBut = buttonSetup(mouseListener);
                this.board[i][j] = newBut;
                mineSweep.add(newBut);
            }
        }

    }

    public JButton buttonSetup(theMouseListener mouseListener) {
        emptyIcon = new ImageIcon();
        //create a new button
        JButton cellButton = new JButton("" );

        //using Dimension, set the size of the button
        cellButton.setPreferredSize(new Dimension(20, 20));

        //add our MouseListener
        cellButton.addMouseListener(mouseListener);

        //return the blueprint
        return cellButton;
    }

    private class buttonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            //if the button that is detected being clicked is the quit
            //button it closes everything associated with the program
            if (quitButton == e.getSource()) {
                System.exit(0);


            }
        }
    }
    public void displayBoard(){
        JButton button;
        for (int row = 0; row < this.size; row++) {
            for (int col = 0; col < this.size; col++) {
                iCell = this.game.getCell(row, col);
                    button = this.board[row][col];
                  if (iCell.isExposed())
                        board[row][col].setEnabled(false);
                    else
                        board[row][col].setEnabled(true);
                }


           }

        }
    private class theMouseListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e){

        }
        @Override
        public void mousePressed(MouseEvent e){
            if (e.getButton() == MouseEvent.BUTTON1) {
                selectCell(e);
            }
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

    this.win = this.game.getWins();
    this.lose = this.game.getLosses();

    winsLabel.setText("wins: " +win);
    lossesLabel.setText(("losses: "+lose));

}

/******************************************************************
 * A class that gets the location of the clicked cell.
 * @param e a Mouse Event for when someone clicks.
 * @return cells an int array that contains cell locations
 *****************************************************************/
private int[] getLocationOfEvent(MouseEvent e){
    int[] cells = {0,0};
    for(int i = 0; i<this.size;i++){
        for (int j = 0; j<this.size;j++)
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
        this.minesExposed = !this.minesExposed;


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

    @Override
    public String toString(){
        String string = new String();
        return string;
    }



}

