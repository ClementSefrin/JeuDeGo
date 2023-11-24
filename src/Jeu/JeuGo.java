package Jeu;

import java.util.Arrays;

public class JeuGo {
    private static final String alphabet = "ABCDEFGHJKLMNOPQRSTUVWXYZ";
    //I est exclu, 2.11 des specifications
    private static boolean isBoardCreated = false;
    private static final String black = "X", white = "O";
    private String[][] board;
    private int blackCaptured = 0, whiteCaptured = 0;

    //number of captured stones of either color
    //move history
    //komi : points de compensation pour le joueur blanc (je crois)
    //time settings

    public void resetCapturedStones() {
        blackCaptured = 0;
        whiteCaptured = 0;
    }

    public String[][] getBoard() {
        return board;
    }

    public static String getAlphabet() {
        return alphabet;
    }

    public int[][] getNeighbours(int x, int y) {
        int[][] neighbours = {{x + 1, y}, {x - 1, y}, {x, y + 1}, {x, y - 1}};
        if (x + 1 > board.length)
            neighbours[0] = null;
        if (x - 1 < 0)
            neighbours[1] = null;
        if (y + 1 > board.length)
            neighbours[2] = null;
        if (y - 1 < 0)
            neighbours[3] = null;
        return neighbours;
    }

    public boolean isCaptured(int x, int y) {
        if (x < 0 || x >= board.length || y < 0 || y >= board.length)
            return false;
        if (board[x][y].equals("."))
            return false;
        String otherColor = board[x][y] == black ? white : black;
        //récupérer les cases voisine valides
        int[][] neighbours = getNeighbours(x, y);
        //vérifier la couleur des cases voisines valides
        for (int[] neighbour : neighbours) {
            if (neighbour != null && !board[neighbour[0]][neighbour[1]].equals(otherColor))
                return false;
        }
        return true;
    }

    public void capture(int x, int y) {
        String color = board[x][y];
        board[x][y] = ".";
        if (color.equals(black))
            blackCaptured++;
        else
            whiteCaptured++;
    }

    public void setSize(int size) {
        isBoardCreated = true;
        board = new String[size][size];
        for (String[] line : board) {
            Arrays.fill(line, ".");
        }
    }

    public void playMove(String color, int x, int y) {
        board[x][y] = (color.equals("black") ? black : white);
    }

    public boolean isBoardCreated() {
        return isBoardCreated;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        //--------------------afficher les lettres----------------------//
        sb.append("    ");
        for (int i = 0; i < board.length; i++) {
            sb.append(alphabet.charAt(i)).append("  ");
        }
        sb.append("\n");

        //--------------------afficher les lignes----------------------//
        for (int i = 0; i < board.length; i++) {
            sb.append(board.length - i).append("  ");
            //espace pour aligner chiffre/nombres
            if (board.length - i < 10)
                sb.append(" ");
            //afficher les cases
            for (int j = 0; j < board[i].length; j++) {
                sb.append(board[i][j]).append("  ");
            }
            //afficher les chiffres
            sb.append(board.length - i);
            //afficher les pierres capturées
            if (i == board.length - 2)
                sb.append("\t").append("WHITE (").append(JeuGo.white).append(") has captures ").append(blackCaptured).append(" stones").append("\n");
            else if (i == board.length - 1)
                sb.append("\t").append("BLACK (").append(JeuGo.black).append(") has captures ").append(whiteCaptured).append(" stones").append("\n");
            else sb.append("\n");

        }

        //--------------------afficher les lettres----------------------//
        sb.append("    ");
        for (int i = 0; i < board.length; i++) {
            sb.append(alphabet.charAt(i)).append("  ");
        }
        sb.append("\n");

        return sb.toString();
    }
}
