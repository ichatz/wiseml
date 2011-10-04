package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.NodeController;
import eu.wisebed.wisedb.controller.NodeReadingController;
import eu.wisebed.wisedb.model.NodeReading;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Node;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.List;

public class OrderListingOfReadings {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(OrderListingOfReadings.class);

    public static void main(String args[]) {
        HibernateUtil.connectEntityManagers();
        Transaction trans = HibernateUtil.getInstance().getSession().beginTransaction();

        // get first node
        Node thisNode = NodeController.getInstance().list().iterator().next();

        // get nodes capability
        for (Capability thisCap : thisNode.getCapabilities()) {
            LOGGER.debug("Node : " + thisNode.getId() + " Capability : " + thisCap.getName());
            // get node reading controller
            NodeReadingController nrc = NodeReadingController.getInstance();

            List<NodeReading> myReadings = nrc.listReadings(thisNode, thisCap);
            for (NodeReading myReading : myReadings) {
                LOGGER.debug("\t" + myReading.getTimestamp() + "\t" + myReading.getReading());
            }
        }
    }

}
