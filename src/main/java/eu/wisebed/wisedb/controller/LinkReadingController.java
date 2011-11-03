package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.exception.UnknownTestbedException;
import eu.wisebed.wisedb.model.*;
import eu.wisebed.wiseml.model.setup.*;
import org.hibernate.Criteria;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.*;

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
     * @throws eu.wisebed.wisedb.exception.UnknownTestbedException
     *          , exception that occurs when the urnPrefix is unknown
     */
    public void insertReading(final String sourceId, final String targetId, final String capabilityName,
                              final String urnPrefix, final double readingValue, final double rssiValue,
                              final Date timestamp) throws UnknownTestbedException {

        Testbed testbed = TestbedController.getInstance().getByUrnPrefix(urnPrefix);
        if (testbed == null) {
            throw new UnknownTestbedException(urnPrefix);
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


        // make a new link reading entity
        LinkReading reading = new LinkReading();
        reading.setLink(link);
        reading.setCapability(capability);
        reading.setReading(readingValue);
        reading.setRssiValue(rssiValue);
        reading.setTimestamp(timestamp);

        // add reading
        add(reading);

        // get last link reading for link and capability if not found create one
        LastLinkReading lastLinkReading = LastLinkReadingController.getInstance().getByID(link, capability);
        if (lastLinkReading == null) {
            lastLinkReading = new LastLinkReading();
        }
        lastLinkReading.setReading(readingValue);
        lastLinkReading.setTimestamp(timestamp);
        lastLinkReading.setLink(link);
        lastLinkReading.setCapability(capability);
        LastLinkReadingController.getInstance().add(lastLinkReading);
    }

    /**
     * Returns the readings count for a link.
     *
     * @param link , a link .
     * @return the count of this link.
     */
    public Long getLinkReadingsCount(final Link link) {
        final org.hibernate.Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(LinkReading.class);
        criteria.createAlias("link", "id");
        criteria.add(Restrictions.eq("id.source", link.getSource()));
        criteria.add(Restrictions.eq("id.target", link.getTarget()));
        criteria.setProjection(Projections.count("id"));
        criteria.setMaxResults(1);
        return (Long) criteria.uniqueResult();
    }

    /**
     * Returns the readings count for a link  per a capability.
     *
     * @param link, a link.
     * @return a map containing readings of a link per capability.
     */
    @SuppressWarnings("unchecked")
    public Map<Capability, Long> getLinkReadingsCountMap(final Link link) {
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(LinkReading.class);
        criteria.createAlias("link", "id");
        criteria.add(Restrictions.eq("id.source", link.getSource()));
        criteria.add(Restrictions.eq("id.target", link.getTarget()));
        criteria.setProjection(Projections.projectionList()
                .add(Projections.rowCount())
                .add(Projections.property("capability"))
                .add(Projections.groupProperty("capability"))
        );
        HashMap<Capability, Long> resultMap = new HashMap<Capability, Long>();
        List<Object> results = criteria.list();
        for (Object result : results) {
            Object[] row = (Object[]) result;
            resultMap.put((Capability) row[1], (Long) row[0]);
        }
        return resultMap;
    }

    /**
     * Returns the readings count for a capability per link in a testbed.
     *
     * @param capability , a capability .
     * @param testbed    , a testbed.
     * @return a map containing readings of a capability per link.
     */
    public Map<Link, Long> getLinkCapabilityReadingsCountPerLink(final Capability capability, final Testbed testbed) {
        final org.hibernate.Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(LinkReading.class);
        criteria.createAlias("link", "li");
        criteria.add(Restrictions.eq("li.setup", testbed.getSetup()));
        criteria.add(Restrictions.eq("capability", capability));
        criteria.setProjection(Projections.projectionList()
                .add(Projections.rowCount())
                .add(Projections.property("link"))
                .add(Projections.groupProperty("link"))
        );
        HashMap<Link, Long> resultMap = new HashMap<Link, Long>();
        List results = criteria.list();
        for (Object result : results) {
            Object[] row = (Object[]) result;
            resultMap.put((Link) row[1], (Long) row[0]);
        }
        return resultMap;
    }

    /**
     * Returns the node readings count for a capability in a testbed.
     *
     * @param capability , a capability.
     * @param testbed    , a testbed .
     * @return total node readings count for a given capability.
     */
    public Long getLinkCapabilityReadingsCount(final Capability capability, final Testbed testbed) {
        final org.hibernate.Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(LinkReading.class);
        criteria.createAlias("link", "li");
        criteria.add(Restrictions.eq("li.setup", testbed.getSetup()));
        criteria.add(Restrictions.eq("capability", capability));
        criteria.setProjection(Projections.count("capability"));
        criteria.setMaxResults(1);
        return (Long) criteria.uniqueResult();
    }

    /**
     * Returns the latest reading of all links of the provided testbed.
     *
     * @param testbed , a testbed instance
     * @return an object instance list with the rows of the query
     */
    public List<LinkReadingStat> getLinkReadingStats(final Testbed testbed) {
        final Session session = getSessionFactory().getCurrentSession();

        Setup setup = SetupController.getInstance().getByID(testbed.getSetup().getId());

        Criteria criteria = session.createCriteria(LinkReading.class);
        criteria.add(Restrictions.in("link", setup.getLink()));
        criteria.setProjection(Projections.projectionList()
                .add(Projections.groupProperty("link"))
                .add(Projections.max("reading"))
                .add(Projections.min("reading"))
                .add(Projections.rowCount())
        );

        List<LinkReadingStat> updates = new ArrayList<LinkReadingStat>();
        for (Object obj : criteria.list()) {
            Object[] row = (Object[]) obj;
            final Link link = (Link) row[0];
            final LastLinkReading llr = LastLinkReadingController.getInstance().getLatestLinkReading(link);
            Date lastTimestamp = null;
            Double lastReading = null;
            if (llr != null) {
                lastTimestamp = llr.getTimestamp();
                lastReading = llr.getReading();
            }
            final Double maxReading = (Double) row[1];
            final Double minReading = (Double) row[2];
            final Long totalCount = (Long) row[3];

            updates.add(new LinkReadingStat(link, lastTimestamp, lastReading, maxReading,minReading, totalCount));
        }
        return updates;
    }

}
