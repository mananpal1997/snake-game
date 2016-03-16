package board;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


@SuppressWarnings("serial")
public class snake_game extends JFrame {

    public snake_game() {
        JOptionPane.showMessageDialog(rootPane, "Instructions :\nPress r(or R) to reset the game, and\nEsc to end the game");
        add(new snake_board());
        setResizable(false);
        pack();
        setTitle("Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {                
                JFrame ex = new snake_game();
                ex.setVisible(true);                
            }
        });
    }
}