package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.LinkReadingController;
import eu.wisebed.wisedb.controller.NodeController;
import eu.wisebed.wisedb.controller.NodeReadingController;
import eu.wisebed.wisedb.model.LinkReading;
import eu.wisebed.wisedb.model.NodeReading;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Link;
import eu.wisebed.wiseml.model.setup.Node;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class AddLinkReading {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = org.apache.log4j.Logger.getLogger(AddLinkReading   .class);

    public static void main(String args[]){
        try{
            // Initialize hibernate
            HibernateUtil.connectEntityManagers();

            // List all nodes. get first one
            List<Node> nodes = NodeController.getInstance().list();
            if(nodes == null || nodes.isEmpty()) return; // no nodes exit
            Node node1 = nodes.iterator().next();
            Node node2 = nodes.iterator().next();
            LOGGER.debug("Selected node : " +  node1.getId());
            LOGGER.debug("Selected node : " +  node2.getId());
            Link link = new Link();
            link.setSource(node1.getId());
            link.setTarget(node2.getId());
            Capability lqi = new Capability();
            lqi.setName("lqi");

            // make a new node reading entity
            LinkReading reading = new LinkReading();
            reading.setLink(link);
            reading.setCapability(lqi);
            reading.setReading(10.0);
            reading.setTimestamp(new Date());
            LinkReadingController.getInstance().add(reading);

        }finally {
            // always close session
            HibernateUtil.getInstance().closeSession();
        }
    }
}
