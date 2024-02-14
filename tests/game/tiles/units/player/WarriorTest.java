package game.tiles.units.player;

import game.utils.AbilityCastInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WarriorTest {
    private Warrior warrior;

    @BeforeEach
    void setUp() {
        warrior=new Warrior("Warrior", 100, 10, 5, 3);
        warrior.initialize(null, (String x)->{});
    }

    @Test
    void abilityCast() {
        warrior.abilityCast(()->{return new AbilityCastInfo(null, null);});
        warrior.getHealth().removeAmount(60);
        assertEquals(3, warrior.getRemainingCooldown(), "Remaining cooldown should be equal to the ability cooldown after ability cast.");
        assertEquals(90, warrior.getHealth().getAmount(), "Current health should be 90;");

    }

    @Test
    void levelUp() {
        warrior.levelUp();
        //assertEquals(-50, warrior.getExperience(), "Experience after leveling up should be -50.");
        assertEquals(2, warrior.getPlayerLevel(), "The level after leveling up should be 2.");
        //assertEquals(130, warrior.getHealth().getPool(), "Health pool after leveling up should be 130.");
        //assertEquals(130, warrior.getHealth().getAmount(), "Health amount after leveling up should be be equal to health pool - 130.");
        //assertEquals(22, warrior.getAttack(), "Attack after leveling up should be 22.");
        //assertEquals(9, warrior.getDefense(), "Defense after leveling up should be 9.");
        assertEquals(0, warrior.getRemainingCooldown(), "Remaining cooldown after leveling up should be 0");

    }

    @Test
    void onGameTick() {
    }
}