package game.tiles.units.enemies;

import game.callbacks.AbilityCastCallback;
import game.tiles.units.HeroicUnit;
import game.tiles.units.player.Player;
import game.utils.AbilityCastInfo;
import game.callbacks.MoveUnitCallback;

public class Boss extends Monster implements HeroicUnit {
    private int abilityFrequency;
    private int combatTicks;

    public Boss(char tile, String name, int health, int attack, int defense, int experienceValue, int visionRange, int abilityFrequency) {
        super(tile,name,health,attack,defense,visionRange,experienceValue);
        this.abilityFrequency = abilityFrequency;
        this.combatTicks = 0;
    }

    public void onGameTick(Player p, MoveUnitCallback callback) {
        moveUnit(moveMonsterTo(p), callback);
    }

    protected char moveMonsterTo(Player player) {
        if (position.range(player.getPosition()) < visionRange) {
            if (combatTicks == abilityFrequency) {
                combatTicks = 0;
                abilityCast(()-> new AbilityCastInfo(null, player));
            }
            else {
                combatTicks++;
                return super.moveMonsterTo(player);
            }
        }
        combatTicks = 0;
        return 'q';
    }

    @Override
    public void abilityCast(AbilityCastCallback callback) {
        Player player = callback.abilityCast().getPlayer();
        if (position.range(player.getPosition()) < visionRange) {
            messageCallback.send(String.format("%s has casted a special ability on %s.\n", name, player.getName()));
            abilityAttack(player, attackPoints);
        }
    }

    protected void abilityAttack(Player player, int abilityDamage) {
        int damageDealt = abilityDamage - player.defend();
        if (damageDealt > 0) {
            player.getHealth().removeAmount(damageDealt);
            messageCallback.send(String.format("%s hit %s for %d ability damage.\n", name, player.getName(), damageDealt));
        }
        if (!player.isAlive())
            player.onDeath();
    }
}
