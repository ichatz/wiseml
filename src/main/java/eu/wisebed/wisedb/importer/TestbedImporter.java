package eu.wisebed.wisedb.importer;

import eu.wisebed.testbed.api.wsn.v22.SessionManagement;
import eu.wisebed.wisedb.controller.TestbedController;
import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wiseml.controller.WiseMLController;
import eu.wisebed.wiseml.model.WiseML;
import eu.wisebed.wiseml.model.setup.Node;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Retrieves testbed records from the Session management endpoint and imports them into the wisedb.
 */
public class TestbedImporter {

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
    public TestbedImporter() {
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
     * Retrieve the records from the remote Session Management endpoint in WiseML format.
     *
     * @return a WiseML document containing the records of the testbed.
     */
    public String getNetwork() {
        return getSessionManagementService().getNetwork();
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

        // Extract urn prefix
        final List<Node> nodes = stp.getSetup().getNodes();
        Set<String> prefixes = new HashSet<String>();
        for (final Node node : nodes) {
            final String id = node.getId();
            final String thisPrefix = id.substring(0, id.lastIndexOf(":"));
            prefixes.add(thisPrefix);
        }

        LOGGER.debug("Total prefixes found: " + prefixes.size());
        LOGGER.debug("First prefix: " + prefixes.iterator().next());

        // Check imported records
        //final List<Testbed> testbedList = TestbedController.getInstance().listByUrnPrefix()
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
