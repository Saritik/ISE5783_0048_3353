package geometries;

import primitives.BoundingBox;
import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * An interface that we need to implement it's methods in each class
 * @author Sarit Tik 213230048 saritik16@gmail.com
 * @author Hadas Zehevi 325543353 h0548510062@gmail.com
 */
public abstract class Intersectable {
	
	
    protected BoundingBox boundingBox = new BoundingBox();

    /**
     * A method that finds the intersections of a ray with the geometry
     * @param ray the ray that we want to find the intersections with
     * @return a list of the intersections points
     */
    public List<Point> findIntersections(Ray ray){
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * A class that represents a point and the geometry that it's on
     */
    public static class GeoPoint {
        //The geometry that the point is on
        public Geometry geometry;
        //The point
        public final Point point;

        /**
         * A constructor that gets a geometry and a point and creates a GeoPoint
         * @param geometry the geometry
         * @param point the point
         */
        public GeoPoint(Geometry geometry, Point point){
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object obj){
            if(this == obj) return true;
            if(obj instanceof GeoPoint other){
                return geometry.emission.equals(other.geometry.emission)
                        && point.equals(other.point);
            }
            return false;
        }

        @Override
        public String toString(){
            return "Geometry: " + geometry + "\n"
                    + "Point: " + point + "\n";
        }
    }


    /**
     * A helper method that needs to be implemented in subclasses to find the geometry-ray intersections
     * @param ray the ray to find intersections with
     * @param maxDistance the maximum allowed distance for intersections
     * @return a list of GeoPoints representing the intersections
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);

    /**
     * Calls the findGeoIntersectionsHelper method with a maximum distance set to positive infinity
     * @param ray the ray to find intersections with
     * @return a list of GeoPoints representing the intersections
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * Calls the findGeoIntersectionsHelper method with a specified maximum distance
     * @param ray the ray to find intersections with
     * @param maxDistance the maximum allowed distance for intersections
     * @return a list of GeoPoints representing the intersections
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }
}
