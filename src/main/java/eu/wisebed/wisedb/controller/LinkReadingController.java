package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.exception.UnknownCapabilityIdException;
import eu.wisebed.wisedb.exception.UnknownNodeIdException;
import eu.wisebed.wisedb.model.LinkReading;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Link;
import eu.wisebed.wiseml.model.setup.Node;
import eu.wisebed.wiseml.model.setup.Rssi;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class LinkReadingController extends AbstractController<LinkReading> {

    /**
     * static instance(ourInstance) initialized as null.
     */
    private static LinkReadingController ourInstance = null;

    /**
     * Public constructor .
     */
    public LinkReadingController() {
        // Does nothing
        super();
    }

    /**
     * LinkReadingController is loaded on the first execution of
     * LinkReadingController.getInstance() or the first access to
     * LinkReadingController.ourInstance, not before.
     *
     * @return ourInstance
     */
    public static LinkReadingController getInstance() {
        synchronized (LinkReadingController.class) {
            if (ourInstance == null) {
                ourInstance = new LinkReadingController();
            }
        }

        return ourInstance;
    }

    /**
     * Listing all the LinkReadings from the database.
     *
     * @return a list of all the entries that exist inside the table LinkReadings.
     */
    public List<LinkReading> list() {
        return super.list(new LinkReading());
    }

    /**
     * Deleting an entry into the database.
     * @param readingId , id of a reading entry
     */
    public void delete(final int readingId){
        super.delete(new LinkReading(),readingId);
    }


    /**
     * Insert a links's reading from it's capabilities and make the appropriate relations
     * such as Link-Reading , Capability-reading
     *
     * @param sourceId , link's source id.
     * @param targetId , target's source id.
     * @param capabilityName , capability's id
     * @param readingValue , value of a sensor reading.
     * @param timestamp , a timestamp.
     * @throws UnknownNodeIdException , cannot find node by id exception.
     * @throws UnknownCapabilityIdException , cannot find capability by id exception.
     */
    public void insertReading(final String sourceId,final String targetId ,final String capabilityName,
                              final double readingValue,final Date timestamp)
            throws UnknownNodeIdException, UnknownCapabilityIdException {
        insertReading(sourceId,targetId,capabilityName,readingValue,0,timestamp);   // todo 0 in rssi value indicates no RSSI given ?
    }

    /**
     * Insert a links's reading from it's capabilities and make the appropriate relations
     * such as Link-Reading , Capability-reading
     *
     * @param sourceId , link's source id.
     * @param targetId , target's source id.
     * @param capabilityName , capability's id.
     * @param rssiValue , the RSSI value of the link.
     * @param readingValue , value of a sensor reading.
     * @param timestamp , a timestamp.
     * @throws UnknownNodeIdException , cannot find node by id exception.
     * @throws UnknownCapabilityIdException , cannot find capability by id exception.
     */
    public void insertReading(final String sourceId,final String targetId ,final String capabilityName,
                              final double readingValue,final double rssiValue,final Date timestamp)
            throws UnknownNodeIdException, UnknownCapabilityIdException {

        // check for nodes if exist
        Node source = NodeController.getInstance().getByID(sourceId);
        if(source == null) throw new UnknownNodeIdException(sourceId);
        Node target = NodeController.getInstance().getByID(targetId);
        if(target == null) throw new UnknownNodeIdException(targetId);


        // look for link
        Link link = LinkController.getInstance().getByID(sourceId, targetId);
        if(link == null){
            // if link not found in db make it and store it
            link = new Link();
            link.setSource(sourceId);
            link.setTarget(targetId);
            link.setEncrypted(false); // todo provide those ?
            link.setVirtual(false);
            Rssi rssi = new Rssi();
            rssi.setDatatype("datatype");
            rssi.setUnit("unit");
            rssi.setValue("0.0");
            link.setRssi(rssi);
            source.getSetup().getLink().add(link);
            link.setSetup(source.getSetup());

            // store it
            LinkController.getInstance().add(link);
        }

        // look for capability
        Capability capability = CapabilityController.getInstance().getByID(capabilityName);
        if(capability == null){
            // if capability not found in db make it and store it
            capability = new Capability();
            capability.setName(capabilityName);
            capability.setDatatype("datatype"); // todo provide those ?
            capability.setDefaultvalue("default value");
            capability.setUnit("unit");

            CapabilityController.getInstance().add(capability);
        }
// TODO check these stuff
//        // associate capability with link . Store association with a link update
//        if(link.getCapabilities() == null){
//            link.setCapabilities(new ArrayList<Capability>());
//        }
//        if(!link.getCapabilities().contains(capability)){
//            link.getCapabilities().add(capability);
//        }
//        LinkController.getInstance().update(link);
//
//        if(capability.getLinks() == null){
//            capability.setLinks(new HashSet<Link>());
//        }
//        if(!capability.getLinks().contains(link)){
//            capability.getLinks().add(link);
//        }

        // make a new node reading entity
        LinkReading reading = new LinkReading();
        reading.setLink(link);
        reading.setCapability(capability);
        reading.setReading(readingValue);
        reading.setRssiValue(rssiValue);
        reading.setTimestamp(timestamp);

// TODO check these stuff
//        // associate reading with link
//        if(link.getReadings() == null){
//            link.setReadings(new HashSet<LinkReading>());
//        }
//        link.getReadings().add(reading);
//
//        // associate reading with capability
//        if(capability.getLinkReadings() == null){
//            capability.setLinkReadings(new HashSet<LinkReading>());
//        }
//        capability.getLinkReadings().add(reading);

        LinkReadingController.getInstance().add(reading);
    }
}
