package Jeu;

import java.util.Arrays;

public class JeuGo {
    private static final String alphabet = "ABCDEFGHJKLMNOPQRSTUVWXYZ";
    private static final String black = "X", white = "O";
    private int blackCaptured = 0, whiteCaptured = 0;
    private String[][] board;
    private static boolean isBoardCreated = false;

    //move history
    //komi : points de compensation pour le joueur blanc (je crois)
    //time settings


    //--------------------------board commands--------------------------//
    public void setSize(int size) {
        isBoardCreated = true;
        board = new String[size][size];
        for (String[] line : board) {
            Arrays.fill(line, ".");
        }
    }

    public String[][] getBoard() {
        return board;
    }

    public boolean isBoardCreated() {
        return isBoardCreated;
    }

    //--------------------------play commands--------------------------//
    public void playMove(String color, int x, int y) {
        board[x][y] = (color.equals("black") ? black : white);
    }

    public void capture(int x, int y) {
        String color = board[x][y];
        board[x][y] = ".";
        if (color.equals(black))
            blackCaptured++;
        else
            whiteCaptured++;
    }

    public boolean isCaptured(int x, int y) {
        if (x < 0 || x >= board.length || y < 0 || y >= board.length)
            return false;

        if (board[x][y].equals("."))
            return false;

        String otherColor = board[x][y] == black ? white : black;

        int[][] neighbours = getNeighbours(x, y);
        for (int[] neighbour : neighbours) {
            if (neighbour != null && !board[neighbour[0]][neighbour[1]].equals(otherColor))
                return false;
        }
        return true;
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

    //--------------------------utils--------------------------//
    public void resetCapturedStones() {
        blackCaptured = 0;
        whiteCaptured = 0;
    }

    public static String getAlphabet() {
        return alphabet;
    }

    private String displayLetters() {
        StringBuilder sb = new StringBuilder();

        sb.append(" \t");
        for (int i = 0; i < board.length; i++) {
            sb.append(alphabet.charAt(i)).append(" \t");
        }
        sb.append("\n");

        return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(displayLetters());

        for (int i = 0; i < board.length; i++) {
            sb.append(board.length - i).append(" \t");
            for (int j = 0; j < board[i].length; j++) {
                sb.append(board[i][j]).append(" \t");
            }
            sb.append(board.length - i);
            if (i == board.length - 2)
                sb.append(" \t").append("WHITE (").append(JeuGo.white).append(") has captures ").append(blackCaptured).append(" stones").append("\n");
            else if (i == board.length - 1)
                sb.append(" \t").append("BLACK (").append(JeuGo.black).append(") has captures ").append(whiteCaptured).append(" stones").append("\n");
            else
                sb.append("\n");
        }

        sb.append(displayLetters());

        return sb.toString();
    }
}
