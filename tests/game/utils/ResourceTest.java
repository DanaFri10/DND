package game.utils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResourceTest {
    private Resource r0,r1,r2,r3;

    @Before
    public void setUp() {
        r0 = new Resource(0,10);
        r1 = new Resource(15);
        r2 = new Resource(5,30);
        r3 = new Resource(50);
    }

    @Test
    public void addAmount() {
        r0.addAmount(1);
        assertEquals(r0.getAmount(),1);
        r1.addAmount(10);
        assertEquals(r1.getAmount(),15);
        r2.addAmount(100);
        assertEquals(r2.getAmount(),30);
    }

    @Test
    public void removeAmount() {
        r0.removeAmount(5);
        assertEquals(r0.getAmount(),0);
        r1.removeAmount(0);
        assertEquals(r1.getAmount(),15);
        r2.removeAmount(7);
        assertEquals(r2.getAmount(),0);
        r3.removeAmount(10);
        assertEquals(r3.getAmount(),40);
    }

    @Test
    public void fill() {
        r0.fill();
        assertEquals(r0.getAmount(),10);
        r1.fill();
        assertEquals(r1.getAmount(),15);
        r2.fill();
        assertEquals(r2.getAmount(),30);
    }

    @Test
    public void addPool() {
        r0.addPool(0);
        assertEquals(r0.getPool(),10);
        r2.addPool(10);
        assertEquals(r2.getPool(),40);
        assertEquals(r2.getAmount(),5);
        r3.addPool(10);
        assertEquals(r3.getPool(),60);
        assertEquals(r3.getAmount(),50);
    }
}