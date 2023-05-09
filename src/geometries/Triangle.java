package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * A class that inherits from Polygon class
 */
public class Triangle extends Polygon{
    /**
     * The constructor of the class Triangle
     * @param p1 first point
     * @param p2 second point
     * @param p3 third point
     */
    public Triangle(Point p1, Point p2, Point p3){
        super(p1, p2, p3);
    }

    @Override
    public List<Point> findIntersections(Ray ray){
        List<Point> rayPoints = plane.findIntersections(ray);
        //check if there is no intersection points
        if (rayPoints == null)
            return null;

        //check if the point in out or on the triangle:
        Vector v1 = vertices.get(0).subtract(ray.getP0());
        Vector v2 = vertices.get(1).subtract(ray.getP0());
        Vector v3 = vertices.get(2).subtract(ray.getP0());

        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();


        //The point is inside if all 𝒗 ∙ 𝑵𝒊 have the same sign (+/-)
        if (alignZero(n1.dotProduct(ray.getDir())) > 0 && alignZero(n2.dotProduct(ray.getDir())) > 0 && alignZero(n3.dotProduct(ray.getDir())) > 0)
        {
            return rayPoints;
        }
        //The point is outside if all 𝒗 ∙ 𝑵𝒊 have the same sign (+/-)
        else if (alignZero(n1.dotProduct(ray.getDir())) < 0 && alignZero(n2.dotProduct(ray.getDir())) < 0 && alignZero(n3.dotProduct(ray.getDir())) < 0)
        {
            return rayPoints;
        }
        if (isZero(n1.dotProduct(ray.getDir())) || isZero(n2.dotProduct(ray.getDir())) || isZero(n3.dotProduct(ray.getDir())))
            return null; //there is no instruction point
        return null;
    }

    /**
     * the toString method that we override
     * @return the string that presents the geometry
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
