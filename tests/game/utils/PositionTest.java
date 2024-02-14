package game.utils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PositionTest {
    private Position p0,p1,p2,p3,p4,p5;

    @Before
    public void setUp() {
        p0 = new Position(0,0);
        p1 = new Position(1,0);
        p2 = new Position(2,0);
        p3 = new Position(0,1);
        p4 = new Position(1,1);
        p5 = new Position(0,2);
    }

    @Test
    public void at() {
        assertEquals(p0,Position.at(0,0));
        assertEquals(p4,Position.at(1,1));
        assertEquals(p5,Position.at(0,2));
    }

    @Test
    public void compareTo() {
        assertTrue(p0.compareTo(p1) < 0);
        assertTrue(p3.compareTo(p2) > 0);
        assertTrue(p4.compareTo(new Position(1,1)) == 0);
    }

    @Test
    public void range() {
        assertEquals(p1.range(p2), 1,0.00001);
        assertEquals(p2.range(p5), Math.sqrt(8),0.00001);
        assertEquals(p4.range(p5), Math.sqrt(2),0.00001);
    }
}