package IHM;

import Jeu.*;

import java.util.Scanner;

public class Session {
    private static final Scanner sc = new Scanner(System.in);

    public static void session() {
        JeuGo go = new JeuGo();

        String command = "start";
        while (!command.equals("quit")) {
            command = sc.nextLine().trim();
            String[] params = command.split(" ");
            command = params[0];
            int id = -1;
            if (isNumeric(command)) {
                id = Integer.parseInt(command);
                command = params[1];
            }
            switch (command) {
                case "boardsize":
                    boardsize(id, go, params);
                    break;
                case "showboard":
                    showboard(id, go, params);
                    break;
                case "quit":
                    quit(id, params);
                    break;
                default:
                    displayErrorMessage(id, "unknown command");
                    break;
            }
        }
    }

    private static void displayErrorMessage(int id, String errorMessage) {
        StringBuilder sb = new StringBuilder();
        sb.append("?");
        if (id != -1)
            sb.append(id);
        sb.append(" ").append(errorMessage);
        System.out.println(sb.toString());
    }

    private static void displaySuccessMessage(int id, String[] successMessages) {
        StringBuilder sb = new StringBuilder();
        sb.append("=");
        if (id != -1)
            sb.append(id);
        sb.append("\n");
        for (String successMessage : successMessages) {
            sb.append(successMessage).append("\n");
        }
        System.out.print(sb.toString());
    }

    private static void boardsize(int id, JeuGo go, String[] params) {
        /*
        Syntax error. If the engine cannot handle the new size,
        fails with the error message ”unacceptable size”
         */

        if ((params.length == 1 && id == -1) || (params.length == 2 && id != -1)) {
            displayErrorMessage(id, "unacceptable size");
            return;
        }

        int size;
        if (id == -1)
            size = Integer.parseInt(params[1]);
        else
            size = Integer.parseInt(params[2]);

        boolean tailleCorrecte = Commandes.boardsize(go, size);
        if (!tailleCorrecte) {
            displayErrorMessage(id, "unacceptable size");
            return;
        }
        displaySuccessMessage(id, new String[]{});
    }

    private static void showboard(int id, JeuGo go, String[] params) {
        /*
        never fails
         */
        String plateau = Commandes.showboard(go);
        displaySuccessMessage(id, new String[]{plateau});
    }

    private static void quit(int id, String[] params) {
        /*
        never fails
         */
        displaySuccessMessage(id, new String[]{});
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
