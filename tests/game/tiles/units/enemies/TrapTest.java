package game.tiles.units.enemies;

import game.tiles.units.player.Player;
import game.tiles.units.player.Warrior;
import game.utils.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrapTest {
    private Trap trap;
    private Player player;

    @BeforeEach
    void setUp() {
        trap=new Trap('t', "Trap", 100,10,5, 25, 1, 1);
        trap.initialize(new Position(0,0), (String x)->{});
        player=new Warrior("Warrior", 100, 10, 5, 3);
        player.initialize(new Position(0,0), (String x)->{});
    }

    @Test
    void onGameTick() {
        trap.onGameTick(player,null);
        assertEquals(1, trap.getTicksCount(), "Ticks count should be 1;");
        assertEquals(true, trap.isVisible(), "Trap should be visible.");

        trap.onGameTick(null,null);
        assertEquals(2, trap.getTicksCount(), "Ticks count should be 2;");
        assertEquals(false, trap.isVisible(), "Trap should be invisible.");
    }
}