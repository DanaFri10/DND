package game.callbacks;

import game.tiles.Tile;

public interface MoveUnitCallback {
    public Tile moveTo(char move);
}