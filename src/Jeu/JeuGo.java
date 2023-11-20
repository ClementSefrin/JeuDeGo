package Jeu;

import java.util.Arrays;

public class JeuGo {
    private static final String alphabet = "ABCDEFGHJKLMNOPQRSTUVWXYZ";
    //I est exclu, 2.11 des specifications
    private static boolean isBoardCreated = false;
    private static final String black = "X", white = "O";
    private String[][] board;

    //board configuration
    //number of captured stones of either color
    //move history
    //komi : points de compensation pour le joueur blanc (je crois)
    //time settings


    public String[][] getBoard() {
        return board;
    }

    public static String getAlphabet() {
        return alphabet;
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

        sb.append("    ");
        for (int i = 0; i < board.length; i++) {
            sb.append(alphabet.charAt(i)).append("  ");
        }
        sb.append("\n");

        for (int i = 0; i < board.length; i++) {
            sb.append(board.length - i).append("  ");
            if (board.length - i < 10)
                sb.append(" ");
            for (int j = 0; j < board[i].length; j++) {
                sb.append(board[i][j]).append("  ");
            }
            sb.append(board.length - i).append("\n");
        }

        sb.append("    ");
        for (int i = 0; i < board.length; i++) {
            sb.append(alphabet.charAt(i)).append("  ");
        }
        sb.append("\n");

        return sb.toString();
    }
}
