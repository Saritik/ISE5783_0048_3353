package primitives;

/**
 * The class is represents a ray
 */
public class Ray {
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
     * A get method for the ray
     * @return our ray
     */
    public Ray getRay(){ return new Ray(p0, dir); }

    /**
     * The equal method that we override to equal between a ray to an object
     * @param obj the object to equal with our ray
     * @return a boolean results that says if its equals or not
     */
    @Override
    public boolean equals(Object obj) { return p0.equals(((Ray)obj).p0) && dir.equals(((Ray)obj).dir); }

    /**
     * The toString method that we override
     * @return a string of the object
     */
    @Override
    public String toString() {
        return p0.toString() + "\n" + dir.toString();
    }
}
