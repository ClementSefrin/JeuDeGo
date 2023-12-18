package Jeu;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class JeuGo {
    private static final int MAX_SIZE = 19, MIN_SIZE = 4;
    private static final String black = "X", white = "O", empty = ".";
    private boolean isOver = false;
    private int blackCaptured = 0, whiteCaptured = 0;
    private Map<String, Player> players;
    private Player currentPlayer;
    private String[][] board;
    private boolean isBoardCreated = false;

    public boolean isOver() {
        return isOver;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public JeuGo(Player white, Player black) {
        players = new HashMap<>();
        players.put("white", white);
        players.put("black", black);
        currentPlayer = players.get("black");
        boardsize(9);
    }

    //--------------------------board commands--------------------------//
    public boolean boardsize(int size) {
        if (size < MIN_SIZE || size > MAX_SIZE)
            return false;
        isBoardCreated = true;
        board = new String[size][size];
        currentPlayer = players.get("black");
        for (String[] line : board) {
            Arrays.fill(line, empty);
        }
        return true;
    }

    public void switchCurrent() {
        currentPlayer = currentPlayer.equals(players.get("white")) ? players.get("black") : players.get("white");
    }

    public void setPlayer(String color, Player type) {
        players.put(color, type);
    }

    public String[][] getBoard() {
        return board;
    }

    public boolean isBoardCreated() {
        return isBoardCreated;
    }

    //--------------------------play commands--------------------------//
    private boolean isMoveIllegal(int x, int y) {
        if (y < 0 || y >= board.length || x < 0 || x >= board.length)
            return true;
        if (!board[x][y].equals("."))
            return true;
        return false;
    }

    public String playMove(String color, String coord) {

        if (!isBoardCreated)
            return "illegal move";
        if (!color.equals("white") && !color.equals("black"))
            return "syntaxe error";
        int x = board.length - Integer.parseInt(coord.substring(1));
        int y = coord.charAt(0) - 'A';
        if (y > 8)
            --y;

        if (isMoveIllegal(x, y))
            return "illegal move";

        board[x][y] = (color.equals("black") ? black : white);

        boolean isCaptured = false;
        LinkedList<Integer[]> group = getGroupOfStones(x, y);
        if (isCaptured(group))
            isCaptured = true;

        String otherColor = board[x][y] == black ? white : black;
        boolean captures = false;
        int[][] neighbours = getNeighbours(x, y);
        for (int[] neighbour : neighbours) {
            if (neighbour != null && board[neighbour[0]][neighbour[1]] == otherColor) {
                group = getGroupOfStones(neighbour[0], neighbour[1]);
                if (isCaptured(group)) {
                    captures = true;
                    capture(group);
                }
            }
        }

        if (isCaptured && !captures) {
            cancelMove(x, y);
            return "illegal move";
        }
        return "ok";
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
        String color = board[x][y] == black ? black : white;
        boolean blocked = true;
        int[][] neighbours = getNeighbours(x, y);
        for (int[] neighbour : neighbours) {
            if (neighbour != null) {
                if (board[neighbour[0]][neighbour[1]].equals(empty))
                    return false;
            }
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
        if (!isBoardCreated())
            return "";

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

    public void end() {
        isOver = true;
    }
}
