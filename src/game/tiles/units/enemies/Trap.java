package game.tiles.units.enemies;

import game.callbacks.MoveUnitCallback;
import game.tiles.units.player.Player;

public class Trap extends Enemy {
    private int visibilityTime;
    private int invisibilityTime;
    private int ticksCount;
    private boolean visible;

    public Trap(char tile, String name, int health, int attack, int defense, int experienceValue, int visibilityTime, int invisibilityTime) {
        super(tile, name, health, attack, defense, experienceValue);
        this.visibilityTime = visibilityTime;
        this.invisibilityTime = invisibilityTime;
        this.ticksCount = 0;
        this.visible = true;
    }

    @Override
    public void visit(Player p) { }

    @Override
    public void visit(Enemy e) { }

    @Override
    public void onGameTick(Player p, MoveUnitCallback callback) {
        visible = ticksCount < visibilityTime;
        if (ticksCount == (visibilityTime + invisibilityTime))
            ticksCount = 0;
        else
            ticksCount++;
        if (this.position.range(p.getPosition()) < 2)
            this.interact(p);
    }

    @Override
    public String toString() {
        return visible ? super.toString() : ".";
    }

    public boolean isVisible() {
        return visible;
    }

    public int getTicksCount() {
        return ticksCount;
    }
}
