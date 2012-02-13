package eu.wisebed.wisedb.test;


import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.CapabilityController;
import eu.wisebed.wisedb.controller.LastLinkReadingController;
import eu.wisebed.wisedb.controller.LastNodeReadingController;
import eu.wisebed.wisedb.controller.TestbedController;
import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.LastLinkReading;
import eu.wisebed.wisedb.model.LastNodeReading;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Lists All readings of the database.
 */
public class ListLastReadings {
    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(ListLastReadings.class);

    public static void main(final String args[]) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        final Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        try {

            final Testbed testbed = TestbedController.getInstance().getByID(18);

            final Capability capability = CapabilityController.getInstance().getByID("temp");

            final List<LastNodeReading> lastNodeReadings = LastNodeReadingController.getInstance().getByCapability(testbed, capability);

            LOGGER.info("Total Last Node Readings : " + lastNodeReadings.size());

            for (final LastNodeReading lastNodeReading : lastNodeReadings) {
                LOGGER.info("LastNodeReading : " + lastNodeReading.getNodeCapability().getCapability().getName()
                        + "," + lastNodeReading.getNodeCapability().getNode().getId()
                        + "," + lastNodeReading.getReading()
                        + "," + lastNodeReading.getStringReading()
                );
            }

            final Capability capability2 = CapabilityController.getInstance().getByID("blah3");

            final List<LastLinkReading> lastLinkReadings = LastLinkReadingController.getInstance().getByCapability(testbed, capability2);

            LOGGER.info("Total Last Link Readings : " + lastLinkReadings.size());

            for (final LastLinkReading lastLinkReading : lastLinkReadings) {
                LOGGER.info("LastNodeReading : " + lastLinkReading.getLinkCapability().getCapability().getName()
                        + "," + lastLinkReading.getLinkCapability().getLink().getSource()
                        + "--" + lastLinkReading.getLinkCapability().getLink().getTarget()
                        + "," + lastLinkReading.getReading()
                        + "," + lastLinkReading.getStringReading()
                );
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
