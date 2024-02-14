package game.tiles.units;

import game.callbacks.MessageCallback;
import game.callbacks.MoveUnitCallback;
import game.tiles.Empty;
import game.tiles.Tile;
import game.tiles.Wall;
import game.tiles.units.enemies.Enemy;
import game.tiles.units.player.Player;
import game.utils.Position;
import game.utils.Resource;

import java.util.Random;

public abstract class Unit extends Tile {
    protected String name;
    protected Resource health;
    protected int attackPoints;
    protected int defensePoints;
    protected MessageCallback messageCallback;
    protected Random rand = new Random();

    protected Unit(char tile, String name, int healthCapacity, int attackPoints, int defensePoints) {
        super(tile);
        this.name = name;
        this.health = new Resource(healthCapacity);
        this.attackPoints = attackPoints;
        this.defensePoints = defensePoints;
    }

    public void initialize(Position position, MessageCallback messageCallback) {
        super.initialize(position);
        this.messageCallback = messageCallback;
    }


    public String getName() {
        return this.name;
    }

    public Resource getHealth() {
        return this.health;
    }

    public int getAttack() {
        return this.attackPoints;
    }

    public int getDefense() {
        return this.defensePoints;
    }

    protected int attack() {
        return rand.nextInt(attackPoints+1);
    }

    public int defend() {
        return rand.nextInt(defensePoints+1);
    }

	// Should be automatically called once the unit finishes its turn
    public abstract void processStep();
	
	// What happens when the unit dies
    public abstract void onDeath();

	// This unit attempts to interact with another tile.
    public void interact(Tile tile){
		tile.accept(this);
    }

    public void visit(Empty e) {
        Position position = getPosition();
		setPosition(e.getPosition());
        e.setPosition(position);
    }

    public void visit(Wall w) { }

    public abstract void visit(Player p);
    public abstract void visit(Enemy e);

	// Combat against another unit.
    protected void battle(Unit u) {
        messageCallback.send(String.format("%s engaged in combat with %s.\n%s\n%s\n",name, u.name, describe(), u.describe()));
        int attackRoll = attack();
        int defenseRoll = u.defend();
        messageCallback.send(String.format("%s rolled %d attack points.\n%s rolled %d defense points.\n", name, attackRoll, u.name, defenseRoll));
        int damageDealt = attackRoll - defenseRoll;
        if (damageDealt > 0) {
            u.health.removeAmount(damageDealt);
            messageCallback.send(String.format("%s dealt %d damage to %s.\n", name, damageDealt, u.name));
        }
        //battle(u, attackRoll, defenseRoll);
    }

    //protected String battle(game.tiles.units.Unit u, int attackRoll, int defenseRoll)
    //{
    //    String battleInfo = "";
    //    if (attackRoll > defenseRoll) {
    //        u.health -= attackRoll - defenseRoll;
    //        battleInfo = String.format("%s dealt %d damage to %s.\n", name, attackRoll-defenseRoll, u.name);
    //    }
    //    String unitDiedString = "";
    //    if (health <= 0) {
    //        unitDiedString = onDeath();
    //        if (unitDiedString.contains("@")) // ???
    //            unitDiedString = unitDiedString.replace("@", u.name);
    //    }
    //    if (u.health <= 0) {
    //        unitDiedString = u.onDeath();
    //        if (unitDiedString.contains("@"))
    //            unitDiedString = unitDiedString.replace("@", this.name);
    //    }
    //    return battleInfo + unitDiedString;
    //}

    public String describe() {
        return String.format("%s\t\tHealth: %s\t\tAttack: %d\t\tDefense: %d", name, health, attackPoints, defensePoints);
    }

    public void moveUnit(char move, MoveUnitCallback callback)
    {
        interact(callback.moveTo(move));
    }

    public boolean isAlive() {
        return health.getAmount() > 0;
    }
}
