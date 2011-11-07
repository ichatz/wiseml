package eu.wisebed.wiseconfig.model;


public class WebService {


    private String urnPrefix;
    private String sessionManagementEndpointUrl;
    private String wsnInstancebaseUrl;
    private String reservationEndpointUrl;
    private String snaaEndpointUrl;
    private String wisemlfilename;
    private ProtobufInterface protobufINT;


    public String getUrnPrefix() {
        return urnPrefix;
    }

    public void setUrnPrefix(String urnPrefix) {
        this.urnPrefix = urnPrefix;
    }

    public String getSessionManagementEndpointUrl() {
        return sessionManagementEndpointUrl;
    }

    public void setSessionManagementEndpointUrl(String sessionManagementEndpointUrl) {
        this.sessionManagementEndpointUrl = sessionManagementEndpointUrl;
    }

    public String getWsnInstancebaseUrl() {
        return wsnInstancebaseUrl;
    }

    public void setWsnInstancebaseUrl(String wsnInstancebaseUrl) {
        this.wsnInstancebaseUrl = wsnInstancebaseUrl;
    }

    public String getReservationEndpointUrl() {
        return reservationEndpointUrl;
    }

    public void setReservationEndpointUrl(String reservationEndpointUrl) {
        this.reservationEndpointUrl = reservationEndpointUrl;
    }

    public String getSnaaEndpointUrl() {
        return snaaEndpointUrl;
    }

    public void setSnaaEndpointUrl(String snaaEndpointUrl) {
        this.snaaEndpointUrl = snaaEndpointUrl;
    }

    public ProtobufInterface getProtobufINT() {
        return protobufINT;
    }

    public void setProtobufINT(ProtobufInterface protobufINT) {
        this.protobufINT = protobufINT;
    }

    public String getWisemlfilename() {
        return wisemlfilename;
    }

    public void setWisemlfilename(String wisemlFilename) {
        this.wisemlfilename = wisemlFilename;
    }
}