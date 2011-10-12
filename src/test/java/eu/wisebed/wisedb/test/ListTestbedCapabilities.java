package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.CapabilityController;
import eu.wisebed.wisedb.controller.TestbedController;
import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wiseml.model.setup.Capability;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.List;

public class ListTestbedCapabilities {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = org.apache.log4j.Logger.getLogger(ListTestbedCapabilities.class);


    public static void main(String[] args) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();

        try {
            final Testbed testbed = TestbedController.getInstance().getByUrnPrefix("urn:wisebed:ctitestbed:");
            List<Capability> capabilities = CapabilityController.getInstance().list(testbed);
            for(Capability capability : capabilities){
                LOGGER.info(capability.getName() + " " + capability.getNodes() + " " + capability.getLinks());
            }
            tx.commit();
        }catch(Exception e){
            tx.rollback();
            LOGGER.fatal(e);
            System.exit(-1);
        } finally {
            // always close session
            HibernateUtil.getInstance().closeSession();
        }
    }
}
