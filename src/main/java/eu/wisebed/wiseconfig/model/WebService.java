package eu.wisebed.wiseconfig.model;



/**
 * This is a persistent class for the object WebService that has the
 * properties of a node in the testbed xml file. In the class there are
 * getter and setter methods for the properties.
 */
public final class WebService {

    /**
     * Unique Resource Name prefix.
     */
    private String urnPrefix;

    /**
     * Session Management Endpoint URL.
     */
    private String sessionManagementEndpointUrl;

    /**
     * WSN instance base URL.
     */
    private String wsnInstancebaseUrl;

    /**
     * Reservation Endpoint URL.
     */
    private String reservationEndpointUrl;

    /**
     * SNAA Endpoint URL.
     */
    private String snaaEndpointUrl;

    /**
     * WiseML filename.
     */
    private String wisemlfilename;

    /**
     * Protobuf Interface.
     */
    private ProtobufInterface protobufINT;




    /**
     * Returns URN prefix.
     *
     * @return a urn prefix.
     */
    public String getUrnPrefix() {
        return urnPrefix;
    }

    /**
     * Sets URN prefix.
     *
     * @param urnPrefix a urn prefix.
     */
    public void setUrnPrefix(final String urnPrefix) {
        this.urnPrefix = urnPrefix;
    }

    /**
     * Returns Session Management Endpoint URL.
     *
     * @return session management endpoint URL.
     */
    public String getSessionManagementEndpointUrl() {
        return sessionManagementEndpointUrl;
    }

    /**
     * Sets Session Management Endpoint URL.
     *
     * @param sessionManagementEndpointUrl a Session Manaagement Endpoint URL.
     */
    public void setSessionManagementEndpointUrl(final String sessionManagementEndpointUrl) {
        this.sessionManagementEndpointUrl = sessionManagementEndpointUrl;
    }

    /**
     * Returns WSN instance base URL.
     *
     * @return WSN instance base URL.
     */
    public String getWsnInstancebaseUrl() {
        return wsnInstancebaseUrl;
    }

    /**
     * Sets WSN instance base URL.
     *
     * @param wsnInstancebaseUrl a wsn instance base URL.
     */
    public void setWsnInstancebaseUrl(final String wsnInstancebaseUrl) {
        this.wsnInstancebaseUrl = wsnInstancebaseUrl;
    }

    /**
     * Returns RS endpoint URL.
     *
     * @return RS endpoint URL.
     */
    public String getReservationEndpointUrl() {
        return reservationEndpointUrl;
    }

    /**
     * Sets Reservation Endpoint URL.
     *
     * @param reservationEndpointUrl reservation endpoint URL.
     */
    public void setReservationEndpointUrl(final String reservationEndpointUrl) {
        this.reservationEndpointUrl = reservationEndpointUrl;
    }

    /**
     * Returns SNAA endpoint URL.
     *
     * @return SNAA endpoint URL.
     */
    public String getSnaaEndpointUrl() {
        return snaaEndpointUrl;
    }

    /**
     * Sets SNAA endpoint URL.
     *
     * @param snaaEndpointUrl SNAA endpoint URL.
     */
    public void setSnaaEndpointUrl(final String snaaEndpointUrl) {
        this.snaaEndpointUrl = snaaEndpointUrl;
    }

    /**
     * Returns ProtobufInterface instance.
     *
     * @return ProtobufInterface instance.
     */
    public ProtobufInterface getProtobufINT() {
        return protobufINT;
    }

    /**
     * Sets ProtobufInterface instance.
     *
     * @param protobufINT instance.
     */
    public void setProtobufINT(final ProtobufInterface protobufINT) {
        this.protobufINT = protobufINT;
    }

    /**
     * Return WiseML filename.
     *
     * @return WiseML filename.
     */
    public String getWisemlfilename() {
        return wisemlfilename;
    }

    /**
     * Sets WiseML filename.
     *
     * @param wisemlFilename a WiseML filename.
     */
    public void setWisemlfilename(final String wisemlFilename) {
        this.wisemlfilename = wisemlFilename;
    }

}
