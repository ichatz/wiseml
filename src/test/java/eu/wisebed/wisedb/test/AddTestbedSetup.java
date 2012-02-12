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

/**
 * Adds a simple Testbed (Not running testbed-runtime).
 */
public class AddTestbedSetup {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(AddTestbedSetup.class);


    public static void main(final String[] args) throws IOException {

        // Construct a TestbedImporter and a Setup Importer
        final TestbedImporter tImp = new TestbedImporter();
        final SetupImporter sImp = new SetupImporter();

        // read from keyboard.
        BufferedReader br;
        br = new BufferedReader(new InputStreamReader(System.in));

        try {

            // Initialize hibernate and begin transaction
            HibernateUtil.connectEntityManagers();
            Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
            final String testbedName = "testbed";
            // begin transaction
            LOGGER.info("For testbed : " + testbedName + " the default setup will be added");
            tx = HibernateUtil.getInstance().getSession().beginTransaction();
            Testbed testbed = TestbedController.getInstance().getByName(testbedName);

            // set the testbed of the setup to be imported
            Setup setup = new Setup();
            setup.setId(testbed.getId());
            Origin origin = new Origin();
            origin.setPhi((float) 0);
            origin.setTheta((float) 0);
            origin.setX((float) 0);
            origin.setY((float) 0);
            origin.setZ((float) 0);
            setup.setOrigin(origin);
            setup.setTimeinfo(new TimeInfo());
            setup.setCoordinateType("Absolute");
            setup.setDescription("My testbed setup");
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
