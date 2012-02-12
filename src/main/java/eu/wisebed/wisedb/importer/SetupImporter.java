package eu.wisebed.wisedb.importer;

import eu.wisebed.wisedb.controller.CapabilityController;
import eu.wisebed.wisedb.controller.LinkCapabilityController;
import eu.wisebed.wisedb.controller.LinkController;
import eu.wisebed.wisedb.controller.NodeController;
import eu.wisebed.wisedb.controller.SetupController;
import eu.wisebed.wisedb.controller.TestbedController;
import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.Link;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wisedb.model.Origin;
import eu.wisebed.wisedb.model.Setup;
import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wisedb.model.TimeInfo;
import eu.wisebed.wiseml.controller.WiseMLController;
import eu.wisebed.wiseml.model.WiseML;
import org.apache.log4j.Logger;
import org.jibx.runtime.JiBXException;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Imports setup entities from the wiseml document into the peristence store.
 */
public final class SetupImporter extends AbstractImporter<Setup> {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(SetupImporter.class);

    /**
     * the Testbed this setup belongs to.
     */
    private Testbed testbed;

    /**
     * Define set of all setup's CAPABILITIES (Nodes & Links) with no duplicate entries.
     */
    private static final Set<Capability> CAPABILITIES = new HashSet<Capability>();


    /**
     * Default constructor.
     */
    public SetupImporter() {
        // empty constructor
    }

    /**
     * Sets the testbed instance and the endpoinurl to find the setup descriptions.
     *
     * @param testbed a testbed instance.
     */
    public void setTestbed(final Testbed testbed) {
        this.testbed = testbed;
        setEndpointUrl(this.testbed.getSessionUrl());
    }

    /**
     * Returns testbed.
     *
     * @return returns testbed.
     */
    public Testbed getTestbed() {
        return testbed;
    }

    /**
     * Returns CAPABILITIES set.
     *
     * @return returns CAPABILITIES set.
     */
    public Set<Capability> getCapabilities() {
        return CAPABILITIES;
    }

    /**
     * Convert the WiseML setup to a WiseDB setup record.
     *
     * @throws JiBXException a JiBXException exception.
     */
    public void convert() throws JiBXException {

        // retrieve setup record record from controllers InputStream
        final WiseMLController cnt = new WiseMLController();
        final WiseML root = cnt.loadWiseMLFromFile(getWiseMlStream());
        final eu.wisebed.wisedb.model.Setup setup = new Setup();
        setup.setCoordinateType(root.getSetup().getCoordinateType());
        setup.setDescription(root.getSetup().getDescription());
        setup.setId(root.getSetup().getId());
        final Origin origin = new Origin();
        origin.setPhi(root.getSetup().getOrigin().getPhi());
        origin.setTheta(root.getSetup().getOrigin().getTheta());
        origin.setX(root.getSetup().getOrigin().getX());
        origin.setY(root.getSetup().getOrigin().getY());
        origin.setZ(root.getSetup().getOrigin().getZ());
        setup.setOrigin(origin);
        final TimeInfo timeInfo = new TimeInfo();
        timeInfo.setDuration(root.getSetup().getTimeinfo().getDuration());
        timeInfo.setEnd(root.getSetup().getTimeinfo().getEnd());
        timeInfo.setStart(root.getSetup().getTimeinfo().getStart());
        timeInfo.setUnit(root.getSetup().getTimeinfo().getUnit());
        setup.setTimeinfo(timeInfo);


        // call convert(setup)
        convert(setup);
    }

    /**
     * Convert the WiseML setup to a WiseDB setup record.
     *
     * @param setup , a setup instance.
     * @throws JiBXException a JiBXException exception.
     */
    public void convert(final Setup setup) throws JiBXException {

        // call convertCollection(list of setups)
        convertCollection(Arrays.asList(setup));
    }

    /**
     * Convert the WiseML setup entries collection to a WiseDB setup records.
     *
     * @param collection , collection of setup entries.
     * @throws JiBXException a JiBXException exception.
     */
    public void convertCollection(final Collection<Setup> collection) throws JiBXException {

        // set entity collection
        setEntities(collection);

        // import records to db
        for (Setup setup : getEntities()) {

            // import setup
            SetupController.getInstance().add(setup);
            LOGGER.debug("Setup imported to DB.");

            // set link,node, CAPABILITIES and setup
            setNodeLinkSetup(setup);

            // reset NodeLink Capabilities
            resetNodeLinkCapabilities(setup);

            // set the testbed setup relation
//            testbed.setSetup(setup);
//
//            // update testbed db
//            TestbedController.getInstance().update(testbed);
//            LOGGER.debug("Testbed updated");

        }
    }

    /**
     * Sets setup for nodes and links.
     *
     * @param setup , a setup instance.
     */
    public void setNodeLinkSetup(final Setup setup) {

        final List<Node> nodes = NodeController.getInstance().list(TestbedController.getInstance().getBySetup(setup));
        if (nodes != null) {
            for (Node node : nodes) {

                // set setup for this node
                node.setSetup(setup);

                // add this node capability to the CAPABILITIES set
                //TODO
//                for (Capability capability : node.getCapabilities()) {
//                    CAPABILITIES.add(capability);
//                }
            }
        }

        final List<Link> links = LinkController.getInstance().list(TestbedController.getInstance().getBySetup(setup));
        if (links != null) {
            for (Link link : links) {

                // set setup for this link
                link.setSetup(setup);

                // add this link's CAPABILITIES to the CAPABILITIES set
                for (String capabilityStr : LinkController.getInstance().listLinkCapabilities(link)) {
                    CAPABILITIES.add(CapabilityController.getInstance().getByID(capabilityStr));
                }
            }
        }
    }

    /**
     * Reset the CAPABILITIES for links and nodes in order to match the unique CAPABILITIES set of this importer.
     *
     * @param setup , a setup instance
     */
    public void resetNodeLinkCapabilities(final Setup setup) {

        // CAPABILITIES must be unique objects so nodes & links must point to the set's entities
        for (Capability capability : CAPABILITIES) {
            final List<Node> nodes = NodeController.getInstance().list(TestbedController.getInstance().getBySetup(setup));
//            if (nodes != null) {
//                for (Node node : nodes) {
//                    if (node.getCapabilities() != null && node.getCapabilities().contains(capability)) {
//                        node.getCapabilities().remove(capability);
//                        node.getCapabilities().add(capability);
//                    }
//                }
//            }
            final List<Link> links = LinkController.getInstance().list(TestbedController.getInstance().getBySetup(setup));
            if (links != null) {
                for (Link link : links) {
                    List<String> capabilites = LinkController.getInstance().listLinkCapabilities(link);
                    if (capabilites != null && capabilites.contains(capability.getName())) {
                        LinkCapabilityController.getInstance().delete(link, capability);
                        LinkCapabilityController.getInstance().add(link, capability);
                    }
                }
            }
        }
    }
}
