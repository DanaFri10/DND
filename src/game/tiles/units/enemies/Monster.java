package game.tiles.units.enemies;

import game.callbacks.MoveUnitCallback;
import game.tiles.units.player.Player;

public class Monster extends Enemy {
    protected int visionRange;

    public Monster(char tile, String name, int health, int attack, int defense, int experienceValue, int visionRange) {
        super(tile,name,health,attack,defense,experienceValue);
        this.visionRange = visionRange;
    }

    public void onGameTick(Player p, MoveUnitCallback callback) {
        moveUnit(moveMonsterTo(p), callback);
    }

    protected char moveMonsterTo(Player player) {
        if (position.range(player.getPosition()) < visionRange) {
            int dx = position.getX() - player.getPosition().getX();
            int dy = position.getY() - player.getPosition().getY();
            if (Math.abs(dx) > Math.abs(dy)) {
                if (dx > 0)
                    return 'a';
                else
                    return 'd';
            }
            else {
                if (dy > 0)
                    return 'w';
                else
                    return 's';
            }
        }
        // Random Movement Left/Right/Up/Down/Stay.
        char[] moves = {'d','a','w','s','q'};
        return moves[rand.nextInt(moves.length)];
    }
}
