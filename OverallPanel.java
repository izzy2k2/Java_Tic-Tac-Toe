import javax.swing.JPanel;

// Makes more sense for this to replace GameTracker
public class OverallPanel extends JPanel{
    private final SubGamePanel[] subGames;
    private char currPlayer;

    public OverallPanel(){
        subGames = new SubGamePanel[9];
        for (int i = 0; i < 9; i++) {
            subGames[i] = new SubGamePanel();
        }
        currPlayer = 'o';
    }

    // for when the player selects which box they want to play in
    public char setBox(int game, int box){
        return subGames[game].setBox(box, currPlayer);
    }

    // Resets the game
    public void resetGame(){
        for (int i = 0; i < 10; i++) {
            subGames[i].resetSubGame();    
        }
        currPlayer = 'o';
    }

    // Swaps which player is playing, whatever class is the controller will call this at turn's end
    public void swapCurrPlayer() {
        if(currPlayer == 'o'){
            currPlayer = 'x';
        }
        else{
            currPlayer = 'o';
        }
    }
}