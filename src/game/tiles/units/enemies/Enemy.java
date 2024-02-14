package game.tiles.units.enemies;

import game.callbacks.EnemyDeathCallback;
import game.callbacks.MessageCallback;
import game.callbacks.MoveUnitCallback;
import game.tiles.units.player.Player;
import game.tiles.units.Unit;
import game.utils.Position;

public abstract class Enemy extends Unit {
    protected int experienceValue;
    protected EnemyDeathCallback deathCallback;
    //protected List<Observer> observerList;

    public Enemy(char tile, String name, int health, int attack, int defense, int experienceValue) {
        super(tile, name, health, attack, defense);
        this.experienceValue = experienceValue;
        //observerList = new ArrayList<>();
    }

    public void initialize(Position position, MessageCallback messageCallback, EnemyDeathCallback deathCallback) {
        super.initialize(position, messageCallback);
        this.deathCallback = deathCallback;
    }

    public int getExperienceValue() {
        return this.experienceValue;
    }

    @Override
    public void accept(Unit unit) {
        unit.visit(this);
    }

    @Override
    public void processStep() {

    }

    public abstract void onGameTick(Player p, MoveUnitCallback callback);

    @Override
    public void onDeath() {
        deathCallback.call(this);
    }

    @Override
    public void visit(Enemy e) { }

    @Override
    public void visit(Player p) {
        this.battle(p);
        if (!p.isAlive())
            p.onDeath();
    }

    //public void addObserver(Observer o) {
    //    observerList.add(o);
    //}
    //
    //public void notifyObservers() {
    //    for (Observer o : observerList)
    //        o.update(this);
    //}
}
