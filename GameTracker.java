public class GameTracker{
    private SubGame[] gameBoard;

    public GameTracker(){
        gameBoard = new SubGame[9];
        for (int i = 0; i < 9; i++) {
            gameBoard[i] = new SubGame();
        }
    }

    public boolean  place(int subGame, int box, char player){
        boolean gameWin = false;
        if(gameBoard[subGame].claimBox(box, player)){
            // check for full game win
        }
        return gameWin;
    }
}