package Jeu;


public class Commandes {

    private static final int MAX_SIZE = 19, MIN_SIZE = 9;

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
        int y = go.getAlphabet().indexOf(coord.charAt(0));
        int x = go.getBoard().length - Integer.parseInt(coord.substring(1));
        if (y == -1)
            return "illegal move";

        if (!board[x][y].equals(".")) {
            return "illegal move";
        }

        go.playMove(color, x, y);
        return "ok";
    }
}
