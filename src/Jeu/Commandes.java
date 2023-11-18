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
        if (color.equals("white") || color.equals("black")) {
            if (coord.length() == 2) {
                int y = go.getAlphabet().indexOf(coord.charAt(0));
                int x = go.getBoard().length- Integer.parseInt(coord.substring(1));
                String[][] board = go.getBoard();
                if (!board[x][y].equals(".")) {
                    return "error";
                }
                if (x >= 0 && x < board.length && y >= 0 && y < board.length) {
                    go.playMove(color, x, y);
                }
            }
        }
        return "ok";
    }
}
