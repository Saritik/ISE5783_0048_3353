package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * A class that inherits from Tube class
 * @author Sarit Tik 213230048 saritik16@gmail.com
 * @author Hadas Zehevi 325543353 h0548510062@gmail.com
 */
public class Cylinder extends Tube {
    /**
     * The height of the cylinder
     */
    private final double height;

    /**
     * Constructor that initialize the values and the inherits values
     * @param h the height of the cylinder
     * @param ray the axis ray of the cylinder
     * @param radius the radius of the cylinder
     */
    public Cylinder(double h, Ray ray, double radius){
        super(ray, radius);
        height = h;
    }

    /**
     * The get method that returns the height of the cylinder
     * @return the height of the cylinder
     */
    public double getHeight(){ return height; }

    /**
     * gets a point and calculates the normal to this point
     * @param point the point that we gets
     * @return the normal
     */
    @Override
    public Vector getNormal(Point point) {
        Point pointTop = axisRay.getP0().add(axisRay.getDir().scale(height));

        //checking if the point is at the top of the cylinder
        if(point.equals(pointTop) || isZero(axisRay.getDir().dotProduct(point.subtract(pointTop))))
            return axisRay.getDir().normalize();

        // checking if the point is at the base of the cylinder
        if(point.equals(axisRay.getP0()) || isZero(axisRay.getDir().dotProduct(point.subtract(axisRay.getP0()))))
            return axisRay.getDir().normalize();

        return super.getNormal(point);
    }

    /**
     * the toString method that we override
     * @return the string that presents the geometry
     */
    @Override
    public String toString() {
        return super.toString() + "\n" +
                "Height: " + height + "\n";
    }
}
