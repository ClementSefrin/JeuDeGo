package players;

import go.GoGame;
import go.Player;
import utils.Coord;

public class RandomPlayer implements Player {
    @Override
    public String[] getMove(GoGame go) {
        String color = go.getTurn() % 2 == 0 ? "black" : "white";

        if (go.isBoardFull())
            return new String[]{"pass", color};
        int size = go.getBoard().length, x, y;
        do {
            x = (int) (Math.random() * size);
            y = (int) (Math.random() * size);
            if (y >= 8)
                ++y;
        } while (!go.isMoveLegal(x, y));

        String coord = (char) (y + 'A') + "" + (size - x);
        return new String[]{"play", color, coord};
    }
}
