package game.tiles.units.player;

import game.callbacks.AbilityCastCallback;
import game.callbacks.EnemyDeathCallback;
import game.callbacks.MessageCallback;
import game.callbacks.MoveUnitCallback;
import game.tiles.Empty;
import game.tiles.TileFactory;
import game.tiles.units.enemies.Enemy;
import game.utils.AbilityCastInfo;
import game.utils.Position;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RogueTest {
    private TileFactory tileFactory;
    private Player r;
    private Enemy e1,e2,e3;
    private Position p1, p2, p3, p4;
    private MessageCallback messageCallback;
    private EnemyDeathCallback enemyDeathCallback;
    private AbilityCastCallback abilityCastCallback;
    private MoveUnitCallback moveUnitCallback;
    private List<Enemy> enemies;


    @Before
    public void setUp() {
        messageCallback = (s)->{};
        enemyDeathCallback = (e)->{};
        moveUnitCallback = (c)->null;
        p1 = new Position(0,0);
        p2 = new Position(1,0);
        p3 = new Position(1,1);
        p4 = new Position(2,3);
        tileFactory = new TileFactory(messageCallback,enemyDeathCallback);
        enemies = new ArrayList<>();

        r = tileFactory.producePlayer(4);
        r.initialize(p1,messageCallback);
        e1 = tileFactory.produceEnemy('s', p2);
        e2 = tileFactory.produceEnemy('k', p3);
        e3 = tileFactory.produceEnemy('k', p4);
        enemies.add(e1);
        enemies.add(e2);

        abilityCastCallback = () -> new AbilityCastInfo(enemies,null);
    }

    @Test
    public void abilityCast() {
        r.abilityCast(abilityCastCallback);
        assertTrue(abilityCastCheck());
    }

    private boolean abilityCastCheck() {
        int e1_damage = e1.getHealth().getPool()-e1.getHealth().getAmount();
        int e2_damage = e2.getHealth().getPool()-e2.getHealth().getAmount();
        boolean e1_hurt = e1_damage > 0,
                e2_hurt = e2_damage > 0;
        boolean e1_valid_damage = e1_damage <= 40 && e1_damage >= 37,
                e2_valid_damage = e2_damage <= 40 && e2_damage >= 32;

        System.out.println("Enemy 1 Health: " + e1.getHealth());
        System.out.println("Enemy 2 Health: " + e2.getHealth());
        System.out.println("Enemy 3 Health: " + e3.getHealth());
        System.out.println();

        // validHit is true if and only if the enemies got hit with a valid damage
        boolean validHit = (e1_hurt || e2_hurt)
                && ((e1_hurt && e1_valid_damage) || (e2_hurt && e2_valid_damage));
        // validRangeLimit is true iff the 3rd enemy, which is out of range, is not hurt
        boolean validRangeLimit = e3.getHealth().getAmount() == e3.getHealth().getPool();
        return validHit && validRangeLimit;
    }

    @Test
    public void levelUp() {
        int previousHealth = r.getHealth().getPool();
        int previousAttack = r.getAttack();
        int previousDefense = r.getDefense();
        int healthBonus = 20;
        int attackBonus = 14;
        int defenseBonus = 2;
        r.levelUp();

        assertEquals(2, r.getPlayerLevel());
        assertTrue(r.getHealth().getAmount() == r.getHealth().getPool() && r.getHealth().getAmount() == previousHealth + healthBonus);
        assertTrue(r.getAttack() == previousAttack + attackBonus);
        assertTrue(r.getDefense() == previousDefense + defenseBonus);

        previousHealth = r.getHealth().getPool();
        previousAttack = r.getAttack();
        previousDefense = r.getDefense();
        healthBonus = 30;
        attackBonus = 21;
        defenseBonus = 3;
        r.levelUp();

        assertEquals(3, r.getPlayerLevel());
        assertTrue(r.getHealth().getAmount() == r.getHealth().getPool() && r.getHealth().getAmount() == previousHealth + healthBonus);
        assertTrue(r.getAttack() == previousAttack + attackBonus);
        assertTrue(r.getDefense() == previousDefense + defenseBonus);
    }

    @Test
    public void onGameTick() {
        // Movement test:
        Position previousPosition = Position.at(p1.getX(),p1.getY());
        Position p = Position.at(p1.getX()+1,p1.getY());
        Empty e = new Empty(p);
        r.onGameTick('d',(c)->e,abilityCastCallback);
        assertEquals(p, r.getPosition());
        assertEquals(e.getPosition(),previousPosition);

        // Ability test:
        r.onGameTick('e',moveUnitCallback,abilityCastCallback);
        assertTrue(abilityCastCheck());
    }
}