package game.tiles.units.player;

import game.callbacks.AbilityCastCallback;
import game.callbacks.MoveUnitCallback;
import game.tiles.units.enemies.Enemy;

import java.util.List;

public class Warrior extends Player {
    private int abilityCooldown;
    private int remainingCooldown;

    public Warrior(String name, int health, int attack, int defense, int abilityCooldown) {
        super(name, health, attack, defense, "Avenger's Shield");
        this.abilityCooldown = abilityCooldown;
        this.remainingCooldown = 0;
    }

    public void abilityCast(AbilityCastCallback callback)
    {
        if(remainingCooldown > 0)
            messageCallback.send(String.format("%s tried to cast %s, but there is a cooldown: %d.\n", name, specialAbility, remainingCooldown));
        else {
            remainingCooldown = abilityCooldown;
            int previousHealth = health.getAmount();
            health.addAmount(10 * defensePoints);
            messageCallback.send(String.format("%s used %s, healing for %d.\n", name, specialAbility, health.getAmount() - previousHealth));

            // Hits a random enemy withing range < 3
            //List<game.tiles.units.enemies.Enemy> enemies = callback.attackEnemies();
            //List<game.tiles.units.enemies.Enemy> enemiesInRange = new ArrayList<>();
            //for (game.tiles.units.enemies.Enemy e : enemies)
            //    if (this.position.range(e.position) < 3)
            //        enemiesInRange.add(e);
            List<Enemy> enemiesInRange = callback.abilityCast().getEnemyList().stream().filter(e -> position.range(e.getPosition()) < 3).toList();
            //String abilityCastInfo = "";
            if (!enemiesInRange.isEmpty()) {
                //Random rand = new Random();
                Enemy randomEnemy = enemiesInRange.get(rand.nextInt(enemiesInRange.size()));
                //abilityCastInfo = this.battle(randomEnemy, (int) (0.1 * health.getPool()), randomEnemy.defensePoints);
                abilityAttack(randomEnemy,(int) (0.1 * health.getPool())); // notice there is a different message for attacking with ability, so we cannot use the simple battle method from Unit class.
            }
        }
    }

    public void levelUp()
    {
        super.levelUp(5*playerLevel, 2*playerLevel, playerLevel);
        remainingCooldown = 0;
        //healthCapacity = health + 5*playerLevel;
        //attack += 2*playerLevel;
        //defense += playerLevel;
    }

    public void onGameTick(char move, MoveUnitCallback moveUnitCallback, AbilityCastCallback abilityCastCallback)
    {
        remainingCooldown = Math.max(0, remainingCooldown-1);
        super.onGameTick(move, moveUnitCallback, abilityCastCallback);
    }

    @Override
    public String describe() {
        return super.describe() + String.format("\t\tCooldown: %d/%d", remainingCooldown, abilityCooldown);
    }

    public int getRemainingCooldown() {
        return remainingCooldown;
    }
}
