package IHM;

import Jeu.*;

import java.util.Arrays;
import java.util.Scanner;

public class Session {
    private static final Scanner sc = new Scanner(System.in);
    private static JeuGo go = new JeuGo();
    private static final int NO_ID = -1;

    public static void session() {
        String command = "start";
        while (!command.equals("quit")) {
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
                case "genmove":
                    genmove(id, params);
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
        boolean tailleCorrecte = Commandes.boardsize(go, Integer.parseInt(params[1]));
        if (!tailleCorrecte) {
            displayErrorMessage(id, "unacceptable size");
            return;
        }

        go.resetCapturedStones();

        displaySuccessMessage(id, "");
    }

    private static void showboard(int id) {
        String board = Commandes.showboard(go);
        displaySuccessMessage(id, board);
    }

    private static void clearBoard(int id) {
        /*
        never fails
         */
        Commandes.boardsize(go, go.getBoard().length);
        displaySuccessMessage(id, "");
    }

    //--------------------------game commands--------------------------//
    private static void play(int id, String[] params) {
        if (params.length != 3) {
            displayErrorMessage(id, "syntax error");
            return;
        }
        String message = Commandes.play(go, params[1], params[2]);
        if (message == "ok")
            displaySuccessMessage(id, "");
        else
            displayErrorMessage(id, message);
    }

    private static void genmove(int id, String[] params) {
        if (params.length != 2) {
            displayErrorMessage(id, "syntax error");
            return;
        }
        String message = Commandes.genmove(go, params[1]);
        if (message.length() <= 3)
            displaySuccessMessage(id, message);
        else
            displayErrorMessage(id, message);
    }
    //--------------------------session commands--------------------------//
    private static void quit(int id) {
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
