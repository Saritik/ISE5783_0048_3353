package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
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

    /**Test method for {@link Plane#findIntersections(Ray)}*/
    @Test
    public void testFindIntersections() {
        Plane myPlane = new Plane(new Point(0,5,0), new Point(-5,0,0), new Point(0,0,3));
        // =============== Boundary Values Tests ==================

        //Ray is parallel to the plane
        // TC01: the ray included in the plane
        Ray myRay= new Ray(new Point(0,5,0), new Vector(-5,0,0));//the plane include this ray
        assertNull(myPlane.findIntersections(myRay), "A included ray has zero intersection points");
        // TC02: the ray not included in the plane
        myRay= new Ray(new Point(0,-5,0), new Vector(5,0,0));//the plane un included this ray
        assertNull(myPlane.findIntersections(myRay), "An un included ray has zero intersection points");

        //Ray is orthogonal to the plane
        // TC03: the ray starts before the plane
        myRay= new Ray(new Point(2,4,0), new Vector(-3,3,5));//the ray is orthogonal to the plane
        assertEquals(1, myPlane.findIntersections(myRay).size(),"Ray is orthogonal to the plane and starts before the plane");
        // TC04: the ray starts in the plane
        myRay= new Ray(new Point(-5,0,0), new Vector(-3,3,5));//the ray is orthogonal to the plane
        assertNull(myPlane.findIntersections(myRay), "Ray is orthogonal to the plane and starts at the plane");
        // TC05: the ray starts after the plane
        myRay= new Ray(new Point(-7,2,4), new Vector(-3,3,5));//the ray is orthogonal to the plane
        assertNull(myPlane.findIntersections(myRay), "Ray is orthogonal to the plane and starts after the plane");

        //Ray is neither orthogonal nor parallel to and begins at the plane
        // TC06: the ray starts before the plane
        myRay= new Ray(new Point(-1,-1,0), new Vector(1,0,0));//the ray is'nt orthogonal or parallel to the plane
        assertNull(myPlane.findIntersections(myRay), "Ray is neither orthogonal nor parallel to and begins at reference point in the plane");

        //Ray is neither orthogonal nor parallel to the plane and begins in
        //the same point which appears as reference point in the plane
        // TC07: the ray starts at the plane
        myRay= new Ray(new Point(0,0,3), new Vector(-5,4,-3));//the ray is'nt orthogonal or parallel to the plane but not intersects the plane
        assertNull(myPlane.findIntersections(myRay), "Ray is neither orthogonal nor parallel to and begins at the plane");

        // ============ Equivalence Partitions Tests ================
        // TC08: The Ray must be neither orthogonal nor parallel to the plane
        //Ray does not intersect the plane
        myRay= new Ray(new Point(1,2,0), new Vector(-3,-7,0));
        assertNull(myPlane.findIntersections(myRay), "Ray is neither orthogonal nor parallel but doesnt intersects the plane");

        // TC09: The Ray must be neither orthogonal nor parallel to the plane
        // Ray intersects the plane
        myRay= new Ray(new Point(4,3,0), new Vector(-5.75,3.57,0));//the ray is'nt orthogonal or parallel to the plane and intersects the plane
        assertEquals(1, myPlane.findIntersections(myRay).size(), "Ray is neither orthogonal nor parallel and intersects the plane ");
    }
}