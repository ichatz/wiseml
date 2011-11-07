package eu.wisebed.rdf;

import com.hp.hpl.jena.rdf.model.Bag;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import eu.wisebed.wiseml.model.setup.Link;
import eu.wisebed.wiseml.model.setup.Node;
import eu.wisebed.wiseml.model.setup.Setup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Setup2RDF extends Setup {


    public Setup2RDF(Setup set) {
        this.setCoordinateType(set.getCoordinateType());
        this.setDefaults(set.getDefaults());
        this.setDescription(set.getDescription());
        this.setId(set.getId());
        this.setInterpolation(set.getInterpolation());
        this.setLink(set.getLink());
        this.setNodes(set.getNodes());
        this.setOrigin(set.getOrigin());
        this.setTimeinfo(set.getTimeinfo());
    }

    public TimeInfo2RDF getTimeinfo2RDF() {
        return (TimeInfo2RDF) this.getTimeinfo();
    }

    public Origin2RDF getOrigin2RDF() {
        return new Origin2RDF(this.getOrigin());
    }

    ///*** RDF ***///
    private Model model;
    private ArrayList theRDFNodes;

    public Resource exportRDF(Model theModel, String uri) {
        // set the Jena model reference
        model = theModel;
        theRDFNodes = new ArrayList();

        Resource resSetup = model.createResource(uri + "Setup");
        model.add(resSetup, RDF.type, RDFS.Class);
        Resource newSetup = model.createResource(uri + "Setup" + "/" + this.getDescription().replace(" ", "_"));

        Property hasDefaults = model.createProperty(uri + "hasDefaults");
        Property hasCoordinateType = model.createProperty(uri + "hasCoordinateType");
        Property hasDescription = model.createProperty(uri + "hasDescription");
        Property hasInterpolation = model.createProperty(uri + "hasInterpolation");
        Property hasTimeInfo = model.createProperty(uri + "hasTimeInfo");
        Property hasOrigin = model.createProperty(uri + "hasOrigin");
        Property hasNodes = model.createProperty(uri + "hasNodes");
        Property hasLinks = model.createProperty(uri + "hasLinks");

        // adding setup nodes into a bag
        Bag setupNodes = model.createBag(uri + "The_Nodes_of_The_Setup");
        List<Node> listOfNodes = this.getNodes();
        Iterator iterNodes = listOfNodes.iterator();

        while (iterNodes.hasNext()) {
            Node2RDF newNode = new Node2RDF((Node) iterNodes.next());
            Resource newRDFNode = newNode.exportRDF(model, uri, this);
            setupNodes.add(newRDFNode);
            theRDFNodes.add(newRDFNode);
        }

        newSetup.addProperty(hasNodes, setupNodes);

        Bag setupLinks = model.createBag(uri + "The_Links_of_The_Setup");
        List<Link> listOfLinks = this.getLink();
        if (listOfLinks != null) {
            Iterator iterLinks = listOfLinks.iterator();

            while (iterLinks.hasNext()) {
                Link2RDF newLink = (Link2RDF) iterLinks.next();
                Resource newRDFLink = newLink.exportRDF(model, uri, this);
                setupLinks.add(newRDFLink);
                theRDFNodes.add(newRDFLink);
            }

            newSetup.addProperty(hasLinks, setupLinks);
        }


        model.add(newSetup, RDF.type, resSetup);
        newSetup.addProperty(hasNodes, setupNodes);
        newSetup.addProperty(hasCoordinateType, (this.getCoordinateType() == null) ? "No_Coordinate_Type" : this.getCoordinateType());
        newSetup.addProperty(hasInterpolation, (this.getInterpolation() == null) ? "NoInterpolation" : this.getInterpolation());
        if (this.getTimeinfo() == null) {
            newSetup.addProperty(hasTimeInfo, "No_Time_Info");
        } else {
            newSetup.addProperty(hasTimeInfo, this.getTimeinfo2RDF().exportRDF(model, uri));
        }
        if (this.getOrigin() == null) {
            newSetup.addProperty(hasOrigin, "No_Origin");
        } else {
            newSetup.addProperty(hasOrigin, this.getOrigin2RDF().exportRDF(model, uri));
        }

        return newSetup;
    }
}
