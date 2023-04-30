package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * An interface that we need to implement it's methods in each class
 * @author Sarit Tik 213230048 saritik16@gmail.com
 * @author Hadas Zehevi 325543353 h0548510062@gmail.com
 */
public interface Intersectable {
    /**A method that find the Intersections*/
    List<Point> findIntersections(Ray ray);
}
