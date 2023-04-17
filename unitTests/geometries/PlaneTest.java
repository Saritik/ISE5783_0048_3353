package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test Plane class
 * @author Sarit Tik 213230048 saritik16@gmail.com
 * @author Hadas Zehevi 325543353 h0548510062@gmail.com
 */
class PlaneTest {

    /**Test method for {@link Plane#Plane(Point, Point, Point)}.*/
    @Test
    public void testPlaneConstructor(){
        // ============ Equivalence Partitions Tests ==============

        // TC 01: tests the three points which given are not on the same line
        Point x = new Point(1, 2, 3);
        Point y = new Point(2, 4, 6);
        Point z = new Point(1, 2, 3);

        assertThrows(IllegalArgumentException.class, () -> new Plane(x, y, z), "illegal parameters, constructor failed");

        // TC 02: tests the three points which given are not coalesce
        Point x2 = new Point(1,2,3);
        Point y2 = new Point(1,2,3);
        Point z2 = new Point(1,7,5);

        assertThrows(IllegalArgumentException.class, () -> new Plane(x2, y2 , z2), "illegal parameters, constructor failed");
    }

    /**Test method for {@link Plane#getNormal(Point)}.*/
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============

        // TC 01: tests the calculation of the normal
        Point a = new Point(1, 2, 3);
        Point b = new Point(2, 1, 4);
        Point c = new Point(2, 1, 1);

        Plane plane = new Plane(a, b, c);
        assertEquals(new Vector(3, 3, 0).normalize(), plane.getNormal(a), "wrong calculation normal");
    }
}