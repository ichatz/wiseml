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
     *
     * @return id.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id an id.
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * Returns a list of Nodenames.
     *
     * @return a list of Nodenames,
     */
    public List<Nodename> getNames() {
        return names;
    }

    /**
     * Sets a list of Nodenames.
     *
     * @param names a list of Nodenames.
     */
    public void setNames(final List<Nodename> names) {
        this.names = names;
    }

    /**
     * Returns a list of Applications.
     *
     * @return a list of Applications.
     */
    public List<Application> getApplications() {
        return applications;
    }

    /**
     * Sets a list of Applications.
     *
     * @param applications a Applications list.
     */
    public void setApplications(final List<Application> applications) {
        this.applications = applications;
    }

    /**
     * Returns a list of ServerConnections.
     *
     * @return a list of ServerConnections.
     */
    public List<ServerConnection> getServerConnections() {
        return serverConnections;
    }

    /**
     * Sets a list of ServerConnections.
     *
     * @param serverConnections a list of ServerConnections.
     */
    public void setServerConnections(final List<ServerConnection> serverConnections) {
        this.serverConnections = serverConnections;
    }

    /**
     * Adds a Nodename to the Nodename list. If list does not exist, it is initialized.
     *
     * @param nodename a Nodename instance.
     */
    public void addNodename(final Nodename nodename) {
        if (this.names == null) {
            this.names = new ArrayList<Nodename>();
        }
        this.findUpdateNodename(nodename);
    }

    /**
     * Adds an Application to the Application list. If list does not exist, it is initialized.
     *
     * @param app an application instance.
     */
    public void addApplication(final Application app) {
        if (this.applications == null) {
            this.applications = new ArrayList<Application>();
        }
        this.findUpdateApplication(app);
    }

    /**
     * Adds a ServerConnection to the ServerConnection list. If list does not exist, it is initialized.
     *
     * @param con a ServerConnection instance.
     */
    public void addServerConnection(final ServerConnection con) {
        if (this.serverConnections == null) {
            this.serverConnections = new ArrayList<ServerConnection>();
        }
        this.findUpdateServerConnection(con);
    }


    private void findUpdateApplication(final Application app) {
        if (this.applications == null) return;
        boolean updated = false;
        for (Object appIt : this.applications.toArray()) {
            try {
                if (((Application) appIt).getWsnDev().getUrn().equals(app.getWsnDev().getUrn()) == true) {
                    this.applications.remove(appIt);
                    this.applications.add(app);
                    updated = true;
                    break;
                }
            } catch (Exception ex) {
                this.applications.remove(appIt);
                this.applications.add(app);
                updated = true;
                break;
            }
        }
        if (updated == false) {
            this.applications.add(app);
        }
    }

    private void findUpdateNodename(final Nodename nodename) {
        if (this.names == null) return;
        boolean updated = false;
        for (Object ndname : this.names.toArray()) {
            if (((Nodename) ndname).getName().equals(nodename.getName()) == true) {
                this.names.remove(ndname);
                this.names.add(nodename);
                updated = true;
                break;
            }
        }
        if (updated == false) {
            this.names.add(nodename);
        }
    }

    private void findUpdateServerConnection(final ServerConnection con) {
        if (this.serverConnections == null) return;
        boolean updated = false;
        for (Object scon : this.serverConnections.toArray()) {
            if (((ServerConnection) scon).getAddress().equals(con.getAddress()) == true) {
                this.serverConnections.remove(scon);
                this.serverConnections.add(con);
                updated = true;
                break;
            }
        }
        if (updated == false) {
            this.serverConnections.add(con);
        }
    }

    public void deleteApplication(final String node_id) {
        if (this.applications == null) return;
        for (Object appIt : this.applications.toArray()) {
            if (((Application) appIt).getWsnDev().getUrn().equals(node_id) == true) {
                this.applications.remove(appIt);
                break;
            }
        }
    }

    public void deleteNodename(final String node_id) {
        if (this.names == null) return;
        for (Object ndname : this.names.toArray()) {
            if (((Nodename) ndname).getName().equals(node_id) == true) {
                this.names.remove(ndname);
                break;
            }
        }
    }
}
