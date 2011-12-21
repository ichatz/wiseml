package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.TestbedController;
import eu.wisebed.wisedb.importer.SetupImporter;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.List;

public class ImportSetupEntriesFromTestbed {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(ImportSetupEntriesFromTestbed.class);

    public static void main(String[] args) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();

        try {

            // Retrieve list of available Testbeds in wisedb
            List<Testbed> testbedList = TestbedController.getInstance().list();

            // Construct a SetupImporter
            final SetupImporter sImp = new SetupImporter();

            for (Testbed tb : testbedList) {

                // set the testbed of the setup to be imported
                sImp.setTestbed(tb);

                // Connect to remote endpoint (url already passed in the importer)
                sImp.connect();

                // import by the convert method
                sImp.convert();
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
