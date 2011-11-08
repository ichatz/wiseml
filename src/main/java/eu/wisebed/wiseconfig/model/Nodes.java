package eu.wisebed.wiseconfig.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a persistent class for the object Nodes that has the
 * properties of a node in the testbed xml file. In the class there are
 * getter and setter methods for the properties.
 */
public class Nodes {

    /**
     * list of nodes of the testbed
     */
    private String ID;

    private List<Nodename> names;
    private List<Application> applications;
    private List<ServerConnection> serverConnections;

    public String getID() {
        return ID;
    }

    public void setID(final String ID) {
        this.ID = ID;
    }

    public List<Nodename> getNames() {
        return names;
    }

    public void setNames(final List<Nodename> namess) {
        this.names = namess;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(final List<Application> applications) {
        this.applications = applications;
    }

    public List<ServerConnection> getServerConnections() {
        return serverConnections;
    }

    public void setServerConnections(final List<ServerConnection> serverConnections) {
        this.serverConnections = serverConnections;
    }

    public void addNodename(final Nodename nodename) {
        if (this.names == null) {
            this.names = new ArrayList<Nodename>();
        }
        this.names.add(nodename);
    }

    public void addApplication(final Application app) {
        if (this.applications == null) {
            this.applications = new ArrayList<Application>();
        }
        this.applications.add(app);
    }

    public void addServerConnection(final ServerConnection con) {
        if (this.serverConnections == null) {
            this.serverConnections = new ArrayList<ServerConnection>();
        }
        this.serverConnections.add(con);
    }
}