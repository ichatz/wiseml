package eu.wisebed.wiseml.model.setup;

/**
 * This is a persistant class for the object position that has the
 * properties of a position. In the class there are
 * getter and setter methods for the properties.
 */

public class Position {

    /**
     * point x in the position.
     */
    private float x;
    /**
     * point y in the position.
     */
    private float y;
    /**
     * point z in the position.
     */
    private float z;
    /**
     * angle phi in the position.
     */
    private int phi;
    /**
     * angle theta in the position.
     */
    private int theta;

    /**
     * this method returns the point x.
     *
     * @return x
     */
    public float getX() {
        return x;
    }

    /**
     * this method sets the point x.
     *
     * @param x
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * this method returns the point y.
     *
     * @return y
     */
    public float getY() {
        return y;
    }

    /**
     * this method sets the point y.
     *
     * @param y
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * this method returns the point z.
     *
     * @return z
     */
    public float getZ() {
        return z;
    }

    /**
     * this method sets the point z.
     *
     * @param z
     */
    public void setZ(float z) {
        this.z = z;
    }

    /**
     * this method returns the angle phi.
     *
     * @return phi
     */
    public int getPhi() {
        return phi;
    }

    /**
     * this method sets the angle phi.
     *
     * @param phi
     */
    public void setPhi(int phi) {
        this.phi = phi;
    }

    /**
     * this method returns the angle theta.
     *
     * @return theta
     */
    public int getTheta() {
        return theta;
    }

    /**
     * this method sets the angle theta.
     *
     * @param theta
     */
    public void setTheta(int theta) {
        this.theta = theta;
    }
}
