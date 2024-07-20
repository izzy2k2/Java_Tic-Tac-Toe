import java.awt.BasicStroke;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

public class BoxPanel extends JButton{
    private char boxState;
    private final JLabel thisLabel;

    public BoxPanel(){
        this.setBackground(new Color(0xffebcd));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(1.5f)));
        boxState = '0';
        thisLabel = new JLabel();
        this.add(thisLabel);
        // keep going, need boundaries
    }

    // Get what state this box is in
    public char getBoxState(){
        return boxState;
    }

    public void setDisabled(){
        this.setEnabled(false);
    }

    public void setEnabled(){        
        this.setEnabled(true);
    }

    // Set the state, either claiming the box or clearing it
    public void setState(char stateTo){
        boxState = stateTo;
        if(stateTo == '0'){
            this.setBackground(new Color(0xffebcd));
            thisLabel.setIcon(null);
            this.setEnabled(true);
            // reset clickable
        }
        // else does whatever changes I want made to the box when it's claimed (not clickable)
        else{
            this.setEnabled(false);
            if(stateTo == 'o'){
                thisLabel.setIcon(new StretchIcon("O.png"));
            }
            else{
                thisLabel.setIcon(new StretchIcon("X.png"));
            }
            // remove clickable, set the background(?) to image
        }
    }
}