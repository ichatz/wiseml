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

            // nodes that made up the links
            List<Node> nodes = NodeController.getInstance().list();
            if(nodes == null || nodes.isEmpty()){
                LOGGER.debug("No nodes found in the database");
                System.exit(-1);
            }
            Node node1 = nodes.iterator().next();
            Node node2 = nodes.iterator().next();
            String node1Id = node1.getId();
            String node2Id = node2.getId();
            String capabilityName = "lqi";

            LOGGER.debug("Selected node : " +  node1Id);
            LOGGER.debug("Selected node : " +  node2Id);
            LOGGER.debug("Capability for link : " + capabilityName);

            // count of links and capabilities before link insertion
            final int beforeLinks = LinkController.getInstance().list().size();
            final int beforeCaps = CapabilityController.getInstance().list().size();

            // look for link
            Link link = LinkController.getInstance().getByID(node1Id, node2Id);
            if(link == null){ // if not found make it
                LOGGER.debug("No links are found. Making this link.");
                LOGGER.debug("Source : " + node1Id);
                LOGGER.debug("Target : " + node2Id);
                link = new Link();
                link.setSource(node1.getId());
                link.setTarget(node2.getId());
                // more data to add if possible ... RSSI n such check wiseml.model.setup.Link
                LinkController.getInstance().add(link);
            }else{
                LOGGER.debug("Link found.");
                LOGGER.debug("Source : " + link.getSource());
                LOGGER.debug("Target : " + link.getTarget());
                LOGGER.debug("Number of registered Capabilities with this node : " + link.getCapabilities().size());
                LOGGER.debug("Number of registered Readings with this node :" + link.getReadings().size());
            }

            // look for capability
            Capability lqi = CapabilityController.getInstance().getByID(capabilityName);
            if(lqi == null){
                LOGGER.debug("No Capability is found. Making this capability");
                LOGGER.debug("Name " + capabilityName);
                lqi = new Capability();
                lqi.setName(capabilityName);
                // more data to add if possible ...  n such check wiseml.model.setup.Capability
            }else{
                LOGGER.debug("Capability found.");
                LOGGER.debug("Name : " + lqi.getName());
                LOGGER.debug("Found in readings : " + lqi.getLinkReadings().size());
                LOGGER.debug("Found in links : " + lqi.getLinks());
            }

            // associate capability with link
            if(link.getCapabilities() == null){
                link.setCapabilities(new ArrayList<Capability>());
            }
            if(link.getCapabilities().contains(lqi) == false){
                link.getCapabilities().add(lqi);
            }
            if(lqi.getLinks() == null){
                lqi.setLinks(new HashSet<Link>());
            }
            lqi.getLinks().add(link);
            LinkController.getInstance().update(link);


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
