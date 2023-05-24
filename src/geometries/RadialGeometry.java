package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * An abstract class that implements the geometric interface
 * @author Sarit Tik 213230048 saritik16@gmail.com
 * @author Hadas Zehevi 325543353 h0548510062@gmail.com
 */
public abstract class RadialGeometry extends Geometry {
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
