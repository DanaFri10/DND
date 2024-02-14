package game.tiles.units.player;

import game.callbacks.AbilityCastCallback;
import game.callbacks.MoveUnitCallback;
import game.tiles.units.enemies.Enemy;

import java.util.List;
import java.util.stream.Collectors;

public class Hunter extends Player {
    private int range;
    private int arrowsCount;
    private int ticksCount;

    public Hunter(String name, int health, int attack, int defense, int range) {
        super(name,health,attack,defense, "Shoot");
        this.range = range;
        this.arrowsCount = 10*playerLevel;
        this.ticksCount = 0;
    }

    @Override
    public void abilityCast(AbilityCastCallback callback) {
        if (arrowsCount == 0)
            messageCallback.send(name + "tried to shoot an arrow but there were no arrows left.\n");
        else {
            //List<game.tiles.units.enemies.Enemy> enemies = callback.attackEnemies();
            //List<game.tiles.units.enemies.Enemy> enemiesInRange = new ArrayList<>();
            //for (game.tiles.units.enemies.Enemy e : enemies)
            //    if (this.position.range(e.position) < range)
            //        enemiesInRange.add(e);
            //if (enemiesInRange.isEmpty())
            //    throw new RuntimeException(name + "tried to shoot an arrow but there were no enemies in range.\n");
//
            //arrowsCount--;
            //// Hits the closest enemy
            //enemiesInRange.sort((game.tiles.units.enemies.Enemy e1, game.tiles.units.enemies.Enemy e2) -> {
            //    return compare(e1, e2);
            //});
            //String abilityCastInfo = "";
            //game.tiles.units.enemies.Enemy closestEnemy = enemiesInRange.get(0);
            //abilityCastInfo += this.battle(closestEnemy, attackPoints, closestEnemy.defensePoints);


            List<Enemy> enemiesInRange = callback.abilityCast().getEnemyList().stream().filter(e->position.range(e.getPosition()) < range).collect(Collectors.toList());
            if (enemiesInRange.isEmpty())
                messageCallback.send(name + "tried to shoot an arrow but there were no enemies in range.\n");
            else {
                arrowsCount--;
                enemiesInRange.sort((e1,e2) -> compare(e1,e2));
                Enemy closestEnemy = enemiesInRange.get(0);
                messageCallback.send(String.format("%s fired an arrow at %s.\n", name, closestEnemy.getName()));
                abilityAttack(closestEnemy,attackPoints);
            }
        }
    }

    public int compare(Enemy e1, Enemy e2)
    {
        if(position.range(e1.getPosition()) > position.range(e2.getPosition())) return 1;
        if(position.range(e1.getPosition()) < position.range(e2.getPosition())) return -1;
        return 0;
    }

    @Override
    public void levelUp() {
        super.levelUp(0, 2*playerLevel, playerLevel);
        arrowsCount += 10 * playerLevel;
        //attack += 2 * playerLevel;
        //defense += playerLevel;
    }

    public void onGameTick(char move, MoveUnitCallback moveUnitCallback, AbilityCastCallback abilityCastCallback) {
        if (ticksCount == 10) {
            arrowsCount += playerLevel;
            ticksCount = 0;
        }
        else
            ticksCount++;
        super.onGameTick(move, moveUnitCallback, abilityCastCallback);
    }

    @Override
    public String describe() {
        return super.describe() + String.format("\t\tArrows: %d\t\tRange: %d", arrowsCount, range);
    }
}
