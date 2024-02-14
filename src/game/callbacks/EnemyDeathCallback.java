package game.callbacks;

import game.tiles.units.enemies.Enemy;

public interface EnemyDeathCallback{
	void call(Enemy e);
}