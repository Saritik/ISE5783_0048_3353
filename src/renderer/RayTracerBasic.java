package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class RayTracerBasic a class that represents a basic ray tracer it extends
 * RayTracerBase
 *
 * @author Sarit Tik 213230048 saritik16@gmail.com
 * @author Hadas Zehevi 325543353 h0548510062@gmail.com
 */
public class RayTracerBasic extends RayTracerBase {

	private static final int MAX_CALC_COLOR_LEVEL = 10;
	private static final Double3 MIN_CALC_COLOR_K = new Double3(0.001);

	/**
	 * constructor of RayTracerBasic
	 *
	 * @author sarit silverstone and rivki adler
	 * @param scene Scene value
	 */
	public RayTracerBasic(Scene scene) {
		super(scene);
	}

	/**
	 * Checks if a given point is unshaded by a light source.
	 *
	 * @param geoPoint The geometric point to be checked.
	 * @param light    The light source.
	 * @param toLight  The vector representing the direction from the point to the
	 *                 light source.
	 * @param normal   The surface normal at the point.
	 * @return {@code true} if the point is unshaded by the light source,
	 *         {@code false} otherwise.
	 */
	private boolean unshaded(GeoPoint geoPoint, LightSource light, Vector toLight, Vector normal) {
		Vector lightDirection = toLight.scale(-1); // from point to light source

		Ray lightRay = new Ray(geoPoint.point, lightDirection, normal);

		List<GeoPoint> intersections = scene.geometries.findGeoIntersectionsHelper(lightRay,
				light.getDistance(geoPoint.point));
		if (intersections == null) {
			return true;
		} else {
			for (GeoPoint g : intersections) {
				if (g.geometry.getMaterial().kT == Double3.ZERO) {
					return false;
				}
			}
			return true;
		}
	}

	private Ray constructRefractedRay(GeoPoint gp, Vector v, Vector normal) {
		return new Ray(gp.point, v);
	}

	/**
	 * A function that find the most closet point to the ray
	 * 
	 * @param ray Ray value
	 * @return the closet point
	 */
	private GeoPoint findClosestIntersection(Ray ray) {
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
		if (intersections == null)
			return null;
		return ray.findClosestGeoPoint(intersections);
	}

	/**
	 * a function that calculates thr diffusive light
	 * 
	 * @param material Material value
	 * @param nl       double value
	 * @return Double3 value
	 */
	private Double3 calcDiffusive(Material material, double nl) {
		return material.kD.scale(Math.abs(nl));
	}

	/**
	 * a function that calculates the specular light
	 * 
	 * @param material Material value
	 * @param n        Vector value
	 * @param l        Vector value
	 * @param nl       double value
	 * @param vector   Vector value
	 * @return Double3 value
	 */
	private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector vector) {
		return material.kS
				.scale(Math.pow(Math.max(0, -vector.dotProduct(l.subtract(n.scale(2 * nl)))), material.nShininess));
	}

	/**
	 * a function that calculates the local effects of a point
	 * 
	 * @param geoPoint GeoPoint value
	 * @param ray      Ray value
	 * @return Color value
	 */
	private Color calcLocalEffects(GeoPoint geoPoint, Ray ray) {
		// the color of the point
		Color color = geoPoint.geometry.getEmission();
		// the direction of the ray
		Vector vector = ray.getDir().normalize();
		// the normal of the point
		Vector n = geoPoint.geometry.getNormal(geoPoint.point);
		// the dot product of the normal and the direction
		double nv = alignZero(n.dotProduct(vector));

		// if the dot product is zero, the point is not visible
		if (isZero(nv))
			return color;
		Material material = geoPoint.geometry.getMaterial();

		// for each light source in the scene
		for (LightSource lightSource : scene.lights) {
			// the direction of the light source
			Vector l = lightSource.getL(geoPoint.point).normalize();
			// the dot product of the normal and the direction of the light source
			double nl = alignZero(n.dotProduct(l));
			// if the dot product is zero, the point is not visible
			if (nl * nv > 0) {
				if (unshaded(geoPoint, lightSource, l, n)) {
					// the intensity of the light source
					Color lightIntensity = lightSource.getIntensity(geoPoint.point);
					color = color.add(lightIntensity.scale(calcDiffusive(material, nl)),
							lightIntensity.scale(calcSpecular(material, n, l, nl, vector)));
				}
			}
		}
		return color;
	}

	/**
	 * A function that calculate the globals effects of the color
	 * 
	 * @param gp    GeoPoint value
	 * @param ray   Ray value
	 * @param level int value
	 * @param k     Double3 value
	 * @return Color
	 */
	private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
		Vector v = ray.getDir();
		Vector n = gp.geometry.getNormal(gp.point);
		Material material = gp.geometry.getMaterial();
		return calcGlobalEffect(constructRefractedRay(gp, v, n), level, k, material.kR)
				.add(calcGlobalEffect(constructRefractedRay(gp, v, n), level, k, material.kT));
	}

	private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
		Double3 kkx = k.product(kx);
		if (kkx.lowerThan(MIN_CALC_COLOR_K))
			return Color.BLACK;
		GeoPoint gp = findClosestIntersection(ray);
		if (gp == null)
			return scene.background.scale(kx);
		return Util.isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDir())) ? Color.BLACK
				: calcColor(gp, ray, level - 1, kkx).scale(kx);
	}

	private Color calcColor(GeoPoint geoPoint, Ray ray) {
		return scene.ambientLight.getIntensity().add(calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, new Double3(1)));
	}

	private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
		Color color = calcLocalEffects(gp, ray);
		return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));
	}

	@Override
	public Color traceRay(Ray ray) throws IllegalArgumentException {
		GeoPoint closestPoint = findClosestIntersection(ray);
		if (closestPoint == null) {
			return this.scene.background;
		}
		return calcColor(closestPoint, ray);
	}
}
