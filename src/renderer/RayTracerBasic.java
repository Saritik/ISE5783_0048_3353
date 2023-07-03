package renderer;

import lighting.DirectionalLight;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
import java.util.List;
import static primitives.Util.*;

/**
 * A basic implementation of a ray tracer.
 */
public class RayTracerBasic extends RayTracerBase {

	private static final int MAX_CALC_COLOR_LEVEL = 10;
	private static final double MIN_CALC_COLOR_K = 0.001;

	/**
	 * Constructor for the RayTracerBasic class.
	 *
	 * @param scene The scene to be rendered.
	 */
	public RayTracerBasic(Scene scene) {
		super(scene);
	}

	@Override
	public Color traceRay(Ray ray) {
		// get the closest intersection point
		var intersection = findClosestIntersection(ray);

		// no intersection points
		if (intersection == null)
			return scene.background;

		// return the color of the point
		return calcColor(intersection, ray);
	}

	private Color calcColor(GeoPoint geoPoint, Ray ray) {
		return scene.ambientLight.getIntensity().add(calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, new Double3(1)));
	}

	private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
		Color color = calcLocalEffects(gp, ray, k).add(gp.geometry.getEmission());

		return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));
	}

	/**
	 * Calculates the color at a specific intersection point based on local lighting effects.
	 *
	 * @param gp The intersection point.
	 * @param ray The ray.
	 * @param k The coefficient.
	 * @return The color at the intersection point.
	 */
	private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
		// Get the direction of the incoming ray
		Vector v = ray.getDir();

		// Calculate the surface normal at the intersection point
		Vector n = gp.geometry.getNormal(gp.point);

		// Calculate the dot product between the normal and the ray direction
		double nv = alignZero(n.dotProduct(v));

		// If the dot product is zero, the ray is parallel to the surface, so return black
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
					// Calculate the light intensity at the intersection point
					Color lightIntensity = lightSource.getIntensity(gp.point).scale(ktr);

					// Add the diffuse and specular components to the color
					color = color.add(calcDiffusive(kd, nl, lightIntensity),
							calcSpecular(ks, l, n, nl, v, nShininess, lightIntensity));
				}
			}
		}
		// Return the calculated color
		return color;
	}

	/**
	 * Calculates the global lighting effects.
	 *
	 * @param gp The intersection point.
	 * @param inRay The incoming ray.
	 * @param level The recursion level.
	 * @param k The coefficient.
	 * @return The color after applying global lighting effects.
	 */
	private Color calcGlobalEffects(GeoPoint gp, Ray inRay, int level, Double3 k) {
		Color color = Color.BLACK;
		Vector n = gp.geometry.getNormal(gp.point);

		Material gpMaterial = gp.geometry.getMaterial();
		Double3 kr = gpMaterial.kR, kkr = kr.product(k);

		if (!kkr.lowerThan(MIN_CALC_COLOR_K)) {
			Ray reflectedRay = constructReflectedRay(gp.point, inRay, n);

			var rays = reflectedRay.generateBeam(n, gpMaterial.blurGlassRadius, gpMaterial.blurGlassDistance,
					gpMaterial.numOfRays);
			color = color.add(calcAverageColor(rays, level - 1, kkr).scale(kr));

		}

		Double3 kt = gpMaterial.kT, kkt = kt.product(k);
		if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {
			Ray refractedRay = constructRefractedRay(gp.point, inRay, n);

			var rays = refractedRay.generateBeam(n, gpMaterial.blurGlassRadius, gpMaterial.blurGlassDistance,
					gpMaterial.numOfRays);
			color = color.add(calcAverageColor(rays, level - 1, kkt).scale(kt));
		}
		return color;
	}

	private Color calcDiffusive(Double3 kd, double nl, Color ip) {
		return ip.scale(kd.scale(Math.abs(nl)));
	}

	private Color calcSpecular(Double3 ks, Vector l, Vector n, double nl, Vector v, int nShininess, Color ip) {

		Vector R = l.add(n.scale(-2 * nl)); // nl must not be zero!
		double minusVR = -alignZero(R.dotProduct(v));
		if (minusVR <= 0) {
			return new primitives.Color(Color.BLACK.getColor()); // view from direction opposite to r vector
		}
		return ip.scale(ks.scale(Math.pow(minusVR, nShininess)));
	}

	private boolean unshaded(GeoPoint gp, LightSource light, Vector l, Vector n) {
		Vector lightDirection = l.scale(-1); // from point to light source

		Ray lightRay = new Ray(gp.point, lightDirection, n);

		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point));
		return intersections == null
				|| !intersections.stream().anyMatch(g -> g.geometry.getMaterial().kT == Double3.ZERO);
	}

	/**
	 * Calculate the transparency coefficient for the given geometry and light source.
	 *
	 * @param gp The intersection point.
	 * @param light The light source.
	 * @param l The direction to the light source.
	 * @param n The normal at the intersection point.
	 * @return The transparency coefficient for the given geometry and light source.
	 */
	private Double3 transparency(GeoPoint gp, LightSource light, Vector l, Vector n) {

		Vector lightDirection = l.scale(-1); // from point to light source

		Ray lightRay = new Ray(gp.point, lightDirection, n);

		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point));
		Double3 ktr = new Double3(1d);
		if (intersections == null)
			return ktr;

		for (GeoPoint p : intersections)
			ktr = ktr.product(p.geometry.getMaterial().kT);
		return ktr;
	}

	/**
	 * Constructs the refracted ray for the given intersection point, incoming ray, and normal.
	 *
	 * @param pointGeo The intersection point.
	 * @param inRay The incoming ray.
	 * @param n The normal at the intersection point.
	 * @return The refracted ray.
	 */
	private Ray constructRefractedRay(Point pointGeo, Ray inRay, Vector n) {
		return new Ray(pointGeo, inRay.getDir(), n);
	}

	/**
	 * Constructs the reflected ray for the given intersection point, incoming ray, and normal.
	 *
	 * @param pointGeo The intersection point.
	 * @param inRay The incoming ray.
	 * @param n The normal at the intersection point.
	 * @return The reflected ray.
	 */
	private Ray constructReflectedRay(Point pointGeo, Ray inRay, Vector n) {
		Vector v = inRay.getDir();
		double vn = v.dotProduct(n);

		if (vn == 0) {
			return null;
		}

		Vector r = v.subtract(n.scale(2 * vn));
		return new Ray(pointGeo, r, n);
	}

	/**
	 * Finds the closest intersection point between the given ray and the scene's geometries.
	 *
	 * @param ray The ray to intersect with the geometries.
	 * @return The closest intersection point, or null if no intersection is found.
	 */
	private GeoPoint findClosestIntersection(Ray ray) {
		return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
	}

	/**
	 * Calculates the average color for a list of rays.
	 *
	 * @param rays The list of rays to calculate the average color from.
	 * @param level The recursion level.
	 * @param kkt The coefficient.
	 * @return The average color.
	 */
	Color calcAverageColor(List<Ray> rays, int level, Double3 kkt) {

		Color color = Color.BLACK;
		for (Ray ray : rays) {

			GeoPoint intersection = findClosestIntersection(ray);

			if (intersection != null)
				color = color.add(calcColor(intersection, ray, level - 1, kkt));

		}

		return color.reduce(rays.size());
	}
}