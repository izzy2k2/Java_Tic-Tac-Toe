import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLayeredPane;

public class Menu extends JLayeredPane implements ActionListener{
    // have a button for 'start' for single or multiplayer games
    private final JButton single;
    private final JButton multi;

    public Menu(){
        // initialize the buttons
        single = new JButton("1 Player");
        multi = new JButton("2 Players");
        // place the buttons in the spots

        this.add(single, Integer.valueOf(1));
        this.add(multi, Integer.valueOf(1));
    }

    // event listener
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == single){
            // do I need to put this in the frame class?
        }
        else if(e.getSource() == multi){
            //
        }
    }
}