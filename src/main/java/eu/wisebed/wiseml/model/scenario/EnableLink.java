package eu.wisebed.wiseml.model.scenario;

/**
 * This is a persistant class for the object enableLink that has the
 * properties of an enableLink. In the class there are
 * getter and setter methods for the properties.
 */
public class EnableLink {

    /**
     * source for object enableLink.
     */
    private String source;

    /**
     * target for object enableLink.
     */
    private String target;

    /**
     * this method returns the enableLink source.
     *
     * @return source
     */
    public String getSource() {
        return source;
    }

    /**
     * this method sets the source for the enableLink.
     *
     * @param source String source
     */
    public void setSource(final String source) {
        this.source = source;
    }

    /**
     * this method returns the enableLink target.
     *
     * @return target
     */
    public String getTarget() {
        return target;
    }

    /**
     * this method sets the target for the enableLink.
     *
     * @param target String target
     */
    public void setTarget(final String target) {
        this.target = target;
    }
}
