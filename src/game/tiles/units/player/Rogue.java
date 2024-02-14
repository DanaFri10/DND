package game.tiles.units.player;

import game.callbacks.AbilityCastCallback;
import game.callbacks.MoveUnitCallback;

public class Rogue extends Player {
    private int cost;
    private int currentEnergy;

    public Rogue(String name, int health, int attack, int defense, int cost) {
        super(name, health, attack, defense, "Fan of Knives");
        this.cost = cost;
        this.currentEnergy = 100;
    }

    public void levelUp()
    {
        super.levelUp(0,3*playerLevel,0);
        this.currentEnergy=100;
        //this.attack += 3*playerLevel;
    }

    public void abilityCast(AbilityCastCallback callback)
    {
        if(currentEnergy < cost)
            messageCallback.send(String.format("%s tried to cast %s, but there was not enough energy: %d/%d.\n", name,specialAbility,currentEnergy,cost));
        else {
            currentEnergy -= cost;
            String abilityCastInfo = "";
            //List<Enemy> enemies = callback.attackEnemies();
            //for (Enemy e : enemies)
            //    if (this.position.range(e.position) < 2)
            //        abilityCastInfo = this.battle(e, attackPoints, defensePoints);
            messageCallback.send(this.name + " casted ability " + specialAbility + ". \n");// + abilityCastInfo);
            callback.abilityCast().getEnemyList().stream().filter(e->position.range(e.getPosition()) < 2).toList().forEach(e->battle(e));
        }
    }

    public void onGameTick(char move, MoveUnitCallback moveUnitCallback, AbilityCastCallback abilityCastCallback)
    {
        currentEnergy = Math.min(currentEnergy+10, 100);
        super.onGameTick(move, moveUnitCallback, abilityCastCallback);
    }

    @Override
    public String describe() {
        return super.describe() + String.format("\t\tEnergy: %d", currentEnergy);
    }
}
