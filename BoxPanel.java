
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class BoxPanel extends JPanel{
    private char boxState;

    public BoxPanel(){
        this.setBackground(new Color(0xffebcd));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        boxState = '0';
        // keep going, need boundaries
    }

    // Get what state this box is in
    public char getBoxState(){
        return boxState;
    }

    // Set the state, either claiming the box or clearing it
    public void setState(char stateTo){
        boxState = stateTo;
        if(stateTo == '0'){
            this.setBackground(new Color(0xffebcd));
            // reset clickable
        }
        // else does whatever changes I want made to the box when it's claimed (not clickable)
        else{
            // remove clickable, set the background(?) to image
        }
    }
}