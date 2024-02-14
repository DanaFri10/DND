package game.tiles.units.player;

import game.callbacks.AbilityCastCallback;
import game.callbacks.MoveUnitCallback;
import game.tiles.units.enemies.Enemy;
import game.utils.Resource;

import java.util.List;
import java.util.stream.Collectors;

public class Mage extends Player {
    private Resource mana;
    private int manaCost;
    private int spellPower;
    private int hitsCount;
    private int abilityRange;

    public Mage(String name, int health, int attack, int defense, int manaPool, int manaCost, int spellPower, int hitsCount, int abilityRange) {
        super(name,health,attack,defense, "Blizzard");
        this.mana = new Resource(manaPool/4, manaPool);
        this.manaCost = manaCost;
        this.spellPower = spellPower;
        this.hitsCount = hitsCount;
        this.abilityRange = abilityRange;
    }

    public void levelUp()
    {
        super.levelUp(0,0,0);
        int manaBoost = 25*playerLevel;
        int spellPowerBoost = 10*playerLevel;
        mana.addPool(manaBoost);
        mana.addAmount(mana.getPool()/4);
        spellPower += spellPowerBoost;
        messageCallback.send(String.format("\b,+%d maximum mana, +%d spell power\n",manaBoost,spellPowerBoost));
    }

    public void abilityCast(AbilityCastCallback callback)
    {
        if(mana.getAmount() < manaCost)
            messageCallback.send(String.format("%s tried to cast %s, but there was not enough mana: %d/%d.\n",name,specialAbility,mana.getAmount(),manaCost));
        else {
            mana.removeAmount(manaCost);
            messageCallback.send(String.format("%s cast %s.\n", name, specialAbility));
            int hits = 0;
            //List<game.tiles.units.enemies.Enemy> enemies = callback.attackEnemies();
            //List<game.tiles.units.enemies.Enemy> enemiesInRange = new ArrayList<>();
            //for (game.tiles.units.enemies.Enemy e : enemies)
            //    if (this.position.range(e.position) < abilityRange)
            //        enemiesInRange.add(e);
            //String abilityCastInfo = "";
            //while (!enemiesInRange.isEmpty() && hits < hitsCount) {
            //    Random rand = new Random();
            //    game.tiles.units.enemies.Enemy randomEnemy = enemiesInRange.get(rand.nextInt(enemiesInRange.size()));
            //    abilityCastInfo = this.battle(randomEnemy, spellPower, randomEnemy.defensePoints);  // only 1 info?
            //    hits++;
            //}

            List<Enemy> enemiesInRange = callback.abilityCast().getEnemyList().stream().filter(e -> position.range(e.getPosition()) < abilityRange).collect(Collectors.toList());
            while (!enemiesInRange.isEmpty() && hits < hitsCount) {
                Enemy randomEnemy = enemiesInRange.get(rand.nextInt(enemiesInRange.size()));
                abilityAttack(randomEnemy, spellPower); // notice there is a different message for attacking with ability, so we cannot use the simple battle method from Unit class.
                hits++;
            }
        }
    }

    public void onGameTick(char move, MoveUnitCallback moveUnitCallback, AbilityCastCallback abilityCastCallback)
    {
        mana.addAmount(playerLevel);
        super.onGameTick(move, moveUnitCallback, abilityCastCallback);
    }

    @Override
    public String describe() {
        return super.describe() + String.format("\t\tMana: %s\t\tSpell Power: %d",mana,spellPower);
    }
}
