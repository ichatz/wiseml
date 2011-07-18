package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.importer.SetupImporter;
import org.apache.log4j.Logger;

/**
 * Retrieves Testbed data and inserts them in the db.
 */
public class ImportSetupEntries {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(ImportTestbedEntries.class);

    public static void main(String[] args) {
        // Initialize hibernate
        HibernateUtil.connectEntityManagers();

        // Constructe a TestbedImporter
        final SetupImporter sImp = new SetupImporter();
        sImp.setEndpointUrl("http://hercules.cti.gr:8888/sessions");

        // Connect to remote endpoint
        sImp.connect();
        sImp.convert();
    }

}
