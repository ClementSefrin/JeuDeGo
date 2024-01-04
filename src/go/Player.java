package go;

import utils.Coord;

public interface Player {
    default String[] getMove(GoGame go) {
        return null;
    }
}
