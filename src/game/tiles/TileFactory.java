package game.tiles;

import game.callbacks.EnemyDeathCallback;
import game.callbacks.MessageCallback;
import game.tiles.units.enemies.Boss;
import game.tiles.units.enemies.Enemy;
import game.tiles.units.enemies.Monster;
import game.tiles.units.enemies.Trap;
import game.tiles.units.player.*;
import game.utils.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class TileFactory {
    private List<Supplier<Player>> playersList;
    private Map<Character, Supplier<Enemy>> enemiesMap;
    private Player selected;
    private List<Enemy> enemies;
    private MessageCallback messageCallback;
    private EnemyDeathCallback enemyDeathCallback;

    public TileFactory(MessageCallback messageCallback, EnemyDeathCallback enemyDeathCallback) {
        playersList = initPlayers();
        enemiesMap = initEnemies();
        enemies = new ArrayList<Enemy>();
        this.messageCallback = messageCallback;
        this.enemyDeathCallback = enemyDeathCallback;
    }

    private Map<Character, Supplier<Enemy>> initEnemies() {
        List<Supplier<Enemy>> enemies = Arrays.asList(
                () -> new Monster('s', "Lannister Solider", 80, 8, 3,25, 3),
                () -> new Monster('k', "Lannister Knight", 200, 14, 8, 50,   4),
                () -> new Monster('q', "Queen's Guard", 400, 20, 15, 100,  5),
                () -> new Monster('z', "Wright", 600, 30, 15,100, 3),
                () -> new Monster('b', "Bear-Wright", 1000, 75, 30, 250,  4),
                () -> new Monster('g', "Giant-Wright",1500, 100, 40,500,   5),
                () -> new Monster('w', "White Walker", 2000, 150, 50, 1000, 6),
                () -> new Boss('M', "The Mountain", 1000, 60, 25,  500, 6, 5),
                () -> new Boss('C', "Queen Cersei", 100, 10, 10,1000, 1, 8),
				() -> new Boss('K', "Night's King", 5000, 300, 150, 5000, 8, 3),
                () -> new Trap('B', "Bonus Trap", 1, 1, 1, 250,  1, 5),
                () -> new Trap('Q', "Queen's Trap", 250, 50, 10, 100, 3, 7),
                () -> new Trap('D', "Death Trap", 500, 100, 20, 250, 1, 10)
        );

        return enemies.stream().collect(Collectors.toMap(s -> s.get().getTile(), Function.identity()));
    }

    private List<Supplier<Player>> initPlayers() {
        return Arrays.asList(
                () -> new Warrior("Jon Snow", 300, 30, 4, 3),
                () -> new Warrior("The Hound", 400, 20, 6, 5),
                () -> new Mage("Melisandre", 100, 5, 1, 300, 30, 15, 5, 6),
                () -> new Mage("Thoros of Myr", 250, 25, 4, 150, 20, 20, 3, 4),
                () -> new Rogue("Arya Stark", 150, 40, 2, 20),
                () -> new Rogue("Bronn", 250, 35, 3, 50),
                () -> new Hunter("Ygritte", 220, 30, 2, 6)
        );
    }

    public List<Player> listPlayers(){
        return playersList.stream().map(Supplier::get).collect(Collectors.toList());
    }

	// TODO: Add additional callbacks of your choice

    public Tile produceTile(char tile, Position position) {
        switch(tile) {
            case '#':
                return produceWall(position);
            case '.':
                return produceEmpty(position);
            case '@':
                selected.initialize(position,messageCallback);
                return selected;
            default:
                return produceEnemy(tile,position);
        }
    }

    public Enemy produceEnemy(char tile, Position position) {
        Enemy enemy = enemiesMap.get(tile).get();
        enemy.initialize(position, messageCallback, enemyDeathCallback);
        enemies.add(enemy);
        return enemy;
    }

    public Player producePlayer(int idx) {
        selected = playersList.get(idx).get();
        return selected;
    }

    public Empty produceEmpty(Position position) {
        return new Empty(position);
    }

    public Wall produceWall(Position position) {
        return new Wall(position);
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}