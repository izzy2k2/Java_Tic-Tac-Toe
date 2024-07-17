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

    // for when the player/ai selects which box they want to play in
    public char setBox(int game, int box){
        char isWin = subGames[game].setBox(box, currPlayer);

        char fullWin = '0';
        // if isWin = '0', failed to place, leave fullWin as is
        // if isWin = '1', placed but nothing else
        if(isWin == '1'){
            fullWin = '1';
        }
        else if( isWin == 'o' || isWin == 'x'){
            // has won, not just placed, so see if the game is won
            fullWin = checkWin(game);
            if(fullWin == '0'){
                fullWin = '1';
            }
        }

        // 
        if(fullWin == '0' && (isWin != '0' && isWin != '1')){
            fullWin = isCat()? 'c' : '0';
        }
        return fullWin;
    }

    // Resets the game
    public void resetGame(){
        for (int i = 0; i < 9; i++) {
            subGames[i].resetSubGame();    
        }
        currPlayer = 'o';
    }

    // gets a random available position from the subgame
    public int getAvailableFrom(int sub){
        return subGames[sub].getRandomAvailable();
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

    public char getPlayer(){
        return currPlayer;
    }

    private boolean isCat(){
        boolean isCat = true;

        for (int i = 0; isCat && i < 9; i++) {
            isCat = subGames[i].getWinState() != '0';
        }
        return isCat;
    }

    // Checks to see if the full game is won when a subgame is won
    private char checkWin(int gameWon){
        char result = '0';
        char gameState = subGames[gameWon].getWinState();

        int gameRow = gameWon / 3;
        int gameCol = gameWon % 3;

        if(subGames[gameRow].getWinState() == gameState && subGames[gameRow + 1].getWinState() == gameState && subGames[gameRow + 2].getWinState() == gameState){
            result = gameState;
        }
        else if(subGames[gameCol].getWinState() == gameState && subGames[gameCol + 3].getWinState() == gameState && subGames[gameCol + 6].getWinState() == gameState){
            result = gameState;
        }
        else if(gameWon % 4 == 0 && (subGames[0].getWinState() == gameState && subGames[4].getWinState() == gameState && subGames[8].getWinState() == gameState)){
            result = gameState;
        }
        else if(gameWon % 2 == 0 && subGames[2].getWinState() == gameState && subGames[4].getWinState() == gameState && subGames[6].getWinState() == gameState){
            result = gameState;
        }

        return result;
    }

    private void gameIsWon(){
        // all the things to do when the game is won
    }

}