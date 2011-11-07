package eu.wisebed.wisedb.test;


import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.LinkController;
import eu.wisebed.wisedb.controller.LinkReadingController;
import eu.wisebed.wisedb.controller.TestbedController;
import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wiseml.model.setup.Link;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

public class LinkReadingCount {
    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = org.apache.log4j.Logger.getLogger(LinkReadingCount.class);

    public static void main(String args[]) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        try {
            final String urnPrefix = "urn:wisebed:ctitestbed:";
            final Testbed testbed = TestbedController.getInstance().getByUrnPrefix(urnPrefix);
            final Link link = LinkController.getInstance().list(testbed).iterator().next();
            LOGGER.info("Selected Link : [" + link.getSource() + "," + link.getTarget() + "]");
            Long readingsCount = LinkReadingController.getInstance().getLinkReadingsCount(link);
            LOGGER.info("Selected Link : [" + link.getSource() + "," + link.getTarget() + "] readings count :" + readingsCount.intValue());
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
