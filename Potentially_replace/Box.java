public class Box{
    // 0 for available, x for x, o for o, w for won
    private char claimType;
    public Box(){
        claimType = '0';
    }
    public void modBox(char modTo){
        claimType = modTo;
    }
    public char getVal(){
        return claimType;
    }
}