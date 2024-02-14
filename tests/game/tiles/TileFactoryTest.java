package game.tiles;

import game.tiles.units.enemies.Enemy;
import game.tiles.units.player.Player;
import game.utils.Position;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TileFactoryTest {
    private TileFactory tileFactory;
    private Position p1,p2;

    @Before
    public void setUp() {
        tileFactory = new TileFactory(null, null);
        p1 = new Position(0,0);
        p2 = new Position(3,2);
    }

    @Test
    public void produceTile() {
        Tile t1 = tileFactory.produceTile('s', p1);
        assertEquals(p1,t1.getPosition());
        assertEquals('s',t1.getTile());
        Tile t2 = tileFactory.produceTile('M', p2);
        assertEquals(p2,t2.getPosition());
        assertEquals('M',t2.getTile());
    }

    @Test
    public void produceEnemy_Lannister_Soldier() {
        Enemy e = tileFactory.produceEnemy('s',p1);
        assertEquals('s',e.getTile());
        assertEquals("Lannister Solider",e.getName());
        assertEquals(80,e.getHealth().getPool());
        assertEquals(e.getHealth().getPool(),e.getHealth().getAmount());
        assertEquals(8,e.getAttack());
        assertEquals(3,e.getDefense());
        assertEquals(25,e.getExperienceValue());
    }

    @Test
    public void produceEnemy_Lannister_Knight() {
        Enemy e = tileFactory.produceEnemy('k',p1);
        assertEquals('k',e.getTile());
        assertEquals("Lannister Knight",e.getName());
        assertEquals(200,e.getHealth().getPool());
        assertEquals(e.getHealth().getPool(),e.getHealth().getAmount());
        assertEquals(14,e.getAttack());
        assertEquals(8,e.getDefense());
        assertEquals(50,e.getExperienceValue());
    }

    @Test
    public void produceEnemy_Queens_Guard() {
        Enemy e = tileFactory.produceEnemy('q',p1);
        assertEquals('q',e.getTile());
        assertEquals("Queen's Guard",e.getName());
        assertEquals(400,e.getHealth().getPool());
        assertEquals(e.getHealth().getPool(),e.getHealth().getAmount());
        assertEquals(20,e.getAttack());
        assertEquals(15,e.getDefense());
        assertEquals(100,e.getExperienceValue());
    }

    @Test
    public void produceEnemy_Wright() {
        Enemy e = tileFactory.produceEnemy('z',p1);
        assertEquals('z',e.getTile());
        assertEquals("Wright",e.getName());
        assertEquals(600,e.getHealth().getPool());
        assertEquals(e.getHealth().getPool(),e.getHealth().getAmount());
        assertEquals(30,e.getAttack());
        assertEquals(15,e.getDefense());
        assertEquals(100,e.getExperienceValue());
    }

    @Test
    public void produceEnemy_Bear_Wright() {
        Enemy e = tileFactory.produceEnemy('b',p1);
        assertEquals('b',e.getTile());
        assertEquals("Bear-Wright",e.getName());
        assertEquals(1000,e.getHealth().getPool());
        assertEquals(e.getHealth().getPool(),e.getHealth().getAmount());
        assertEquals(75,e.getAttack());
        assertEquals(30,e.getDefense());
        assertEquals(250,e.getExperienceValue());
    }

    @Test
    public void produceEnemy_Giant_Wright() {
        Enemy e = tileFactory.produceEnemy('g',p1);
        assertEquals('g',e.getTile());
        assertEquals("Giant-Wright",e.getName());
        assertEquals(1500,e.getHealth().getPool());
        assertEquals(e.getHealth().getPool(),e.getHealth().getAmount());
        assertEquals(100,e.getAttack());
        assertEquals(40,e.getDefense());
        assertEquals(500,e.getExperienceValue());
    }

    @Test
    public void produceEnemy_White_Walker() {
        Enemy e = tileFactory.produceEnemy('w',p1);
        assertEquals('w',e.getTile());
        assertEquals("White Walker",e.getName());
        assertEquals(2000,e.getHealth().getPool());
        assertEquals(e.getHealth().getPool(),e.getHealth().getAmount());
        assertEquals(150,e.getAttack());
        assertEquals(50,e.getDefense());
        assertEquals(1000,e.getExperienceValue());
    }

    @Test
    public void produceEnemy_The_Mountain() {
        Enemy e = tileFactory.produceEnemy('M',p1);
        assertEquals('M',e.getTile());
        assertEquals("The Mountain",e.getName());
        assertEquals(1000,e.getHealth().getPool());
        assertEquals(e.getHealth().getPool(),e.getHealth().getAmount());
        assertEquals(60,e.getAttack());
        assertEquals(25,e.getDefense());
        assertEquals(500,e.getExperienceValue());
    }

    @Test
    public void produceEnemy_Queen_Cersei() {
        Enemy e = tileFactory.produceEnemy('C',p1);
        assertEquals('C',e.getTile());
        assertEquals("Queen Cersei",e.getName());
        assertEquals(100,e.getHealth().getPool());
        assertEquals(e.getHealth().getPool(),e.getHealth().getAmount());
        assertEquals(10,e.getAttack());
        assertEquals(10,e.getDefense());
        assertEquals(1000,e.getExperienceValue());
    }

    @Test
    public void produceEnemy_Nights_King() {
        Enemy e = tileFactory.produceEnemy('K',p1);
        assertEquals('K',e.getTile());
        assertEquals("Night's King",e.getName());
        assertEquals(5000,e.getHealth().getPool());
        assertEquals(e.getHealth().getPool(),e.getHealth().getAmount());
        assertEquals(300,e.getAttack());
        assertEquals(150,e.getDefense());
        assertEquals(5000,e.getExperienceValue());
    }

    @Test
    public void produceEnemy_Bonus_Trap() {
        Enemy e = tileFactory.produceEnemy('B',p1);
        assertEquals('B',e.getTile());
        assertEquals("Bonus Trap",e.getName());
        assertEquals(1,e.getHealth().getPool());
        assertEquals(e.getHealth().getPool(),e.getHealth().getAmount());
        assertEquals(1,e.getAttack());
        assertEquals(1,e.getDefense());
        assertEquals(250,e.getExperienceValue());
    }

    @Test
    public void produceEnemy_Queens_Trap() {
        Enemy e = tileFactory.produceEnemy('Q',p1);
        assertEquals('Q',e.getTile());
        assertEquals("Queen's Trap",e.getName());
        assertEquals(250,e.getHealth().getPool());
        assertEquals(e.getHealth().getPool(),e.getHealth().getAmount());
        assertEquals(50,e.getAttack());
        assertEquals(10,e.getDefense());
        assertEquals(100,e.getExperienceValue());
    }

    @Test
    public void produceEnemy_Death_Trap() {
        Enemy e = tileFactory.produceEnemy('D',p1);
        assertEquals('D',e.getTile());
        assertEquals("Death Trap",e.getName());
        assertEquals(500,e.getHealth().getPool());
        assertEquals(e.getHealth().getPool(),e.getHealth().getAmount());
        assertEquals(100,e.getAttack());
        assertEquals(20,e.getDefense());
        assertEquals(250,e.getExperienceValue());
    }

    @Test
    public void producePlayer_Jon_Snow() {
        Player p = tileFactory.producePlayer(0);
        assertEquals('@',p.getTile());
        assertEquals("Jon Snow",p.getName());
        assertEquals(1,p.getPlayerLevel());
        assertEquals(300,p.getHealth().getPool());
        assertEquals(p.getHealth().getPool(),p.getHealth().getAmount());
        assertEquals(30,p.getAttack());
        assertEquals(4,p.getDefense());
        assertEquals("Avenger's Shield",p.getSpecialAbility());
    }

    @Test
    public void producePlayer_The_Hound() {
        Player p = tileFactory.producePlayer(1);
        assertEquals('@',p.getTile());
        assertEquals("The Hound",p.getName());
        assertEquals(1,p.getPlayerLevel());
        assertEquals(400,p.getHealth().getPool());
        assertEquals(p.getHealth().getPool(),p.getHealth().getAmount());
        assertEquals(20,p.getAttack());
        assertEquals(6,p.getDefense());
        assertEquals("Avenger's Shield",p.getSpecialAbility());
    }

    @Test
    public void producePlayer_Melisandre() {
        Player p = tileFactory.producePlayer(2);
        assertEquals('@',p.getTile());
        assertEquals("Melisandre",p.getName());
        assertEquals(1,p.getPlayerLevel());
        assertEquals(100,p.getHealth().getPool());
        assertEquals(p.getHealth().getPool(),p.getHealth().getAmount());
        assertEquals(5,p.getAttack());
        assertEquals(1,p.getDefense());
        assertEquals("Blizzard",p.getSpecialAbility());
    }

    @Test
    public void producePlayer_Thoros_of_Myr() {
        Player p = tileFactory.producePlayer(3);
        assertEquals('@',p.getTile());
        assertEquals("Thoros of Myr",p.getName());
        assertEquals(1,p.getPlayerLevel());
        assertEquals(250,p.getHealth().getPool());
        assertEquals(p.getHealth().getPool(),p.getHealth().getAmount());
        assertEquals(25,p.getAttack());
        assertEquals(4,p.getDefense());
        assertEquals("Blizzard",p.getSpecialAbility());
    }

    @Test
    public void producePlayer_Arya_Stark() {
        Player p = tileFactory.producePlayer(4);
        assertEquals('@',p.getTile());
        assertEquals("Arya Stark",p.getName());
        assertEquals(1,p.getPlayerLevel());
        assertEquals(150,p.getHealth().getPool());
        assertEquals(p.getHealth().getPool(),p.getHealth().getAmount());
        assertEquals(40,p.getAttack());
        assertEquals(2,p.getDefense());
        assertEquals("Fan of Knives",p.getSpecialAbility());
    }

    @Test
    public void producePlayer_Bronn() {
        Player p = tileFactory.producePlayer(5);
        assertEquals('@',p.getTile());
        assertEquals("Bronn",p.getName());
        assertEquals(1,p.getPlayerLevel());
        assertEquals(250,p.getHealth().getPool());
        assertEquals(p.getHealth().getPool(),p.getHealth().getAmount());
        assertEquals(35,p.getAttack());
        assertEquals(3,p.getDefense());
        assertEquals("Fan of Knives",p.getSpecialAbility());
    }

    @Test
    public void producePlayer_Ygritte() {
        Player p = tileFactory.producePlayer(6);
        assertEquals('@',p.getTile());
        assertEquals("Ygritte",p.getName());
        assertEquals(1,p.getPlayerLevel());
        assertEquals(220,p.getHealth().getPool());
        assertEquals(p.getHealth().getPool(),p.getHealth().getAmount());
        assertEquals(30,p.getAttack());
        assertEquals(2,p.getDefense());
        assertEquals("Shoot",p.getSpecialAbility());
    }

    @Test
    public void produceEmpty() {
        Empty e1 = tileFactory.produceEmpty(p1);
        assertEquals('.',e1.getTile());
        assertEquals(p1,e1.getPosition());
        Empty e2 = tileFactory.produceEmpty(p2);
        assertEquals('.',e2.getTile());
        assertEquals(p2,e2.getPosition());
    }

    @Test
    public void produceWall() {
        Wall w1 = tileFactory.produceWall(p1);
        assertEquals('#',w1.getTile());
        assertEquals(p1,w1.getPosition());
        Wall w2 = tileFactory.produceWall(p2);
        assertEquals('#',w2.getTile());
        assertEquals(p2,w2.getPosition());
    }
}