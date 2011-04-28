package eu.wisebed.wiseml.model.setup;

/**
 * This is a persistant class for the object origin that has the
 * properties of an origin. In the class there are
 * getter and setter methods for the properties.
 */

public class Origin {

    /**
     * the global position x in the local system.
     */
    private float x;

    /**
     * the global position y in the local system.
     */
    private float y;

    /**
     * the global position z in the local system.
     */
    private float z;

    /**
     * the angle phi of system rotation.
     */
    private float phi;

    /**
     * the angle theta of system rotation.
     */
    private float theta;

    /**
     * this method returns the position x.
     *
     * @return the x position.
     */
    public float getX() {
        return x;
    }

    /**
     * this method sets the position x.
     *
     * @param x the position x.
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * this method returns the position y.
     *
     * @return the y position.
     */
    public float getY() {
        return y;
    }

    /**
     * this method sets the position y.
     *
     * @param y the position y.
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * this method returns the position z.
     *
     * @return the z position.
     */
    public float getZ() {
        return z;
    }

    /**
     * this method sets the position z.
     *
     * @param z the position z.
     */
    public void setZ(float z) {
        this.z = z;
    }

    /**
     * this method returns the angle phi.
     *
     * @return the phi angle.
     */
    public float getPhi() {
        return phi;
    }

    /**
     * this method sets the angle phi.
     *
     * @param phi the phi angle.
     */
    public void setPhi(float phi) {
        this.phi = phi;
    }

    /**
     * this method sets the angle phi.
     *
     * @param phi the phi angle.
     */
    public void setPhi(int phi) {
        this.phi = phi;
    }

    /**
     * this method returns the angle theta.
     *
     * @return the theta angle.
     */
    public float getTheta() {
        return theta;
    }

    /**
     * this method sets the angle theta.
     *
     * @param theta the theta angle.
     */
    public void setTheta(float theta) {
        this.theta = theta;
    }

    /**
     * this method sets the angle theta.
     *
     * @param theta the theta angle.
     */
    public void setTheta(int theta) {
        this.theta = theta;
    }

}
