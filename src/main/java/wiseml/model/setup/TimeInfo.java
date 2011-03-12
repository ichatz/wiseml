package wiseml.model.setup;

/**
 * This is a persistant class for the object timeinfo that has the
 * properties of a timeinfo. In the class there are
 * getter and setter methods for the properties.
 */

public class TimeInfo {

    /**
     * start time of the experiment.
     */
    private String start;

    /**
     * end time of the experiment.
     */
    private String end;

    /**
     * duration of the experiment.
     */
    private int duration;

    /**
     * the unit information.
     */
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
    public void setStart(String start) {
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
    public void setEnd(String end) {
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
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * this method returns the duration of the experiment.
     *
     * @return the duration of the experiment.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * this method sets the duration of the experiment.
     *
     * @param duration the experiment duration.
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }
}
