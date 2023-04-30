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
     * A get function to return the point of the ray
     * @returnthe point of the ray
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
