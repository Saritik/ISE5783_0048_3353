package geometries;
import primitives.Point;
import primitives.Vector;

/**
 * The class is represents a plane
 */
public class Plane implements Geometry {
    /**
     * The point that presents the plane
     */
    private Point q0;
    /**
     * The normal that presents the plane with the point
     */
    private Vector normal;

    /**
     * Constructor of the plane that initialize the values
     * @param p1 first point
     * @param p2 second point
     * @param p3 third point
     */
    public Plane(Point p1, Point p2, Point p3){ normal = null; q0 = p1; }

    /**
     * constructor that gets a point and a vector to initialize the values
     * @param point the point
     * @param vector the vector
     */
    public Plane(Point point, Vector vector){
        q0 = point;
        normal = vector.normalize();
    }

    /**
     * the get method of the point
     * @return the point of the plane
     */
    public Point getPoint(){ return q0; }

    /**
     * the get method of the normal
     * @return the normal of the plane
     */
    public Vector getNormal(){ return normal; }

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
        return "Point: " + q0 + "\n" +
                "Vector: " + normal + "\n";
    }
}
