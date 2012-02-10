package eu.wisebed.wisedb.model;

import java.io.Serializable;

/**
 * This is a persistant class for the object position that has the
 * properties of a position. In the class there are
 * getter and setter methods for the properties.
 */
public class Position implements Serializable {

    /**
     * Serial Version Unique ID.
     */
    private static final long serialVersionUID = -3009012883407990409L;

    /**
     * point x in the position.
     */
    private Float x;

    /**
     * point y in the position.
     */
    private Float y;

    /**
     * point z in the position.
     */
    private Float z;

    /**
     * angle phi in the position.
     */
    private Float phi;

    /**
     * angle theta in the position.
     */
    private Float theta;

    /**
     * this method returns the point x.
     *
     * @return x
     */
    public Float getX() {
        return x;
    }

    /**
     * this method sets the point x.
     *
     * @param x x point
     */
    public void setX(final Float x) {
        this.x = x;
    }

    /**
     * this method returns the point y.
     *
     * @return y
     */
    public Float getY() {
        return y;
    }

    /**
     * this method sets the point y.
     *
     * @param y y point
     */
    public void setY(final Float y) {
        this.y = y;
    }

    /**
     * this method returns the point z.
     *
     * @return z
     */
    public Float getZ() {
        return z;
    }

    /**
     * this method sets the point z.
     *
     * @param z z point
     */
    public void setZ(final Float z) {
        this.z = z;
    }

    /**
     * this method returns the angle phi.
     *
     * @return phi
     */
    public Float getPhi() {
        return phi;
    }

    /**
     * this method sets the angle phi.
     *
     * @param phi phi angle.
     */
    public void setPhi(final Float phi) {
        this.phi = phi;
    }

    /**
     * this method returns the angle theta.
     *
     * @return theta
     */
    public Float getTheta() {
        return theta;
    }

    /**
     * this method sets the angle theta.
     *
     * @param theta theta angle
     */
    public void setTheta(final Float theta) {
        this.theta = theta;
    }
}
