package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.*;

/**
 * test Geometries class
 * @author Sarit Tik 213230048 saritik16@gmail.com
 * @author Hadas Zehevi 325543353 h0548510062@gmail.com
 */

public class Geometries implements Intersectable {
    /**
     * list of geometries
     */
    private List<Intersectable> Geometries;

    /**
     * empty constructor that initialize the list with empty list
     */
    public Geometries(){
        Geometries = new LinkedList<>();
        Geometries.clear();
    }

    /**
     * constructor that initialize the list with the given list
     * @param geometries list of geometries
     */
   public Geometries(Intersectable... geometries){
        Geometries = new LinkedList<>();
        for(Intersectable geometry: geometries){
            Geometries.add(geometry);
        }
   }

    /**
     * add geometries to list of geometries
     * @param geometries list of geometries
     */
   public  void add(Intersectable... geometries){
       for(Intersectable geometry: geometries){
           Geometries.add(geometry);
       }
   }

    /**
     * find intersections of ray with the geometries
     * @param ray ray to find intersections with
     * @return list of intersections points
     */
    @Override
    public List<Point> findIntersections(Ray ray){
        List<Point> points = new ArrayList<Point>();
        for ( Intersectable geometry : Geometries)
        {
            List<Point> intersections = geometry.findIntersections(ray);
            //if there are intersections add them to the list
            if (intersections != null)
                points.addAll(intersections);
        }

        //if there are no intersections return null
        if (points.isEmpty())
            return null;
        //else return the list of intersections
        return points;
    }
}
