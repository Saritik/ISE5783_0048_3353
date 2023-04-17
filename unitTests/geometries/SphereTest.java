package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

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
                sphere.getNormal(new Point(1.73, 0, 1)), "wrong normal caculation");
    }
}