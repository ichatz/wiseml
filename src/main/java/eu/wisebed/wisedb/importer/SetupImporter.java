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
import java.util.Iterator;
import java.util.List;
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
     * Default constructor.
     */
    public SetupImporter() {
        // empty constructor.
    }

    /**
     * Returns the testbed instance.
     *
     * @return , the testbed instance.
     */
    public Testbed getTestbed() {
        return testbed;
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
    public void convert() {

        // retrieve setup record record from controllers InputStream
        final WiseMLController cnt = new WiseMLController();
        WiseML root = cnt.loadWiseMLFromFile(getWiseMlStream());
        Setup setup = root.getSetup();

        // if setup is null do nothing
        if (setup == null) return;

        // call convert(setup)
        convert(setup);
    }

    /**
     * Convert the WiseML setup to a WiseDB setup record.
     */

    public void convert(final Setup setup) {

        if (setup == null) {
            LOGGER.fatal("Setup cannot be null");
            System.exit(-1);
        }

        // set this setup as entity
        setEntity(setup);

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
            SetupImporter.setNodeLinkSetup(setup);

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

    public static void setNodeLinkSetup(final Setup setup) {

        if (setup.getNodes() != null && !setup.getNodes().isEmpty()) {
            for (Node node : setup.getNodes()) {
                node.setSetup(setup);
            }
        }
        if (setup.getLink() != null && !setup.getLink().isEmpty()) {
            for (Link link : setup.getLink()) {
                link.setSetup(setup);
            }
        }

        // define set of all setup's capabilities with no duplicate entries
        Set<Capability> capabilities = new HashSet<Capability>();

        // get nodes and scan their capabilities
        List<Node> nodes = setup.getNodes();
        if (nodes != null && !nodes.isEmpty()) {
            for (Node node : nodes) {
                for (Capability capability : node.getCapabilities()) {
                    capabilities.add(capability);
                }
            }
        }

        // get links and scan their capabilities
        List<Link> links = setup.getLink();
        if (links != null && !links.isEmpty()) {
            for (Link link : links) {
                for (Capability capability : link.getCapabilities()) {
                    capabilities.add(capability);
                }
            }
        }

        // capabilities must be unique objects so nodes & links must point to the set's entities
        Iterator<Capability> it = capabilities.iterator();
        while (it.hasNext()) {
            Capability capability = it.next();
            if (nodes != null && !nodes.isEmpty()) {
                for (Node node : nodes) {
                    List<Capability> nodeCapabilities = node.getCapabilities();
                    if (nodeCapabilities.contains(capability)) {
                        nodeCapabilities.remove(capability);
                        nodeCapabilities.add(capability);
                    }
                }
            }
            if (links != null && !links.isEmpty()) {
                for (Link link : links) {
                    List<Capability> linkCapabilities = link.getCapabilities();
                    if (linkCapabilities.contains(capability)) {
                        linkCapabilities.remove(capability);
                        linkCapabilities.add(capability);
                    }
                }
            }
        }
    }
}
