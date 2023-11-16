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
                    quit(id);
                    break;
                default:
                    System.out.println("? unknown command");
                    break;
            }
        }
    }

    private static void boardsize(int id, JeuGo go, String[] params) {
        if ((params.length != 2 && id == -1) || (params.length != 3 && id != -1)) {
            System.out.println("? missing parameter");
            return;
        }
        int size;
        if (id == -1)
            size = Integer.parseInt(params[1]);
        else
            size = Integer.parseInt(params[2]);

        boolean tailleCorrecte = Commandes.boardsize(go, size);
        if (!tailleCorrecte) {
            System.out.println("? unacceptable size");
            return;
        }
        if (id != -1)
            System.out.print(id);
        System.out.println(" =");
    }

    private static void showboard(int id, JeuGo go, String[] params) {
        if ((params.length != 1 && id == -1) || (params.length != 2 && id != -1)) {
            System.out.println("? no parameter needed");
            return;
        }
        String plateau = Commandes.showboard(go);
        if (plateau.equals("erreur")) {
            System.out.println("? board has no size");
            return;
        }
        StringBuilder sb = new StringBuilder();
        if (id != -1)
            sb.append(id);
        sb.append(" =\n").append(plateau).append("\n");
        System.out.println(sb.toString());
    }

    private static void quit(int id) {
        if (id != -1)
            System.out.print(id);
        System.out.println(" =");
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
