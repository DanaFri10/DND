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

public class MonsterTest {
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
        p1 = new Position(2,0);
        p2 = new Position(1,2);
        p3 = new Position(0,1);
        tileFactory = new TileFactory(messageCallback,enemyDeathCallback);

        e = tileFactory.produceEnemy('s',p0);
        player = tileFactory.producePlayer(0);
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
        assertTrue(damage<=8 && damage >= 0);
    }
}