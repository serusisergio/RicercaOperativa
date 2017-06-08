package Model;
/**
 * Created by Sergio Serusi on 07/06/2017.
 */
public class Node {
    private int x = 0;
    private int y = 0;

    Node(int x, int y){
        setX(x);
        setY(y);
    }

    public int getX() {
        return x;
    }

    protected void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    protected void setY(int y) {
        this.y = y;
    }

}
