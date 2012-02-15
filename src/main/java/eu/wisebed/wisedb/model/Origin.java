package eu.wisebed.wisedb.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * This is a persistant class for the object origin that has the
 * properties of an origin. In the class there are
 * getter and setter methods for the properties.
 */
@Embeddable
public class Origin implements Serializable {

    /**
     * Serial Version Unique ID.
     */
    private static final long serialVersionUID = -6275211639454458482L;

    /**
     * the global position x in the local system.
     */
    @Column(name = "position_x")
    private Float x;

    /**
     * the global position y in the local system.
     */
    @Column(name = "position_y")
    private Float y;

    /**
     * the global position z in the local system.
     */
    @Column(name = "position_z")
    private Float z;

    /**
     * the angle phi of system rotation.
     */
    @Column(name = "position_phi")
    private Float phi;

    /**
     * the angle theta of system rotation.
     */
    @Column(name = "position_theta")
    private Float theta;

    /**
     * this method returns the position x.
     *
     * @return the x position.
     */
    public Float getX() {
        return x;
    }

    /**
     * this method sets the position x.
     *
     * @param x the position x.
     */
    public void setX(final Float x) {
        this.x = x;
    }

    /**
     * this method returns the position y.
     *
     * @return the y position.
     */
    public Float getY() {
        return y;
    }

    /**
     * this method sets the position y.
     *
     * @param y the position y.
     */
    public void setY(final Float y) {
        this.y = y;
    }

    /**
     * this method returns the position z.
     *
     * @return the z position.
     */
    public Float getZ() {
        return z;
    }

    /**
     * this method sets the position z.
     *
     * @param z the position z.
     */
    public void setZ(final Float z) {
        this.z = z;
    }

    /**
     * this method returns the angle phi.
     *
     * @return the phi angle.
     */
    public Float getPhi() {
        return phi;
    }

    /**
     * this method sets the angle phi.
     *
     * @param phi the phi angle.
     */
    public void setPhi(final Float phi) {
        this.phi = phi;
    }

    /**
     * this method returns the angle theta.
     *
     * @return the theta angle.
     */
    public Float getTheta() {
        return theta;
    }

    /**
     * this method sets the angle theta.
     *
     * @param theta the theta angle.
     */
    public void setTheta(final Float theta) {
        this.theta = theta;
    }

    @Override
    public String toString() {
        return "Origin{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", phi=" + phi +
                ", theta=" + theta +
                '}';
    }
}
