package game.utils;

import game.tiles.units.enemies.Enemy;
import game.tiles.units.player.Player;

import java.util.List;

public class AbilityCastInfo {
    private List<Enemy> enemyList;
    private Player player;

    public AbilityCastInfo(List<Enemy> enemyList, Player player) {
        this.enemyList = enemyList;
        this.player = player;
    }

    public List<Enemy> getEnemyList() {
        return this.enemyList;
    }

    public Player getPlayer() {
        return this.player;
    }
}
