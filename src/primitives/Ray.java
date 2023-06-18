package primitives;

import geometries.Intersectable.GeoPoint;

import java.util.List;

/**
 * The class is representing a ray
 * @author Sarit Tik 213230048 saritik16@gmail.com
 * @author Hadas Zehevi 325543353 h0548510062@gmail.com
 */
public class Ray {
	
	private static final double DELTA = 0.00001; // Small value used for offset the ray origin
	
    /**
     * presents a point in a ray
     */
    private final Point p0;
    /**
     * presents a vector in a ray
     */
    private final Vector dir;

    /**
     * Constructor that gets a point and a vector and checks if the vector is normalized
     * @param point the point that we received
     * @param vector the vector that we received
     */
    public Ray(Point point, Vector vector){
        p0 = point;
        dir = vector.normalize();
    }
    
    /**
	 * Constructs a new ray with a start point, direction, and normal vector.
	 *
	 * @param point     The start point of the ray.
	 * @param direction The direction vector of the ray.
	 * @param normal    The normal vector used to move the start point.
	 */
	public Ray(Point point, Vector direction, Vector normal) {
		// point + normal.scale(±DELTA)
		this.dir = direction.normalize();

		double nv = normal.dotProduct(direction);

		Vector normalDelta = normal.scale((nv > 0 ? DELTA : -DELTA));
		p0 = point.add(normalDelta);
	}

    /**
     * A get function to return the point of the ray
     * @return the point of the ray
     */
    public Point getP0(){ return p0; }

    /**
     * A get function to return the vector of the ray
     * @return vector of the ray
     */
    public Vector getDir() { return dir; }

    /**
     * A function that gets a double and returns a point in the ray
     * @param t the double that we received
     * @return the point in the ray
     */
    public Point getPoint(double t){
        return p0.add(dir.scale(t));
    }

    /**
     * A function that gets a point and returns the closest point to the ray
     * @param points the list of points that we received
     * @return the closest point to the ray
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    /**
     * A function that gets a list of points and returns the closest point to the ray
     * @param intersections the list of points that we received
     * @return the closest point to the ray
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> intersections){
        //if there are no intersections
        if(intersections == null)
            return null;

        GeoPoint closet = intersections.get(0);
        //if there are more than one intersection
        for (GeoPoint geoPoint : intersections)
        {
            //if the distance between the point and the ray is smaller than the distance between the closet point and the ray
            if(geoPoint.point.distance(p0) < closet.point.distance(p0))
                closet = geoPoint;

        }
        return closet;
    }


    /**
     * The equal method that we override to equal between a ray to an object
     * @param obj the object to equal with our ray
     * @return a boolean results that says if its equals or not
     */
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj instanceof Ray other)
            return p0.equals(other.p0) && dir.equals(other.dir);
        return false;
    }

    /**
     * The toString method that we override
     * @return a string of the object
     */
    @Override
    public String toString() {
        return p0.toString() + "\n" + dir.toString();
    }
}
