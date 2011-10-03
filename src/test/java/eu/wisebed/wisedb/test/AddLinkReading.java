package eu.wisebed.wisedb.test;

import com.hp.hpl.jena.tdb.store.NodeId;
import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.CapabilityController;
import eu.wisebed.wisedb.controller.LinkController;
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

            // source node id
            Iterator<Node> nodeIt = NodeController.getInstance().list().iterator();
            Node source = nodeIt.next();
            final String sourceId = source.getId();

            // target node id
            Node target = nodeIt.next();
            final String targetId = target.getId();

            // link capability name
            final String capabilityName = "status";

            LOGGER.debug("Selected node : " + sourceId);
            LOGGER.debug("Selected node : " + targetId);
            LOGGER.debug("Capability for link : " + capabilityName);


            // count of links, capabilities & readings before link insertion
            final int beforeLinks = LinkController.getInstance().list().size();
            final int beforeCaps = CapabilityController.getInstance().list().size();
            final int beforeReadings = LinkReadingController.getInstance().list().size();

            // insert reading
            LinkReadingController.getInstance().insertReading(sourceId, targetId, capabilityName, 10.0, new Date());

            // count of links, capabilities & readigns after link insertion
            final int afterLinks = LinkController.getInstance().list().size();
            final int afterCaps = CapabilityController.getInstance().list().size();
            final int afterReadings = LinkReadingController.getInstance().list().size();

            LOGGER.debug("Before insertion of Link (Links) : " + beforeLinks);
            LOGGER.debug("Before insertion of Link (Capabilities) : " + beforeCaps);
            LOGGER.debug("After insertion of Link (Links) : " + afterLinks);
            LOGGER.debug("After insertion of Link (Capabilities) : " + afterCaps);
            LOGGER.debug("Before insertion of Reading (readings) : " + beforeReadings);
            LOGGER.debug("After insertion of Reading (readings) : " + afterReadings);
//            LOGGER.debug("Link's readings after inseration : " + LinkController.getInstance().getByID(sourceId, targetId)
//                    .getReadings().size());
//            LOGGER.debug("Capability's readings after insertion : " + CapabilityController.getInstance().getByID(capabilityName)
//                    .getLinkReadings().size());
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            LOGGER.fatal(e.getMessage());
            System.exit(-1);
        } finally {
            // always close session
            HibernateUtil.getInstance().closeSession();
        }
    }
}
