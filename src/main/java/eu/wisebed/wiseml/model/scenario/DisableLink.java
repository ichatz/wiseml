package eu.wisebed.wiseml.model.scenario;

/**
 * This is a persistant class for the object disableLink that has the
 * properties of a disableLink. In the class there are
 * getter and setter methods for the properties.
 */

public class DisableLink {

    /**
     * source for object disableLink.
     */
    private String source;

    /**
     * target for object disableLink.
     */
    private String target;

    /**
     * this method returns the disableLink source.
     *
     * @return source
     */
    public String getSource() {
        return source;
    }

    /**
     * this method sets the source for the disableLink.
     *
     * @param source String source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * this method returns the disableLink target.
     *
     * @return target
     */
    public String getTarget() {
        return target;
    }

    /**
     * this method sets the target for the disableLink.
     *
     * @param target String target
     */
    public void setTarget(String target) {
        this.target = target;
    }
}
