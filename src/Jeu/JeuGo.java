package Jeu;

import java.util.Arrays;

public class JeuGo {
    private static final char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static boolean plateauCree = false;
    private String[][] plateau;

    public void setSize(int size) {
        plateauCree = true;
        plateau = new String[size][size];
        for (String[] ligne : plateau) {
            Arrays.fill(ligne, ".");
        }
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("    ");
        for (int i = 0; i < plateau.length; i++) {
            sb.append(alphabet[i]).append("  ");
        }
        sb.append("\n");

        for (int i = 0; i < plateau.length; i++) {
            sb.append(i + 1).append("  ");
            if (i < 9)
                sb.append(" ");
            for (int j = 0; j < plateau[i].length; j++) {
                sb.append(plateau[i][j]).append("  ");
            }
            sb.append(i + 1).append("\n");
        }

        sb.append("    ");
        for (int i = 0; i < plateau.length; i++) {
            sb.append(alphabet[i]).append("  ");
        }
        sb.append("\n");

        return sb.toString();
    }

    public boolean plateauCree() {
        return plateauCree;
    }
}
