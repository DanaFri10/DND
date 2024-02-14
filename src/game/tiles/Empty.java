package game.tiles;

import game.tiles.units.Unit;
import game.utils.Position;

public class Empty extends Tile {
    public Empty(Position p) {
        super('.');
        initialize(p);
    }

    @Override
    public void accept(Unit unit) {
        unit.visit(this);
    }
}
