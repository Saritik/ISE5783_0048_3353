package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * The class is represents a plane
 * @author Sarit Tik 213230048 saritik16@gmail.com
 * @author Hadas Zehevi 325543353 h0548510062@gmail.com
 */
public class Plane extends Geometry {
    /**
     * The point that presents the plane
     */
    private final Point q0;
    /**
     * The normal that presents the plane with the point
     */
    private final Vector normal;

    /**
     * Constructor of the plane that initialize the values
     * @param p1 first point
     * @param p2 second point
     * @param p3 third point
     */
    public Plane(Point p1, Point p2, Point p3){
        Vector v1 = p2.subtract(p1);
        Vector v2 = p3.subtract(p1);
        // we multiply (by vector product) the two vectors to get the normal
        normal = v1.crossProduct(v2).normalize();
        q0 = p1;
    }

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

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray){
        // we multiply (by scalar product) the normal with the vector of the ray
        double nv = normal.dotProduct(ray.getDir());
        if (isZero(nv))
        {
            return null;
        }

        try
        {
            // creating new vector that intersects the ray and the surface
            Vector qSubtractP0 = q0.subtract(ray.getP0());
            double t = alignZero((normal.dotProduct(qSubtractP0)) / nv);

            // if the point is behind the camera
            if(t <= 0)
            {
                return null;
            }
            return List.of(new GeoPoint(this, ray.getPoint(t)));
        }
        catch(Exception ex)
        {
            return null;
        }
    }

    /**
     * gets a point and calculates the normal to this point
     * @param point the point that we gets
     * @return the normal
     */
    @Override
    public Vector getNormal(Point point) { return normal; }

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
