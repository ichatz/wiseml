package eu.wisebed.wisedb.importer;

import eu.wisebed.wisedb.controller.CapabilityController;
import eu.wisebed.wisedb.controller.LinkController;
import eu.wisebed.wisedb.controller.NodeController;
import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.Link;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wiseml.controller.WiseMLController;
import eu.wisebed.wiseml.model.WiseML;
import org.apache.log4j.Logger;
import org.jibx.runtime.JiBXException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Imports capability entities from the wiseml document into the peristence store.
 */
public final class CapabilityImporter extends AbstractImporter<Capability> {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = org.apache.log4j.Logger.getLogger(CapabilityImporter.class);

    /**
     * Convert the WiseML Capability entries to a WiseDB capability records.
     *
     * @throws JiBXException a JibException exception.
     */
    @SuppressWarnings("NullableProblems")
    public void convert() throws JiBXException {

        // retrieve records from controllers InputStream
        final WiseMLController cnt = new WiseMLController();
        final WiseML root = cnt.loadWiseMLFromFile(getWiseMlStream());
        final List<Node> nodeList = (root.getSetup() == null) ? null : NodeController.getInstance().list(root.getSetup().getTestbed());
        final List<Link> linkList = (root.getSetup() == null) ? null : LinkController.getInstance().list(root.getSetup().getTestbed());

        // accumulate all capability records in the set bellow
        final Set<Capability> capabilitySet = new HashSet<Capability>();
        if (nodeList != null) {
            for (Node node : nodeList) {
//                final Iterator<Capability> capIt = node.getCapabilities().iterator();
//                capIt.next().setNodes(null);
//                while (capIt.hasNext()) {
//                    capabilitySet.add(capIt.next());
//                }
            }
        }
        if (linkList != null) {
            for (Link link : linkList) {
                final Iterator<Capability> capIt = CapabilityController.getInstance().list(link).iterator();
                while (capIt.hasNext()) {
                    capabilitySet.add(capIt.next());
                }
            }
        }

        // set entity collection
        setEntities(capabilitySet);

        // import records to db
        for (Capability capability : getEntities()) {
            CapabilityController.getInstance().add(capability);
            LOGGER.debug("Capability (" + capability.getName() + ") imported to db (" + capabilitySet.size() + ")");
        }
    }

    /**
     * Convert the WiseML Capability entries collection to a WiseDB capability records.
     *
     * @param collection , collection of capabilities.
     */
    public void convertCollection(final Collection<Capability> collection) {

        // set entity collection
        setEntities(collection);

        // import records to db
        for (Capability capability : getEntities()) {
            CapabilityController.getInstance().add(capability);
        }
    }
}
