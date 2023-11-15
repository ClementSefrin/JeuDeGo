package Jeu;


public class Commandes {

    public static boolean boardsize(JeuGo go, int size) {
        if (size < 9 || size > 19)
            return false;
        go.setSize(size);
        return true;
    }

    public static String showboard(JeuGo go) {
        if (!go.plateauCree())
            return "erreur";
        return go.toString();
    }
}
