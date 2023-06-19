package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
import java.util.List;
import static primitives.Util.*;

public class RayTracerBasic extends RayTracerBase {

    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

    /**
     * constructor
     * @param scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        //get the closet intersection point
        var intersection = findClosestIntersection(ray);

        //no intersection points
        if (intersection == null) return scene.background;

        //return the color of the point
        return calcColor(intersection,ray);
    }

    /**
     * get point in scene and calculate its color
     * @param geoPoint point in scene
     * @param ray ray from camera to the point
     * @return color of the point
     */
    private Color calcColor( GeoPoint geoPoint,Ray ray) {

        return scene.ambientLight.getIntensity()
                .add(calcColor(geoPoint,ray,MAX_CALC_COLOR_LEVEL,new Double3(1)));
    }

    /**
     * get point in scene and calculate its color
     * @param gp point in scene
     * @param ray ray from camera to the point
     * @param level level of recursion
     * @param k transparency coefficient
     * @return color of the point
     */
    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(gp, ray,k).add(gp.geometry.getEmission());

        return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));
    }

    /**
     * Calculate the effect of different light sources on point in scene
     * according to the "Phong model"
     * @param gp point on geometry in  scene
     * @param ray ray from camera to the gp
     * @return color of the point
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
		// Get the direction of the incoming ray
		Vector v = ray.getDir();

		// Calculate the surface normal at the intersection point
		Vector n = gp.geometry.getNormal(gp.point);

		// Calculate the dot product between the normal and the ray direction
		double nv = alignZero(n.dotProduct(v));

		// If the dot product is zero, the ray is parallel to the surface, so return
		// black
		if (Util.isZero(nv))
			return Color.BLACK;

		// Get the material properties of the intersected geometry
		int nShininess = gp.geometry.getMaterial().nShininess; // Shininess coefficient
		Double3 kd = gp.geometry.getMaterial().kD; // Diffuse reflection coefficient
		Double3 ks = gp.geometry.getMaterial().kS; // Specular reflection coefficient

		// Initialize the color as black
		Color color = Color.BLACK;

		// Iterate over all light sources in the scene
		for (LightSource lightSource : scene.lights) {
			// Get the direction to the light source at the intersection point
			Vector l = lightSource.getL(gp.point);

			// Calculate the dot product between the normal and the light direction
			double nl = alignZero(n.dotProduct(l));

			// Check if the signs of nl and nv are the same (sign(nl) == sign(nv))
			if (nl * nv > 0) {
				// Calculate the transparency coefficient for the current light source
				Double3 ktr = transparency(gp, lightSource, l, n).product(k);

				// Check if the combined transparency coefficient is above a minimum threshold
				if (!ktr.lowerThan(MIN_CALC_COLOR_K)) {
					// Check if the intersection point is unshaded by the light source
					if (unshaded(gp, lightSource, l, n)) {
						// Calculate the light intensity at the intersection point
						Color lightIntensity = lightSource.getIntensity(gp.point).scale(ktr);

						// Add the diffuse and specular components to the color
						color = color.add(calcDiffusive(kd, nl, lightIntensity),
								calcSpecular(ks, l, n, nl, v, nShininess, lightIntensity));
					}
				}
			}
		}
		// Return the calculated color
		return color;
    }


    /**
     * Calculate global effect reflected and refracted
     * @param gp point on geometry in  scene
     * @param inRay ray from camera to the gp
     * @param level level of recursion
     * @param k transparency coefficient
     * @return color of the point
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray inRay, int level, Double3 k) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point);

        Material gpMaterial = gp.geometry.getMaterial();
        Double3 kr = gpMaterial.kR, kkr = kr.product(k);

        // Reflection calculation
        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) {
            Ray reflectedRay = constructReflectedRay(gp.point, inRay, n);
            var rays = reflectedRay.generateBeam(n, gpMaterial.blurGlassRadius, gpMaterial.blurGlassDistance, gpMaterial.numOfRays);
            color = color.add(calcAvarageColor(rays, level - 1, kkr).scale(kr));
        }

        Double3 kt = gpMaterial.kT, kkt = kt.product(k);

        // Refraction calculation
        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {
            Ray refractedRay = constructRefractedRay(gp.point, inRay, n);
            var rays = refractedRay.generateBeam(n, gpMaterial.blurGlassRadius, gpMaterial.blurGlassDistance, gpMaterial.numOfRays);
            color = color.add(calcAvarageColor(rays, level - 1, kkt).scale(kt));
        }

        return color;
    }

    /**
     * Calculate Diffusive component of light reflection.
     * @param kd diffuse coefficient
     * @param nl dot product between normal and light direction
     * @param ip light intensity
     * @return color of the point
     */
    private Color calcDiffusive(Double3 kd, double nl, Color ip) {
        return ip.scale(kd.scale(Math.abs(nl) ));
    }

    /**
     *  Calculate Specular component of light reflection
     * @param ks specular coefficient
     * @param l direction to light source
     * @param n normal to geometry
     * @param nl dot product between normal and light direction
     * @param v direction to camera
     * @param nShininess shininess coefficient
     * @param ip light intensity
     * @return color of the point
     */
    private  Color calcSpecular(Double3 ks, Vector l, Vector n, double nl, Vector v, int nShininess,  Color ip) {

        Vector R = l.add(n.scale(-2 * nl)); // nl must not be zero!
        double minusVR = -alignZero(R.dotProduct(v));
        if (minusVR <= 0) {
            return new primitives.Color(Color.BLACK.getColor()); // view from direction opposite to r vector
        }
        return ip.scale(ks.scale( Math.pow(minusVR, nShininess)));
    }

    /**
     *  get light and gp and check if there is element between them
     * @param gp point on geometry in  scene
     * @param light light source
     * @param l direction to light source
     * @param n normal to geometry
     * @return true if there is no element between them
     */
    private boolean unshaded(GeoPoint gp, LightSource light, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1); // from point to light source

        Ray lightRay = new Ray(gp.point, lightDirection,n);

        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point));
        return intersections==null ||!intersections.stream().anyMatch (g -> g.geometry.getMaterial().kT== Double3.ZERO);
    }

    /**
     * get light and gp and move ao all the objects between them
     * and calculate the transparency
     * @param gp point on geometry in  scene
     * @param light light source
     * @param l direction to light source
     * @param n normal to geometry
     * @return transparency coefficient
     */
    private Double3 transparency(GeoPoint gp, LightSource light, Vector l, Vector n) {

        Vector lightDirection = l.scale(-1); // from point to light source

        Ray lightRay = new Ray(gp.point, lightDirection,n);

        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point));
        Double3 ktr = new Double3(1d);
        if (intersections == null) return ktr;

        for(GeoPoint p :intersections)
            ktr = ktr.product(p.geometry.getMaterial().kT);
        return ktr;
    }

    /**
     * Calculate refracted ray
     * @param pointGeo point on geometry in  scene
     * @param inRay ray from camera to the gp
     * @param n normal to geometry
     * @return refracted ray
     */
    private Ray constructRefractedRay(Point pointGeo, Ray inRay, Vector n) {
        return new Ray(pointGeo, inRay.getDir(), n);
    }

    /**
     * Calculate Reflected ray
     * @param pointGeo point on geometry in  scene
     * @param inRay ray from camera to the gp
     * @param n normal to geometry
     * @return reflected ray
     */
    private Ray constructReflectedRay(Point pointGeo, Ray inRay, Vector n) {
        //𝒓=𝒗 −𝟐∙(𝒗∙𝒏)∙𝒏
        Vector v = inRay.getDir();
        double vn = v.dotProduct(n);

        if (vn == 0) {
            return null;
        }

        Vector r = v.subtract(n.scale(2 * vn));
        return new Ray(pointGeo, r, n);
    }

    /**
     * get ray and return the closet intersection geoPoint
     * @param ray ray from camera to the gp
     * @return the closet intersection geoPoint
     */
    private GeoPoint findClosestIntersection(Ray ray){
        return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections( ray));
    }

    /**
     * Calculate the average color of the list of rays
     * @param rays list of rays
     * @param level recursion level
     * @param kkt transparency factor
     * @return the average color of the list of rays
     */
    Color calcAvarageColor(List<Ray> rays, int level, Double3 kkt) {
        Color color = Color.BLACK;
        // Iterate over each ray in the list of rays
        for (Ray ray : rays) {
            // Find the closest intersection point for the current ray
            GeoPoint intersection = findClosestIntersection(ray);
            // Check if there is an intersection
            if (intersection != null) {
                // Calculate the color at the intersection point using the calcColor method
                // Consider the current recursion level and transparency factor (kkt)
                // Add the calculated color to the accumulated color
                color = color.add(calcColor(intersection, ray, level - 1, kkt));
            }
        }
        // Reduce the accumulated color by dividing it by the number of rays to get the average color
        return color.reduce(rays.size());
    }
}
