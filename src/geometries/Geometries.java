package geometries;

import primitives.Ray;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * test Geometries class
 * @author Sarit Tik 213230048 saritik16@gmail.com
 * @author Hadas Zehevi 325543353 h0548510062@gmail.com
 */

public class Geometries extends Intersectable {
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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray){
        List<GeoPoint> points = new ArrayList<GeoPoint>();
        for ( Intersectable geometry : Geometries)
        {
            List<GeoPoint> intersections = geometry.findGeoIntersectionsHelper(ray);
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
