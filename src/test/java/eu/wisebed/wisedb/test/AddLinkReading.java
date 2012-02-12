package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.LinkReadingController;
import eu.wisebed.wisedb.controller.NodeController;
import eu.wisebed.wisedb.model.Node;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.Iterator;

/**
 * Adds a new Link Reading to the database.
 * If the Link does not exist it is generated.
 * If the Capability does not exist it is generated.
 * If the LinkCapability does not exist it is generated.
 */
public class AddLinkReading {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = org.apache.log4j.Logger.getLogger(AddLinkReading.class);

    public static void main(final String args[]) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        final Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();

        try {

            // an id for testbed
            final int testbedId = 18;

            // source node id
            final Iterator<Node> nodeIt = NodeController.getInstance().list().iterator();
            final Node source = nodeIt.next();
            final String sourceId = source.getId();

            // target node id
            final Node target = nodeIt.next();
            final String targetId = target.getId();

            // link capability name
            final String capabilityName = "blah3";

            // reading value
            final double reading = 7.0;

            // timestamp
            final Date timestamp = new Date();

            LOGGER.debug("Selected node : " + sourceId);
            LOGGER.debug("Selected node : " + targetId);
            LOGGER.debug("Capability for link : " + capabilityName);

            // insert reading
            LinkReadingController.getInstance().insertReading(sourceId, targetId, capabilityName, testbedId, reading, null, timestamp);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            LOGGER.fatal(e);
            e.printStackTrace();
            System.exit(-1);
        } finally {
            // always close session
            HibernateUtil.getInstance().closeSession();
        }
    }
}
