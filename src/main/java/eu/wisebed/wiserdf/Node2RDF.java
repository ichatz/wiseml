package eu.wisebed.wiserdf;

import com.hp.hpl.jena.rdf.model.Bag;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Seq;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Defaults;
import eu.wisebed.wiseml.model.setup.Node;
import eu.wisebed.wiseml.model.setup.Setup;

import java.util.Iterator;
import java.util.List;


public class Node2RDF extends Node {

    private static Model model;
    //private Resource theRDFNode;
    private boolean initialized = false;
    private Seq timestampAppearences;


    public Node2RDF(Node nd) {
        this.setCapabilities(nd.getCapabilities());
        this.setData(nd.getData());
        this.setDescription(nd.getDescription());
        this.setGateway(nd.getGateway());
        this.setId(nd.getId());
        this.setMessage(nd.getMessage());
        this.setNodeType(nd.getNodeType());
        this.setPosition(nd.getPosition());
        this.setProgramDetails(nd.getProgramDetails());
        this.setSetup(nd.getSetup());
    }

    public Resource exportRDF(Model theModel, String uri, Setup theSetupOfNodes) {
        // set the Jena model reference
        model = theModel;
        String defType = null;
        String defDescr = null;
        String defGateway = null;
        timestampAppearences = model.createSeq(uri + "timestampAppearences");

        // if null values are returned for some node properties
        // the node defaults will be used as defined in the setup
        Defaults theDefaults = theSetupOfNodes.getDefaults();
        if (theDefaults != null) {
            Node defaultNode = theDefaults.getNode();
            defType = (defaultNode.getNodeType() == null) ? "NullType" : defaultNode.getNodeType();
            defDescr = (defaultNode.getDescription() == null) ? "NullDescription" : defaultNode.getDescription();
            defGateway = (defaultNode.getGateway() == null) ? "NullGateway" : defaultNode.getGateway();
        }

        // initialize resource and properties
        Resource resNode = model.createResource(uri + "Node");
        Property nodeDescription = model.createProperty(uri + "nodeDescription");
        Property hasCapabilities = model.createProperty(uri + "hasCapabilities");
        //Bag capabilitiesBag = model.createBag(uri + "capabilitiesBag");
        Property hasGateway = model.createProperty(uri + "hasGateway");

        //Property appearsInTimeStamp = model.createProperty(uri + "appearsInTimeStamp");
        Property hasNodeType = model.createProperty(uri + "nodeType");

        model.add(resNode, RDF.type, RDFS.Class);
        Resource newNode = model.createResource(uri + "Node" + "/" + this.getId());
        model.add(newNode, RDF.type, resNode);

        if (this.getDescription() != null) {
            newNode.addProperty(nodeDescription, this.getDescription());
        } else {
            if (defDescr != null) {
                newNode.addProperty(hasGateway, defDescr);
            }
        }


        if (this.getNodeType() != null) {
            newNode.addProperty(hasNodeType, this.getNodeType());
        } else {
            if (defType != null) {
                newNode.addProperty(hasNodeType, defType);
            }
        }

        if (this.getGateway() != null) {
            newNode.addProperty(hasGateway, this.getGateway());
        } else {
            if (defGateway != null) {
                newNode.addProperty(hasGateway, defGateway);
            }
        }

        List<Capability> nodesCaps = null;

        // if null capabilities
        // the default capabilities will be used

        if (this.getCapabilities() == null) {
            nodesCaps = theSetupOfNodes.getDefaults().getNode().getCapabilities();
        } else {
            nodesCaps = this.getCapabilities();
        }

        // checking if we do have any kind of capabilities at the end
        if (nodesCaps != null) {
            Iterator iterCaps = nodesCaps.iterator();
            Bag capabilitiesBag = model.createBag(uri + "capabilities_of_node_" + this.getId());
            while (iterCaps.hasNext()) {
                Capability2RDF newCap = new Capability2RDF((Capability) iterCaps.next());
                Resource resCap = newCap.exportRDF(model, uri);
                capabilitiesBag.add(resCap);
            }

            newNode.addProperty(hasCapabilities, capabilitiesBag);
        }

        //theRDFNode = newNode;
        initialized = true;
        //System.out.println("Initializing Node " + newNode);

        return newNode;

    }

    public void addTimeStamp(int TSValue, String theURI) {
        Property appearsInTimeStamp = model.createProperty(theURI + "nodeAppearsInTimeStamp");
        Resource theTS = model.getResource(theURI + "TimeStamp" + "/" + "TimeStamp_" + TSValue);
        Resource rdfnode = model.getResource(theURI + "Node" + "/" + this.getId());
        rdfnode.addProperty(appearsInTimeStamp, theTS);
    }
}
