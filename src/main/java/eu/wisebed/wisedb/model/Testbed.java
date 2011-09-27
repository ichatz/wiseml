package eu.wisebed.wisedb.model;

import eu.wisebed.wiseml.model.setup.Setup;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a WSN testbed.
 */
public class Testbed {

    /**
     * Identity of the network.
     */
    private int id;

    /**
     * Name of the testbed.
     */
    private String name;

    /**
     * Description of the testbed.
     */
    private String description;

    /**
     * Testbed url.
     */
    private String url;

    /**
     * URN prefix.
     */
    private String urnPrefix;

    /**
     * URL for SNAA endpoint.
     */
    private String snaaUrl;

    /**
     * URL for RS endpoint.
     */
    private String rsUrl;

    /**
     * URL for Session Management endpoint.
     */
    private String sessionUrl;

    /**
     * If the testbed is federated.
     */
    private boolean federated;

    /**
     * the Setup belonging in Testbed
     */
    private Setup setup;

    /**
     * Get the Identity of the testbed.
     *
     * @return Identity of the testbed.
     */
    public int getId() {
        return id;
    }

    /**
     * Set the Identity of the testbed.
     *
     * @param identity the new Identity of the testbed.
     */
    public void setId(final int identity) {
        this.id = identity;
    }

    /**
     * Get the Name of the testbed.
     *
     * @return Name of the testbed.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the Name of the testbed.
     *
     * @param value the new Name of the testbed.
     */
    public void setName(final String value) {
        this.name = value;
    }

    /**
     * Get the urn prefix of the testbed.
     *
     * @return urn prefix of the testbed.
     */
    public String getUrnPrefix() {
        return urnPrefix;
    }

    /**
     * Set the urn prefix of the testbed.
     *
     * @param value the urn prefix of the testbed.
     */
    public void setUrnPrefix(final String value) {
        this.urnPrefix = value;
    }

    /**
     * Get the Description of the testbed.
     *
     * @return Description of the testbed.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the Description of the testbed.
     *
     * @param value the new Description of the testbed.
     */
    public void setDescription(final String value) {
        this.description = value;
    }

    /**
     * Get the URL of the testbed.
     *
     * @return the URL of the testbed.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set the URL of the testbed.
     *
     * @param value the URL of the testbed.
     */
    public void setUrl(final String value) {
        this.url = value;
    }

    /**
     * Get the URL of the SNAA endpoint.
     *
     * @return the URL of the SNAA endpoint.
     */
    public String getSnaaUrl() {
        return snaaUrl;
    }

    /**
     * Set the URL of the SNAA endpoint.
     * @param value the URL of the SNAA endpoint.
     */
    public void setSnaaUrl(final String value) {
        this.snaaUrl = value;
    }

    /**
     * Get the URL of the Reservation endpoint.
     *
     * @return the URL of the Reservation endpoint.
     */
    public String getRsUrl() {
        return rsUrl;
    }

    /**
     * Set the the URL of the Reservation endpoint.
     * @param value the URL of the Reservation endpoint.
     */
    public void setRsUrl(final String value) {
        this.rsUrl = value;
    }

    /**
     * Get the URL of the Session Management endpoint.
      * @return the URL of the Session Management endpoint.
     */
    public String getSessionUrl() {
        return sessionUrl;
    }

    /**
     * Set the URL of the Session Management endpoint.
     * @param value the URL of the Session Management endpoint.
     */
    public void setSessionUrl(final String value) {
        this.sessionUrl = value;
    }

    /**
     * Get if the testbed is federated.
     * @return true if the testbed is federated.
     */
    public boolean getFederated() {
        return federated;
    }

    /**
     * Set if the testbed is federated.
     * @param value true if the testbed is federated.
     */
    public void setFederated(final boolean value) {
        federated = value;
    }

    /**
     * Get the setup.
     * @return the setup instance.
     */
    public Setup getSetup(){
        return setup;
    }

    /**
     * Set the setup.
     * @param setup
     */
    public void setSetup(final Setup setup){
        this.setup = setup;
    }
}
