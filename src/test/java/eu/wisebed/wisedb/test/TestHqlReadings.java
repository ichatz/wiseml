package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.*;
import eu.wisebed.wisedb.model.NodeReading;
import eu.wisebed.wiseml.model.setup.Node;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;


public class TestHqlReadings {
    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = org.apache.log4j.Logger.getLogger(TestHqlReadings.class);


    public static void main(String[] args) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();

        try {

            List<Node> list = NodeController.getInstance().list();

            // Akribopo
            final long t1 = System.currentTimeMillis();
            for (Node node : list) {
                final Date date = NodeReadingController.getInstance().getLatestNodeReadingDateAKRIBOPO(node);
                LOGGER.info(node.getId() + "," + date);
            }
            LOGGER.info("Akribopo : " + (System.currentTimeMillis() - t1));

            // Bousis
            final long t2 = System.currentTimeMillis();
            final List results = NodeReadingController.getInstance().getLatestNodeReadingDateBOUSIS();
            for (Object obj : results) {
                LOGGER.info(obj);
            }
            LOGGER.info("Bousis : " + (System.currentTimeMillis() - t2));


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
