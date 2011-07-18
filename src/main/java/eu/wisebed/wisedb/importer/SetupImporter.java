package eu.wisebed.wisedb.importer;

import eu.wisebed.testbed.api.wsn.v22.SessionManagement;
import eu.wisebed.wisedb.controller.CapabilityController;
import eu.wisebed.wisedb.controller.NodeController;
import eu.wisebed.wisedb.controller.SetupController;
import eu.wisebed.wiseml.controller.WiseMLController;
import eu.wisebed.wiseml.model.WiseML;
import eu.wisebed.wiseml.model.scenario.Scenario;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Setup;
import eu.wisebed.wiseml.model.setup.Node;
import eu.wisebed.wiseml.model.trace.Trace;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SetupImporter {
    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(TestbedImporter.class);

    /**
     * The URL of the Session Management endpoint.
     */
    private String endpointUrl;

    /**
     * The Session Management endpoint.
     */
    private SessionManagement sessionManagementService;

    /**
     * Default constructor.
     */
    public SetupImporter() {
        // empty constructor.
    }

    /**
     * Connect to the remote endpoint.
     */
    public void connect() {
        // Connect to remote endpoint
        sessionManagementService = eu.wisebed.testbed.api.wsn.WSNServiceHelper
                .getSessionManagementService(getEndpointUrl());
    }

    /**
     * Convert the WiseML document describing the records of the testbed into wisedb records.
     */
    public void convert() {
        // Retrieve records
        final String wiseml = getNetwork();

        // Convert records into a WiseML document
        WiseML stp = null;
        LOGGER.debug("Got WiseML from URI: " + endpointUrl);
        InputStream is = new ByteArrayInputStream(wiseml.getBytes());
        try {
            final WiseMLController cnt = new WiseMLController();
            stp = cnt.loadWiseMLFromFile(is);
            LOGGER.debug(stp.getVersion());
        } catch (Exception ex) {
            LOGGER.fatal("Error while unmarshalling WiseML document", ex);
            return;
        }
        Setup setup = stp.getSetup();

        // insert capabilities
        Iterator<Node> it =setup.getNodes().iterator();
        while(it.hasNext()) {
            Node node = it.next();

            if( node.getCapabilities() == null) // if node has not capabilities continue loop
                continue;

            LOGGER.debug("Node : " + node.getId() + " has " + node.getCapabilities().size() + " capabilities");
            Iterator<Capability> itCap = node.getCapabilities().iterator();
            while(itCap.hasNext()){
                Capability capability = itCap.next();
                if(capability.getNodes() == null)
                    capability.setNodes(new HashSet<Node>());
                capability.getNodes().add(node);
                CapabilityController.getInstance().add(capability);           // saves or updates capability
            }
        }

        // insert setup,nodes & links to db
        SetupController.getInstance().add(setup);
        LOGGER.debug("Setup added to DB");
        Setup pSetup = SetupController.getInstance().getByID(setup.getId());
        LOGGER.debug(pSetup.getNodes().size() + " nodes persisted");
        LOGGER.debug(pSetup.getLink().size() + " links peristsed");


    }

    /**
     * Retrieve the records from the remote Session Management endpoint in WiseML format.
     *
     * @return a WiseML document containing the records of the testbed.
     */
    public String getNetwork() {
        return getSessionManagementService().getNetwork();
    }

        /**
     * Get the URL of the session management endpoint.
     *
     * @return the URL of the session management endpoint.
     */
    public String getEndpointUrl() {
        return endpointUrl;
    }

    /**
     * Set the URL of the session management endpoint.
     *
     * @param value the URL of the session management endpoint.
     */
    public void setEndpointUrl(final String value) {
        this.endpointUrl = value;
    }

    /**
     * Get the Session Management endpoint.
     *
     * @return the Session Management endpoint.
     */
    public SessionManagement getSessionManagementService() {
        return sessionManagementService;
    }

    /**
     * Set the Session Management endpoint.
     *
     * @param value the Session Management endpoint.
     */
    public void setSessionManagementService(final SessionManagement value) {
        this.sessionManagementService = value;
    }

}