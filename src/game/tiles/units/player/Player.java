package game.tiles.units.player;

import game.callbacks.AbilityCastCallback;
import game.callbacks.MoveUnitCallback;
import game.tiles.units.HeroicUnit;
import game.tiles.units.Unit;
import game.tiles.units.enemies.Enemy;
import game.utils.Position;

public abstract class Player extends Unit implements HeroicUnit {
    protected int experience;
    protected int playerLevel;
    protected String specialAbility;
    protected int healthBoost, attackBoost, defenseBoost;

    public Player(String name, int health, int attack, int defense, String specialAbility) {
        super('@', name, health, attack, defense);
        this.experience = 0;
        this.playerLevel = 1;
        this.specialAbility = specialAbility;
    }

    public abstract void levelUp();

    protected void levelUp(int healthBonus, int attackBonus, int defenseBonus)
    {
        experience -= 50*playerLevel;
        playerLevel++;
        healthBoost = 10*playerLevel + healthBonus;
        attackBoost = 4*playerLevel + attackBonus;
        defenseBoost = playerLevel + defenseBonus;
        health.addPool(healthBoost);
        health.fill();
        attackPoints += attackBoost;
        defensePoints += defenseBoost;
        messageCallback.send(String.format("%s reached level %d: +%d Health, +%d Attack, +%d Defense\n", name, playerLevel, healthBoost, attackBoost, defenseBoost));
    }

    public abstract void abilityCast(AbilityCastCallback callback);

    @Override
    public void accept(Unit unit) {
        unit.visit(this);
    }

    @Override
    public void processStep() {

    }

    public void onGameTick(char move, MoveUnitCallback moveUnitCallback, AbilityCastCallback abilityCastCallback)
    {
        if(move=='e')
            abilityCast(abilityCastCallback);
        else
            moveUnit(move, moveUnitCallback);
    }

    @Override
    public void onDeath() {
        messageCallback.send("Player died.\n");
    }

    @Override
    public void visit(Player p) { }

    @Override
    public void visit(Enemy e) {
        this.battle(e);
        if (!e.isAlive()) {
            Position p = position;
            setPosition(e.getPosition());
            e.setPosition(p);
            onEnemyKill(e);
        }
    }

    protected void abilityAttack(Enemy e, int abilityDamage) {
        int damageDealt = abilityDamage - e.defend();
        if (damageDealt > 0) {
            e.getHealth().removeAmount(damageDealt);
            messageCallback.send(String.format("%s hit %s for %d ability damage.\n", name, e.getName(), damageDealt));
        }
        if (!e.isAlive())
            onEnemyKill(e);
    }

    protected void onEnemyKill(Enemy e) {
        experience += e.getExperienceValue();
        while (experience >= 50*playerLevel)
            this.levelUp();
        messageCallback.send(String.format("%s died. %s gained %d experience points.\n", e.getName(), name, e.getExperienceValue()));
        e.onDeath();
    }

    @Override
    public String describe() {
        return super.describe() + String.format("\t\tLevel: %d\t\tExperience: %d/%d", playerLevel, experience, playerLevel*50);
    }

    public String toString() {
        return isAlive() ? super.toString() : "X";
    }



    public int getPlayerLevel() {
        return playerLevel;
    }

    public int getExperience() {
        return experience;
    }
}
