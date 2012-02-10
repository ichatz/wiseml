package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.NodeController;
import eu.wisebed.wisedb.model.Node;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

public class OrderListingOfReadings {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(OrderListingOfReadings.class);

    public static void main(String args[]) {
        HibernateUtil.connectEntityManagers();
        Transaction trans = HibernateUtil.getInstance().getSession().beginTransaction();

        // get first node
        Node thisNode = (Node) NodeController.getInstance().list().iterator().next();

        // get nodes capability
//        for (Object thisC : thisNode.getCapabilities().toArray()) {
//            eu.wisebed.wiseml.model.setup.Capability thisCap= (eu.wisebed.wiseml.model.setup.Capability) thisC;
//            LOGGER.debug("Node : " + thisNode.getId() + " Capability : " + thisCap.getName());
//            // get node reading controller
//            NodeReadingController nrc = NodeReadingController.getInstance();
//
//            List<NodeReading> myReadings = nrc.listNodeReadings(thisNode, thisCap);
//            for (NodeReading myReading : myReadings) {
//                LOGGER.debug("\t" + myReading.getTimestamp() + "\t" + myReading.getReading());
//            }
//        }
    }

}
