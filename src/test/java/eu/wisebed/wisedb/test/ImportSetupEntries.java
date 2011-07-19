package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.TestbedController;
import eu.wisebed.wisedb.importer.SetupImporter;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Retrieves Testbed data and inserts them in the db.
 */
public class ImportSetupEntries {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(ImportSetupEntries.class);

    public static void main(String[] args) {
        // Initialize hibernate
        HibernateUtil.connectEntityManagers();

        // list of testbeds
        List<Testbed> testbedList = TestbedController.getInstance().list();
        if(testbedList == null || testbedList.size() == 0) {
            LOGGER.debug("No testbeds found persisted");
            System.exit(-1);
        }
        LOGGER.debug("Found : " + testbedList.size() +" testbeds");
        Testbed testbed = testbedList.iterator().next();

        // Construct a SetupImporter
        final SetupImporter sImp = new SetupImporter();
        sImp.setEndpointUrl(testbed.getSessionUrl());

        // Connect to remote endpoint
        sImp.connect();
        sImp.convert();
    }

}
