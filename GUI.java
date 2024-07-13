import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI implements ActionListener{
    int count = 0;
    JLabel labelHere;
    GameTracker tracker;


    public GUI(){
        tracker = new GameTracker();

        JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        JButton button = new JButton("Click here");
        button.addActionListener(this);

        labelHere = new JLabel("It's been clicked");
        panel.add(button);
        panel.add(labelHere);
        panel.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                // gives position relative to top left of panel
                System.out.println(e.getX() + " " + e.getY());
            }
        });

        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout());

        ImageIcon logo = new ImageIcon("My Logo 1.png");
        frame.setIconImage(logo.getImage());

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Tic Tac Toe");
        frame.pack();
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        new GUI();
    }
    @Override
    public void actionPerformed(ActionEvent e){
        count++;
        labelHere.setText("button pushed " + count + " times");
    }
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
                int temp;
                while(subGameChosen == 9){
                    temp = (int)(Math.random() * 9);
                    if(tracker.getSubStatus(temp) == '0'){
                        // valid subgame, see if random box is acceptable
                        int temp2 = (int)(Math.random() * 9);
                        if(tracker.getAtPosition(temp, temp2) == '0'){
                            subGameChosen = temp;
                            boxChosen = temp2;
                        }
                    }
                }
            }
        }
        else{
            // pick something within subSelected, picks spot that will give a win if AI can win the subgame
            subGameChosen = subSelected;
            boxChosen = checkCanWin(subSelected, 'x');

            if(boxChosen == 9){
                boxChosen = checkCanWin(subSelected, 'o');
                
                // pick something at random to sit on if there's no blocking the user
                if(boxChosen == 9){
                    boolean isOk = false;

                    // making sure the random selection is at an available spot
                    while(!isOk){
                        boxChosen = (int)(Math.random() * 9);
                        char pos = tracker.getAtPosition(subGameChosen, boxChosen);
                        if(pos == 'x' || pos == 'o'){
                            isOk = true;
                        }
                    }
                }
            }
        }

        // end of turn stuff here, using subGameChosen and boxChosen
        endTurn(subGameChosen, boxChosen, 'x');
    }

    // starts by checking if the player can win the box
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

    private void endTurn(int subHere, int boxHere, char player){
        // place selection, then do any updating needed
        tracker.place(subHere, boxHere, player);
    }
}