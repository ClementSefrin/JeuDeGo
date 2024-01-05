package tests;

import go.GoGame;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GoGameTest {
    @Test
    void testGetTurn() {
        GoGame g = new GoGame(6, "bb ab ac aa");
        System.out.println(g);
        assertEquals(2, g.getNbLiberties(0, 2));
        assertEquals(1, g.getNbLiberties(0, 1));
        assertEquals(1, g.getNbLiberties(0, 0));
        assertEquals(3, g.getNbLiberties(1, 1));
        g.playMove("black", "B2");
        System.out.println(g);
    }
}