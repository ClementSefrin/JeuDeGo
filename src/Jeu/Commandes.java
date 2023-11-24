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
        //tableau pas créé
        if (!go.isBoardCreated())
            return "illegal move";
        //mauvaise couleur
        if (!color.equals("white") && !color.equals("black"))
            return "syntaxe error";
        //mauvaise coordonnée
        String[][] board = go.getBoard();
        int y = go.getAlphabet().indexOf(coord.charAt(0));
        int x = go.getBoard().length - Integer.parseInt(coord.substring(1));
        if (y == -1)
            return "illegal move";
        //case déjà occupée
        if (!board[x][y].equals(".")) {
            return "illegal move";
        }

        go.playMove(color, x, y);
        //-------------pour l'instant on peut capturer que une seule pierre à la fois----------------//

        //si la pierre posée capture
        int[][] neighbours = go.getNeighbours(x, y);
        for (int[] neighbour : neighbours) {
            if (neighbour != null && go.isCaptured(neighbour[0], neighbour[1]))
                go.capture(neighbour[0], neighbour[1]);
        }

        //si la pierre posée se fait capturer
        boolean captured = false;
        if (go.isCaptured(x, y)) {
            go.capture(x, y);
            captured = true;
        }

        return "ok";
    }
}
