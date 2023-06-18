package renderer;

import primitives.*;

import java.util.MissingResourceException;

import static primitives.Util.isZero;

/**
 * Camera class represents a camera in the 3D space
 * 
 * @author Sarit Tik 213230048 saritik16@gmail.com
 * @author Hadas Zehevi 325543353 h0548510062@gmail.com
 */
public class Camera {
	// camera position
	private Point p0;
	private Vector vTo;
	private Vector vUp;
	private Vector vRight;
	private double height;
	private double width;
	private double distance;
	private ImageWriter imageWriter;
	private RayTracerBase rayTracer;

	/**
	 * Camera constructor
	 *
	 * @param p  camera position
	 * @param to vector to the view plane
	 * @param up vector to the up direction
	 */
	public Camera(Point p, Vector to, Vector up) {
		p0 = p;
		// check if the vectors are verticals
		if (!isZero(up.dotProduct(to))) {
			throw new IllegalArgumentException("The vectors are not verticals");
		}
		vTo = to.normalize();
		vUp = up.normalize();
		// calculate the right vector
		vRight = vTo.crossProduct(vUp);
	}

	/**
	 * Getters
	 *
	 * @return the requested value
	 */

	public Point getP0() {
		return p0;
	}

	public Vector getvTo() {
		return vTo;
	}

	public Vector getvUp() {
		return vUp;
	}

	public Vector getvRight() {
		return vRight;
	}

	public double getHeight() {
		return height;
	}

	public double getWidth() {
		return width;
	}

	public double getDistance() {
		return distance;
	}

	/**
	 * Set the view plane size
	 *
	 * @param w width
	 * @param h height
	 * @return the camera itself
	 */
	public Camera setVPSize(double w, double h) {
		width = w;
		height = h;
		return this;
	}

	/**
	 * Set the distance from the camera to the view plane
	 *
	 * @param d distance
	 * @return the camera itself
	 */
	public Camera setVPDistance(double d) {
		distance = d;
		return this;
	}

	/**
	 * A function that sets the image writer
	 * 
	 * @param image ImageWriter value
	 * @return the camera itself
	 */
	public Camera setImageWriter(ImageWriter image) {
		imageWriter = image;
		return this;
	}

	/**
	 * A function that sets the ray tracer
	 * 
	 * @param rayBase RayTracerBase value
	 * @return the camera itself
	 */
	public Camera setRayTracer(RayTracerBase rayBase) {
		rayTracer = rayBase;
		return this;
	}

	/**
	 * A function that writes the image to the file
	 */
	public void writeToImage() {
		// check if the fields have been initialized
		if (p0 == null && vRight == null && vTo == null && vUp == null && imageWriter == null && rayTracer == null
				&& height < 0 && width < 0 && distance < 0) {
			throw new MissingResourceException("One or more necessary fields are null.", "Camera", "");
		}
		imageWriter.writeToImage();
	}

	/**
	 * A function that creates a grid of lines
	 * 
	 * @author sarit silverstone and rivki adler
	 * @param interval int value
	 * @param color    Color value
	 */
	public Camera printGrid(int interval, Color color) {
		if (imageWriter == null)
			throw new MissingResourceException("this function must have values in all the fileds", "ImageWriter", "i");

		for (int i = 0; i < imageWriter.getNx(); i++) {
			for (int j = 0; j < imageWriter.getNy(); j++) {
				if (i % interval == 0 || j % interval == 0)
					imageWriter.writePixel(i, j, color);
			}
		}
		return this;
	}

	/**
	 * A function that construct a camera through the view plane
	 * 
	 * @return the camera itself
	 */
	public Camera renderImage() {
		try {
			// check if the fields have been initialized
			if (imageWriter == null) {
				throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
			}
			// check if the fields have been initialized
			if (rayTracer == null) {
				throw new MissingResourceException("missing resource", RayTracerBase.class.getName(), "");
			}

			int nX = imageWriter.getNx();
			int nY = imageWriter.getNy();
			for (int i = 0; i < nY; i++) {
				for (int j = 0; j < nX; j++) {
					castRay(nX, nY, i, j);
				}
			}
//            }
		} catch (MissingResourceException e) {
			throw new UnsupportedOperationException("Not implemented yet" + e.getClassName());
		}
		return this;
	}

	/**
	 * Cast ray from camera in order to color a pixel
	 *
	 * @param nX   - resolution on X axis (number of pixels in row)
	 * @param nY   - resolution on Y axis (number of pixels in column)
	 * @param icol - pixel's column number (pixel index in row)
	 * @param jrow - pixel's row number (pixel index in column)
	 */
	private void castRay(int nX, int nY, int icol, int jrow) {
		Ray ray = constructRay(nX, nY, jrow, icol);
		Color pixelColor = rayTracer.traceRay(ray);
		imageWriter.writePixel(jrow, icol, pixelColor);
	}

	/**
	 * Construct a ray through a pixel in the view plane
	 *
	 * @param nX number of pixels in the x axis
	 * @param nY number of pixels in the y axis
	 * @param j  pixel index in the x axis
	 * @param i  pixel index in the y axis
	 * @return the ray through the pixel
	 */
	public Ray constructRay(int nX, int nY, int j, int i) {
		// view plane center Point
		Point Pc = p0.add(vTo.scale(distance));

		// pixels ratios
		double Rx = width / nX;
		double Ry = height / nY;

		// Pij point[i,j] in view-plane coordinates
		Point Pij = Pc;

		// delta values for moving on the view=plane
		double Xj = (j - (nX - 1) / 2d) * Rx;
		double Yi = -(i - (nY - 1) / 2d) * Ry;

		if (!isZero(Xj)) {
			Pij = Pij.add(vRight.scale(Xj));
		}
		if (!isZero(Yi)) {
			Pij = Pij.add(vUp.scale(Yi));
		}

		// vector from camera's eye in the direction of point(i,j) in the viewplane
		Vector Vij = Pij.subtract(p0);

		return new Ray(p0, Vij);
	}
}
