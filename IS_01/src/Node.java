import java.util.Set;

public class Node {

    public int X;
    public int Y;
    public int G;
    public int F;

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public int getG() {
        return G;
    }

    public void setG(int g) {
        G = g;
    }

    public int getF() {
        return F;
    }

    public void setF(int f) {
        F = f;
    }

    public Node(int x, int y) {
        this.X = x;
        this.Y = y;
        this.G = 0;
        this.F = 0;
    }

    public Node(int x, int y,int g, int f) {
        this.X = x;
        this.Y = y;
        this.G = g;
        this.F = f;
    }

    public int estimatedCost(Node start, Node finish) {
        ///Manhattan distance
        int x = Math.abs(start.X - finish.X);
        int y = Math.abs(start.Y - finish.Y);

        return  (x + y);
    }

    public boolean is_goal (Node node){
        return this.Y == node.Y && this.X == node.X;
    }





}
