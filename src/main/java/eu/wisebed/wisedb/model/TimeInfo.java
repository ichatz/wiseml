package eu.wisebed.wisedb.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * This is a persistant class for the object timeinfo that has the
 * properties of a timeinfo. In the class there are
 * getter and setter methods for the properties.
 */
@Embeddable
public class TimeInfo implements Serializable {

    /**
     * Serial Version Unique ID.
     */
    private static final long serialVersionUID = 6916511608083049806L;

    /**
     * start time of the experiment.
     */
    @Column(name = "timeinfo_start")
    private String start;

    /**
     * end time of the experiment.
     */
    @Column(name = "timeinfo_end")
    private String end;

    /**
     * duration of the experiment.
     */
    @Column(name = "timeinfo_duration")
    private Integer duration;

    /**
     * the unit information.
     */
    @Column(name = "timeinfo_unit")
    private String unit;

    /**
     * this method returns the start time of the experiment.
     *
     * @return the start time.
     */

    public String getStart() {
        return start;
    }

    /**
     * this method sets the start time of the experiment.
     *
     * @param start the start time
     */
    public void setStart(final String start) {
        this.start = start;
    }

    /**
     * this method returns the end time of the experiment.
     *
     * @return the end time.
     */
    public String getEnd() {
        return end;
    }

    /**
     * this method sets the end time of the experiment.
     *
     * @param end the end time
     */
    public void setEnd(final String end) {
        this.end = end;
    }

    /**
     * this method returns the unit info.
     *
     * @return the unit info.
     */
    public String getUnit() {
        return unit;
    }

    /**
     * this method sets the unit info.
     *
     * @param unit the unit info.
     */
    public void setUnit(final String unit) {
        this.unit = unit;
    }

    /**
     * this method returns the duration of the experiment.
     *
     * @return the duration of the experiment.
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * this method sets the duration of the experiment.
     *
     * @param duration the experiment duration.
     */
    public void setDuration(final Integer duration) {
        this.duration = duration;
    }
}
