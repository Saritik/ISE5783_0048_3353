package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test Sphere class
 * @author Sarit Tik 213230048 saritik16@gmail.com
 * @author Hadas Zehevi 325543353 h0548510062@gmail.com
 */
class SphereTest {

    /**Test method for {@link Sphere#getNormal(Point)}.*/
    @Test
    void testGetNormal() {
        Sphere sphere = new Sphere(new Point(0, 0, 0), 2);
        // ============ Equivalence Partitions Tests ==============

        // TC 01: test if gets a normal vector to a point on the sphere
        assertEquals(new Vector(1.73, 0, 1).normalize(),
                sphere.getNormal(new Point(1.73, 0, 1)), "wrong normal calculation");
    }

    /**Test method for {@link Sphere#findIntersections(Ray)}*/
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(new Point(1, 0, 0), 1d);

        // ============ Equivalence Partitions Tests ==============
        Point r = new Point(-1, 0, 0);

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(r, new Vector(1, 1, 0))), "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> result = sphere.findIntersections(new Ray(r, new Vector(3, 1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        result = sphere.findIntersections(new Ray(new Point(0.5, 0, 0), new Vector(1.08, -0.02, 0.82)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point(1.5761664768422614, -0.01992900883041225, 0.817089362046902),
                result.get(0), "Wrong point");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(3, 0, 0), new Vector(1, 0, 0))),
                "Ray's line after the sphere");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        Point point = new Point(1.5, 0.87, 0);
        //result = sphere.findIntersections(new Ray(point, new Vector(0, -1.73, 0)));

        assertEquals(1, result.size(), "Wrong number of points");
        //assertEquals(new Point(1.5, 0.866025403784, 0), result.get(0), "Wrong point");

        // TC12: Ray starts at sphere and goes outside (0 points)
        result = sphere.findIntersections(new Ray(point, new Vector(0, 1.73, 0)));
        assertNull(result, "Ray's line after the sphere");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        Vector v = new Vector(0, -2.5, 0);
        Point p = new Point(1, -1, 0);
        result = sphere.findIntersections(new Ray(new Point(1, 1.5, 0), v));
        assertEquals(2, result.size(), "Wrong number of points");
        assertEquals(p, result.get(0), "Wrong point0");
        assertEquals(new Point(1, 1, 0), result.get(1), "Wrong point1");

        // TC14: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntersections(new Ray(new Point(1, 1, 0), v));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(p, result.get(0), "Wrong point");

        // TC15: Ray starts inside (1 points)
        result = sphere.findIntersections(new Ray(new Point(1, 0.5, 0), v));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(p, result.get(0), "Wrong point");

        // TC16: Ray starts at the center (1 points)
        result = sphere.findIntersections(new Ray(new Point(1, 0, 0), v));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(p, result.get(0), "Wrong point");

        // TC17: Ray starts at sphere and goes outside (0 points)
        result = sphere.findIntersections(new Ray(p, v));
        assertNull(result, "Ray starts at sphere and goes outside ");

        // TC18: Ray starts after sphere (0 points)
        result = sphere.findIntersections(new Ray(new Point(1, -1.1, 0), v));
        assertNull(result, "Ray starts after sphere ");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts behind the tangent point
        v = new Vector(-3, 0, 0);
        result = sphere.findIntersections(new Ray(new Point(0, -1, 0), v));
        assertNull(result, "ERROR: TC019");

        // TC20: Ray starts at the tangent point
        result = sphere.findIntersections(new Ray(p, v));
        assertNull(result, "Ray starts at the tangent point ");

        // TC21: Ray starts after the targent point
        result = sphere.findIntersections(new Ray(new Point(2.5, -1, 0), v.scale(-1)));
        assertNull(result, "Ray starts after the tangent point ");

        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's
        // center line
        result = sphere.findIntersections(new Ray(new Point(1, -2, 0), v));
        assertNull(result, "Ray's line is outside, ray is orthogonal to ray start to sphere's center line ");
    }
}
