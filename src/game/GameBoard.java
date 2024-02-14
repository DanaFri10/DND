package game;

import game.tiles.Empty;
import game.tiles.units.enemies.Enemy;
import game.tiles.Tile;
import game.tiles.units.Unit;
import game.utils.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class GameBoard {
    private List<Tile> tiles;
    private int width;
    private int height;

    public GameBoard(Tile[][] board){
		tiles = new ArrayList<>();
        for(Tile[] line : board) {
            tiles.addAll(Arrays.asList(line));
        }
        height = board.length;
        width = board[0].length;
    }

    public Tile get(int x, int y) {
		for(Tile t : tiles) {
			if (t.getPosition().equals(Position.at(x, y))){
				return t;
			}
		}
		// Throw an exception if no such tile.
        throw new NoSuchElementException("There is no tile in position ("+x+","+y+").\n");
    }

    public void remove(Enemy e) {
        tiles.remove(e);
        Position p = e.getPosition();
        tiles.add(new Empty(p));
    }

    public Tile moveTo(Unit unit, char move) {
        int currX = unit.getPosition().getX();
        int currY = unit.getPosition().getY();
        Tile moveTo = get(currX, currY);
        switch (move) {
            case 'w':
                moveTo = get(currX,currY-1);
                break;
            case 's':
                moveTo = get(currX,currY+1);
                break;
            case 'a':
                moveTo = get(currX-1,currY);
                break;
            case 'd':
                moveTo = get(currX+1,currY);
                break;
            case 'q':
                break;
        }
        return moveTo;
    }

    @Override
    public String toString() {
        tiles = tiles.stream().sorted().collect(Collectors.toList());
        String result = "";
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++)
                result += get(j,i).toString();
            result += "\n";
        }
        return result;
    }
}