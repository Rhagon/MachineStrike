package machinestrike.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PointTests {

    @Test
    public void testEquals() {
        Point p = new Point(0, 0);
        Assertions.assertEquals(Point.ZERO, p);
        Assertions.assertNotSame(Point.ZERO, p);
        Assertions.assertNotEquals(new Point(1, 2), new Point(2, 1));
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

}
