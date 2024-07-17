public class SubGame{
    // 0 for available, x for x, o for o, c for cat
    private char claimType;
    final Box[] boxSet;

    public SubGame(){
        claimType = '0';
        boxSet = new Box[9];
        for(int i = 0; i < 9; i++){
            boxSet[i] = new Box();
        }
    }

    public char getStatus(){
        return claimType;
    }

    public boolean claimBox(int boxPos, char whoClaims){
        boolean boxWins = false;
        if(claimType == '0'){
            boxSet[boxPos].modBox(whoClaims);
            if(checkForWin(boxPos)){
                claimType = whoClaims;
                boxWins = true;
            }
        }
        return boxWins;
    }
    
    public char getAt(int pos){
        return boxSet[pos].getVal();
    }

    private  boolean checkForWin(int atNewPos){
        int rowNo = atNewPos % 3;
        int colNo = atNewPos - (rowNo * 3);

        // row check
        boolean isWin = boxSet[rowNo].getVal() == boxSet[rowNo + 1].getVal() && boxSet[rowNo + 1].getVal() == boxSet[rowNo + 2].getVal();

        // column check
        isWin = isWin || (boxSet[colNo].getVal() == boxSet[colNo + 3].getVal() && boxSet[colNo + 3].getVal() == boxSet[colNo + 6].getVal());
        
        // diagonal check
        if(!isWin && atNewPos % 2 == 0){
            // see about topLeft section
            isWin = atNewPos % 4 == 0 && (boxSet[0].getVal() == boxSet[4].getVal() && boxSet[4].getVal() == boxSet[8].getVal());
            isWin = isWin || (boxSet[2].getVal() == boxSet[4].getVal() && boxSet[4].getVal() == boxSet[6].getVal());
        }
        
        return isWin;
    }

    public boolean checkCouldWin(int atPos, char checkFor){
        int row = atPos / 3;
        int col = atPos % 3;

        // just start with the row
        boolean couldWin = (atPos == row || boxSet[row].getVal() == checkFor) && (atPos == row + 1 || boxSet[row + 1].getVal() == checkFor) && (atPos == row + 2 || boxSet[row + 2].getVal() == checkFor);

        // column check
        couldWin = couldWin || ((atPos == col || boxSet[col].getVal() == checkFor) && (atPos == col + 3 || boxSet[col + 3].getVal() == checkFor) && (atPos == col + 6 || boxSet[col + 6].getVal() == checkFor));

        // diagonals
        if(!couldWin && atPos % 2 == 0){
            couldWin = atPos % 4 == 0 && ((atPos == 0 || boxSet[0].getVal() == checkFor) && (atPos == 4 || boxSet[4].getVal() == checkFor) && (atPos == 8 || boxSet[8].getVal() == checkFor));
            couldWin = couldWin || ((atPos == 2 || boxSet[2].getVal() == checkFor) && (atPos == 4 || boxSet[4].getVal() == checkFor) && (atPos == 6 || boxSet[6].getVal() == checkFor));
        }

        return couldWin;
    }

    public int getRandomAvailable(){
        int toReturn = 9;
        while(toReturn == 9){
            toReturn = (int)(Math.random()*9);
            if(boxSet[toReturn].getVal() != '0'){
                toReturn = 9;
            }
        }
        return toReturn;
    }
}