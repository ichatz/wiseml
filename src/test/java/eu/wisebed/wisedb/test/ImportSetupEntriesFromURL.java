package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.importer.SetupImporter;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

/**
 * Retrieves Testbed data and inserts them in the db.
 */
public class ImportSetupEntriesFromURL {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(ImportSetupEntriesFromURL.class);

    public static void main(String[] args) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        try {

            // Construct a SetupImporter and Node Importer
            final SetupImporter sImp = new SetupImporter();
            sImp.setEndpointUrl("http://hercules.cti.gr:8888/sessions");

            // Connect to remote endpoint (url already passed in the importer)
            sImp.connect();

            sImp.convert();
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
