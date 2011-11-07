package eu.wisebed.wisedb.importer;

import eu.wisebed.wisedb.controller.SetupController;
import eu.wisebed.wisedb.controller.TestbedController;
import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wiseml.controller.WiseMLController;
import eu.wisebed.wiseml.model.WiseML;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Link;
import eu.wisebed.wiseml.model.setup.Node;
import eu.wisebed.wiseml.model.setup.Setup;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class SetupImporter extends AbstractImporter<Setup> {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(SetupImporter.class);

    /**
     * the testbed this setup belongs to.
     */
    private Testbed testbed;

    /**
     * define set of all setup's capabilities (Nodes & Links) with no duplicate entries
     */
    private Set<Capability> capabilities;


    /**
     * Default constructor.
     */
    public SetupImporter() {
        capabilities = new HashSet<Capability>();
    }

    /**
     * Sets the testbed instance and the endpoinurl to find the setup descriptions
     *
     * @param testbed, a testbed instance.
     */
    public void setTestbed(final Testbed testbed) {
        this.testbed = testbed;
        setEndpointUrl(this.testbed.getSessionUrl());
    }

    /**
     * Convert the WiseML setup to a WiseDB setup record.
     */
    public void convert() throws Exception {

        // retrieve setup record record from controllers InputStream
        final WiseMLController cnt = new WiseMLController();
        WiseML root = cnt.loadWiseMLFromFile(getWiseMlStream());
        Setup setup = root.getSetup();

        // if setup is null do nothing
        if (setup == null){
            return;
        }

        // call convert(setup)
        convert(setup);
    }

    /**
     * Convert the WiseML setup to a WiseDB setup record.
     * @param setup , a setup instance.
     * @throws Exception , an exception
     */
    public void convert(final Setup setup) throws Exception {

        if (setup == null) {
            LOGGER.fatal("Setup cannot be null");
            throw new Exception("Setup cannot be null");
        }

        // call convertCollection(list of setups)
        convertCollection(Arrays.asList(setup));
    }

    /**
     * Convert the WiseML setup entries collection to a WiseDB setup records.
     *
     * @param collection , collection of setup entries.
     */
    public void convertCollection(final Collection<Setup> collection) {

        if (collection == null) {
            LOGGER.fatal("Collection cannot be null");
            System.exit(-1);
        }

        // set entity collection
        setEntities(collection);

        // import records to db
        for (Setup setup : getEntities()) {

            // set link,node, capabilities and setup
            setNodeLinkSetup(setup);

            // reset NodeLink Capabilities
            resetNodeLinkCapabilities(setup);

            // set the testbed setup relation
            testbed.setSetup(setup);
            setup.setTestbed(testbed);

            // update testbed db
            TestbedController.getInstance().update(testbed);
            LOGGER.debug("Testbed updated");

            // import setup
            SetupController.getInstance().add(setup);
            LOGGER.debug("Setup imported to DB.");
        }
    }

    /**
     * Sets setup for nodes and links.
     * @param setup , a setup instance.
     */
    public void setNodeLinkSetup(final Setup setup) {

        if (setup.getNodes() != null) {
            for (Node node : setup.getNodes()) {

                // set setup for this node
                node.setSetup(setup);

                // add this node capability to the capabilities set
                for( Capability capability : node.getCapabilities()) {
                    capabilities.add(capability);
                }
            }
        }

        if (setup.getLink() != null) {
            for (Link link : setup.getLink()) {

                // set setup for this link
                link.setSetup(setup);

                // add this link's capabilities to the capabilities set
                for( Capability capability : link.getCapabilities()) {
                    capabilities.add(capability);
                }
            }
        }
    }

    /**
     * Reset the capabilities for links and nodes in order to match the unique capabilities set of this importer.
     * @param setup , a setup instance
     */
    public void resetNodeLinkCapabilities(final Setup setup){

        // capabilities must be unique objects so nodes & links must point to the set's entities
        for(Capability capability : capabilities){

            if(setup.getNodes() != null){
                for(Node node : setup.getNodes()) {
                    if(node.getCapabilities() != null && node.getCapabilities().contains(capability)){
                        node.getCapabilities().remove(capability);
                        node.getCapabilities().add(capability);
                    }
                }
            }

            if(setup.getLink() != null){
                for(Link link : setup.getLink()) {
                    if(link.getCapabilities() != null && link.getCapabilities().contains(capability)){
                        link.getCapabilities().remove(capability);
                        link.getCapabilities().add(capability);
                    }
                }
            }
        }
    }
}
