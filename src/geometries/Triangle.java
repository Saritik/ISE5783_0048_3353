package geometries;

import primitives.Point;

/**
 * A class that inherits from Polygon class
 */
public class Triangle extends Polygon{
    /**
     * The constructor of the class Triangle
     * @param p1 first point
     * @param p2 second point
     * @param p3 third point
     */
    public Triangle(Point p1, Point p2, Point p3){
        super(p1, p2, p3);
    }

    /**
     * the toString method that we override
     * @return the string that presents the geometry
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
