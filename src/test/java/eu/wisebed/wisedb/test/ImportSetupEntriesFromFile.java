package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.importer.SetupImporter;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

public class ImportSetupEntriesFromFile {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(ImportSetupEntriesFromFile.class);

    public static void main(String[] args) throws Exception {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        try {
            // Construct a SetupImporter
            final SetupImporter sImp = new SetupImporter();

            // open local file
            try {
                sImp.open("/Developer/whantana.Projects/github/wiseml/src/test/resources/telosB_short.wiseml");
            } catch (Exception e) {
                LOGGER.error(e);
                System.exit(-1);
            }

            // import to db
            sImp.convert();
            tx.commit();
        } finally {
            tx.rollback();
            // always close session
            HibernateUtil.getInstance().closeSession();
        }
    }
}
