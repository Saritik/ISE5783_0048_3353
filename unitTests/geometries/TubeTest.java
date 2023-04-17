package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test Tube class
 * @author Sarit Tik 213230048 saritik16@gmail.com
 * @author Hadas Zehevi 325543353 h0548510062@gmail.com
 */

class TubeTest {

    /**Test method for {@link Tube#getNormal(Point)}.*/
    @Test
    void testGetNormal() {

        // ============ Equivalence Partitions Tests ==============
        Ray ray = new Ray(new Point(0, 0, 0.5), new Vector(0, 0, 1));
        Tube tube = new Tube(ray, 2);

        /**
         * TC 01, normal vector to a point on the tube not paralleled to p0
         */
        assertEquals(new Vector(0, -1, 0), tube.getNormal(new Point(0, -2, 2)), "wrong calculation normal");

        // =============== Boundary Values Tests ==================
        /**
         * TC 02, normal vector to a point on the tube paralleled to p0
         */
        assertEquals(new Vector(0, -1, 0), tube.getNormal(new Point(0, -2, 0.5)), "wrong calculation normal");
    }
}