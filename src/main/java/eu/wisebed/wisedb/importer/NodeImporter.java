package eu.wisebed.wisedb.importer;

import eu.wisebed.wisedb.controller.NodeController;
import eu.wisebed.wiseml.controller.WiseMLController;
import eu.wisebed.wiseml.model.WiseML;
import eu.wisebed.wiseml.model.setup.Node;
import org.jibx.runtime.JiBXException;

import java.util.Collection;
import java.util.List;

public class NodeImporter extends AbstractImporter<Node> {

    /**
     * a log4j logger to print messages.
     */
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(NodeImporter.class);

    /**
     * Convert the WiseML setup to a WiseDB setup record.
     */
    public void convert() throws JiBXException {
        // retrieve setup record record from controllers InputStream
        final WiseMLController cnt = new WiseMLController();
        WiseML root = cnt.loadWiseMLFromFile(getWiseMlStream());
        List<Node> nodeList = root.getSetup().getNodes();

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
     */
    public void update() throws JiBXException {
        // retrieve setup record record from controllers InputStream
        final WiseMLController cnt = new WiseMLController();
        WiseML root = cnt.loadWiseMLFromFile(getWiseMlStream());
        List<Node> nodeList = root.getSetup().getNodes();

        // check for null entry
        if (nodeList == null) {
            LOGGER.error("Could not find list of nodes while parsing the WiseML setup entry.");
            return;
        }

        // set as entities
        setEntities(nodeList);
        int count = 0;
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
     * @param collection , collection of nodes
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
