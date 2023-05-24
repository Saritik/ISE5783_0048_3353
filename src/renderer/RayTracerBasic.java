package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class RayTracerBasic
 * a class that represents a basic ray tracer
 * it extends RayTracerBase
 *
 * @author Sarit Tik 213230048 saritik16@gmail.com
 * @author Hadas Zehevi 325543353 h0548510062@gmail.com
 */
public class RayTracerBasic extends RayTracerBase{
    /**
     * constructor of RayTracerBasic
     *
     * @author sarit silverstone and rivki adler
     * @param scene Scene value
     */
    public RayTracerBasic(Scene scene){ super(scene); }

    /**
     * a function that calculates thr diffusive light
     * @param material Material value
     * @param nl double value
     * @return Double3 value
     */
    private Double3 calcDiffusive(Material material, double nl){
        return material.kD.scale(Math.abs(nl));
    }

    /**
     * a function that calculates the specular light
     * @param material Material value
     * @param n Vector value
     * @param l Vector value
     * @param nl double value
     * @param vector Vector value
     * @return Double3 value
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector vector){
        return material.kS.scale(Math.pow(Math.max(0, -vector.dotProduct(l.
                subtract(n.scale(2 * nl)))), material.nShininess));
    }

    /**
     * a function that calculates the local effects of a point
     * @param geoPoint GeoPoint value
     * @param ray Ray value
     * @return Color value
     */
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray){
        //the color of the point
        Color color = geoPoint.geometry.getEmission();
        //the direction of the ray
        Vector vector = ray.getDir();
        //the normal of the point
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        //the dot product of the normal and the direction
        double nv = alignZero(n.dotProduct(vector));

        //if the dot product is zero, the point is not visible
        if(isZero(nv)) return color;
        Material material = geoPoint.geometry.getMaterial();

        //for each light source in the scene
        for(LightSource lightSource : scene.lights){
            //the direction of the light source
            Vector l = lightSource.getL(geoPoint.point).normalize();
            //the dot product of the normal and the direction of the light source
            double nl = alignZero(n.dotProduct(l));
            //if the dot product is zero, the point is not visible
            if(nl * nv > 0){
                //the intensity of the light source
                Color lightIntensity = lightSource.getIntensity(geoPoint.point);
                color = color.add(lightIntensity.scale(calcDiffusive(material, nl)),
                        lightIntensity.scale(calcSpecular(material, n, l, nl, vector)));
            }
        }
        return color;
    }

    /**
     * a function that returns the color of a point
     * @author sarit silverstone and rivki adler
     * @param intersection
     */
    private Color calcColor(GeoPoint intersection, Ray ray){
        return scene.ambientLight.getIntensity().
                add(calcLocalEffects(intersection, ray));
    }

    @Override
    public Color traceRay(Ray ray) throws IllegalArgumentException {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        //if there are no intersections, return the background color
        if(intersections == null) return scene.background;
        //find the closest point to the camera
        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint, ray);
    }
}
