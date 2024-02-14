package game.tiles;

import game.tiles.units.Unit;
import game.utils.Position;

public class Wall extends Tile {

    public Wall(Position p) {
        super('#');
        initialize(p);
    }

    @Override
    public void accept(Unit unit) {
        unit.visit(this);
    }
}
