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

public class MageTest {
    private TileFactory tileFactory;
    private Player m;
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
        p2 = new Position(2,1);
        p3 = new Position(1,2);
        p4 = new Position(2,3);
        tileFactory = new TileFactory(messageCallback,enemyDeathCallback);
        enemies = new ArrayList<>();

        m = tileFactory.producePlayer(2);
        m.initialize(p1,messageCallback);
        e1 = tileFactory.produceEnemy('s', p2);
        e2 = tileFactory.produceEnemy('k', p3);
        e3 = tileFactory.produceEnemy('k', p4);
        enemies.add(e1);
        enemies.add(e2);

        abilityCastCallback = () -> new AbilityCastInfo(enemies,null);
    }

    @Test
    public void abilityCast() {
        m.abilityCast(abilityCastCallback);
        assertTrue(abilityCastCheck());
    }

    private boolean abilityCastCheck() {
        int e1_damage = e1.getHealth().getPool()-e1.getHealth().getAmount();
        int e2_damage = e2.getHealth().getPool()-e2.getHealth().getAmount();
        boolean e1_hurt = e1_damage > 0,
                e2_hurt = e2_damage > 0;
        boolean e1_valid_damage = e1_damage <= 75 && e1_damage >= 12,
                e2_valid_damage = e2_damage <= 75 && e2_damage >= 7;

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
        int previousHealth = m.getHealth().getPool();
        int previousAttack = m.getAttack();
        int previousDefense = m.getDefense();
        int healthBonus = 20;
        int attackBonus = 8;
        int defenseBonus = 2;
        m.levelUp();

        assertEquals(2, m.getPlayerLevel());
        assertTrue(m.getHealth().getAmount() == m.getHealth().getPool() && m.getHealth().getAmount() == previousHealth + healthBonus);
        assertTrue(m.getAttack() == previousAttack + attackBonus);
        assertTrue(m.getDefense() == previousDefense + defenseBonus);

        previousHealth = m.getHealth().getPool();
        previousAttack = m.getAttack();
        previousDefense = m.getDefense();
        healthBonus = 30;
        attackBonus = 12;
        defenseBonus = 3;
        m.levelUp();

        assertEquals(3, m.getPlayerLevel());
        assertTrue(m.getHealth().getAmount() == m.getHealth().getPool() && m.getHealth().getAmount() == previousHealth + healthBonus);
        assertTrue(m.getAttack() == previousAttack + attackBonus);
        assertTrue(m.getDefense() == previousDefense + defenseBonus);
    }

    @Test
    public void onGameTick() {
        // Movement test:
        Position previousPosition = Position.at(p1.getX(),p1.getY());
        Position p = Position.at(p1.getX()+1,p1.getY());
        Empty e = new Empty(p);
        m.onGameTick('d',(c)->e,abilityCastCallback);
        assertEquals(p, m.getPosition());
        assertEquals(e.getPosition(),previousPosition);

        // Ability test:
        m.onGameTick('e',moveUnitCallback,abilityCastCallback);
        assertTrue(abilityCastCheck());
    }
}