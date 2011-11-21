package eu.wisebed.wiseconfig.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a persistent class for the object Nodes that has the
 * properties of a node in the testbed xml file. In the class there are
 * getter and setter methods for the properties.
 */
public final class Nodes {

    /**
     * Identification String.
     */
    private String id;

    /**
     * List of NodeNames.
     */
    private List<Nodename> names;

    /**
     * List of Applications.
     */
    private List<Application> applications;

    /**
     * List of ServerConnections.
     */
    private List<ServerConnection> serverConnections;

    /**
     * Returns id.
     * @return id.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     * @param id an id.
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * Returns a list of Nodenames.
     * @return a list of Nodenames,
     */
    public List<Nodename> getNames() {
        return names;
    }

    /**
     * Sets a list of Nodenames.
     * @param names a list of Nodenames.
     */
    public void setNames(final List<Nodename> names) {
        this.names = names;
    }

    /**
     * Returns a list of Applications.
     * @return a list of Applications.
     */
    public List<Application> getApplications() {
        return applications;
    }

    /**
     * Sets a list of Applications.
     * @param applications a Applications list.
     */
    public void setApplications(final List<Application> applications) {
        this.applications = applications;
    }

    /**
     * Returns a list of ServerConnections.
     * @return a list of ServerConnections.
     */
    public List<ServerConnection> getServerConnections() {
        return serverConnections;
    }

    /**
     * Sets a list of ServerConnections.
     * @param serverConnections a list of ServerConnections.
     */
    public void setServerConnections(final List<ServerConnection> serverConnections) {
        this.serverConnections = serverConnections;
    }

    /**
     * Adds a Nodename to the Nodename list. If list does not exist, it is initialized.
     * @param nodename a Nodename instance.
     */
    public void addNodename(final Nodename nodename) {
        if (this.names == null) {
            this.names = new ArrayList<Nodename>();
        }
        this.names.add(nodename);
    }

    /**
     * Adds an Application to the Application list. If list does not exist, it is initialized.
     * @param app an application instance.
     */
    public void addApplication(final Application app) {
        if (this.applications == null) {
            this.applications = new ArrayList<Application>();
        }
        this.applications.add(app);
    }

    /**
     * Adds a ServerConnection to the ServerConnection list. If list does not exist, it is initialized.
     * @param con a ServerConnection instance.
     */
    public void addServerConnection(final ServerConnection con) {
        if (this.serverConnections == null) {
            this.serverConnections = new ArrayList<ServerConnection>();
        }
        this.serverConnections.add(con);
    }
}
