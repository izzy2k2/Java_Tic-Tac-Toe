public class GUI{
    GameTracker tracker;
    WindowFrame myFrame;

    public GUI(){
        tracker = new GameTracker();

        myFrame = new WindowFrame();
    }
    public static void main(String[] args) {
        new GUI();
    }

/*
    // responds to action being performed
    @Override
    public void actionPerformed(ActionEvent e){
        count++;
        labelHere.setText("button pushed " + count + " times");
    }
*/

    // The intelligence behind the bot's turn in singleplayer games
    public void AITurn(int subSelected){
        int subGameChosen = 9;
        int boxChosen = 9;

        if(subSelected == 9){
            // any box can be picked
            boolean aIWin = false;
            int blockEnemySub = 9;
            int blockEnemyBox = 9;
            boolean playerCanWin = false;

            for (int i = 0; !aIWin && i < 9; i++) {
                // ends if there's the potential for the bot to win
                // start by seeing if the subgame is taken
                if(tracker.getSubStatus(i) == 'x' || tracker.getSubStatus(i) == 'o' || tracker.getSubStatus(i) == 'c'){
                    int winInI = checkCanWin(i, 'x');
                    int playerWinInI = checkCanWin(i, 'o');

                    if(winInI != 9){
                        // the AI can win the box
                        // see if the box will win the game
                        if(subGameChosen == 9){
                            subGameChosen = i;
                            boxChosen = winInI;
                        }
                        else{
                            // choose at random between the previously selected win box and this one
                        }
                        if(checkCanWinGame(winInI, 'x')){
                            subGameChosen = i;
                            boxChosen = winInI;
                            aIWin = true;
                        }
                    }

                    // if the AI can't win the box and it hasn't found a spot where the player can win everything, it should see if the player can win the box here
                    else if(!playerCanWin && playerWinInI != 9){
                        // the player can win in the box
                        if(blockEnemyBox == 9){
                            blockEnemySub = i;
                            blockEnemyBox = playerWinInI;
                        }
                        else{
                            // pick one at random between the old and new
                        }
                        if(checkCanWinGame(playerWinInI, 'o')){
                            blockEnemySub = i;
                            blockEnemyBox = playerWinInI;
                            playerCanWin = true;
                        }
                    }
                }
            }

            // now see what the loop has yielded, if the loop makes the decision or if it should place something random
            // no need to do anything if aiwin is true, subGame and box are perfect
            // if ai can't win, see if it can block a player win
            if(!aIWin && (playerCanWin || (subGameChosen == 9 && blockEnemyBox != 9))){
                subGameChosen = blockEnemySub;
                boxChosen = blockEnemyBox;
            }

            // if the user can't win and a spot has been selected by the loop, leave that alone
            // Otherwise if the player can win something block it

            else if(!aIWin && subGameChosen == 9){
                // random selection
                subGameChosen = tracker.getRandomAvailable();
                boxChosen = tracker.getAvailableFrom(subGameChosen);
            }
        }
        else{
            // pick something within subSelected, picks spot that will give a win if AI can win the subgame
            subGameChosen = subSelected;
            boxChosen = checkCanWin(subSelected, 'x');

            if(boxChosen == 9){
                boxChosen = checkCanWin(subSelected, 'o');
                
                // pick something at random to pick if there's no blocking the user
                if(boxChosen == 9){
                    boxChosen = tracker.getAvailableFrom(subGameChosen);
                }
            }
        }

        // end of turn stuff here, using subGameChosen and boxChosen
        endTurn(subGameChosen, boxChosen);
    }

    // Sees if a player can win the game via placing something in checkBox, returns the box that will cause the win if so
    private int checkCanWin(int checkBox, char playerIs){
        int isWin = 9;
        if(tracker.getSubStatus(checkBox) == '0'){
            for (int i = 0; i < 9; i++) {
                if(tracker.checkCouldWin(checkBox, i, playerIs)){
                    isWin = i;
                }
            }
        }
        return isWin;
    }

    // Sees if a player could win the game due to winning the given subGame
    private boolean checkCanWinGame(int claimingBox, char playerIs){
        int claimingInRow = claimingBox % 3;
        int claimingInCol = claimingBox - (claimingInRow * 3);

        // see if row/column is a winner if the position is claimed
        boolean isWin = (claimingBox == claimingInRow || tracker.getSubStatus(claimingInRow) == playerIs) && (claimingBox == claimingInRow + 1 || tracker.getSubStatus(claimingInRow + 1) == playerIs) && (claimingBox == claimingInRow + 2 || tracker.getSubStatus(claimingInRow + 2) == playerIs);
        isWin = isWin || ((claimingBox == claimingInCol || tracker.getSubStatus(claimingInCol) == playerIs) && (claimingBox == claimingInCol + 3 || tracker.getSubStatus(claimingInCol + 3) == playerIs) && (claimingBox == claimingInCol + 6 || tracker.getSubStatus(claimingInCol + 6) == playerIs));

        // see about diagonals
        if(!isWin && claimingBox % 2 == 0){
            isWin = (claimingBox % 4 == 0) && ((claimingBox == 0 || tracker.getSubStatus(0) == playerIs) && (claimingBox == 4 || tracker.getSubStatus(4) == playerIs) && (claimingBox == 8 || tracker.getSubStatus(8) == playerIs));
            isWin = isWin || ((claimingBox == 2 || tracker.getSubStatus(2) == playerIs) && (claimingBox == 4 || tracker.getSubStatus(4) == playerIs) && (claimingBox == 8 || tracker.getSubStatus(8) == playerIs));
        }

        return isWin;
    }

    // End of turn actions for either player
    private void endTurn(int subHere, int boxHere){
        // place selection, then do any updating needed
        tracker.place(subHere, boxHere);
        tracker.switchPlayer();

        // update GUI accordingly
    }
}