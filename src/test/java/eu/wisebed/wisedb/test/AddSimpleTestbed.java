package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.TestbedController;
import eu.wisebed.wisedb.importer.SetupImporter;
import eu.wisebed.wisedb.importer.TestbedImporter;
import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wiseml.model.setup.Setup;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import org.jibx.runtime.JiBXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Adds a simple Testbed (Not running testbed-runtime).
 */
public class AddSimpleTestbed {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(ImportTestbedEntries.class);


    public static void main(final String[] args) throws IOException {

        // Construct a TestbedImporter and a Setup Importer
        final TestbedImporter tImp = new TestbedImporter();
        final SetupImporter sImp = new SetupImporter();

        // read from keyboard.
        BufferedReader br;
        br = new BufferedReader(new InputStreamReader(System.in) );
        try{
            LOGGER.info("Provide Testbed Name");
            final String testbedName = br.readLine();
            tImp.setName(testbedName);

            LOGGER.info("Provide Testbed Description");
            final String testbedDescription = br.readLine();
            tImp.setDescription(testbedDescription);

            LOGGER.info("Provide Testbed's web page URL");
            final String testbedWebPageUrl = br.readLine();
            tImp.setWebPageUrl(testbedWebPageUrl);

            // Initialize hibernate and begin transaction
            HibernateUtil.connectEntityManagers();
            Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();

            // import to db
            tImp.convert();

            // commmit transaction
            tx.commit();

            // begin transaction
            tx = HibernateUtil.getInstance().getSession().beginTransaction();
            Testbed testbed = TestbedController.getInstance().getByName(testbedName);

            // set the testbed of the setup to be imported
            Setup setup = new Setup();

            // import by the convert method
            sImp.setTestbed(testbed);
            sImp.convert(setup);

        } catch (JiBXException e) {
            e.printStackTrace();
        } finally {
            // always close session
            HibernateUtil.getInstance().closeSession();
        }
    }
}
