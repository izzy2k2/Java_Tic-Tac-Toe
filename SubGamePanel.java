import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class SubGamePanel extends JPanel{
    private final BoxPanel[] boxes;
    private char subGameState;
    public SubGamePanel(){
        this.setBackground(new Color(0xffebcd));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        boxes = new BoxPanel[9];
        subGameState = '0';

        for (int i = 0; i < 9; i++) {
            boxes[i] = new BoxPanel();
        }
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

    public char getBoxState(int boxAt){
        return boxes[boxAt].getBoxState();
    }

    public char getWinState(){
        return subGameState;
    }

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
        resetBoxes();
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
        }
        return isWin;
    }

    // checks to see if the given char can win the subGame at fillBox
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