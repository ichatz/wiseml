package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.LinkReadingController;
import eu.wisebed.wisedb.controller.NodeController;
import eu.wisebed.wiseml.model.setup.Node;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.Iterator;

public class AddLinkReading {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = org.apache.log4j.Logger.getLogger(AddLinkReading.class);

    public static void main(String args[]) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();

        try {

            // a valid urnPrefix for CTI's testbed
            final String urnPrefix = "urn:wisebed:ctitestbed:";

            // a valid id for CTI's testbed
            final int testbedId = 1;

            // source node id
            Iterator<Node> nodeIt = NodeController.getInstance().list().iterator();
            Node source = nodeIt.next();
            final String sourceId = source.getId();

            // target node id
            Node target = nodeIt.next();
            final String targetId = target.getId();

            // link capability name
            final String capabilityName = "status1";

            // reading value
            final double reading = -11.0;

            // rssi
            final double rssi = 0.0;

            // timestamp
            final Date timestamp = new Date();

            LOGGER.debug("Selected node : " + sourceId);
            LOGGER.debug("Selected node : " + targetId);
            LOGGER.debug("Capability for link : " + capabilityName);

            // insert reading
            LinkReadingController.getInstance().insertReading(sourceId, targetId, capabilityName, testbedId, reading, null, rssi, timestamp);


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            LOGGER.fatal(e);
            System.exit(-1);
        } finally {
            // always close session
            HibernateUtil.getInstance().closeSession();
        }
    }
}
