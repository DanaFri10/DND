package game.utils;

public class Position implements Comparable<Position> {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Position at(int x, int y) {
        return new Position(x,y);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int compareTo(Position o) {
        if(this.y < o.y)
            return -1;
        if(this.y > o.y)
            return 1;
        if(this.x < o.x)
            return -1;
        if(this.x > o.x)
            return 1;
        return 0;
    }

    public double range(Position other)
    {
        return Math.sqrt(Math.pow(this.x-other.x,2) + Math.pow(this.y-other.y, 2));
    }

    public boolean equals(Position o) {
        return compareTo(o) == 0;
    }
}