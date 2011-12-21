package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.TestbedController;
import eu.wisebed.wisedb.model.Testbed;
import org.hibernate.Transaction;

import java.util.TimeZone;

public class SetTimeZones {

    public static void main(String args[]) {
        HibernateUtil.connectEntityManagers();
        Transaction trans = HibernateUtil.getInstance().getSession().beginTransaction();
        try {

            // get Testbeds
            final Testbed testbedWisebedCTI = TestbedController.getInstance().getByID(1);
            final Testbed testbedSantander = TestbedController.getInstance().getByID(2);
            final Testbed testbedCTINetwork = TestbedController.getInstance().getByID(3);
            final Testbed testbedCTIBuilding = TestbedController.getInstance().getByID(4);

            // set their timezone
            testbedCTINetwork.setTimeZone(TimeZone.getTimeZone("GMT+2"));
            testbedWisebedCTI.setTimeZone(TimeZone.getTimeZone("GMT+2"));
            testbedSantander.setTimeZone(TimeZone.getTimeZone("GMT+1"));
            testbedCTIBuilding.setTimeZone(TimeZone.getTimeZone("GMT+2"));

            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            System.exit(-1);
        } finally {
            // always close session
            HibernateUtil.getInstance().closeSession();
        }

    }
}
