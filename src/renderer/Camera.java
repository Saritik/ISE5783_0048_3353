package renderer;

import primitives.*;

import static primitives.Util.isZero;

/**
 * Camera class represents a camera in the 3D space
 */
public class Camera {
    private Point p0;
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;
    private double height;
    private double width;
    private double distance;


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
     * Construct a ray through a pixel in the view plane
     *
     * @param nX number of pixels in the x axis
     * @param nY number of pixels in the y axis
     * @param j  pixel index in the x axis
     * @param i  pixel index in the y axis
     * @return the ray through the pixel
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        //view plane center Point
        Point Pc = p0.add(vTo.scale(distance));

        //pixels ratios
        double Rx = width / nX;
        double Ry = height / nY;

        //Pij point[i,j] in view-plane coordinates
        Point Pij = Pc;

        //delta values for moving on the view=plane
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
