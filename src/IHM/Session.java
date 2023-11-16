package IHM;

import Jeu.*;

import java.util.Scanner;

public class Session {
    private static final Scanner sc = new Scanner(System.in);

    public static void session() {
        JeuGo go = new JeuGo();

        String command = "go";
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
                    command = quit(id, params);
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
        return;
    }

    private static void boardsize(int id, JeuGo go, String[] params) {
        if ((params.length != 2 && id == -1) || (params.length != 3 && id != -1)) {
            displayErrorMessage(id, "one parameter is needed");
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
        System.out.print("=");
        if (id != -1)
            System.out.println(id);
        else System.out.println("");

    }

    private static void showboard(int id, JeuGo go, String[] params) {
        if ((params.length != 1 && id == -1) || (params.length != 2 && id != -1)) {
            displayErrorMessage(id, "one parameter is needed");
            return;
        }
        String plateau = Commandes.showboard(go);
        if (plateau.equals("erreur")) {
            displayErrorMessage(id, "board has no size");
            return;
        }
        StringBuilder sb = new StringBuilder();

        sb.append("=");
        if (id != -1)
            sb.append(id).append("\n");
        else
            sb.append("\n");
        sb.append(plateau).append("\n");
        System.out.println(sb.toString());
    }

    private static String quit(int id, String[] params) {
        if ((params.length != 1 && id == -1) || (params.length != 2 && id != -1)) {
            displayErrorMessage(id, "no parameters needed");
            return "notQuit";
        }
        System.out.print("=");
        if (id != -1)
            System.out.println(id);
        return "quit";
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
