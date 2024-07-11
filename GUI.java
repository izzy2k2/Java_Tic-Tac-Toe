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

public class GUI implements ActionListener{
    int count = 0;
    JLabel labelHere;
    public GUI(){
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        JButton button = new JButton("Click here");
        button.addActionListener(this);

        labelHere = new JLabel("It's been clicked");
        panel.add(button);
        panel.add(labelHere);
        panel.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                // gives position relative to top left of panel
                System.out.println(e.getX() + " " + e.getY());
            }
        });

        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout());

        ImageIcon logo = new ImageIcon("My Logo 1.png");
        frame.setIconImage(logo.getImage());

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Tic Tac Toe");
        frame.pack();
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        new GUI();
    }
    @Override
    public void actionPerformed(ActionEvent e){
        count++;
        labelHere.setText("button pushed " + count + " times");
    }
    public void AITurn(){
        //
    }
}