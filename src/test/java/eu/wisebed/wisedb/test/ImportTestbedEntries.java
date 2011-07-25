package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.importer.TestbedImporter;
import org.apache.log4j.Logger;

/**
 * Retrieves Testbed data and inserts them in the db.
 */
public class ImportTestbedEntries {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(ImportTestbedEntries.class);

    public static void main(String[] args) {
        try{
            // Initialize hibernate
            HibernateUtil.connectEntityManagers();

            // Constructe a TestbedImporter
            final TestbedImporter tImp = new TestbedImporter();

            // must set attributes before importing to db
            tImp.setTestbedName("CTI main testbed");
            tImp.setTestbedUrnPrefix("urn:wisebed:ctitestbed:");
            tImp.setTestbedDescription("This is the description WiseML file of the RACTI testbed in Patras Greece containing" +
                    " iSense telosB and xbee sensor nodes equiped with temperature light infrared humidity Wind Speed Wind " +
                    "Direction and Air Quality Sensors");
            tImp.setTestbedFederated(false);
            tImp.setTestbedRsUrl("http://hercules.cti.gr:8888/rs");
            tImp.setTestbedSnaaUrl("http://hercules.cti.gr:8890/snaa/shib1");
            tImp.setTestbedSessionUrl("http://hercules.cti.gr:8888/sessions");

            // import to db
            tImp.convert();
        }finally{
            // always close session
            HibernateUtil.getInstance().closeSession();
        }
    }

}
