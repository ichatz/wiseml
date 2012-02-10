package eu.wisebed.wisedb.importer;

import eu.wisebed.wisedb.controller.NodeController;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wiseml.controller.WiseMLController;
import eu.wisebed.wiseml.model.WiseML;
import org.jibx.runtime.JiBXException;

import java.util.Collection;
import java.util.List;

/**
 * Imports node entities from the wiseml document into the peristence store.
 */
public final class NodeImporter extends AbstractImporter<Node> {

    /**
     * a log4j logger to print messages.
     */
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(NodeImporter.class);

    /**
     * Convert the WiseML setup to a WiseDB setup record.
     *
     * @throws JiBXException a JiBXException exception.
     */
    public void convert() throws JiBXException {
        // retrieve setup record record from controllers InputStream
        final WiseMLController cnt = new WiseMLController();
        final WiseML root = cnt.loadWiseMLFromFile(getWiseMlStream());
        final List<Node> nodeList = NodeController.getInstance().list(root.getSetup().getTestbed());


        // check for null entry
        if (nodeList == null) {
            LOGGER.error("Could not find list of nodes while parsing the WiseML setup entry.");
            return;
        }

        // set as entities
        setEntities(nodeList);

        // import records to db
        for (Node node : nodeList) {
            NodeController.getInstance().add(node);
            LOGGER.debug("Node (" + node.getId() + ") imported to db (" + nodeList.size() + ")");
        }
        LOGGER.debug("Nodes imported to DB (" + nodeList.size() + ").");
    }

    /**
     * Update the WiseDB entries from the WiseML setup.
     *
     * @throws JiBXException a JiBXException exception.
     */
    public void update() throws JiBXException {
        // retrieve setup record record from controllers InputStream
        final WiseMLController cnt = new WiseMLController();
        final WiseML root = cnt.loadWiseMLFromFile(getWiseMlStream());
        final List<Node> nodeList = NodeController.getInstance().list(root.getSetup().getTestbed());

        // check for null entry
        if (nodeList == null) {
            LOGGER.error("Could not find list of nodes while parsing the WiseML setup entry.");
            return;
        }

        // set as entities
        setEntities(nodeList);
        // import records to db
        for (Node node : nodeList) {
            NodeController.getInstance().update(node);
            LOGGER.debug("Node (" + node.getId() + ") update to db (" + nodeList.size() + ")");
        }
        LOGGER.debug("Nodes update to DB (" + nodeList.size() + ").");
    }

    /**
     * Convert the WiseML Node entries collection to a WiseDB node records.
     *
     * @param collection , collection of nodes.
     */
    public void convertCollection(final Collection<Node> collection) {

        // set entity collection
        setEntities(collection);

        // import records to db
        for (Node node : getEntities()) {
            NodeController.getInstance().add(node);
        }
        LOGGER.debug("Nodes imported to DB (" + collection.size() + ").");
    }
}
