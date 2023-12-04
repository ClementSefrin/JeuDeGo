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
        if (!go.isBoardCreated())
            return "illegal move";
        if (!color.equals("white") && !color.equals("black"))
            return "syntaxe error";
        String[][] board = go.getBoard();
        int x = go.getBoard().length - Integer.parseInt(coord.substring(1));
        int y = coord.charAt(0) - 'A';
        if (y > 8)
            --y;

        if (isMoveIllegal(go, color, x, y))
            return "illegal move";

        go.playMove(color, x, y);

        boolean isCaptured = false;
        LinkedList<Integer[]> group = go.getGroupOfStones(x, y);
        if (go.isCaptured(group))
            isCaptured = true;

        String otherColor = board[x][y] == JeuGo.getBlack() ? JeuGo.getWhite() : JeuGo.getBlack();
        boolean captures = false;
        int[][] neighbours = go.getNeighbours(x, y);
        for (int[] neighbour : neighbours) {
            if (neighbour != null && board[neighbour[0]][neighbour[1]] == otherColor) {
                group = go.getGroupOfStones(neighbour[0], neighbour[1]);
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

        return "ok";
    }

    public static String genmove(JeuGo go, String color) {
        if (!go.isBoardCreated())
            return "illegal move";

        if (!color.equals("white") && !color.equals("black"))
            return "syntaxe error";

        String[][] board = go.getBoard();
        int size = board.length;
        int x = (int) (Math.random() * size);
        int y = (int) (Math.random() * size);

        while (isMoveIllegal(go, color, x, y)) {
            x = (int) (Math.random() * size);
            y = (int) (Math.random() * size);
        }

        if (y >= 8)
            ++y;
        String coord = (char) (y + 'A') + "" + (size - x);
        String message = play(go, color, coord);
        if (message == "ok")
            return coord;
        else
            return genmove(go, color);
    }

    private static boolean isMoveIllegal(JeuGo go, String color, int x, int y) {
        String[][] board = go.getBoard();
        if (y < 0 || y >= board.length || x < 0 || x >= board.length)
            return true;
        if (!board[x][y].equals("."))
            return true;
        return false;
    }
}
