package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.importer.TraceImporter;
import org.apache.log4j.Logger;

public class ImportTraceEntriesFromFile {

     /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(ImportTraceEntriesFromFile.class);

    public static void main(String[] args) {
        // Initialize hibernate
        HibernateUtil.connectEntityManagers();

        // Construct a SetupImporter
        final TraceImporter tImp = new TraceImporter();

        // open and convert
        tImp.open("/Developer/whantana.Projects/github/wiseml/src/test/resources/telosB_short.wiseml");
        tImp.convert();
    }
}
