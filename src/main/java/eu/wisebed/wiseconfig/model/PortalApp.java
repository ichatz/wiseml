package eu.wisebed.wiseconfig.model;

/**
 * This is a persistent class for the object PortalApp that has the
 * properties of a node in the testbed xml file. In the class there are
 * getter and setter methods for the properties.
 */
public final class PortalApp {

    /**
     * WebService instance.
     */
    private WebService webSERVICE;

    /**
     * Returns WebService instance.
     * @return a WebService instance.
     */
    public WebService getWebSERVICE() {
        return webSERVICE;
    }

    /**
     * Sets WebService instance.
     * @param webSERVICE a WebService instance.
     */
    public void setWebSERVICE(final WebService webSERVICE) {
        this.webSERVICE = webSERVICE;
    }
}
