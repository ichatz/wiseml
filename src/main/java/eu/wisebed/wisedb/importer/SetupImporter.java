package eu.wisebed.wisedb.importer;

import eu.wisebed.testbed.api.wsn.v22.SessionManagement;
import eu.wisebed.wisedb.controller.CapabilityController;
import eu.wisebed.wisedb.controller.SetupController;
import eu.wisebed.wiseml.controller.WiseMLController;
import eu.wisebed.wiseml.model.WiseML;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Setup;
import eu.wisebed.wiseml.model.setup.Node;
import org.apache.log4j.Logger;

import java.io.*;

import java.util.HashSet;
import java.util.Iterator;

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
     * WiseML source input stream.
     */
    private InputStream in;

    /**
     * WiseML Setup instance.
     */
    private Setup setup;

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
        String wiseMl = getNetwork();
        LOGGER.debug("Got WiseML from URI: " + endpointUrl);

        // setting input stream
        in = new ByteArrayInputStream(wiseMl.getBytes());
        LOGGER.debug("Input stream comming from remote source.");
    }

    /**
     * Open local WiseML file.
     */
    public void open(final String path){

        try {
            in = new FileInputStream(path);
        } catch(Exception e){
            System.err.println(e);
            System.exit(-1);
        }
        LOGGER.debug("Input stream comming from local file.");
    }

    /**
     * Convert the WiseML document describing the records of the testbed into wisedb records.
     */
    public void convert() {
        WiseML root = null;
        try {
            final WiseMLController cnt = new WiseMLController();
            root = cnt.loadWiseMLFromFile(in);
            setup = root.getSetup();
            LOGGER.debug(root.getVersion());
        } catch (Exception ex) {
            LOGGER.fatal("Error while unmarshalling WiseML document", ex);
            return;
        }

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