package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.exception.UnknownTestbedException;
import eu.wisebed.wisedb.model.LinkReading;
import eu.wisebed.wisedb.model.LinkReadingStat;
import eu.wisebed.wisedb.model.NodeReading;
import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wiseml.model.setup.*;
import org.hibernate.Criteria;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
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
     * @throws eu.wisebed.wisedb.exception.UnknownTestbedException , exception that occurs when the urnPrefix is unknown
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
    }

    /**
     * Return list of readings for a selected link and capability.
     *
     * @param link       , link of a testbed.
     * @param capability , capability of a link
     * @return a list with link readings for a link/capability combination
     */
    @SuppressWarnings("unchecked")
    public List<LinkReading> listReadings(final Link link, final Capability capability) {
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria;
        criteria = session.createCriteria(LinkReading.class);
        criteria.createAlias("link", "id");
        criteria.add(Restrictions.eq("id.source", link.getSource()));
        criteria.add(Restrictions.eq("id.target",link.getTarget()));
        criteria.add(Restrictions.eq("capability", capability));
        criteria.addOrder(Order.asc("timestamp"));
        return (List<LinkReading>) criteria.list();
    }

    /**
     * Returns the readings count for a link.
     *
     * @param link , a link .
     * @return the count of this link.
     */
    public Long getReadingsCount(final Link link){
        final org.hibernate.Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(LinkReading.class);
        criteria.createAlias("link", "id");
        criteria.add(Restrictions.eq("id.source", link.getSource()));
        criteria.add(Restrictions.eq("id.target",link.getTarget()));
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
    public Map<Capability,Long> getReadingsCountPerCapability(final Link link){
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(LinkReading.class);
        criteria.createAlias("link", "id");
        criteria.add(Restrictions.eq("id.source", link.getSource()));
        criteria.add(Restrictions.eq("id.target",link.getTarget()));
        criteria.setProjection(Projections.projectionList()
                .add(Projections.rowCount())
                .add(Projections.property("capability"))
                .add(Projections.groupProperty("capability"))
        );
        HashMap<Capability,Long> resultMap = new HashMap<Capability, Long>();
        List<Object> results = criteria.list();
        for(Object result : results){
            Object[] row = (Object[]) result;
            resultMap.put((Capability)row[1],(Long)row[0]);
        }
        return resultMap;
    }

    /**
     * Returns the readings count for a link and a capability.
     *
     * @param link  , a link.
     * @param capability , a capability
     * @return the count of readings for this node and capability.
     */
    public Long getReadingsCount(final Link link,final Capability capability){
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(LinkReading.class);
        criteria.createAlias("link", "id");
        criteria.add(Restrictions.eq("id.source", link.getSource()));
        criteria.add(Restrictions.eq("id.target",link.getTarget()));
        criteria.add(Restrictions.eq("capability",capability));
        criteria.setProjection(Projections.count("link"));
        criteria.setMaxResults(1);
        return (Long) criteria.uniqueResult();
    }

    /**
     * Returns the latest reading update (link,timestamp,row) of any link persisted.
     *
     * @return an object instance list with the rows of the query
     */
    public List<LinkReadingStat> getLatestLinkReadingUpdates() {
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(LinkReading.class);
        criteria.setProjection(Projections.projectionList()
                .add(Projections.groupProperty("link"))
                .add(Projections.max("timestamp"))
                .add(Projections.min("timestamp"))
                .add(Projections.max("reading"))
                .add(Projections.min("reading"))
                .add(Projections.rowCount())
        );
        List<LinkReadingStat> updates = new ArrayList<LinkReadingStat>();
        for (Object obj : criteria.list()) {
            Object[] row = (Object[]) obj;
            final Link link = (Link) row[0];
            final Date latestDate = (Date) row[1];
            final Date earliestDate = (Date) row[2];
            final Double maxReading = (Double) row[3];
            final Double minReading = (Double) row[4];
            final Long totalCount = (Long) row[5];

            // reading of latest recording
            Criteria criteria1 = session.createCriteria(LinkReading.class);
            criteria1.setProjection(Projections.projectionList()
                    .add(Projections.property("timestamp"))
                    .add(Projections.property("reading"))
            );
            criteria1.add(Restrictions.eq("link", link));
            criteria1.add(Restrictions.eq("timestamp", latestDate));
            criteria1.setMaxResults(1);
            Object[] row1 = (Object[]) criteria1.uniqueResult();
            final Double latestDateReading = (Double) row1[1];

            // reading of earliest recording
            Criteria criteria2 = session.createCriteria(LinkReading.class);
            criteria2.setProjection(Projections.projectionList()
                    .add(Projections.property("timestamp"))
                    .add(Projections.property("reading"))
            );
            criteria2.add(Restrictions.eq("link", link));
            criteria2.add(Restrictions.eq("timestamp", earliestDate));
            criteria2.setMaxResults(1);
            Object[] row2 = (Object[]) criteria2.uniqueResult();
            final Double earliestDateReading = (Double) row2[1];

            updates.add(new LinkReadingStat(link, latestDate, latestDateReading, earliestDate, earliestDateReading,
                    maxReading, minReading, totalCount));
        }
        return updates;
    }

    /**
     * Returns the latest reading of all links of the provided testbed.
     *
     * @param testbed , a testbed instance
     * @return an object instance list with the rows of the query
     */
    public List<LinkReadingStat> getLatestLinkReadingUpdates(final Testbed testbed) {
        final Session session = getSessionFactory().getCurrentSession();

        Criteria criteria = session.createCriteria(LinkReading.class);
        criteria.setProjection(Projections.projectionList()
                .add(Projections.groupProperty("link"))
                .add(Projections.max("timestamp"))
                .add(Projections.min("timestamp"))
                .add(Projections.max("reading"))
                .add(Projections.min("reading"))
                .add(Projections.rowCount())
        );
        Setup setup = SetupController.getInstance().getByID(testbed.getSetup().getId());

        criteria.add(Restrictions.in("link", setup.getLink()));

        List<LinkReadingStat> updates = new ArrayList<LinkReadingStat>();
        for (Object obj : criteria.list()) {
            Object[] row = (Object[]) obj;

            final Link link = (Link) row[0];
            final Date latestDate = (Date) row[1];
            final Date earliestDate = (Date) row[2];
            final Double maxReading = (Double) row[3];
            final Double minReading = (Double) row[4];
            final Long totalCount = (Long) row[5];

            // reading of latest recording
            Criteria criteria1 = session.createCriteria(LinkReading.class);
            criteria1.setProjection(Projections.projectionList()
                    .add(Projections.property("timestamp"))
                    .add(Projections.property("reading"))
            );
            criteria1.add(Restrictions.eq("link", link));
            criteria1.add(Restrictions.eq("timestamp", latestDate));
            criteria1.setMaxResults(1);
            Object[] row1 = (Object[]) criteria1.uniqueResult();
            final Double latestDateReading = (Double) row1[1];

            // reading of earliest recording
            Criteria criteria2 = session.createCriteria(LinkReading.class);
            criteria2.setProjection(Projections.projectionList()
                    .add(Projections.property("timestamp"))
                    .add(Projections.property("reading"))
            );
            criteria2.add(Restrictions.eq("link", link));
            criteria2.add(Restrictions.eq("timestamp", earliestDate));
            criteria2.setMaxResults(1);
            Object[] row2 = (Object[]) criteria2.uniqueResult();
            final Double earliestDateReading = (Double) row2[1];

            updates.add(new LinkReadingStat(link, latestDate, latestDateReading, earliestDate, earliestDateReading,
                    maxReading, minReading, totalCount));
        }
        return updates;
    }

    /**
     * Returns the latest reading of a specific link
     *
     * @param link , a link
     * @return the latest node reading
     */
    public LinkReadingStat getLatestLinkReadingUpdate(final Link link) {
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(LinkReading.class);
        criteria.add(Restrictions.eq("link", link));
        criteria.setProjection(Projections.projectionList()
                .add(Projections.groupProperty("link"))
                .add(Projections.max("timestamp"))
                .add(Projections.min("timestamp"))
                .add(Projections.max("reading"))
                .add(Projections.min("reading"))
                .add(Projections.rowCount())
        );
        criteria.setMaxResults(1);
        Object[] row = (Object[]) criteria.uniqueResult();
        final Link linkQ = (Link) row[0];
        final Date latestDate = (Date) row[1];
        final Date earliestDate = (Date) row[2];
        final Double maxReading = (Double) row[3];
        final Double minReading = (Double) row[4];
        final Long totalCount = (Long) row[5];

        // reading of latest recording
        Criteria criteria1 = session.createCriteria(LinkReading.class);
        criteria1.setProjection(Projections.projectionList()
                .add(Projections.property("timestamp"))
                .add(Projections.property("reading"))
        );
        criteria1.add(Restrictions.eq("link", linkQ));
        criteria1.add(Restrictions.eq("timestamp", latestDate));
        criteria1.setMaxResults(1);
        Object[] row1 = (Object[]) criteria1.uniqueResult();
        final Double latestDateReading = (Double) row1[1];

        // reading of earliest recording
        Criteria criteria2 = session.createCriteria(LinkReading.class);
        criteria2.setProjection(Projections.projectionList()
                .add(Projections.property("timestamp"))
                .add(Projections.property("reading"))
        );
        criteria2.add(Restrictions.eq("link", linkQ));
        criteria2.add(Restrictions.eq("timestamp", earliestDate));
        criteria2.setMaxResults(1);
        Object[] row2 = (Object[]) criteria2.uniqueResult();
        final Double earliestDateReading = (Double) row2[1];

        return new LinkReadingStat(linkQ, latestDate, latestDateReading, earliestDate, earliestDateReading,
                maxReading, minReading, totalCount);
    }

    /**
     * Returns the latest reading for a specific capability.
     *
     * @param capability , a capability
     * @return returns the latest node reading for a specific capability
     */
    public LinkReadingStat getLatestLinkReadingUpdate(final Capability capability) {
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(LinkReading.class);
        criteria.add(Restrictions.eq("capability", capability));
        criteria.setProjection(Projections.projectionList()
                .add(Projections.groupProperty("link"))
                .add(Projections.max("timestamp"))
                .add(Projections.min("timestamp"))
                .add(Projections.max("reading"))
                .add(Projections.min("reading"))
                .add(Projections.rowCount())
        );
        criteria.setMaxResults(1);
        Object[] row = (Object[]) criteria.uniqueResult();

        final Link link = (Link) row[0];
        final Date latestDate = (Date) row[1];
        final Date earliestDate = (Date) row[2];
        final Double maxReading = (Double) row[3];
        final Double minReading = (Double) row[4];
        final Long totalCount = (Long) row[5];

        // reading of latest recording
        Criteria criteria1 = session.createCriteria(LinkReading.class);
        criteria1.setProjection(Projections.projectionList()
                .add(Projections.property("timestamp"))
                .add(Projections.property("reading"))
        );
        criteria1.add(Restrictions.eq("link", link));
        criteria1.add(Restrictions.eq("timestamp", latestDate));
        criteria1.setMaxResults(1);
        Object[] row1 = (Object[]) criteria1.uniqueResult();
        final Double latestDateReading = (Double) row1[1];

        // reading of earliest recording
        Criteria criteria2 = session.createCriteria(LinkReading.class);
        criteria2.setProjection(Projections.projectionList()
                .add(Projections.property("timestamp"))
                .add(Projections.property("reading"))
        );
        criteria2.add(Restrictions.eq("link", link));
        criteria2.add(Restrictions.eq("timestamp", earliestDate));
        criteria2.setMaxResults(1);
        Object[] row2 = (Object[]) criteria2.uniqueResult();
        final Double earliestDateReading = (Double) row2[1];

        return new LinkReadingStat(link, latestDate, latestDateReading, earliestDate, earliestDateReading,
                maxReading, minReading, totalCount);
    }

    /**
     * Returns the latest reading for a specific link & capability.
     *
     * @param link . a link.
     * @param capability , a capability
     * @return the latest reading for a specific link & capability.
     */
    public LinkReadingStat getLatestLinkReadingUpdate(final Link link, final Capability capability) {
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(LinkReading.class);
        criteria.add(Restrictions.eq("link", link));
        criteria.add(Restrictions.eq("capability", capability));
        criteria.setProjection(Projections.projectionList()
                .add(Projections.groupProperty("node"))
                .add(Projections.max("timestamp"))
                .add(Projections.min("timestamp"))
                .add(Projections.max("reading"))
                .add(Projections.min("reading"))
                .add(Projections.rowCount())
        );
        criteria.setMaxResults(1);
        Object[] row = (Object[]) criteria.uniqueResult();
        final Link linkQ = (Link) row[0];
        final Date latestDate = (Date) row[1];
        final Date earliestDate = (Date) row[2];
        final Double maxReading = (Double) row[3];
        final Double minReading = (Double) row[4];
        final Long totalCount = (Long) row[5];

        // reading of latest recording
        Criteria criteria1 = session.createCriteria(LinkReading.class);
        criteria1.setProjection(Projections.projectionList()
                .add(Projections.property("timestamp"))
                .add(Projections.property("reading"))
        );
        criteria1.add(Restrictions.eq("link", linkQ));
        criteria1.add(Restrictions.eq("timestamp", latestDate));
        criteria1.setMaxResults(1);
        Object[] row1 = (Object[]) criteria1.uniqueResult();
        final Double latestDateReading = (Double) row1[1];

        // reading of earliest recording
        Criteria criteria2 = session.createCriteria(LinkReading.class);
        criteria2.setProjection(Projections.projectionList()
                .add(Projections.property("timestamp"))
                .add(Projections.property("reading"))
        );
        criteria2.add(Restrictions.eq("link", linkQ));
        criteria2.add(Restrictions.eq("timestamp", earliestDate));
        criteria2.setMaxResults(1);
        Object[] row2 = (Object[]) criteria2.uniqueResult();
        final Double earliestDateReading = (Double) row2[1];

        return new LinkReadingStat(linkQ, latestDate, latestDateReading, earliestDate, earliestDateReading,
                maxReading, minReading, totalCount);
    }
}
