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
        /*
        Syntax error. If the engine cannot handle the new size,
        fails with the error message ”unacceptable size”
         */

        if (params.length == 1) {
            displayErrorMessage(id, "unacceptable size");
            return;
        }


        boolean tailleCorrecte = Commandes.boardsize(go, Integer.parseInt(params[1]));
        if (!tailleCorrecte) {
            displayErrorMessage(id, "unacceptable size");
            return;
        }

        //reset number of captured stones of either color
        go.resetCapturedStones();

        //reset move history to empty


        displaySuccessMessage(id, new String[]{});
    }

    private static void showboard(int id) {
        /*
        never fails
         */
        String board = Commandes.showboard(go);
        displaySuccessMessage(id, new String[]{board});
    }

    private static void clearBoard(int id) {
        /*
        never fails
         */
        Commandes.boardsize(go, go.getBoard().length);
        displaySuccessMessage(id, new String[]{});
    }

    //--------------------------game commands--------------------------//
    private static void play(int id, String[] params) {
        if (params.length != 3) {
            displayErrorMessage(id, "syntax error");
            return;
        }
        String message = Commandes.play(go, params[1], params[2]);
        if (message == "ok")
            displaySuccessMessage(id, new String[]{});
        else
            displayErrorMessage(id, message);
    }


    //--------------------------session commands--------------------------//
    private static void quit(int id) {
        /*
        never fails
         */
        displaySuccessMessage(id, new String[]{});
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

    private static void displaySuccessMessage(int id, String[] successMessages) {
        StringBuilder sb = new StringBuilder();
        sb.append("=");
        if (id != NO_ID)
            sb.append(id);
        sb.append("\n");
        for (String successMessage : successMessages) {
            sb.append(successMessage).append("\n");
        }
        System.out.print(sb.toString());
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
