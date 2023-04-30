package primitives;

/**
 * The class is represents a point in the space
 */
public class Point {
    /**
     * Making an object of Double3 type
     */
    final Double3 xyz;

    /**
     * Constructor that gets three double variables for the Point class
     * @param d1 double variable 1
     * @param d2 double variable 2
     * @param d3 double variable 3
     */
    public Point(double d1, double d2, double d3){
        xyz = new Double3(d1, d2, d3);
    }

    /**
     * Constructor that gets an object of type Double3 that initialize
     our object
     * @param obj the object to initialize
     */
    Point(Double3 obj){ xyz  = obj; }

    /**
     * The equal method that we override to equal between a Point to an object
     * @param obj the object to equal with our point
     * @return a boolean results that says if its equals or not
     */
    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj instanceof Point other)
            return xyz.equals(other.xyz);
        return false;
    }

    @Override
    public int hashCode() { return xyz.hashCode(); }

    /**
     * The toString method that we override
     * @return a string of the object
     */
    @Override
    public String toString() { return "" + xyz; }

    /**
     * Adds a vector to the point
     * @param rhs the vector that we have received
     * @return the new point
     */
    public Point add(Vector rhs) { return new Point(xyz.add(rhs.xyz)); }

    /**
     * Subtract a second pint from the first point
     * @param rhs the point that we have received
     * @return the new vector
     */
    public Vector subtract(Point rhs){ return new Vector(xyz.subtract(rhs.xyz)); }

    /**
     * A get function to return the x value
     * @return a double (the x)
     */
    public double getX(){ return xyz.d1; }

    /**
     * A get function to return the y value
     * @return a double (the y)
     */
    public double getY(){ return xyz.d2; }

    /**
     * A get function to return the z value
     * @return a double (the z)
     */
    public double getZ(){ return xyz.d3; }

    /**
     * The function returns the distance by square between our point to the point that
     we have received
     * @param point the point that we have received
     * @return the distance by square
     */
    public double distanceSquared (Point point){
        return (((this.xyz.d1 - point.xyz.d1) * (this.xyz.d1 - point.xyz.d1))  +
                ((this.xyz.d2 - point.xyz.d2) * (this.xyz.d2 - point.xyz.d2)) +
                ((this.xyz.d3 - point.xyz.d3) * (this.xyz.d3 - point.xyz.d3)));
    }

    /**
     * The function returns the distance between our point to the point that
     we have received
     * @param point the point that we have received
     * @return the distance
     */
    public double distance (Point point){
        return Math.sqrt(distanceSquared(point));
    }
}
