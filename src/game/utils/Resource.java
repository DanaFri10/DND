package game.utils;

public class Resource {
    private int amount;
    private int pool;

    public Resource(int amount, int pool) {
        this.amount = amount;
        this.pool = pool;
    }

    public Resource(int pool) {
        this.amount = pool;
        this.pool = pool;
    }

    public int getAmount() {
        return this.amount;
    }

    public int getPool() {
        return this.pool;
    }

    public void addAmount(int amount) {
        this.amount = Math.min(this.amount + amount, pool);
    }

    public void removeAmount(int amount) {
        this.amount = Math.max(this.amount - amount, 0);
    }

    public void fill() {
        this.amount = pool;
    }

    public void addPool(int pool) {
        this.pool += pool;
    }

    public String toString() {
        return this.amount + "/" + this.pool;
    }
}
