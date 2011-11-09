package eu.wisebed.wiseconfig.model;


public class ServerConnection {


    private String address;
    private String type;

    public final String getAddress() {
        return address;
    }

    public final void setAddress(final String address) {
        this.address = address;
    }

    public final String getType() {
        return type;
    }

    public final void setType(final String type) {
        this.type = type;
    }
}