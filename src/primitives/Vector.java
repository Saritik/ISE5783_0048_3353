package primitives;
/**
 * The class is representing a vector
 * @author Sarit Tik 213230048 saritik16@gmail.com
 * @author Hadas Zehevi 325543353 h0548510062@gmail.com
 */
public class Vector extends Point {

    /**
     * Constructor that gets three double variables for the point class
     * @param d1 double variable 1
     * @param d2 double variable 2
     * @param d3 double variable 3
     */
    public Vector(double d1, double d2, double d3) {
        super(d1, d2, d3);
        if(Double3.ZERO.equals(this.xyz))
            throw new IllegalArgumentException("The vector is a ZERO vector!");
    }

    /**
     * Constructor that gets an object of type Double3 that initialize
     the object in point
     * @param obj the object to initialize
     */
    Vector(Double3 obj) {
        super(obj);
        if(Double3.ZERO.equals(this.xyz))
            throw new IllegalArgumentException("The vector is a ZERO vector!");
    }

    /**
     * The equal method that we override to equal between a vector to an object
     * @param obj the object to equal with our vector
     * @return a boolean results that says if its equals or not
     */
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj instanceof Vector other)
            return super.equals(other);
        return false;
    }

    @Override
    public int hashCode() { return xyz.hashCode(); }

    /**
     * The toString method that we override
     * @return a string of the object
     */
    @Override
    public String toString() { return "->" + super.toString(); }

    /**
     * Adds two vectors and making a new one
     * @param rhs the vector that we have received
     * @return the new vector
     */
    public Vector add(Vector rhs) { return new Vector(this.xyz.add(rhs.xyz)); }

    /**
     * Multiply a vector by a scalar number
     * @param scalar the number that we multiply with
     * @return the new vector after we have multiplied
     */
    public Vector scale(double scalar){ return new Vector(this.xyz.scale(scalar)); }

    /**
     * Doing a vector multiplication
     * @param vector the second vector that we have received
     * @return the new vector after we have multiplied
     */
    public Vector crossProduct(Vector vector ){
        return new Vector((this.xyz.d2 * vector.xyz.d3) - (this.xyz.d3 * vector.xyz.d2), (this.xyz.d3 * vector.xyz.d1) - (this.xyz.d1 * vector.xyz.d3),
                (this.xyz.d1 * vector.xyz.d2) - (this.xyz.d2 * vector.xyz.d1));
    }

    /**
     * Calculates the length of the vector by square
     * @return the length of the vector by square
     */
    public double lengthSquared(){
        return ((this.xyz.d1 * this.xyz.d1) +
                (this.xyz.d2 * this.xyz.d2) +
                (this.xyz.d3 * this.xyz.d3));
    }

    /**
     * Calculates the length of the vector
     * @return the length of the vector
     */
    public double length(){ return Math.sqrt(lengthSquared()); }

    /**
     * Normalized our vector
     * @return the new vector after the normalization
     */
    public Vector normalize(){ return new Vector(this.xyz.d1 / length(), this.xyz.d2 / length(), this.xyz.d3 / length()); }

    /**
     * Doing a scalar product between two vectors
     * @param vector the second vector that we multiply with
     * @return the scalar
     */
    public double dotProduct(Vector vector){
        return ((this.xyz.d1 * vector.xyz.d1) +
                (this.xyz.d2 * vector.xyz.d2) +
                (this.xyz.d3 * vector.xyz.d3));
    }
}