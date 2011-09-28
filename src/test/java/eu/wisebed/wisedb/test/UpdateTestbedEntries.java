package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.TestbedController;
import eu.wisebed.wisedb.importer.TestbedImporter;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.List;

public class UpdateTestbedEntries {
    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(UpdateTestbedEntries.class);

    public static void main(String[] args) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();

        try {

            List<Testbed> testbeds = TestbedController.getInstance().list();

            // Constructe a TestbedImporter
            final TestbedImporter tImp = new TestbedImporter();

            // import to db
            for(Testbed testbed : testbeds){
                tImp.update(testbed);
            }
            tx.commit();
        } finally {
            // always close session
            HibernateUtil.getInstance().closeSession();
        }
    }
}
