import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;

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