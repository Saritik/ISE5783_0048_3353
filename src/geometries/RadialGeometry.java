package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * An abstract class that implements the geometric interface
 */
public abstract class RadialGeometry implements Geometry {
    /**
     * The radius of each geometry
     */
     protected final double radius;

    /**
     * Constructor that sets the radius
     * @param r the radius that we got
     */
    RadialGeometry(double r){ radius = r; }

    /**
     * gets a point and calculates the normal to this point
     * @param point the point that we gets
     * @return the normal
     */
    @Override
    public Vector getNormal(Point point) { return null; }

    /**
     * the toString method that we override
     * @return the string that presents the geometry
     */
    @Override
    public String toString() {
        return "Radius: " + radius + "\n";
    }
}
