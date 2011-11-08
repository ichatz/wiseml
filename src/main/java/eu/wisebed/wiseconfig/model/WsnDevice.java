package eu.wisebed.wiseconfig.model;


public class WsnDevice {


    private String urn;
    private String type;
    private String serialinterface;
    private String usbchipid;

    public final String getUrn() {
        return urn;
    }

    public final void setUrn(String urn) {
        this.urn = urn;
    }

    public final String getType() {
        return type;
    }

    public final void setType(String type) {
        this.type = type;
    }

    public final String getSerialinterface() {
        return serialinterface;
    }

    public final void setSerialinterface(String serialinterface) {
        this.serialinterface = serialinterface;
    }

    public final String getUsbchipid() {
        return usbchipid;
    }

    public final void setUsbchipid(String usbchipid) {
        this.usbchipid = usbchipid;
    }
}