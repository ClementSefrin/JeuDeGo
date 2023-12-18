package joueurs;

import Jeu.JeuGo;
import Jeu.Player;

import java.util.Objects;
import java.util.Random;

public class RandomPlayer implements Player {
    private String color;

    public RandomPlayer(String color) {
        this.color = color;
    }


    @Override
    public String play(JeuGo go) {

        String[][] board = go.getBoard();
        int size = board.length;
        int x = (int) (Math.random() * size);
        int y = (int) (Math.random() * size);

        if (y >= 8)
            ++y;
        String coord = (char) (y + 'A') + "" + (size - x);
        String message = go.playMove(color, coord);
        while (!message.equals("ok")) {
            x = (int) (Math.random() * size);
            y = (int) (Math.random() * size);
            if (y >= 8)
                ++y;
            coord = (char) (y + 'A') + "" + (size - x);
            message = go.playMove(color, coord);
        }
        return "ok";
    }
}
