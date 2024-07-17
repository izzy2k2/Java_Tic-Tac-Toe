import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class WindowFrame extends JFrame{

    public WindowFrame(){

        this.setTitle("Tic Tac Toe");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(420,420);
        this.setVisible(true);
        
        ImageIcon logo = new ImageIcon("My Logo 1.png");
        this.setIconImage(logo.getImage());
        this.getContentPane().setBackground(new Color(191, 127, 255));
    }
}