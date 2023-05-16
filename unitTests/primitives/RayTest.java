package primitives;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * test for Ray class
 * @author Sarit Tik 213230048 saritik16@gmail.com
 * @author Hadas Zehevi 325543353 h0548510062@gmail.com
 */
public class RayTest {

    /**Test method for {@link primitives.Ray#findClosestPoint(List)}*/
    @Test
    public void testFindClosestPoint() {
        Ray ray = new Ray(Point.ZERO, new Vector(1, 0, 0));
        List<Point> l = new LinkedList<Point>();

        // =============== Boundary Values Tests ==================

        // TC01: The Points' list is empty
        assertNull(null, "The Points' list is empty");

        Point p0 = new Point(2, 0, 0);
        Point p1 = new Point(2.5, 0, 0);
        Point p2 = new Point(4, 0, 0);

        l.add(p0);
        l.add(p1);
        l.add(p2);

        // TC02: The first point in the list is the closest
        assertEquals(p0, ray.findClosestPoint(l), "The first point in the list is the closest");

        // TC03: The last point in the list is the closest
        l.set(0, p2);
        l.set(2, p0);

        assertEquals(p0, ray.findClosestPoint(l), "The last point in the list is the closest");

        // ============ Equivalence Partitions Tests ==============

        // TC03: The middle point in the list is the closest
        l.set(2, p1);
        l.set(1, p0);

        assertEquals(p0, ray.findClosestPoint(l), "The middle point in the list is the closest");
    }
}
