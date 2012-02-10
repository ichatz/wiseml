package eu.wisebed.wisedb.importer;

import eu.wisebed.wisedb.controller.LinkController;
import eu.wisebed.wiseml.controller.WiseMLController;
import eu.wisebed.wiseml.model.WiseML;
import eu.wisebed.wisedb.model.Link;
import org.jibx.runtime.JiBXException;

import java.util.Collection;
import java.util.List;

/**
 * Imports link entities from the wiseml document into the peristence store.
 */
public final class LinkImporter extends AbstractImporter<Link> {

    /**
     * a log4j logger to print messages.
     */
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(LinkImporter.class);

    /**
     * Convert the WiseML setup to a WiseDB setup record.
     *
     * @throws JiBXException a JiBXException exception.
     */
    public void convert() throws JiBXException {
        // retrieve setup record record from controllers InputStream
        final WiseMLController cnt = new WiseMLController();
        final WiseML root = cnt.loadWiseMLFromFile(getWiseMlStream());
        final List<Link> linkList = LinkController.getInstance().list(root.getSetup().getTestbed());

        // check for null entry
        if (linkList == null) {
            LOGGER.debug("Could not find list of lists while parsing the WiseML setup entry.");
            return;
        }

        // set as entities
        setEntities(linkList);

        // import records to db
        for (Link link : linkList) {
            LinkController.getInstance().add(link);
            LOGGER.debug("Link (" + link.getSource() + " -> " + link.getTarget() + ") imported to db ("
                    + linkList.size() + ")");
        }
        LOGGER.debug("Links imported to DB (" + linkList.size() + ").");
    }

    /**
     * Convert the WiseML Link entries collection to a WiseDB link records.
     *
     * @param collection , collection of links.
     */
    public void convertCollection(final Collection<Link> collection) {

        // set entity collection
        setEntities(collection);

        // import records to db
        for (Link link : getEntities()) {
            LinkController.getInstance().add(link);
        }
        LOGGER.debug("Links imported to DB (" + collection.size() + ").");
    }
}
