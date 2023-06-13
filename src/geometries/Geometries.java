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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance){
        List<GeoPoint> points = null;
        for (var geometry : Geometries)
        {
            var intersections = geometry.findGeoIntersections(ray, maxDistance);
            //if there are intersections add them to the list
            if (intersections != null) {
                if (points == null)
                    points = new ArrayList<GeoPoint>(intersections);
                else
                    points.addAll(intersections);
            }
        }
        //else return the list of intersections
        return points;
    }
}
