import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SubGamePanel extends JPanel{
    private final BoxPanel[] boxes;
    private char subGameState;
    private final JLabel thisLabel;

    public SubGamePanel(){
        this.setBackground(new Color(0xffebcd));
        this.setLayout(new GridLayout(3,3,0,0));
        boxes = new BoxPanel[9];
        subGameState = '0';

        for (int i = 0; i < 9; i++) {
            boxes[i] = new BoxPanel();
            this.add(boxes[i]);
        }
        thisLabel = new JLabel();
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(2.0f)));
    }

    // If the box is successfully changed, it won't return '0'.
    // Just a successful placement is '1' 
    // If the box causes setTo to win the box it'll return that
    // If it's a cat's game, it's 'c'
    // Does not reset the boxes or do anything else for a won subGame
    public char setBox(int boxAt, char setTo){
        char isWin = '0';
        if(subGameState == '0' && boxes[boxAt].getBoxState() != '0'){
            // available subGame, available box
            boxes[boxAt].setState(setTo);
            isWin = checkForWin(boxAt) ? setTo : '1';
            if(isWin == '1' && checkForCat()){
                // see if cat's game
                isWin = 'c';
            }
        }
        return isWin;
    }

    // Gets the requested box's state
    public char getBoxState(int boxAt){
        return boxes[boxAt].getBoxState();
    }

    public void wonSub(char winner){
        subGameState = winner;
        switch (winner) {
            case 'o' -> thisLabel.setIcon(new StretchIcon("O.png"));
            case 'x' -> thisLabel.setIcon(new StretchIcon("X.png"));
            default -> thisLabel.setIcon(new StretchIcon("C.png"));
        }
    }

    // Gets this subGame's state
    public char getWinState(){
        return subGameState;
    }

    // Gets a random position out of the available spaces
    public int getRandomAvailable(){
        int toReturn = 9;
        while(toReturn == 9){
            toReturn = (int)(Math.random()*9);
            if(boxes[toReturn].getBoxState() != '0'){
                toReturn = 9;
            }
        }
        return toReturn;
    }

    // Resets this subGame and its boxes
    public void resetSubGame() {
        subGameState = '0';
        this.setBackground(new Color(0xffebcd));
        thisLabel.setIcon(null);
        resetBoxes();
    }

    // For temporarily making the entire box temporarily diabled
    public void makeAllUnavailable(){
        this.setEnabled(false);
        for (int i = 0; i < 9; i++) {
            boxes[i].setDisabled();
        }
    }

    // For bringing back all unclaimed boxes
    public void makeAllAvailable(){
        this.setEnabled(true);
        for (int i = 0; i < 9; i++) {
            if(boxes[i].getBoxState() == '0'){
                boxes[i].setEnabled();
            }
        }
    }

    // Delegating resetting boxes since both a full reset and a win should reset them
    private void resetBoxes(){
        for (int i = 0; i < 9; i++) {
            boxes[i].setState('0');
        }
    }

    // Delegating the check for if the newest change causes a win state
    private boolean checkForWin(int checkChangedBox){
        char checkEquals = boxes[checkChangedBox].getBoxState();
        int inRow = checkChangedBox / 3;
        int inCol = checkChangedBox % 3;

        // start with row
        boolean isWin = boxes[inRow].getBoxState() == checkEquals && boxes[inRow + 1].getBoxState() == checkEquals && boxes[inRow + 2].getBoxState() == checkEquals;

        // col
        isWin = isWin || (boxes[inCol].getBoxState() == checkEquals && boxes[inCol + 3].getBoxState() == checkEquals && boxes[inCol + 6].getBoxState() == checkEquals);

        // diagonal check
        if(!isWin && checkChangedBox % 2 == 0){
            isWin = boxes[2].getBoxState() == checkEquals && boxes[4].getBoxState() == checkEquals && boxes[6].getBoxState() == checkEquals;
            isWin = isWin || (checkChangedBox % 4 == 0 && boxes[0].getBoxState() == checkEquals && boxes[4].getBoxState() == checkEquals && boxes[8].getBoxState() == checkEquals);
        }

        if(isWin){
            subGameState = checkEquals;
            for (int i = 0; i < 9; i++) {
                boxes[i].setEnabled(false);
            }
            if(checkEquals == 'x'){
                thisLabel.setIcon(new StretchIcon("X.png"));
            }
            else{
                thisLabel.setIcon(new StretchIcon("O.png"));
            }
            this.setBackground(new Color(0xc9bb9d));
        }
        return isWin;
    }

    // Checks to see if the given char can win the subGame at fillBox
    public boolean checkCouldWin(int fillBox, char checkFor){
        char checkEquals = checkFor;
        int inRow = fillBox / 3;
        int inCol = fillBox % 3;

        // start with row
        boolean couldWin = (boxes[inRow].getBoxState() == checkEquals || fillBox == inRow) && (boxes[inRow + 1].getBoxState() == checkEquals || fillBox == inRow + 1) && (boxes[inRow + 2].getBoxState() == checkEquals || fillBox == inRow + 2);

        // col
        couldWin = couldWin || (boxes[inCol].getBoxState() == checkEquals && boxes[inCol + 3].getBoxState() == checkEquals && boxes[inCol + 6].getBoxState() == checkEquals);

        // diagonal check
        if(!couldWin && fillBox % 2 == 0){
            couldWin = boxes[2].getBoxState() == checkEquals && boxes[4].getBoxState() == checkEquals && boxes[6].getBoxState() == checkEquals;
            couldWin = couldWin || (fillBox % 4 == 0 && boxes[0].getBoxState() == checkEquals && boxes[4].getBoxState() == checkEquals && boxes[8].getBoxState() == checkEquals);
        }

        return couldWin;
    }

    // Delegation for checking if the game is resolved as a cat's game
    private boolean checkForCat(){
        boolean isCat = true;
        for (int i = 0; isCat && i < 9; i++) {
            isCat = boxes[i].getBoxState() != '0';
        }
        if(isCat){
            subGameState = 'c';
        }
        return isCat;
    }
}