package eu.wisebed.wisedb.test;


import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.SetupController;
import eu.wisebed.wisedb.controller.TestbedController;
import eu.wisebed.wisedb.importer.SetupImporter;
import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wisedb.model.Setup;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.List;

public class WiseDBUpdateSetupTestbed {
    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(WiseDBUpdateSetupTestbed.class);

    public static void main(String args[]) {
        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        try {
            List<Setup> setups = SetupController.getInstance().list();
            Setup setup = setups.get(0);
            List<Testbed> testbeds = TestbedController.getInstance().list();
            Testbed testbed = testbeds.get(0);

            // set nodes and links
            SetupImporter sImp = new SetupImporter();
            sImp.setTestbed(testbed);
            sImp.setNodeLinkSetup(setup);
            sImp.resetNodeLinkCapabilities(setup);


            // make relation
            testbed.setSetup(setup);

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
