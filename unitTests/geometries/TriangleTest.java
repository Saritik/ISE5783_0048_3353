package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * test Triangle class
 * @author Sarit Tik 213230048 saritik16@gmail.com
 * @author Hadas Zehevi 325543353 h0548510062@gmail.com
 */
class TriangleTest {

    /**Test method of {@link Triangle#getNormal(Point)}.*/
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        try
        {
            Triangle myTriangle = new Triangle(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
            double sqrt3 = Math.sqrt(1d / 3);
            Vector normal = new Vector(sqrt3, sqrt3, sqrt3);
            assertEquals(normal , myTriangle.getNormal(new Point(0, 0, 1)), "wrong calculation normal");
        }
        catch(Exception ex)
        {
            fail("for vectors that not zero vector does not need throw an exception");
        }
    }

    /**Test method for {@link Triangle#findIntersections(Ray)}*/
    @Test
    public void testFindIntersections() {
        Triangle triangle = new Triangle(new Point(1, 0, 2), new Point(-3, -1, 2), new Point(-2, 3, 2));
        Point rayPoint = new Point(3, 2, 1);
        Ray r = new Ray(rayPoint, new Vector(-5, 0, 1));
        Point p = new Point(-2, 2, 2);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's Intersect the triangle (1 points)
        assertEquals(p, triangle.findIntersections(r).get(0), "Ray's start before the triangle and Intersect");

        // TC02: Ray's Intersect against the edge (0 points)
        r = new Ray(rayPoint, new Vector(-3, 0, 1));
        assertNull(triangle.findIntersections(r), "Ray's start before the triangle and Intersect");

        // TC03: Ray's Intersect against the vertex (0 points) ---------------------------------
        r = new Ray(rayPoint, new Vector(0, -2.5, 1));
        assertNull(triangle.findIntersections(r), "Ray's start before the triangle and Intersect");
        // -------------------------------------------------------------------------------------

        // =============== Boundary Values Tests ==================

        // TC04: Ray's Intersect at the edge (0 points)
        r = new Ray(rayPoint, new Vector(-3.5, -0.5, 1));
        assertNull(triangle.findIntersections(r), "Ray's Intersect at the edge");

        // TC05: Ray's Intersect at the vertex (0 points)
        r = new Ray(rayPoint, new Vector(-5, 1, 1));
        assertNull(triangle.findIntersections(r), "Ray's Intersect at the vertex");

        // TC06: Ray's Intersect at the continuation of edge (0 points)
        r = new Ray(rayPoint, new Vector(-4.56, -2.76, 1));
        assertNull(triangle.findIntersections(r), "Ray's Intersect at the continuation of edge");
    }
}