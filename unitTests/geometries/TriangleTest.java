package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
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

    


}