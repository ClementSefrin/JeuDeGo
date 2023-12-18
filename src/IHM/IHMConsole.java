package IHM;

import Jeu.*;
import joueurs.ConsolePlayer;
import joueurs.RandomPlayer;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class IHMConsole {
    private static final Scanner sc = new Scanner(System.in);
    private static JeuGo go = new JeuGo(new ConsolePlayer(), new ConsolePlayer());
    private static final int NO_ID = -1;

    public static void session() {
        String command = "start";
        while (!go.isOver()) {
            System.out.println(go.getCurrentPlayer());
            if (!(go.getCurrentPlayer() instanceof ConsolePlayer)) {
                go.getCurrentPlayer().play(go);
                System.out.println(go.toString());
                go.switchCurrent();
                continue;
            }

            command = sc.nextLine().trim();
            String[] params = command.split(" ");

            command = params[0];
            int id = NO_ID;
            if (isNumeric(command)) {
                id = Integer.parseInt(command);
                command = params[1];
                params = Arrays.copyOfRange(params, 1, params.length);
            }

            switch (command) {
                case "player":
                    player(id, params);
                    break;
                case "boardsize":
                    boardsize(id, params);
                    break;
                case "play":
                    play(id, params);
                    break;
                case "showboard":
                    showboard(id);
                    break;
                case "clear_board":
                    clearBoard(id);
                    break;
                case "quit":
                    quit(id);
                    break;
                default:
                    displayErrorMessage(id, "unknown command");
                    break;
            }
        }
    }

    private static void player(int id, String[] params) {
        String color = params[1];
        String type = params[2];
        switch (type) {
            case "random":
                go.setPlayer(color, new RandomPlayer(color));
                break;
            case "console":
                go.setPlayer(color, new ConsolePlayer());
                break;
        }
    }

    //--------------------------board commands--------------------------//
    private static void boardsize(int id, String[] params) {
        if (params.length == 1) {
            displayErrorMessage(id, "unacceptable size");
            return;
        }

        if (!isNumeric(params[1])) {
            displayErrorMessage(id, "not an Integer");
            return;
        }
        boolean tailleCorrecte = go.boardsize(Integer.parseInt(params[1]));
        if (!tailleCorrecte) {
            displayErrorMessage(id, "unacceptable size");
            return;
        }

        go.resetCapturedStones();

        displaySuccessMessage(id, "");
    }

    private static void clearBoard(int id) {
        /*
        never fails
         */
        go.boardsize(go.getBoard().length);
        displaySuccessMessage(id, "");
    }

    private static void showboard(int id) {
        displaySuccessMessage(id, go.toString());
    }

    //--------------------------game commands--------------------------//

    private static void play(int id, String[] params) {
        if (params.length != 3) {
            displayErrorMessage(id, "syntax error");
            return;
        }
        String message = go.playMove(params[1], params[2]);
        if (message == "ok") {
            displaySuccessMessage(id, go.toString());
            go.switchCurrent();
        } else
            displayErrorMessage(id, message);
    }

    //--------------------------session commands--------------------------//
    private static void quit(int id) {
        go.end();
        displaySuccessMessage(id, "");
    }

    //--------------------------display commands--------------------------//
    private static void displayErrorMessage(int id, String errorMessage) {
        StringBuilder sb = new StringBuilder();
        sb.append("?");
        if (id != NO_ID)
            sb.append(id);
        sb.append(" ").append(errorMessage);
        System.out.println(sb.toString());
    }

    private static void displaySuccessMessage(int id, String successMessages) {
        StringBuilder sb = new StringBuilder();
        sb.append("=");
        if (id != NO_ID)
            sb.append(id);
        sb.append(" ").append(successMessages);
        System.out.println(sb.toString());
    }

    //--------------------------utils--------------------------//
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
