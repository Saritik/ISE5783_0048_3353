package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * A class that inherits from RadialGeometry class
 * @author Sarit Tik 213230048 saritik16@gmail.com
 * @author Hadas Zehevi 325543353 h0548510062@gmail.com
 */
public class Tube extends RadialGeometry{
    /**
     * The axis ray of the tube
     */
    protected final Ray axisRay;

    /**
     * Constructor that initialize the values
     * @param ray the ray that we initialize with it the axis ray
     * @param radius the radius of the tube
     */
    public Tube(Ray ray, double radius){
        super(radius);
        axisRay = ray;
    }

    /**
     * The get method that returns the axis ray
     * @return the axis ray of the tube
     */
    public Ray getAxisRay(){ return axisRay; }

    /**
     * gets a point and calculates the normal to this point
     * @param point the point that we gets
     * @return the normal
     */
    @Override
    public Vector getNormal(Point point) {
        double t = axisRay.getDir().dotProduct(point.subtract(axisRay.getP0()));

        //if the point is on the axis ray
        if(!primitives.Util.isZero(t)){
            Point O = axisRay.getP0().add(axisRay.getDir().scale(t));
            return point.subtract(O).normalize();
        }
        //if the point is on the axis ray's head
        else{
            return point.subtract(axisRay.getP0()).normalize();
        }
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray){
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * the toString method that we override
     * @return the string that presents the geometry
     */
    @Override
    public String toString() {
        return "->" + super.toString() +
                "Axis ray: " + axisRay + "\n";
    }
}
