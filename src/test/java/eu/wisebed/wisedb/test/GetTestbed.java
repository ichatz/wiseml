package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.TestbedController;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

/**
 * Searches for a testbed in the database using its name.
 * Then displays all the testbed related information or an error message.
 */
public class GetTestbed {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(GetTestbed.class);


    public static void main(final String[] args) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        final Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();

        try {
            //A testbed name
            final String name = "dsafg";

            final Testbed testbed = TestbedController.getInstance().getByName(name);
            if (testbed != null) {
                LOGGER.info("name: " + testbed.getName());
                LOGGER.info("id: " + testbed.getId());
                LOGGER.info("description: " + testbed.getDescription());
                LOGGER.info("rsUrl: " + testbed.getRsUrl());
                LOGGER.info("sessionUrl: " + testbed.getSessionUrl());
                LOGGER.info("snaaUrl: " + testbed.getSnaaUrl());
                LOGGER.info("url: " + testbed.getUrl());
                LOGGER.info("urnPrefix: " + testbed.getUrnPrefix());
                LOGGER.info("federated: " + testbed.getFederated());
                LOGGER.info("timeZone: " + testbed.getTimeZone().getDisplayName());
                LOGGER.info("setupId: " + testbed.getSetup().getId());
            } else {
                LOGGER.error("testbed " + name + " does not exist!");
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
