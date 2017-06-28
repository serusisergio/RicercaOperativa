package core.model;
/**
 * Created by Sergio Serusi on 07/06/2017.
 */
public abstract class Node {
    protected int x = 0;
    protected int y = 0;
    protected int id;

    public Node(int x, int y, int id){
        setX(x);
        setY(y);
        this.id = id;
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

    public int getId(){return id;}
}
