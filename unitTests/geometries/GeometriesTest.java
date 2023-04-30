package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test Geometries class
 * @author Sarit Tik 213230048 saritik16@gmail.com
 * @author Hadas Zehevi 325543353 h0548510062@gmail.com
 */

class GeometriesTest {

    /**Test method for {@link Geometries#findIntersections(Ray)}*/
    @Test
    void testFindIntersections() {
        Geometries geometries = new Geometries();

        // =============== Boundary Values Tests ==================

        // TC01 empty list (0 point)
        assertNull(geometries.findIntersections(null), "Empty list");

        Point e = new Point(1, 0, 2);
        Plane plane = new Plane(e, new Vector(3, 3, 0));
        Sphere sphere = new Sphere(new Point(1, 0, 0), 1d);
        Triangle triangle = new Triangle(e, new Point(-3, -1, 2), new Point(-2, 3, 2));
        geometries.add(plane, sphere, triangle);

        // ============ Equivalence Partitions Tests ==============

        Ray ray = new Ray(new Point(0.5,0.5,3.2), new Vector(2.5,-4.5,-3.2));

        //TC02 There are no intersections with any shape(0 point)
        assertNull(geometries.findIntersections(ray), "There are no intersections with any shape");

        Point p = new Point(-2, 0, 3.2);
        ray = new Ray(p, new Vector(-1, -1, -4.2));

        //TC03 There are intersection with one shape(1 point)
        assertEquals(1 ,geometries.findIntersections(ray).size(), "There are intersection with one shape");

        ray = new Ray(new Point(2, 3, 3.2), new Vector(-0.2, -4 , -4.2));
        // TC04 There are intersections with some shapes(3 point)
        assertEquals(3 , geometries.findIntersections(ray).size(), "There are intersections with some shapes");

        ray = new Ray(p, new Vector(3.8, -1 , -4.2));
        // TC05 There are intersections with all the shapes(4 point)
        assertEquals(4 , geometries.findIntersections(ray).size(), "There are intersections with all the shapes");
    }
}