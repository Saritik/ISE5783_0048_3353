package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

/**
 * class RayTracerBasic
 * a class that represents a basic ray tracer
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
     * a function that returns the color of a point
     * @author sarit silverstone and rivki adler
     * @param point
     */
    private Color calcColor(Point point){
        return scene.ambientLight.getIntensity();
    }

    @Override
    public Color traceRay(Ray ray) throws IllegalArgumentException
    {
        Point closestPoint = ray.findClosestPoint(scene.geometries.findIntersections(ray));
        return closestPoint == null ? scene.background : calcColor(closestPoint);

    }
}
