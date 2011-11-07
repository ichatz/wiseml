package eu.wisebed.wisedb.importer;

import eu.wisebed.wisedb.controller.CapabilityController;
import eu.wisebed.wiseml.controller.WiseMLController;
import eu.wisebed.wiseml.model.WiseML;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Link;
import eu.wisebed.wiseml.model.setup.Node;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CapabilityImporter extends AbstractImporter<Capability> {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = org.apache.log4j.Logger.getLogger(CapabilityImporter.class);

    /**
     * Convert the WiseML Capability entries to a WiseDB capability records.
     */
    public void convert() {

        // retrieve records from controllers InputStream
        final WiseMLController cnt = new WiseMLController();
        WiseML root = cnt.loadWiseMLFromFile(getWiseMlStream());
        List<Node> nodeList = null;
        List<Link> linkList = null;
        if (root.getSetup() != null) {
            nodeList = root.getSetup().getNodes();
            linkList = root.getSetup().getLink();
        }


        // accumulate all capability records in the set bellow
        Set<Capability> capabilitySet = new HashSet<Capability>();
        if (nodeList != null) {
            for (Node node : nodeList) {
                Iterator<Capability> it = node.getCapabilities().iterator();
                it.next().setNodes(null);
                it.next().setNodes(null);
                while (it.hasNext()) {
                    capabilitySet.add(it.next());
                }
            }
        }
        if (linkList != null) {
            for (Link link : linkList) {
                Iterator<Capability> it = link.getCapabilities().iterator();
                it.next().setLinks(null);
                it.next().setLinks(null);
                while (it.hasNext()) {
                    capabilitySet.add(it.next());
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
     * @param collection , collection of capabilities
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
