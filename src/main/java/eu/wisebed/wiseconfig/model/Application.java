package eu.wisebed.wiseconfig.model;


public class Application {


    private String factoryclass;
    private String name;

    private PortalApp portal;
    private WsnDevice wsnDev;

    public String getFactoryclass() {
        return factoryclass;
    }

    public void setFactoryclass(final String factoryclass) {
        this.factoryclass = factoryclass;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public WsnDevice getWsnDev() {
        return wsnDev;
    }

    public void setWsnDev(final WsnDevice wsnDev) {
        this.wsnDev = wsnDev;
    }

    public PortalApp getPortal() {
        return portal;
    }

    public void setPortal(final PortalApp portal) {
        this.portal = portal;
    }
}