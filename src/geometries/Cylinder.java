package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * A class that inherits from Tube class
 */
public class Cylinder extends Tube {
    /**
     * The height of the cylinder
     */
    private final double height;

    /**
     * Constructor that initialize the values and the inherits values
     * @param h the height of the cylinder
     * @param ray the axis ray of the cylinder
     * @param radius the radius of the cylinder
     */
    public Cylinder(double h, Ray ray, double radius){
        super(ray, radius);
        height = h;
    }

    /**
     * The get method that returns the height of the cylinder
     * @return the height of the cylinder
     */
    public double getHeight(){ return height; }

    /**
     * gets a point and calculates the normal to this point
     * @param point the point that we gets
     * @return the normal
     */
    public Vector getNormal(Point point) { return null; }

    /**
     * the toString method that we override
     * @return the string that presents the geometry
     */
    @Override
    public String toString() {
        return super.toString() + "\n" +
                "Height: " + height + "\n";
    }
}
