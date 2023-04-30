package geometries;
import primitives.Vector;
import primitives.Point;

/**
 * An interface of the geometries
 */
public interface Geometry extends  Intersectable {
    Vector getNormal(Point point);
}
