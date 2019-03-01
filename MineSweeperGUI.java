import javax.swing.*;

public class MineSweeperGUI extends JFrame {
    public MineSweeperGUI(String name) {
        JFrame frame = new JFrame(name);
        //set the title of the game to the name variable



        //make sure the frame will close
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            String playerName = JOptionPane.showInputDialog("Please enter your name: ");
            frame.setTitle(playerName + "'s Minesweeper");
            String sizeHolder = JOptionPane.showInputDialog("Please" +
                            " enter the preferred size of the board (3-30: ",
                    "10");
            int size = Integer.parseInt(sizeHolder);




            frame.getContentPane().add(new MineSweeperPanel(size));
            frame.setSize(10 * size, 10 * size);
            frame.setVisible(true);

        } catch (Exception e) {
            System.out.println(e);
        }




    }
    @Override
    public String toString(){
        String string = new String();
        return string;
    }

    public static void main (String[]args){
        new MineSweeperGUI("Mine");
    }
}
