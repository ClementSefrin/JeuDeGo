package Jeu;


import java.util.LinkedList;

public class Commandes {

    private static final int MAX_SIZE = 19, MIN_SIZE = 4;

    public static boolean boardsize(JeuGo go, int size) {
        if (size < MIN_SIZE || size > MAX_SIZE)
            return false;
        go.setSize(size);
        return true;
    }

    public static String showboard(JeuGo go) {
        if (!go.isBoardCreated())
            return "";
        return go.toString();
    }

    public static String play(JeuGo go, String color, String coord) {
        //tableau pas créé
        if (!go.isBoardCreated())
            return "illegal move";
        //mauvaise couleur
        if (!color.equals("white") && !color.equals("black"))
            return "syntaxe error";
        //mauvaise coordonnée
        String[][] board = go.getBoard();
        int x = go.getBoard().length - Integer.parseInt(coord.substring(1));
        int y = coord.charAt(0) - 'A';
        if (y > 8)
            --y;

        if (y < 0 || y >= board.length || x < 0 || x >= board.length)
            return "illegal move";


        if (!board[x][y].equals(".")) {
            return "illegal move";
        }

        go.playMove(color, x, y);

        boolean isCaptured = false;
        boolean captures = false;
        if (go.isCaptured(x, y))
            isCaptured = true;

        int[][] neighbours = go.getNeighbours(x, y);
        for (int[] neighbour : neighbours) {
            if (neighbour != null && go.getBoard()[neighbour[0]][neighbour[1]] != color) {
                LinkedList<Integer[]> group = go.getGroupOfStones(neighbour[0], neighbour[1]);
                if (go.isCaptured(group)) {
                    captures = true;
                    go.capture(group);
                }
            }
        }

        if (isCaptured && !captures) {
            go.cancelMove(x, y);
            return "illegal move";
        }

        System.out.println(showboard(go));
        return "ok";
    }
}
