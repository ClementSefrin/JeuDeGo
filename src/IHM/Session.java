package IHM;

import Jeu.*;

import java.util.Arrays;
import java.util.Scanner;

public class Session {
    private static final Scanner sc = new Scanner(System.in);

    private static final int NO_ID = -1;

    public static void session() {
        JeuGo go = new JeuGo();

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
                    boardsize(id, go, params);
                    break;
                case "play":
                    play(id, go, params);
                    break;
                case "showboard":
                    showboard(id, go);
                    break;
                case "clear_board":
                    clearBoard(id, go);
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

    private static void play(int id, JeuGo go, String[] params) {
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

    private static void clearBoard(int id, JeuGo go) {
        /*
        never fails
         */
        Commandes.boardsize(go, go.getBoard().length);
        //reset number of captured stones of either color
        //reset move history to empty

        displaySuccessMessage(id, new String[]{});
    }

    private static void boardsize(int id, JeuGo go, String[] params) {
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
        displaySuccessMessage(id, new String[]{});
    }

    private static void showboard(int id, JeuGo go) {
        /*
        never fails
         */
        String board = Commandes.showboard(go);
        displaySuccessMessage(id, new String[]{board});
    }

    private static void quit(int id) {
        /*
        never fails
         */
        displaySuccessMessage(id, new String[]{});
    }

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
