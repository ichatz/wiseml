package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.LinkReading;
import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Link;
import eu.wisebed.wiseml.model.setup.Node;
import eu.wisebed.wiseml.model.setup.Rssi;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Projections;

import java.util.Date;
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
                              final Date timestamp){

        // Retrieve testbed by urn
        Testbed testbed = null;
        if (urnPrefix != null) {
            testbed = TestbedController.getInstance().getByUrnPrefix(urnPrefix);
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
            if (testbed != null) {
                source.setSetup(testbed.getSetup());
            }
        }
        Node target = NodeController.getInstance().getByID(targetId);
        if (target == null) {
            // if target node not found in db make it and store it
            target = new Node();
            target.setId(sourceId);
            target.setDescription("description"); // todo provide those ?
            target.setGateway("false");
            target.setProgramDetails("program details");
            if (testbed != null) {
                target.setSetup(testbed.getSetup());
            }
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
            source.getSetup().getLink().add(link);
            if(testbed != null) {
                link.setSetup(testbed.getSetup());
            }

            // store it
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

            CapabilityController.getInstance().add(capability);
        }

        // associate Link with Capability
        if (!link.getCapabilities().contains(capability)) {
            // if link does not contain this capability add it
            link.getCapabilities().add(capability);
        }
        if (!capability.getLinks().contains(link)) {
            // if capability contains this link add it
            capability.getLinks().add(link);
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

    /**
     * Returns the latest link reading date for a given node.
     *
     * @param link, a testbed link.
     * @return the latest node reading date
     */
    public Date getLatestLinkReadingDateAKRIBOPO(final Link link) {
        final Session session = getSessionFactory().getCurrentSession();
        String HQL_QUERY = "select max(timestamp) from LinkReading where source=:source and target=:target";
        Query query = session.createQuery(HQL_QUERY);
        query.setParameter("source", link.getSource());
        query.setParameter("target", link.getTarget());
        query.setMaxResults(1);
        return (Date) query.uniqueResult();
    }

    /**
     * Returns the latest node reading date for a given node.
     *
     * @return the latest node reading date
     */
    public List getLatestLinkReadingDateBOUSIS() {
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(LinkReading.class);
        criteria.setProjection(Projections.projectionList()
                .add(Projections.max("timestamp"))
                .add(Projections.rowCount())
                .add(Projections.groupProperty("source"))
                .add(Projections.groupProperty("target")));
        return criteria.list();
    }


}
