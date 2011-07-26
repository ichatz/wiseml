package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.*;
import eu.wisebed.wisedb.model.LinkReading;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Link;
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

            // source node id
            final String sourceId = "urn:wisebed:ctitestbed:0x995d";

            // target node id
            final String targetId = "urn:wisebed:ctitestbed:0x14e6";

            // link capability name
            final String capabilityName = "status";

            LOGGER.debug("Selected node : " +  sourceId);
            LOGGER.debug("Selected node : " +  targetId);
            LOGGER.debug("Capability for link : " + capabilityName);


            // count of links, capabilities & readings before link insertion
            final int beforeLinks = LinkController.getInstance().list().size();
            final int beforeCaps = CapabilityController.getInstance().list().size();
            final int beforeReadings = LinkReadingController.getInstance().list().size();

            // insert reading
            LinkReadingController.getInstance().insertReading(sourceId,targetId,capabilityName,10.0,new Date());

            // count of links, capabilities & readigns after link insertion
            final int afterLinks = LinkController.getInstance().list().size();
            final int afterCaps = CapabilityController.getInstance().list().size();
            final int afterReadings = LinkReadingController.getInstance().list().size();

            LOGGER.debug("Before insertion of Link (Links) : " + beforeLinks);
            LOGGER.debug("Before insertion of Link (Capabilities) : " + beforeCaps);
            LOGGER.debug("After insertion of Link (Links) : " + afterLinks);
            LOGGER.debug("After insertion of Link (Capabilities) : " + afterCaps);
            LOGGER.debug("Before insertion of Reading (readings) : " + beforeReadings);
            LOGGER.debug("After insertion of Reading (readings) : " + afterReadings );
//            LOGGER.debug("Link's readings after inseration : " + LinkController.getInstance().getByID(sourceId, targetId)
//                    .getReadings().size());
//            LOGGER.debug("Capability's readings after insertion : " + CapabilityController.getInstance().getByID(capabilityName)
//                    .getLinkReadings().size());
        }catch(Exception e){
            LOGGER.fatal(e.getMessage());
            System.exit(-1);
        }finally {
            // always close session
            HibernateUtil.getInstance().closeSession();
        }
    }
}
