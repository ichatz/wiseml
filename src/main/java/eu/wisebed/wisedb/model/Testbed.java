package eu.wisebed.wisedb.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.TimeZone;

/**
 * This is a persistent class for the object Testbed that has the
 * properties of a wisedb entry. In the class there are
 * getter and setter methods for the properties.
 */
@Entity
@Table(name = "testbeds")
public final class Testbed implements Serializable {

    /**
     * Serial Version Unique ID.
     */
    private static final long serialVersionUID = -2786542310266504137L;
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
    private Boolean federated;

    /**
     * Set of Setups belonging in Testbed.
     */
    private Setup setup;

    /**
     * Testbed timezone.
     */
    private TimeZone timeZone;

    /**
     * Get the Identity of the testbed.
     *
     * @return Identity of the testbed.
     */
    @Id
    @GeneratedValue
    @Column(name = "testbed_id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    /**
     * Set the id of the testbed.
     *
     * @param id the new Identity of the testbed.
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * Get the Name of the testbed.
     *
     * @return Name of the testbed.
     */
    @Column(name = "name", unique = true, nullable = false)
    public String getName() {
        return name;
    }

    /**
     * Set the Name of the testbed.
     *
     * @param name the new Name of the testbed.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Get the urn prefix of the testbed.
     *
     * @return urn prefix of the testbed.
     */
    @Column(name = "urnPrefix", unique = true, nullable = false)
    public String getUrnPrefix() {
        return urnPrefix;
    }

    /**
     * Set the urn prefix of the testbed.
     *
     * @param urnPrefix the urn prefix of the testbed.
     */
    public void setUrnPrefix(final String urnPrefix) {
        this.urnPrefix = urnPrefix;
    }

    /**
     * Get the Description of the testbed.
     *
     * @return Description of the testbed.
     */
    @Column(name = "description", unique = false, nullable = false)
    public String getDescription() {
        return description;
    }

    /**
     * Set the Description of the testbed.
     *
     * @param description the new Description of the testbed.
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Get the URL of the testbed.
     *
     * @return the URL of the testbed.
     */
    @Column(name = "url", unique = false, nullable = false)
    public String getUrl() {
        return url;
    }

    /**
     * Set the URL of the testbed.
     *
     * @param url the URL of the testbed.
     */
    public void setUrl(final String url) {
        this.url = url;
    }

    /**
     * Get the URL of the SNAA endpoint.
     *
     * @return the URL of the SNAA endpoint.
     */
    @Column(name = "snaaUrl", unique = true, nullable = false)
    public String getSnaaUrl() {
        return snaaUrl;
    }

    /**
     * Set the URL of the SNAA endpoint.
     *
     * @param snaaUrl the URL of the SNAA endpoint.
     */
    public void setSnaaUrl(final String snaaUrl) {
        this.snaaUrl = snaaUrl;
    }

    /**
     * Get the URL of the Reservation endpoint.
     *
     * @return the URL of the Reservation endpoint.
     */
    @Column(name = "rsUrl", unique = true, nullable = false)
    public String getRsUrl() {
        return rsUrl;
    }

    /**
     * Set the the URL of the Reservation endpoint.
     *
     * @param rsUrl the URL of the Reservation endpoint.
     */
    public void setRsUrl(final String rsUrl) {
        this.rsUrl = rsUrl;
    }

    /**
     * Get the URL of the Session Management endpoint.
     *
     * @return the URL of the Session Management endpoint.
     */
    @Column(name = "sessionUrl", unique = true, nullable = false)
    public String getSessionUrl() {
        return sessionUrl;
    }

    /**
     * Set the URL of the Session Management endpoint.
     *
     * @param sessionUrl the URL of the Session Management endpoint.
     */
    public void setSessionUrl(final String sessionUrl) {
        this.sessionUrl = sessionUrl;
    }

    /**
     * Get if the testbed is federated.
     *
     * @return true if the testbed is federated.
     */
    @Column(name = "federated", unique = false, nullable = false)
    public Boolean getFederated() {
        return federated;
    }

    /**
     * Set if the testbed is federated.
     *
     * @param federated true if the testbed is federated.
     */
    public void setFederated(final Boolean federated) {
        this.federated = federated;
    }

    /**
     * Returns the testbed setup.
     *
     * @return the testbed setup.
     */
    @OneToOne
    @JoinColumn(name = "setup_id")
    public Setup getSetup() {
        return setup;
    }

    /**
     * Sets the testbed setup.
     *
     * @param setup , a setup instance.
     */
    public void setSetup(final Setup setup) {
        this.setup = setup;
    }

    /**
     * Returns testbed's timezone.
     *
     * @return testbed's timezone.
     */
    @Column(name = "timeZone", unique = false, nullable = false)
    @Basic(fetch = FetchType.LAZY)
    public TimeZone getTimeZone() {
        return timeZone;
    }

    /**
     * Sets testbed's timezone.
     *
     * @param timeZone testbed's timezeone.
     */
    public void setTimeZone(final TimeZone timeZone) {
        this.timeZone = timeZone;
    }
}
