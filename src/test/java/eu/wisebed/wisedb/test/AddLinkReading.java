package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.*;
import eu.wisebed.wisedb.model.LinkReading;
import eu.wisebed.wisedb.model.NodeReading;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Link;
import eu.wisebed.wiseml.model.setup.Node;
import org.apache.log4j.Logger;

import java.util.*;

public class AddLinkReading {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = org.apache.log4j.Logger.getLogger(AddLinkReading.class);

    public static void main(String args[]){
        try{
            // Initialize hibernate
            HibernateUtil.connectEntityManagers();

            // count of links and capabilities before link insertion
            final int beforeLinks = LinkController.getInstance().list().size();
            final int beforeCaps = CapabilityController.getInstance().list().size();

            // link & lqi cap
            Link link;
            Capability lqi;

            if(beforeLinks == 0) { // if no links are made make one and the respected capability
                // List all nodes. get first one
                List<Node> nodes = NodeController.getInstance().list();
                if(nodes == null || nodes.isEmpty()) return; // no nodes exit
                Node node1 = nodes.iterator().next();
                Node node2 = nodes.iterator().next();
                LOGGER.debug("Selected node : " +  node1.getId());
                LOGGER.debug("Selected node : " +  node2.getId());
                LOGGER.debug("Capability for link : lqi");
                link = new Link();
                link.setSource(node1.getId());
                link.setTarget(node2.getId());
                lqi = new Capability();
                lqi.setName("lqi");
                link.setCapabilities(Arrays.asList(lqi));
                LinkController.getInstance().add(link);
            }else{
                link = LinkController.getInstance().list().iterator().next(); // get first link
                lqi = CapabilityController.getInstance().getByID("lqi");
            }

            // count of links and capabilities after link insertion
            final int afterLinks = LinkController.getInstance().list().size();
            final int afterCaps = CapabilityController.getInstance().list().size();

            // count of link readings before reading insertion
            final int beforeReadings = LinkReadingController.getInstance().list().size();

            // make a new node reading entity
            LinkReading reading = new LinkReading();
            reading.setLink(link);
            reading.setCapability(lqi);
            reading.setReading(10.0);
            reading.setTimestamp(new Date());
            if(link.getReadings() == null){
                link.setReadings(new HashSet<LinkReading>());
            }
            link.getReadings().add(reading);
            if(lqi.getLinkReadings() == null){
                lqi.setLinkReadings(new HashSet<LinkReading>());
            }
            lqi.getLinkReadings().add(reading);
            LinkReadingController.getInstance().add(reading);

            // count of link readings after reading insertion
            final int afterReadings = LinkReadingController.getInstance().list().size();

            LOGGER.debug("Before insertion of Link (Links) : " + beforeLinks);
            LOGGER.debug("Before insertion of Link (Capabilities) : " + beforeCaps);
            LOGGER.debug("After insertion of Link (Links) : " + afterLinks);
            LOGGER.debug("After insertion of Link (Capabilities) : " + afterCaps);
            LOGGER.debug("Before insertion of Reading (readings) : " + beforeReadings);
            LOGGER.debug("After insertion of Reading (readings) : " + afterReadings );
            LOGGER.debug("Link's readings after inseration : " + link.getReadings().size());
            LOGGER.debug("Capability's readings after insertion : " + lqi.getLinkReadings().size());
        }finally {
            // always close session
            HibernateUtil.getInstance().closeSession();
        }
    }
}
