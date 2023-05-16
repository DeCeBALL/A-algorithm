import java.util.Random;
import java.util.*;

import static java.lang.Integer.MAX_VALUE;

public class Main {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    static void reconstruct(Map<Node,Node> parent, Node current,String[][]maze, Node start){
        Node path = current;
        Node prev = new Node(0,0);
        boolean ok = false;
        while(path != start){
            for (Node key : parent.keySet()) {
                if (key == path && path != prev) {
                    if (ok) {
                       maze[key.X][key.Y] = "+";
                    }
                    ok = true;
                    prev = path;
                    path = parent.get(key);
                }
            }
        }
        for (int i = 0; i < 60; i++) {
            for (int j = 0; j < 80; j++) {
                if (maze[i][j].contains("+")){
                    System.out.print(ANSI_RED + "+" + ANSI_RESET);
                }
                else{
                    System.out.print(maze[i][j]);
                }
            }
            System.out.println();
        }
        System.exit(0);
    }
    static Node lowestF(Set<Node>set){
        int min = MAX_VALUE;
        Node minNode = new Node(0,0);
        for(Node node: set){
            if(node.F < min) {
                min = node.F;
                minNode = node;
            }
        }
        return minNode;
    }

    static List<Node> Neighbours(Node node,String[][]maze){
        List<Node> nb = new ArrayList<>();

        if( node.X-1 >= 0 && !Objects.equals(maze[node.X - 1][node.Y], "*")){
            nb.add(new Node(node.X-1,node.Y));
        }
        if(node.Y-1 >= 0 && !Objects.equals(maze[node.X][node.Y - 1], "*")){
            nb.add(new Node(node.X,node.Y-1));
        }
        if(node.X+1 < 60 && !Objects.equals(maze[node.X + 1][node.Y], "*")){
            nb.add(new Node(node.X+1,node.Y));
        }
        if(node.Y+1 < 80 && !Objects.equals(maze[node.X][node.Y + 1], "*")){
            nb.add(new Node(node.X,node.Y+1));
        }
    return nb;
    }
    public static void main(String[] args) {

        String[][] maze = new String[60][80];
        for (int i = 0; i < 60; i++) {
            for (int j = 0; j < 80; j++) {
                maze[i][j] = " ";
            }
        }

        Random rand = new Random();
        for (int z = 0; z < 4800 * 0.3; z++) {
            maze[rand.nextInt(60)][rand.nextInt(80)] = "*";
        }

        int row = rand.nextInt(60);
        int column = rand.nextInt(80);
        int row1 = rand.nextInt(60);
        int column1 = rand.nextInt(80);

        if(!Objects.equals(maze[row][column], "*"))
            maze[row][column] ="I";
        else
            throw ( new RuntimeException("The initial state couldn't be added"));

        if(!Objects.equals(maze[row1][column1], "*"))
            maze[row1][column1] ="G" ;
        else
            throw ( new RuntimeException("The goal state couldn't be added"));

        maze[row][column] ="I";
        Node start = new Node(row,column);
        maze[row1][column1] ="G" ;
        Node finish = new Node(row1,column1);

//        for (int i = 0; i < 60; i++) {
//            for (int j = 0; j < 80; j++) {
//                System.out.print(maze[i][j]);
//            }
//            System.out.println();
//        }

        Set<Node> closedSet = new HashSet<>();
        Set<Node> openSet = new HashSet<>();
        Map<Node,Node> parent = new HashMap<>();

        openSet.add(start);

        start.setG(0);
        start.setF(start.G + start.estimatedCost(start,finish));

        List<Node>nbs;
        Node current;
        int count = 0;

            while (!openSet.isEmpty()) {
                count++;
                if(count == 10000)
                    throw (new RuntimeException("failure"));
                current = lowestF(openSet);

                if (current.is_goal(finish)) {
                    reconstruct(parent, current, maze, start);
                }

                openSet.remove(current);
                closedSet.add(current);

                nbs = Neighbours(current, maze);

                for (Node nb : nbs) {
                    if (closedSet.contains(nb)) continue;

                    int tentative_g = current.G + current.estimatedCost(current, nb);

                    if (!openSet.contains(nb) || tentative_g <= nb.G) {
                        parent.put(nb, current);
                        nb.setG(tentative_g);
                        nb.setF(nb.getG() + nb.estimatedCost(nb, finish));

                        if (!openSet.contains(nb)) {
                            openSet.add(nb);
                        }
                    }
                }
            }
        throw (new RuntimeException("failure"));
    }
}
