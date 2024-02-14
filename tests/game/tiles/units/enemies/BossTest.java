package game.tiles.units.enemies;

import game.callbacks.EnemyDeathCallback;
import game.callbacks.MessageCallback;
import game.tiles.Empty;
import game.tiles.TileFactory;
import game.tiles.units.player.Player;
import game.utils.Position;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BossTest {
    private TileFactory tileFactory;
    private Player player;
    private Enemy e;
    private Position p0, p1, p2, p3;
    private MessageCallback messageCallback;
    private EnemyDeathCallback enemyDeathCallback;


    @Before
    public void setUp() {
        messageCallback = (s)->{};
        enemyDeathCallback = (e)->{};
        p0 = new Position(0,0);
        p1 = new Position(5,0);
        p2 = new Position(1,2);
        p3 = new Position(0,1);
        tileFactory = new TileFactory(messageCallback,enemyDeathCallback);

        e = tileFactory.produceEnemy('K',p0);
        player = tileFactory.producePlayer(1);
    }

    @Test
    public void onGameTick_movement_right() {
        player.initialize(p1,messageCallback);
        Position previousPosition = Position.at(p0.getX(),p0.getY());
        Position pos = Position.at(p0.getX()+1,p0.getY());
        Empty empty = new Empty(pos);
        e.onGameTick(player,(c)->{assertEquals('d',c); return empty;});
        assertEquals(pos,e.getPosition());
        assertEquals(empty.getPosition(),previousPosition);
    }

    @Test
    public void onGameTick_movement_down() {
        player.initialize(p2,messageCallback);
        Position previousPosition = Position.at(p0.getX(),p0.getY());
        Position pos = Position.at(p0.getX(),p0.getY()-1);
        Empty empty = new Empty(pos);
        e.onGameTick(player,(c)->{ assertEquals('s',c); return empty;});
        assertEquals(pos,e.getPosition());
        assertEquals(empty.getPosition(),previousPosition);
    }

    @Test
    public void onGameTick_combat() {
        player.initialize(p3,messageCallback);
        Position position = Position.at(p0.getX(),p0.getY());
        e.onGameTick(player,(c)->{assertEquals('s',c); return player;});
        // Check that the monster did not move, only initiated combat with player
        assertEquals(position,e.getPosition());

        // Check if the damage done by the monster is valid
        int damage = player.getHealth().getPool()-player.getHealth().getAmount();
        System.out.println("Player Health: " + player.getHealth());
        System.out.println();
        assertTrue(damage<=300 && damage >= 0);
    }

    @Test
    public void onGameTick_AbilityCast() {
        // Check the boss only casts the special ability after exactly 3 ticks (ability frequency)
        player.initialize(p1,messageCallback);
        Position pos = Position.at(p0.getX()+1,p0.getY());
        Empty empty1 = new Empty(pos);
        e.onGameTick(player,(c)->{assertEquals('d',c); return empty1;});
        int damage = player.getHealth().getPool()-player.getHealth().getAmount();
        assertEquals(0,damage);

        pos = Position.at(p0.getX()+2,p0.getY());
        Empty empty2 = new Empty(pos);
        e.onGameTick(player,(c)->{assertEquals('d',c); return empty2;});
        damage = player.getHealth().getPool()-player.getHealth().getAmount();
        assertEquals(0,damage);

        pos = Position.at(p0.getX()+3,p0.getY());
        Empty empty3 = new Empty(pos);
        e.onGameTick(player,(c)->{assertEquals('d',c); return empty3;});
        damage = player.getHealth().getPool()-player.getHealth().getAmount();
        assertEquals(0,damage);

        // Check if the boss casts the special ability after 3 ticks, and if the damage done is valid.
        e.onGameTick(player,(c)->{assertEquals('q',c); return e;});
        damage = player.getHealth().getPool()-player.getHealth().getAmount();
        assertTrue(damage<=300 && damage>=294);
    }
}