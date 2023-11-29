package Jeu;

import java.util.Arrays;
import java.util.LinkedList;

public class Go {
    private static final String black = "X", white = "O", empty = ".";
    private int blackCaptured = 0, whiteCaptured = 0;
    private String[][] board;
    private static boolean isBoardCreated = false;

    //move history
    //komi : points de compensation pour le joueur blanc

    //--------------------------board commands--------------------------//
    public void setSize(int size) {
        isBoardCreated = true;
        board = new String[size][size];
        for (String[] line : board) {
            Arrays.fill(line, empty);
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

    public void cancelMove(int x, int y) {
        board[x][y] = empty;
    }

    public void capture(int x, int y) {
        String color = board[x][y];
        board[x][y] = ".";
        if (color.equals(black))
            blackCaptured++;
        else
            whiteCaptured++;
    }

    public void capture(LinkedList<Integer[]> group) {
        for (Integer[] coord : group)
            capture(coord[0], coord[1]);
    }

    public boolean isCaptured(int x, int y) {
        if (x < 0 || x >= board.length || y < 0 || y >= board.length)
            return false;

        if (board[x][y].equals("."))
            return false;

        String otherColor = board[x][y] == black ? white : black;

        int[][] neighbours = getNeighbours(x, y);
        for (int[] neighbour : neighbours) {
            if (neighbour != null && board[neighbour[0]][neighbour[1]].equals(empty))
                return false;
        }
        return true;
    }

    public boolean isCaptured(LinkedList<Integer[]> group) {
        for (Integer[] coord : group) {
            if (!isCaptured(coord[0], coord[1]))
                return false;
        }
        return true;
    }


    //--------------------------utils--------------------------//
    public LinkedList<Integer[]> getGroupOfStones(int x, int y) {
        LinkedList<Integer[]> temp = new LinkedList<>();
        LinkedList<Integer[]> group = new LinkedList<>();
        String color = board[x][y];
        temp.push(new Integer[]{x, y});
        group.push(new Integer[]{x, y});
        while (!temp.isEmpty()) {
            Integer[] current = temp.pop();

            int[][] ns = getNeighbours(current[0], current[1]);
            Integer[][] neighbours = convertNeighbours(ns);

            for (Integer[] neighbour : neighbours) {
                if (neighbour != null && board[neighbour[0]][neighbour[1]].equals(color)) {
                    if (!contains(group, neighbour)) {
                        temp.push(neighbour);
                        group.push(neighbour);
                    }
                }
            }
        }
        return group;
    }

    private static boolean contains(LinkedList<Integer[]> array, Integer[] element) {
        for (Integer[] e : array) {
            if (Arrays.equals(e, element))
                return true;
        }
        return false;
    }

    private Integer[][] convertNeighbours(int[][] neighbours) {
        Integer[][] neighboursInteger = new Integer[neighbours.length][2];
        for (int i = 0; i < neighbours.length; i++) {
            if (neighbours[i] != null) {
                neighboursInteger[i][0] = Integer.valueOf(neighbours[i][0]);
                neighboursInteger[i][1] = Integer.valueOf(neighbours[i][1]);
            } else {
                neighboursInteger[i] = null;
            }
        }
        return neighboursInteger;
    }

    public int[][] getNeighbours(int x, int y) {
        int[][] neighbours = {{x + 1, y}, {x - 1, y}, {x, y + 1}, {x, y - 1}};
        if (x + 1 >= board.length)
            neighbours[0] = null;
        if (x - 1 < 0)
            neighbours[1] = null;
        if (y + 1 >= board.length)
            neighbours[2] = null;
        if (y - 1 < 0)
            neighbours[3] = null;
        return neighbours;
    }

    public void resetCapturedStones() {
        blackCaptured = 0;
        whiteCaptured = 0;
    }

    private String displayLetters() {
        StringBuilder sb = new StringBuilder();
        int i = 0, len = board.length;
        sb.append(" \t");
        while (i < len) {
            if (i == 8) {
                ++i;
                ++len;
            }
            sb.append(Character.toString('A' + i)).append(" \t");
            i++;
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
                sb.append(" \t").append("WHITE (").append(Go.white).append(") has captures ").append(blackCaptured).append(" stones").append("\n");
            else if (i == board.length - 1)
                sb.append(" \t").append("BLACK (").append(Go.black).append(") has captures ").append(whiteCaptured).append(" stones").append("\n");
            else
                sb.append("\n");
        }

        sb.append(displayLetters());

        return sb.toString();
    }
}
