public class GameTracker{
    final SubGame[] gameBoard;
    private char currPlayer;

    public GameTracker(){
        gameBoard = new SubGame[9];
        for (int i = 0; i < 9; i++) {
            gameBoard[i] = new SubGame();
        }
        currPlayer = 'o';
    }

    public char getPlayer(){
        return currPlayer;
    }

    public void switchPlayer(){
        if(currPlayer == 'x'){
            currPlayer = 'o';
        }
        else{
            currPlayer = 'x';
        }
    }

    public boolean  place(int subGame, int box, char player){
        boolean gameWin = false;
        if(gameBoard[subGame].claimBox(box, player)){
            // check for full game win
            if(checkWin(subGame)){
                // game is won
                gameWin = true;
            }
        }
        return gameWin;
    }

    public int getAvailableFrom(int sub){
        return gameBoard[sub].getRandomAvailable();
    }

    private boolean checkWin(int subToCheck){
        int rowNo = subToCheck % 3;
        int colNo = subToCheck - (rowNo * 3);
        boolean isWin = false;
        if(gameBoard[subToCheck].getStatus() != '0'){

            // row check
            isWin = gameBoard[rowNo].getStatus() == gameBoard[rowNo + 1].getStatus() && gameBoard[rowNo].getStatus() == gameBoard[rowNo + 2].getStatus();

            // column check
            isWin = isWin || (gameBoard[colNo].getStatus() == gameBoard[colNo + 3].getStatus() && gameBoard[colNo].getStatus() == gameBoard[colNo + 6].getStatus());

            if(!isWin && subToCheck % 2 == 0){
                if(subToCheck % 4 == 0){
                    isWin = gameBoard[0].getStatus() == gameBoard[4].getStatus() && gameBoard[0].getStatus() == gameBoard[8].getStatus();
                }
                else{
                    isWin = gameBoard[2].getStatus() == gameBoard[4].getStatus() && gameBoard[2].getStatus() == gameBoard[6].getStatus();
                }
            }
        }
        return isWin;
    }
    
    public boolean checkCouldWin(int subToCheck, int posIn, char player){
        return gameBoard[subToCheck].checkCouldWin(posIn, player);
    }

    public char getSubStatus(int subGame){
        return gameBoard[subGame].getStatus();
    }

    public char getAtPosition(int subGame, int box){
        return gameBoard[subGame].getAt(box);
    }

    public int getRandomAvailable(){
        int canReturn = 9;
        while(canReturn == 9){
            canReturn = (int)(Math.random()*9);
            if(gameBoard[canReturn].getStatus() != '0'){
                canReturn = 9;
            }
        }
        return canReturn;
    }
}