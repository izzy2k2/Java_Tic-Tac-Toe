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
}