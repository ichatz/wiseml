package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.SetupController;
import eu.wisebed.wisedb.controller.TestbedController;
import eu.wisebed.wisedb.importer.SetupImporter;
import eu.wisebed.wisedb.importer.TestbedImporter;
import eu.wisebed.wisedb.model.Origin;
import eu.wisebed.wisedb.model.Setup;
import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wisedb.model.TimeInfo;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TimeZone;

/**
 * Adds a simple Testbed (Not running testbed-runtime).
 */
public class AddSimpleTestbed {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(AddSimpleTestbed.class);


    public static void main(final String[] args) throws IOException {

        // Construct a TestbedImporter and a Setup Importer
        final TestbedImporter tImp = new TestbedImporter();
        final SetupImporter sImp = new SetupImporter();

        // read from keyboard.
        BufferedReader br;
        br = new BufferedReader(new InputStreamReader(System.in));

        try {
            LOGGER.info("Provide Testbed Name");
            final String testbedName = br.readLine();
            tImp.setName(testbedName);

            LOGGER.info("Provide Testbed Description");
            final String testbedDescription = br.readLine();
            tImp.setDescription(testbedDescription);

            LOGGER.info("Provide Testbed's web page URL");
            final String testbedWebPageUrl = br.readLine();
            tImp.setWebPageUrl(testbedWebPageUrl);

            LOGGER.info("Provide Testbed's urnPrefix");
            final String urnPrefix = br.readLine();
            tImp.setUrnPrefix(urnPrefix);

            LOGGER.info("Using your default TimeZone : " + TimeZone.getDefault().getDisplayName());
            tImp.setTimeZone(TimeZone.getDefault());

            // Initialize hibernate and begin transaction
            HibernateUtil.connectEntityManagers();
            Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();

            // import to db
            tImp.convert();

            // commmit transaction
            tx.commit();


            // begin transaction
            LOGGER.info("For testbed : " + testbedName + " the default setup will be added");
            tx = HibernateUtil.getInstance().getSession().beginTransaction();
            Testbed testbed = TestbedController.getInstance().getByUrnPrefix(urnPrefix);

            // set the testbed of the setup to be imported
            Setup setup = new Setup();
            Origin origin = new Origin();
            origin.setPhi((float) 0);
            origin.setTheta((float) 0);
            origin.setX((float) 0);
            origin.setY((float) 0);
            origin.setZ((float) 0);
            setup.setOrigin(origin);
            setup.setTimeinfo(new TimeInfo());
            setup.setCoordinateType("Absolute");
            setup.setDescription("description");
//            setup.setTestbed(testbed);
            testbed.setSetup(setup);
            //update testbed
            TestbedController.getInstance().update(testbed);
            // import by the convert method
            SetupController.getInstance().add(setup);

            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // always close session
            HibernateUtil.getInstance().closeSession();
        }
    }
}
