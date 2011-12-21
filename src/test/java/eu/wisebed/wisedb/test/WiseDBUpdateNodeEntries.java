package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.TestbedController;
import eu.wisebed.wisedb.importer.NodeImporter;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Retrieves Testbed data and inserts them in the db.
 */
public class WiseDBUpdateNodeEntries {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(WiseDBUpdateNodeEntries.class);

    public static void main(String[] args) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        try {

            // get testbed
            List<Testbed> testbeds = TestbedController.getInstance().list();
            Testbed testbed = testbeds.get(0);

            // Construct a Node Importer in order to update stuff
            final NodeImporter nImp = new NodeImporter();
            nImp.connect(testbed.getSessionUrl());
            nImp.update();
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
