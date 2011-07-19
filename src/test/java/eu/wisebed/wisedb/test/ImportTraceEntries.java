package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.TestbedController;
import eu.wisebed.wisedb.importer.TraceImporter;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;

import java.util.List;

public class ImportTraceEntries {
    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(ImportTraceEntries.class);

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
        final TraceImporter tImp = new TraceImporter();
        tImp.setEndpointUrl(testbed.getSessionUrl());

        // Connect to remote endpoint
        tImp.connect();
        tImp.convert();
    }
}

