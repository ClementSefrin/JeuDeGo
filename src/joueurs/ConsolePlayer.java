package joueurs;

import Jeu.JeuGo;
import Jeu.Player;

public class ConsolePlayer implements Player {
    @Override
    public String play(JeuGo go) {
        return "ok";
    }
}
