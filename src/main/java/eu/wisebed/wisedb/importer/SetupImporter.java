package eu.wisebed.wisedb.importer;

import eu.wisebed.wisedb.controller.CapabilityController;
import eu.wisebed.wisedb.controller.SetupController;
import eu.wisebed.wisedb.controller.TestbedController;
import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wiseml.controller.WiseMLController;
import eu.wisebed.wiseml.model.WiseML;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Link;
import eu.wisebed.wiseml.model.setup.Node;
import eu.wisebed.wiseml.model.setup.Setup;
import org.apache.log4j.Logger;

import java.util.*;


public class SetupImporter extends AbstractImporter<Setup> {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(SetupImporter.class);

    /**
     * the testbed this setup belongs to.
     */
    private String endPointUrl;

    /**
     * Default constructor.
     */
    public SetupImporter() {
        // empty constructor.
    }

    /**
     * Returns the endpoint url set
     * @return ,the testbed instance.
     */
    public String getEndpointUrl(){
        return endPointUrl;
    }

    /**
     * Sets the testbed's endpoin url to find the setup descriptions
     * @param endPointUrl
     */
    public void setEndpointUrl(final String endPointUrl){
        this.endPointUrl = endPointUrl;
    }

    /**
     * Convert the WiseML setup to a WiseDB setup record.
     */
    public void convert(){

        // retrieve setup record record from controllers InputStream
        final WiseMLController cnt = new WiseMLController();
        WiseML root = cnt.loadWiseMLFromFile(getWiseMlStream());
        Setup setup = root.getSetup();

        // if setup is null do nothing
        if(setup == null) return;

        // call convert(setup)
        convert(setup);
    }

    @Override
    public void convertCollection(Collection<Setup> records) {}

    /**
     * Convert the WiseML setup to a WiseDB setup record.
     */

    public void convert(final Setup setup){

        if(setup == null){
            LOGGER.fatal("Setup cannot be null");
            System.exit(-1);
        }

        // define set of all setup's capabilities with no duplicate entries
        Set<Capability> capabilities = new HashSet<Capability>();
        List<Node> nodes = setup.getNodes();
        if(nodes != null && nodes.isEmpty() == false){
            for(Node node: nodes){
                for(Capability capability : node.getCapabilities()){
                    capabilities.add(capability);
                }
            }
        }
        List<Link> links = setup.getLink();
        if(links != null && links.isEmpty() == false){
            for(Link link: links){
                for(Capability capability : link.getCapabilities()){
                    capabilities.add(capability);
                }
            }
        }

        // capabilities must be unique objects so nodes & links must point to the set's entities
        Iterator<Capability> it = capabilities.iterator();
        while(it.hasNext()){
            Capability capability = it.next();
            if(nodes != null && nodes.isEmpty() == false){
                for(Node node:nodes){
                    List<Capability> nodeCapabilities = node.getCapabilities();
                    if(nodeCapabilities.contains(capability)){
                        nodeCapabilities.remove(capability);
                        nodeCapabilities.add(capability);
                    }
                }
            }
            if(links != null && links.isEmpty() == false){
                for(Link link:links){
                    List<Capability> linkCapabilities = link.getCapabilities();
                    if(linkCapabilities.contains(capability)){
                        linkCapabilities.remove(capability);
                        linkCapabilities.add(capability);
                    }
                }
            }
        }
        setEntity(setup);
    }

    public void update(Setup setup){

        // fetch new setup
        final WiseMLController cnt = new WiseMLController();
        WiseML root = cnt.loadWiseMLFromFile(getWiseMlStream());
        Setup setup1 = root.getSetup();

        setup.setDefaults(setup1.getDefaults());
        setup.setDescription(setup1.getDescription());
        setup.setInterpolation(setup1.getInterpolation());
        setup.setCoordinateType(setup1.getCoordinateType());
        setup.setNodes(setup1.getNodes());
        setup.setLink(setup1.getLink());
        setup.setTimeinfo(setup1.getTimeinfo());
        setup.setOrigin(setup1.getOrigin());

        convert(setup);
    }
}