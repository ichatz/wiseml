package eu.wisebed.wisedb.test;


import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.NodeReadingController;
import eu.wisebed.wisedb.model.NodeReading;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.List;

public class ListReadings {
    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(ListReadings.class);

    public static void main(String args[]) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        try {

            Long readingsCount = NodeReadingController.getInstance().count();
            LOGGER.info("Total Readings : " + readingsCount);

            List<NodeReading> nodeReadings = NodeReadingController.getInstance().list();
            for (final NodeReading nodeReading : nodeReadings) {
                LOGGER.info("Reading : " + nodeReading.getCapability().getCapability().getName() + "," + nodeReading.getReading());
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
