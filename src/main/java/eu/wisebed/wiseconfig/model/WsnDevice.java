package eu.wisebed.wiseconfig.model;


public class WsnDevice {


    private String urn;
    private String type;
    private String serialinterface;
    private String usbchipid;

    public String getUrn() {
        return urn;
    }

    public void setUrn(String urn) {
        this.urn = urn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSerialinterface() {
        return serialinterface;
    }

    public void setSerialinterface(String serialinterface) {
        this.serialinterface = serialinterface;
    }

    public String getUsbchipid() {
        return usbchipid;
    }

    public void setUsbchipid(String usbchipid) {
        this.usbchipid = usbchipid;
    }
}