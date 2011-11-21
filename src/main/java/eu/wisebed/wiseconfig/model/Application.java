package eu.wisebed.wiseconfig.model;

/**
 * This is a persistent class for the object Application that has the
 * properties of a testbed xml file. In the class there are
 * getter and setter methods for the properties.
 */
public final class Application {

    /**
     * Factory Class.
     */
    private String factoryclass;

    /**
     * Application Name.
     */
    private String name;

    /**
     * PortalApp instance.
     */
    private PortalApp portal;

    /**
     * WSN Device instance.
     */
    private WsnDevice wsnDev;

    /**
     * Returns FactoryClass string.
     *
     * @return factory class string.
     */
    public String getFactoryclass() {
        return factoryclass;
    }

    /**
     * Sets FactoryClass String.
     * @param factoryclass a string instance.
     */
    public void setFactoryclass(final String factoryclass) {
        this.factoryclass = factoryclass;
    }

    /**
     * Returns Application name.
     *
     * @return application name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets Application name.
     *
     * @param name a string name.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Returns WsnDevice instance.
     *
     * @return WsnDevice instance.
     */
    public WsnDevice getWsnDev() {
        return wsnDev;
    }

    /**
     * Sets WsnDevice instance.
     *
     * @param wsnDev a WsnDevice instance.
     */
    public void setWsnDev(final WsnDevice wsnDev) {
        this.wsnDev = wsnDev;
    }

    /**
     * Returns PortalApp instance.
     *
     * @return PortalApp instance.
     */
    public PortalApp getPortal() {
        return portal;
    }

    /**
     * Sets PortalApp instance.
     *
     * @param portal a PortalApp instance.
     */
    public void setPortal(final PortalApp portal) {
        this.portal = portal;
    }
}
