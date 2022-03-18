package machinestrike.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PointTests {

    @Test
    public void testEquals() {
        Point p = new Point(0, 0);
        Assertions.assertEquals(Point.ZERO, p);
        Assertions.assertNotSame(Point.ZERO, p);
        Assertions.assertNotEquals(new Point(1, 2), new Point(2, 1));
        Assertions.assertNotEquals(p, null);
    }

    @Test
    public void testAdd() {
        Point p = new Point(1, 2);
        Assertions.assertEquals(new Point(4, 6), p.add(new Point(3, 4)));
        Assertions.assertEquals(new Point(-2, -5), p.add(new Point(-3, -7)));
    }

    @Test
    public void testSubtract() {
        Point p = new Point(7, 4);
        Assertions.assertEquals(new Point(2, -1), p.subtract(new Point(5, 5)));
        Assertions.assertEquals(new Point(9, 7), p.subtract(new Point(-2, -3)));
    }

    @Test
    public void testMultiply() {
        Point p = new Point(-2, 3);
        Assertions.assertEquals(new Point(-4, 6), p.multiply(2));
        Assertions.assertEquals(Point.ZERO, p.multiply(0));
        Assertions.assertEquals(new Point(2, -3), p.multiply(-1));
    }

    @Test
    public void testInDirection() {
        Point p = new Point(3, 2);
        Assertions.assertEquals(new Point(3, 3), p.inDirection(Orientation.NORTH));
        Assertions.assertEquals(new Point(3, 1), p.inDirection(Orientation.SOUTH));
        Assertions.assertEquals(new Point(2, 2), p.inDirection(Orientation.WEST));
        Assertions.assertEquals(new Point(4, 2), p.inDirection(Orientation.EAST));
    }

    @Test
    public void testInDirectionWithSteps() {
        Point p = new Point(3, 2);
        Assertions.assertEquals(new Point(3, 5), p.inDirection(Orientation.NORTH, 3));
        Assertions.assertEquals(new Point(3, -2), p.inDirection(Orientation.NORTH, -4));
    }

    @Test
    public void testSumDistance() {
        Point p = new Point(3, 2);
        Assertions.assertEquals(6, p.sumDistance(new Point(4, 7)));
        Assertions.assertEquals(4, p.sumDistance(new Point(1, 4)));
        Assertions.assertEquals(5, p.sumDistance(Point.ZERO));
        Assertions.assertEquals(0, p.sumDistance(p));
    }

    @Test
    public void testMaximumDistance() {
        Point p = new Point(3, 2);
        Assertions.assertEquals(5, p.maximumDistance(new Point(4, 7)));
        Assertions.assertEquals(2, p.maximumDistance(new Point(1, 4)));
        Assertions.assertEquals(3, p.maximumDistance(Point.ZERO));
        Assertions.assertEquals(0, p.maximumDistance(p));
    }

    @Test
    public void testHashCode() {
        Point p = new Point(1, 2);
        Assertions.assertNotEquals(p.hashCode(), new Point(2, 1).hashCode());
        Assertions.assertNotEquals(p.hashCode(), new Point(-1, -2).hashCode());
        Assertions.assertNotEquals(p.hashCode(), new Point(-2, -1).hashCode());
        Assertions.assertNotEquals(p.hashCode(), new Point(2, 2).hashCode());
    }

    @Test
    public void testNeighbours() {
        Point p = new Point(3, 2);
        List<Point> neighbours = p.neighbours();
        Assertions.assertEquals(Orientation.values().length, neighbours.size());
        for(Orientation orientation : Orientation.values()) {
            Assertions.assertTrue(neighbours.contains(p.inDirection(orientation)));
        }
    }

    @Test
    public void testNeighboursWithFilter() {
        Point p = Point.ZERO;
        List<Point> neighbours = p.neighbours(x -> x.x() < 0 || x.y() < 0);
        Assertions.assertEquals(2, neighbours.size());
        Assertions.assertTrue(neighbours.contains(p.inDirection(Orientation.NORTH)));
        Assertions.assertTrue(neighbours.contains(p.inDirection(Orientation.EAST)));
    }

    @Test
    public void testInRange() {
        Point p = new Point(3, 2);
        Assertions.assertTrue(p.inRange(new Point(2, 1), new Point(4, 3)));
        Assertions.assertTrue(p.inRange(new Point(3, 2), new Point(3, 2)));
        Assertions.assertTrue(p.inRange(new Point(3, 2), new Point(-1, -1)));
        Assertions.assertFalse(p.inRange(new Point(4, 4), new Point(4, 1)));
    }

    @Test
    public void testToString() {
        Assertions.assertEquals("(2, 3)", new Point(2, 3).toString());
        Assertions.assertEquals("(-1, -2)", new Point(-1, -2).toString());
    }

}
