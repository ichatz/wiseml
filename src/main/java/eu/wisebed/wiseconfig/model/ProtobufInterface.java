package eu.wisebed.wiseconfig.model;


public class ProtobufInterface {


    private String port;
    private String ip;

    public final String getPort() {
        return port;
    }

    public final void setPort(final String port) {
        this.port = port;
    }

    public final String getIp() {
        return ip;
    }

    public final void setIp(final String ip) {
        this.ip = ip;
    }
}