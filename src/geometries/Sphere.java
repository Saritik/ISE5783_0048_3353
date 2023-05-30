package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * A class that inherits from RadialGeometry class
 * @author Sarit Tik 213230048 saritik16@gmail.com
 * @author Hadas Zehevi 325543353 h0548510062@gmail.com
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

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance){
        List<GeoPoint> result = null;
        if (ray.getP0().equals(center)) {// if the begin of the ray in the center, the point, is on the radius
            if(alignZero(radius - maxDistance) <= 0) {
                return List.of(new GeoPoint(this, ray.getPoint(radius)));
            }
            return null;
        }

        Vector u = center.subtract(ray.getP0());
        double tM = alignZero(ray.getDir().dotProduct(u));
        double d = alignZero(Math.sqrt(u.length() * u.length()- tM * tM));
        double tH = alignZero(Math.sqrt(radius * radius - d * d));
        double t1 = alignZero(tM + tH);
        double t2 = alignZero(tM - tH);

        // if the ray is outside the sphere
        if (d >= radius)
            return null; // there are no instructions

        // if the ray is tangent to the sphere
        if (t1 <= 0 && t2 <= 0)
            return null;

        // if the ray is inside the sphere
        if (t1 > 0 && t2 > 0) {
            if(alignZero(t2 - maxDistance) <= 0 && alignZero(t1 - maxDistance) <= 0 ){
                return List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
            }
            return null;
        }

        // if the ray is on the sphere
        if (t1 > 0)
        {
            if(alignZero(t1 - maxDistance) <= 0) {
                return List.of(new GeoPoint(this, ray.getPoint(t1)));
            }
            return null;
        }
        else {
            if(alignZero(t2 - maxDistance) <= 0) {
                return List.of(new GeoPoint(this, ray.getPoint(t2)));
            }
            return null;
        }
    }

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
