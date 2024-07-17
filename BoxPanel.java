
import java.awt.Color;
import javax.swing.JPanel;

public class BoxPanel extends JPanel{
    private char boxState;

    public BoxPanel(){
        this.setBackground(new Color(0xffebcd));
        boxState = '0';
        // keep going, need boundaries
    }

    public char getBoxState(){
        return boxState;
    }

    public void setState(char stateTo){
        boxState = stateTo;
        if(stateTo == '0'){
            this.setBackground(new Color(0xffebcd));
            // reset clickable
        }
        // else does whatever changes I want made to the box(not clickable)
    }
}