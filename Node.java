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

    private void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    private void setY(int y) {
        this.y = y;
    }

}
