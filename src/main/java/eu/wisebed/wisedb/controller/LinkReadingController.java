package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.LinkReading;
import eu.wisebed.wisedb.model.NodeReading;
import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Link;
import eu.wisebed.wiseml.model.setup.Node;
import eu.wisebed.wiseml.model.setup.Rssi;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Projections;

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
     *
     * @param readingId , id of a reading entry
     */
    public void delete(final int readingId) {
        super.delete(new LinkReading(), readingId);
    }


    /**
     * Insert a links's reading from it's capabilities and make the appropriate relations
     * such as Link-Reading , Capability-reading
     *
     * @param sourceId       , link's source id.
     * @param targetId       , target's source id.
     * @param capabilityName , capability's id.
     * @param urnPrefix      , a testbed urn prefix.
     * @param rssiValue      , the RSSI value of the link.
     * @param readingValue   , value of a sensor reading.
     * @param timestamp      , a timestamp.
     */
    public void insertReading(final String sourceId, final String targetId, final String capabilityName,
                              final String urnPrefix, final double readingValue, final double rssiValue,
                              final Date timestamp) {

        Testbed testbed = TestbedController.getInstance().getByUrnPrefix(urnPrefix);
        if (testbed == null) {
            return; // TODO throw an exception
        }
        // look for node and target
        Node source = NodeController.getInstance().getByID(sourceId);
        if (source == null) {
            // if source node not found in db make it and store it
            source = new Node();
            source.setId(sourceId);
            source.setDescription("description"); // todo provide those ?
            source.setGateway("false");
            source.setProgramDetails("program details");
            source.setReadings(new HashSet<NodeReading>());
            source.setCapabilities(new ArrayList<Capability>());
            source.setSetup(testbed.getSetup());
            NodeController.getInstance().add(source);
        }
        Node target = NodeController.getInstance().getByID(targetId);
        if (target == null) {
            // if target node not found in db make it and store it
            target = new Node();
            target.setId(targetId);
            target.setDescription("description"); // todo provide those ?
            target.setGateway("false");
            target.setProgramDetails("program details");
            target.setReadings(new HashSet<NodeReading>());
            target.setCapabilities(new ArrayList<Capability>());
            target.setSetup(testbed.getSetup());
            NodeController.getInstance().add(target);
        }

        // look for link
        Link link = LinkController.getInstance().getByID(sourceId, targetId);
        if (link == null) {
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
            link.setCapabilities(new ArrayList<Capability>());
            link.setReadings(new HashSet<LinkReading>());
            link.setSetup(testbed.getSetup());
            LinkController.getInstance().add(link);
        }

        // look for capability
        Capability capability = CapabilityController.getInstance().getByID(capabilityName);
        if (capability == null) {
            // if capability not found in db make it and store it
            capability = new Capability();
            capability.setName(capabilityName);
            capability.setDatatype("datatype"); // todo provide those ?
            capability.setDefaultvalue("default value");
            capability.setUnit("unit");
            capability.setNodes(new HashSet<Node>());
            capability.setNodeReadings(new HashSet<NodeReading>());
            capability.setLinks(new HashSet<Link>());
            capability.setLinkReadings(new HashSet<LinkReading>());
            CapabilityController.getInstance().add(capability);
        }

        // associate Link with Capability
        if (!link.getCapabilities().contains(capability)) {
            // if link does not contain this capability add it
            link.getCapabilities().add(capability);
            LinkController.getInstance().add(link);
        }
        if (!capability.getLinks().contains(link)) {
            // if capability contains this link add it
            capability.getLinks().add(link);
            CapabilityController.getInstance().add(capability);
        }


        // make a new node reading entity
        LinkReading reading = new LinkReading();
        reading.setLink(link);
        reading.setCapability(capability);
        reading.setReading(readingValue);
        reading.setRssiValue(rssiValue);
        reading.setTimestamp(timestamp);

        // add reading
        add(reading);
    }
}
