package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.importer.SetupImporter;
import org.apache.log4j.Logger;

public class ImportSetupEntriesFromFile {

     /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(ImportSetupEntriesFromFile.class);

    public static void main(String[] args) {
        // Initialize hibernate
        HibernateUtil.connectEntityManagers();

        // Construct a SetupImporter
        final SetupImporter sImp = new SetupImporter();

        // open and convert
        sImp.open("/Developer/whantana.Projects/github/wiseml/src/test/resources/telosB_short.wiseml");
        sImp.convert();
    }
}
