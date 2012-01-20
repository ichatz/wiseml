package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.TestbedController;
import eu.wisebed.wisedb.importer.SetupImporter;
import eu.wisebed.wisedb.importer.TestbedImporter;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TimeZone;

/**
 * Add TR Testbed.
 */
public class AddTRTestbed {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(AddTRTestbed.class);


    public static void main(String args[]) throws IOException {

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

            LOGGER.info("Provide Testbed's SNAA URL");
            final String testbedSNAAUrl = br.readLine();
            tImp.setSnaaUrl(testbedSNAAUrl);

            LOGGER.info("Provide Testbed's RS URL");
            final String testbedRSUrl = br.readLine();
            tImp.setSnaaUrl(testbedRSUrl);

            LOGGER.info("Provide Testbed's Session Management URL");
            final String testbedSMUrl = br.readLine();
            tImp.setSessionUrl(testbedSMUrl);

            String yesOrNo;
            do{
                LOGGER.info("Provide \"Yes\" if testbed is federated , otherwise provvide \"No\".");
                yesOrNo = br.readLine();
            }while(!(yesOrNo.equals("Yes") || yesOrNo.equals("No")));
            tImp.setFederated(yesOrNo.equals("Yes"));


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
            LOGGER.info("For testbed : " + testbedName + " we retrieve it's setup through the SessionManagment webservice.");
            tx = HibernateUtil.getInstance().getSession().beginTransaction();
            Testbed testbed = TestbedController.getInstance().getByName(testbedName);

            // import by the convert method
            sImp.setTestbed(testbed);
            sImp.connect();
            sImp.convert();

            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // always close session
            HibernateUtil.getInstance().closeSession();
        }
    }
}