package eu.wisebed.wisedb.importer;

import eu.wisebed.testbed.api.wsn.v22.SessionManagement;
import eu.wisebed.wisedb.controller.CapabilityController;
import eu.wisebed.wisedb.controller.SetupController;
import eu.wisebed.wiseml.controller.WiseMLController;
import eu.wisebed.wiseml.model.WiseML;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Setup;
import eu.wisebed.wiseml.model.setup.Node;
import eu.wisebed.wiseml.model.setup.Link;
import org.apache.log4j.Logger;

import java.io.*;

import java.util.HashSet;
import java.util.Iterator;

public class SetupImporter {
    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(SetupImporter.class);

    /**
     * The URL of the Session Management endpoint.
     */
    private String endpointUrl;

    /**
     * The Session Management endpoint.
     */
    private SessionManagement sessionManagementService;

    /**
     * WiseML root entity.
     */
    private WiseML root;

    /**
     * WiseML setup entity
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
        InputStream in = new ByteArrayInputStream(wiseMl.getBytes());
        LOGGER.debug("Input stream comming from remote source.");

        // load wiseml from in
        loadWiseMLFromFile(in);
    }

    /**
     * Open local WiseML file.
     */
    public void open(final String path){
        InputStream in =null;
        try {
            in = new FileInputStream(path);
        } catch(Exception e){
            System.err.println(e);
            System.exit(-1);
        }
        LOGGER.debug("Input stream comming from local file.");

        // load wiseml from in
        loadWiseMLFromFile(in);
    }

    /**
     * Convert the WiseML document describing the records of the testbed into wisedb records.
     */
    public void convert() {
        // insert and associate nodes/links & capabilities
        if(setup.getNodes() != null) {

            // insert node capabilities
            Iterator<Node> nodeIt =setup.getNodes().iterator();
            while(nodeIt.hasNext()) {
                Node node = nodeIt.next();

                if( node.getCapabilities() == null) // if node has not capabilities continue loop
                    continue;

                LOGGER.debug("Node : " + node.getId() + " has " + node.getCapabilities().size() + " capabilities");
                Iterator<Capability> capaIt = node.getCapabilities().iterator();
                while(capaIt.hasNext()) {
                    Capability capability = capaIt.next();
                    if(capability.getNodes() == null)
                        capability.setNodes(new HashSet<Node>());
                    capability.getNodes().add(node);
                    CapabilityController.getInstance().add(capability);           // saves or updates capability
                }
            }
        }
        if(setup.getLink() != null) {

            // insert link capabilities
            Iterator<Link> linkIt = setup.getLink().iterator();
            while(linkIt.hasNext()) {
                Link link = linkIt.next();

                if(link.getCapabilities() == null)
                    continue;

                LOGGER.debug("Link : [" + link.getSource() + "," + link.getTarget() + "] has " + link.getCapabilities().size());
                Iterator<Capability> capaIt = link.getCapabilities().iterator();
                while(capaIt.hasNext()) {
                    Capability capability = capaIt.next();
                    if(capability.getLinks() == null)
                        capability.setLinks(new HashSet<Link>());
                    capability.getLinks().add(link);
                    CapabilityController.getInstance().add(capability);           // saves or updates capability
                }
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

    /**
     * Load WiseML entities from the XML.
     *
     * @param in an input stream.
     */
    private void loadWiseMLFromFile(final InputStream in){
        try{
            // set the controller
            final WiseMLController cnt = new WiseMLController();

            // load wiseml root element
            root = cnt.loadWiseMLFromFile(in);
            LOGGER.debug(root.getVersion());

            // add setup found
            setup = root.getSetup();


        } catch (Exception ex) {
            LOGGER.fatal("Error while unmarshalling WiseML document", ex);
            System.err.println(ex);
            System.exit(-1);
        }
    }
}