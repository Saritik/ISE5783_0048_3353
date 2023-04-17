package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * A class that inherits from RadialGeometry class
 */
public class Sphere extends RadialGeometry{
    /**
     * The center point of the sphere
     */
    private final Point center;

    /**
     * The constructor of sphere that gets a point and a radius and initialize the values
     * @param point the center point that we gets
     * @param radius the radius of the sphere
     */
    public Sphere(Point point, double radius){
        super(radius);
        center = point;
    }

    /**
     * The get method of the center point of the sphere
     * @return the center point of the sphere
     */
    public Point getCenter() { return center;}

    /**
     * gets a point and calculates the normal to this point
     * @param point the point that we gets
     * @return the normal
     */
    @Override
    public Vector getNormal(Point point) {
        return point.subtract(center).normalize();
    }

    /**
     * the toString method that we override
     * @return the string that presents the geometry
     */
    @Override
    public String toString() {
        return super.toString() +
                "Center: " + center + "\n";
    }
}
