public class TSTNode {
    char c;
    boolean isLastChar;
    TSTNode left, middle, right;
 
    public TSTNode(char c)
    {
        this.c = c;
        this.isLastChar = false;
        this.left = null;
        this.middle = null;
        this.right = null;
    }
}