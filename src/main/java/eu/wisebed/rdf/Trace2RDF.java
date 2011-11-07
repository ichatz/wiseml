package eu.wisebed.rdf;


import com.hp.hpl.jena.rdf.model.Bag;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import eu.wisebed.wiseml.model.scenario.Timestamp;
import eu.wisebed.wiseml.model.setup.Defaults;
import eu.wisebed.wiseml.model.setup.Link;
import eu.wisebed.wiseml.model.setup.Node;
import eu.wisebed.wiseml.model.setup.Rssi;
import eu.wisebed.wiseml.model.setup.Setup;
import eu.wisebed.wiseml.model.trace.Trace;

import java.util.List;

public class Trace2RDF extends Trace {

    private static Model model;
    //private ArrayList theRDFNodes;

    public Trace2RDF(Trace tra) {
        this.setChildren(tra.getChildren());
        this.setId(tra.getId());
        this.setTimestamp(tra.getTimestamp());

    }

    public Resource exportRDF(Model theModel, String uri, Setup theSetup) {
        // set the Jena model reference
        model = theModel;
        float posX = 0;
        float posY = 0;
        float posZ = 0;
        int posTheta = 0;
        int posPhi = 0;

        Resource resTrace = model.createResource(uri + "Trace");
        model.add(resTrace, RDF.type, RDFS.Class);
        Resource newTrace = model.createResource(uri + "Trace" + "/" + "_ID_" + this.getId());
        model.add(newTrace, RDF.type, resTrace);
        Property hasTimestamps = model.createProperty(uri + "hasTimestamps");
        Bag traceTimestamps = model.createBag(uri + "The_Timestamps_of_The_Trace");
        newTrace.addProperty(hasTimestamps, traceTimestamps);
        Defaults defs = theSetup.getDefaults();
        if (defs != null) {
            float defX = defs.getNode().getPosition().getX();
            float defY = defs.getNode().getPosition().getY();
            float defZ = defs.getNode().getPosition().getZ();
            int defPhi = defs.getNode().getPosition().getPhi();
            int defTheta = defs.getNode().getPosition().getTheta();
            Rssi defRssi = defs.getLink().getRssi();
            if (defRssi != null) {
                String defRssiUnit = defRssi.getUnit();
                String defRssiValue = defRssi.getValue();
            }
        }
        List listOfChildren = this.getChildren();
        Timestamp2RDF currentTS = null;
        Object lastItem = null;
        for (Object item : listOfChildren) {
            if (item.getClass().equals(Timestamp.class)) {
                if (lastItem != null) {
                    Resource resCurrentTS = currentTS.exportRDF(model, uri);
                    Bag nodesOfTS = resCurrentTS.getModel().getBag(uri + "NodesOfTimeStamp" + "_" + ((Timestamp) lastItem).getValue());
                    System.out.println("TimeStamp: " + ((Timestamp) lastItem).getValue());
                    System.out.println("Readings: " + nodesOfTS.size());
                }
                currentTS = new Timestamp2RDF((Timestamp) item);
                Resource resCurrentTS = currentTS.exportRDF(model, uri);
                traceTimestamps.add(resCurrentTS);
                lastItem = item;
            }
            if (item.getClass().equals(Node.class)) {
                Node2RDF tempNode = new Node2RDF((Node) item);
                currentTS.addRDFNode(tempNode, model, uri, theSetup);
                tempNode.addTimeStamp(Integer.parseInt(currentTS.getValue()), uri);
            }
            if (item.getClass().equals(Link.class)) {
                Link2RDF tempLink = new Link2RDF((Link) item);
                currentTS.addRDFLink(tempLink, model, uri, theSetup);
                tempLink.addTimeStamp(Integer.parseInt(currentTS.getValue()), uri, model);
            }
        }
        return newTrace;
    }
}
