package IHM;

import Jeu.*;

import java.util.Scanner;

public class Session {
    private static final Scanner sc = new Scanner(System.in);

    public static void session() {
        JeuGo go = new JeuGo();

        String commande = "go";
        while (!commande.equals("quit")) {
            commande = sc.nextLine().trim();
            String[] params = commande.split(" ");
            commande = params[0];

            switch (commande) {
                case "boardsize":
                    boardsize(go, params);
                    break;
                case "showboard":
                    showboard(go, params);
                    break;
                case "quit":
                    break;
                default:
                    System.out.printf("? unknown command");
                    break;
            }
        }
    }

    private static void boardsize(JeuGo go, String[] params) {
        if (params.length != 2) {
            System.out.println("? Erreur : la commande boardsize doit être suivie d'un paramètre");
            return;
        }
        boolean tailleCorrecte = Commandes.boardsize(go, Integer.parseInt(params[1]));
        if (!tailleCorrecte) {
            System.out.println("? unacceptable size");
            return;
        }
        System.out.println("=\n");
    }

    private static void showboard(JeuGo go, String[] params) {
        if (params.length != 1) {
            System.out.println("? Erreur : la commande showboard ne doit pas être suivie de paramètres");
            return;
        }
        String plateau = Commandes.showboard(go);
        if (plateau.equals("erreur")) {
            System.out.println("? Erreur : le plateau n'a pas de taille définie");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("=\n").append(plateau).append("\n\n");
        System.out.println(sb.toString());
    }
}
