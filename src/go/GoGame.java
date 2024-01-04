package go;

import utils.Coord;

import java.util.*;

public class GoGame {
    private static final Character black = 'X', white = 'O', empty = '.';
    private static final int MAX_SIZE = 19, MIN_SIZE = 1;
    private Character[][] board;
    private int blackCaptured = 0, whiteCaptured = 0;
    private boolean isOver;
    private int turnsPassed;
    private int turn;

    public GoGame() {
        boardsize(5);
        isOver = false;
        turn = 0;
        turnsPassed = 0;
    }

    public int getTurn() {
        return turn;
    }

    //--------------------------play--------------------------//
    public String[] playMove(String color, String coord) {
        if (!color.equals("white") && !color.equals("black"))
            return new String[]{"error", "syntax error"};
        if (turn % 2 == 0 && !color.equals("black"))
            return new String[]{"error", "wrong color"};
        if (turn % 2 != 0 && !color.equals("white"))
            return new String[]{"error", "wrong color"};

        Character oppositeColor = color.equals("black") ? white : black;
        int x = board.length - Integer.parseInt(coord.substring(1));
        int y = coord.charAt(0) - 'A';
        if (y > 8)
            --y;

        if (!isMoveLegal(x, y))
            return new String[]{"error", "illegal move"};

        board[x][y] = color.equals("black") ? black : white;

        Set<Coord> neighbours = getAllNeighbours(new Coord(x, y));
        for (Coord c : neighbours) {
            if (board[c.x()][c.y()].equals(oppositeColor)) {
                int liberties = getNbLiberties(c.x(), c.y());
                if (liberties == 0) {
                    capture(c, oppositeColor);
                }
            }
        }
        ++turn;
        return new String[]{"success", coord + "\n" + toString()};
    }

    public String pass(String color) {
        if (!color.equals("white") && !color.equals("black"))
            return "syntax error";
        if (turn % 2 == 0 && !color.equals("black"))
            return "wrong color";
        if (turn % 2 != 0 && !color.equals("white"))
            return "wrong color";

        ++turn;
        ++turnsPassed;
        return "success";
    }

    public boolean isMoveLegal(int x, int y) {
        if (y < 0 || y >= board.length || x < 0 || x >= board.length)
            return false;
        if (!board[x][y].equals(empty))
            return false;
        return true;
    }


    //--------------------------groups--------------------------//
    public Set<Coord> getGroup(Coord xy) {
        Set<Coord> group = new HashSet<>();
        Set<Coord> visited = new HashSet<>();
        group.add(xy);
        visited.add(xy);
        return getSameColorNeighbours(xy, group, visited);
    }

    private Set<Coord> getSameColorNeighbours(Coord xy, Set<Coord> group, Set<Coord> visited) {
        Set<Coord> neighbours = getAllNeighbours(xy);
        for (Coord c : neighbours) {
            if (visited.contains(c))
                continue;
            visited.add(c);
            if (board[c.x()][c.y()].equals(board[xy.x()][xy.y()])) {
                group.add(c);
                group.addAll(getSameColorNeighbours(c, group, visited));
            }
        }
        return group;
    }


    //--------------------------liberties--------------------------//
    public int getNbLiberties(int x, int y) {
        if (!isMoveLegal(x, y))
            return -1;
        if (board[x][y].equals(empty))
            return -2;
        Coord c = new Coord(x, y);
        Set<Coord> visited = new HashSet<>();
        return nbStones(c, visited);
    }

    private int nbStones(Coord xy, Set<Coord> visited) {
        int cpt = 0;
        Set<Coord> neighbours = getAllNeighbours(xy);
        for (Coord c : neighbours) {
            if (visited.contains(c))
                continue;
            if (board[c.x()][c.y()].equals(empty)) {
                ++cpt;
                visited.add(c);
            } else if (board[c.x()][c.y()].equals(board[xy.x()][xy.y()])) {
                visited.add(c);
                cpt += nbStones(c, visited);
            }
        }
        return cpt;
    }

    private Set<Coord> getAllNeighbours(Coord xy) {
        Set<Coord> neighbours = new HashSet<>();
        int x = xy.x(), y = xy.y();
        if (x > 0)
            neighbours.add(new Coord(x - 1, y));
        if (x < board.length - 1)
            neighbours.add(new Coord(x + 1, y));
        if (y > 0)
            neighbours.add(new Coord(x, y - 1));
        if (y < board.length - 1)
            neighbours.add(new Coord(x, y + 1));
        return neighbours;
    }

    //--------------------------captures--------------------------//
    private void capture(Coord xy, Character color) {
        Set<Coord> group = getGroup(xy);
        for (Coord c : group) {
            board[c.x()][c.y()] = empty;
            if (color.equals(black))
                ++blackCaptured;
            else
                ++whiteCaptured;
        }
    }

    //--------------------------board--------------------------//
    public boolean boardsize(int size) {
        if (size < MIN_SIZE || size > MAX_SIZE)
            return false;
        board = new Character[size][size];
        for (Character[] line : board) {
            Arrays.fill(line, empty);
        }
        blackCaptured = whiteCaptured = 0;
        return true;
    }

    public boolean isBoardFull() {
        for (Character[] line : board) {
            for (Character c : line) {
                if (c.equals(empty))
                    return false;
            }
        }
        return true;
    }

    public Character[][] getBoard() {
        return board;
    }

    //--------------------------end--------------------------//
    public boolean isOver() {
        if (turnsPassed >= 2)
            isOver = true;
        return isOver;
    }

    public void end() {
        isOver = true;
    }

    //--------------------------display--------------------------//
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
        /*
        if (!isBoardCreated())
            return "";
        */

        StringBuilder sb = new StringBuilder();

        sb.append(displayLetters());

        for (int i = 0; i < board.length; i++) {
            sb.append(board.length - i).append(" \t");
            for (int j = 0; j < board[i].length; j++) {
                sb.append(board[i][j]).append(" \t");
            }
            sb.append(board.length - i);
            if (i == board.length - 2)
                sb.append(" \t").append("WHITE (").append(GoGame.white).append(") has captures ").append(blackCaptured).append(" stones").append("\n");
            else if (i == board.length - 1)
                sb.append(" \t").append("BLACK (").append(GoGame.black).append(") has captures ").append(whiteCaptured).append(" stones").append("\n");
            else
                sb.append("\n");
        }

        sb.append(displayLetters());

        return sb.toString();
    }
}
