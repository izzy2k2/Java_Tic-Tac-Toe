public class GameTracker{
    final SubGame[] gameBoard;

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
            if(checkWin(subGame)){
                // game is won
            }
        }
        return gameWin;
    }

    private boolean checkWin(int subToCheck){
        int rowNo = subToCheck % 3;
        int colNo = subToCheck - (rowNo * 3);
        boolean isWin = false;
        if(gameBoard[subToCheck].getStatus() != '0'){

            // row check
            isWin = gameBoard[rowNo].getVal() == gameBoard[rowNo + 1].getVal() && gameBoard[rowNo].getVal() == gameBoard[rowNo + 2].getVal();

            // column check
            isWin = isWin || (gameBoard[colNo].getVal() == gameBoard[colNo + 3].getVal() && gameBoard[colNo].getVal() == gameBoard[colNo + 6].getVal());

            if(!isWin && subToCheck % 2 == 0){
                if(subToCheck % 4 == 0){
                    isWin = gameBoard[0].getVal() == gameBoard[4].getVal() && gameBoard[0].getVal() == gameBoard[8].getVal();
                }
                else{
                    isWin = gameBoard[2].getVal() == gameBoard[4].getVal() && gameBoard[2].getVal() == gameBoard[6].getVal();
                }
            }
        }
        return isWin;
    }
}