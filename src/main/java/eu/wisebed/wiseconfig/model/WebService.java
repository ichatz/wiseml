package eu.wisebed.wiseconfig.model;


public class WebService {


    private String urnPrefix;
    private String sessionManagementEndpointUrl;
    private String wsnInstancebaseUrl;
    private String reservationEndpointUrl;
    private String snaaEndpointUrl;
    private String wisemlfilename;
    private ProtobufInterface protobufINT;


    public final String getUrnPrefix() {
        return urnPrefix;
    }

    public final void setUrnPrefix(final String urnPrefix) {
        this.urnPrefix = urnPrefix;
    }

    public final String getSessionManagementEndpointUrl() {
        return sessionManagementEndpointUrl;
    }

    public final void setSessionManagementEndpointUrl(final String sessionManagementEndpointUrl) {
        this.sessionManagementEndpointUrl = sessionManagementEndpointUrl;
    }

    public final String getWsnInstancebaseUrl() {
        return wsnInstancebaseUrl;
    }

    public final void setWsnInstancebaseUrl(final String wsnInstancebaseUrl) {
        this.wsnInstancebaseUrl = wsnInstancebaseUrl;
    }

    public final String getReservationEndpointUrl() {
        return reservationEndpointUrl;
    }

    public final void setReservationEndpointUrl(final String reservationEndpointUrl) {
        this.reservationEndpointUrl = reservationEndpointUrl;
    }

    public final String getSnaaEndpointUrl() {
        return snaaEndpointUrl;
    }

    public final void setSnaaEndpointUrl(final String snaaEndpointUrl) {
        this.snaaEndpointUrl = snaaEndpointUrl;
    }

    public final ProtobufInterface getProtobufINT() {
        return protobufINT;
    }

    public final void setProtobufINT(final ProtobufInterface protobufINT) {
        this.protobufINT = protobufINT;
    }

    public final String getWisemlfilename() {
        return wisemlfilename;
    }

    public final void setWisemlfilename(final String wisemlFilename) {
        this.wisemlfilename = wisemlFilename;
    }
}