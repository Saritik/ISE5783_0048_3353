package geometries;

/**
 * An abstract class that implements the geometric interface
 */
public abstract class RadialGeometry {
    /**
     * The radius of each geometry
     */
     protected final double radius;

    /**
     * Constructor that sets the radius
     * @param r the radius that we got
     */
    RadialGeometry(double r){ radius = r; }

    /**
     * the toString method that we override
     * @return the string that presents the geometry
     */
    @Override
    public String toString() {
        return "Radius: " + radius + "\n";
    }
}
