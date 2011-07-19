package eu.wisebed.wisedb.importer;

import eu.wisebed.testbed.api.wsn.v22.SessionManagement;
import eu.wisebed.wisedb.controller.LinkReadingController;
import eu.wisebed.wisedb.controller.NodeReadingController;
import eu.wisebed.wisedb.model.LinkReading;
import eu.wisebed.wisedb.model.NodeReading;
import eu.wisebed.wiseml.controller.WiseMLController;
import eu.wisebed.wiseml.model.WiseML;
import eu.wisebed.wiseml.model.scenario.Timestamp;
import eu.wisebed.wiseml.model.setup.Data;
import eu.wisebed.wiseml.model.trace.Trace;
import eu.wisebed.wiseml.model.setup.Link;
import eu.wisebed.wiseml.model.setup.Node;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import java.util.List;

public class TraceImporter {
    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(TraceImporter.class);

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
     * WiseML Trace entity
     */
    private Trace trace;

    /**
     * Default constructor.
     */
    public TraceImporter() {
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

        // get trace items
        List traceItems = trace.getChildren();
        System.out.println("Trace items count :" + traceItems.size());


        // define last occured timestamp
        long lastTimeStamp = 0L;

        // iterate trace items. In case a node/link has not been set in the setup we set it
        for(Object item : traceItems)
        {
            if (item.getClass().equals(Timestamp.class)){

                // in case of a timestamp item . Just keep tha last timestamp to perist
                Timestamp ts=(Timestamp) item;
                System.out.println("Timestamp : "+ts.getValue());
                if(ts.getValue() != null){
                    lastTimeStamp = Long.parseLong(ts.getValue());
                }
            }else if (item.getClass().equals(Node.class)){

                // in case of a node item , using the last timestamp persist the node and it's readings
                Node nd=(Node) item;
                System.out.println("Node : " + nd.getId());
                System.out.println("Node has " + nd.getData().size() + " readings" );
                for(Data d :nd.getData()) {
                    System.out.println("\t\t" + d.getKey() + " : " + d.getValue() );
                    NodeReading nr1 = new NodeReading();
                    nr1.setTimestamp(lastTimeStamp);
                    nr1.setNodeId(nd.getId());
                    nr1.setCapId(d.getKey());
                    nr1.setReading(Double.parseDouble(d.getValue()));
                    NodeReadingController.getInstance().add(nr1);
                }

                // (todo) use controller to persist them.
            }else if (item.getClass().equals(Link.class)){

                // in case of a link item , using the last timestamp persist the node and it's readings
                Link ln=(Link) item;
                System.out.println("Link : "+ ln.getSource() + "-->" + ln.getTarget());
                System.out.println("Link RSSI : "+ln.getRssi().getValue());
                System.out.println("Link has " + ln.getData().size()  + " readings");
                for(Data d :ln.getData()) {
                    System.out.println("\t\t" + d.getKey() + " : " + d.getValue() );
                    LinkReading lr1 = new LinkReading();
                    lr1.setTimestamp(lastTimeStamp);
                    lr1.setLinkSource(ln.getSource());
                    lr1.setLinkTarget(ln.getTarget());
                    lr1.setCapId(d.getKey());
                    lr1.setReading(Double.parseDouble(d.getValue()));
                    lr1.setRssiValue(Double.parseDouble(ln.getRssi().getValue()));
                    LinkReadingController.getInstance().add(lr1);
                }

                // (todo) use controller to persist them.
            }
}
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
            trace = root.getTrace();
            if(trace == null){
                System.err.println("Trace cannot be null. Exiting");
                System.exit(-1);
            }



        } catch (Exception ex) {
            LOGGER.fatal("Error while unmarshalling WiseML document", ex);
            System.err.println(ex);
            System.exit(-1);
        }
    }
}
