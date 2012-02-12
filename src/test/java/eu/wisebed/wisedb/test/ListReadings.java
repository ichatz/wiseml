package eu.wisebed.wisedb.test;


import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.LinkReadingController;
import eu.wisebed.wisedb.controller.NodeReadingController;
import eu.wisebed.wisedb.model.LinkReading;
import eu.wisebed.wisedb.model.NodeReading;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Lists All readings of the database.
 */
public class ListReadings {
    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(ListReadings.class);

    public static void main(final String args[]) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        final Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        try {

            final Long nodeReadingsCount = NodeReadingController.getInstance().count();
            LOGGER.info("Total Node Readings : " + nodeReadingsCount);

            final List<NodeReading> nodeReadings = NodeReadingController.getInstance().list();
            for (final NodeReading nodeReading : nodeReadings) {
                LOGGER.info("NodeReading : " + nodeReading.getCapability().getCapability().getName() + "," + nodeReading.getReading());
            }

            final Long linkReadingsCount = LinkReadingController.getInstance().count();
            LOGGER.info("Total Link Readings : " + linkReadingsCount);

            final List<LinkReading> linkReadings = LinkReadingController.getInstance().list();
            for (final LinkReading linkReading : linkReadings) {
                LOGGER.info("LinkReading : " + linkReading.getCapability().getCapability().getName() + "," + linkReading.getReading());
            }

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
