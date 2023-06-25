package geometries;

import primitives.BoundingBox;
import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * test Geometries class
 * 
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
	public Geometries() {
		Geometries = new LinkedList<>();
		Geometries.clear();
	}

	/**
	 * constructor that initialize the list with the given list
	 * 
	 * @param geometries list of geometries
	 */
	public Geometries(Intersectable... geometries) {
		Geometries = new LinkedList<>();
		for (Intersectable geometry : geometries) {
			Geometries.add(geometry);
		}
	}

	/**
	 * add geometries to list of geometries
	 * 
	 * @param geometries list of geometries
	 */
	public void add(Intersectable... geometries) {
		this.Geometries.addAll(List.of(geometries));

		// build a bounding box
		// search in all new geometries
		// for the min and max X,Y,Z (if they bigger then the current x,y,z bounding
		// box)
		double xMax = Double.NEGATIVE_INFINITY;
		double xMin = Double.MAX_VALUE;

		double yMax = Double.NEGATIVE_INFINITY;
		double yMin = Double.MAX_VALUE;

		double zMax = Double.NEGATIVE_INFINITY;
		double zMin = Double.MAX_VALUE;

		for (Intersectable g : this.Geometries) {

			// check x
			if (g.boundingBox.getxMin() < xMin)
				xMin = g.boundingBox.getxMin();

			if (g.boundingBox.getxMax() > xMax)
				xMax = g.boundingBox.getxMax();

			// check y
			if (g.boundingBox.getyMin() < yMin)
				yMin = g.boundingBox.getyMin();

			if (g.boundingBox.getyMax() > yMax)
				yMax = g.boundingBox.getyMax();

			// check z
			if (g.boundingBox.getzMin() < zMin)
				zMin = g.boundingBox.getzMin();

			if (g.boundingBox.getzMax() > zMax)
				zMax = g.boundingBox.getzMax();
		}
		boundingBox = new BoundingBox(new Point(xMin, yMin, zMin), new Point(xMax, yMax, zMax));
	}

	public Geometries setBoundingBox(Point p1, Point p2) {
		this.boundingBox = new BoundingBox(p1, p2);
		return this;
	}

	/**
	 * find intersections of ray with the geometries
	 * 
	 * @param ray ray to find intersections with
	 * @return list of intersections points
	 */
	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {

		if (ray==null ||( this.boundingBox != null && !this.boundingBox.intersectionBox(ray)))
			return null;

		List<GeoPoint> points = null;
		for (var geometry : Geometries) {
			var intersections = geometry.findGeoIntersections(ray, maxDistance);
			// if there are intersections add them to the list
			if (intersections != null) {
				if (points == null)
					points = new ArrayList<GeoPoint>(intersections);
				else
					points.addAll(intersections);
			}
		}
		// else return the list of intersections
		return points;
	}
}
