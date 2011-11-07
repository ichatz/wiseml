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
import org.apache.log4j.Logger;

public class Timestamp2RDF extends Timestamp {

    private Model model;
    private String uri;
    private static Logger LOGGER = Logger.getLogger(Timestamp2RDF.class);


    public Timestamp2RDF(Timestamp item) {
        this.setDisableLink(item.getDisableLink());
        this.setDisableNode(item.getDisableNode());
        this.setEnableLink(item.getEnableLink());
        this.setEnableNode(item.getEnableNode());
        this.setLink(item.getLink());
        this.setMessage(item.getMessage());
        this.setNode(item.getNode());
        this.setValue(item.getValue());
    }

    public Resource exportRDF(Model theModel, String uri) {

        // set the Jena model reference
        model = theModel;

        Resource resTS = model.createResource(uri + "TimeStamp");
        Bag nodesOfTS = model.createBag(uri + "NodesOfTimeStamp" + "_" + this.getValue());
        Bag linksOfTS = model.createBag(uri + "LinksOfTimeStamp" + "_" + this.getValue());
        model.add(resTS, RDF.type, RDFS.Class);
        Resource newTS = model.createResource(uri + "TimeStamp" + "/" + "TimeStamp_" + this.getValue());
        Property TShasNodes = model.createProperty(uri + "TShasNodes");
        Property TShasLinks = model.createProperty(uri + "TShasLinks");
        newTS.addProperty(TShasNodes, nodesOfTS);
        newTS.addProperty(TShasLinks, linksOfTS);
        model.add(newTS, RDF.type, resTS);


        return newTS;
    }

    public void printDBG() {
        Bag nodesOfTS = model.getBag(uri + "NodesOfTimeStamp" + "_" + this.getValue());
        LOGGER.info("TimeStamp: " + this.getValue());
        LOGGER.info("Readings: " + nodesOfTS.size());

    }

    /* this is the function that adds dynamic information within the timestamps
    reagrding the nodes' beahvior during the experiment
    i.e. it adds the node, its retrieved position (if any) and its data
    the default values are also checked
     */
    public void addRDFNode(Node theNodetoAdd, Model theModel, String uri, Setup theSetup) {

        float defX = 0;
        float defY = 0;
        float defZ = 0;
        int defPhi = 0;
        int defTheta = 0;
        String defKey = null;
        String defValue = null;
        Defaults defs = theSetup.getDefaults();
        if (defs != null) {
            // default node positions
            defX = defs.getNode().getPosition().getX();
            defY = defs.getNode().getPosition().getY();
            defZ = defs.getNode().getPosition().getZ();
            defPhi = defs.getNode().getPosition().getPhi();
            defTheta = defs.getNode().getPosition().getTheta();
            if (defs.getNode().getData() != null) {
                defKey = defs.getNode().getData().get(0).getKey();
                defValue = defs.getNode().getData().get(0).getValue();
            }
        }

        Bag addNodesHere = theModel.getBag(uri + "NodesOfTimeStamp" + "_" + this.getValue());
        Bag nodeBag = theModel.getBag(uri + "Node_" + theNodetoAdd.getId() + "_Bag" + "_for_TimeStamp_" + this.getValue());
        Resource The_Node = theModel.createResource(uri + "Node" + "/" + theNodetoAdd.getId());
        Property NodeDataKey = theModel.createProperty(uri + "NodeDataKey");
        Property NodeDataValue = theModel.createProperty(uri + "NodeDataValue");
        Property positionX = theModel.createProperty(uri + "positionX");
        Property positionY = theModel.createProperty(uri + "positionY");
        Property positionZ = theModel.createProperty(uri + "positionZ");
        Property positionTheta = theModel.createProperty(uri + "positionTheta");
        Property positionPhi = theModel.createProperty(uri + "positionPhi");
        nodeBag.add(The_Node);
        if (theNodetoAdd.getPosition() != null) {
            if (theNodetoAdd.getPosition().getX() == 0) {
                if (defX != 0) {
                    nodeBag.addLiteral(positionX, defX);
                } else {
                    nodeBag.addLiteral(positionX, theNodetoAdd.getPosition().getX());
                }
            }
            if (theNodetoAdd.getPosition().getY() == 0) {
                if (defY != 0) {
                    nodeBag.addLiteral(positionY, defY);
                } else {
                    nodeBag.addLiteral(positionY, theNodetoAdd.getPosition().getY());
                }
            }
            if (theNodetoAdd.getPosition().getZ() == 0) {
                if (defZ != 0) {
                    nodeBag.addLiteral(positionZ, defZ);
                } else {
                    nodeBag.addLiteral(positionZ, theNodetoAdd.getPosition().getZ());
                }
            }
            if (theNodetoAdd.getPosition().getTheta() == 0) {
                if (defTheta != 0) {
                    nodeBag.addLiteral(positionTheta, defTheta);
                } else {
                    nodeBag.addLiteral(positionTheta, theNodetoAdd.getPosition().getTheta());
                }
            }
            if (theNodetoAdd.getPosition().getPhi() == 0) {
                if (defPhi != 0) {
                    nodeBag.addLiteral(positionPhi, defPhi);
                } else {
                    nodeBag.addLiteral(positionPhi, theNodetoAdd.getPosition().getPhi());
                }
            }
        }
        /* adding data key and data value, also cheking the defaults in case
         * returned values are null
         */
        if (theNodetoAdd.getData() != null) {
            for (int counter = 0; counter < theNodetoAdd.getData().size(); counter++) {
                if (theNodetoAdd.getData() == null) {
                    if (defKey != null) {
                        nodeBag.addProperty(NodeDataKey, defKey);
                    }
                    if (defValue != null) {
                        nodeBag.addProperty(NodeDataKey, defValue);
                    }
                } else {
                    nodeBag.addProperty(NodeDataKey, theNodetoAdd.getData().get(counter).getKey());
                    nodeBag.addProperty(NodeDataValue, theNodetoAdd.getData().get(counter).getValue());
                }
            }
        }

        addNodesHere.add(nodeBag);

    }

    public void addRDFLink(Link theLinktoAdd, Model theModel, String uri, Setup theSetup) {
        Resource resLink = model.createResource(uri + "Link");
        model.add(resLink, RDF.type, RDFS.Class);
        Resource newLink = model.createResource(uri + "Link" + "/" + theLinktoAdd.getSource() + "--" + theLinktoAdd.getTarget());
        model.add(newLink, RDF.type, resLink);
        Defaults defs = theSetup.getDefaults();
        Rssi defRssi = null;
        Resource The_Link = theModel.createResource(uri + "Link" + "/" + theLinktoAdd.getSource() + "--" + theLinktoAdd.getTarget());
        Bag addLinksHere = theModel.getBag(uri + "LinksOfTimeStamp" + "_" + this.getValue());
        Bag linkBag = theModel.getBag(uri + "Link_" + theLinktoAdd.getSource() + "--" + theLinktoAdd.getTarget() + "_Bag" + "_for_TimeStamp_" + this.getValue());
        Property RSSI_Datatype = theModel.createProperty(uri + "RSSI_Datatype");
        Property RSSI_Value = theModel.createProperty(uri + "RSSI_Value");
        Property RSSI_Unit = theModel.createProperty(uri + "RSSI_Unit");
        Property LinkDataKey = theModel.createProperty(uri + "LinkDataKey");
        Property LinkDataValue = theModel.createProperty(uri + "LinkDataValue");
        linkBag.add(The_Link);
        if (theLinktoAdd.getData() != null) {
            for (int counter = 0; counter < theLinktoAdd.getData().size(); counter++) {
                linkBag.addProperty(LinkDataKey, theLinktoAdd.getData().get(counter).getKey());
                linkBag.addProperty(LinkDataValue, theLinktoAdd.getData().get(counter).getValue());
            }
        }
        if (theLinktoAdd.getRssi() == null) {
            defRssi = defs.getLink().getRssi();
            if (defRssi != null) {
                linkBag.addProperty(RSSI_Datatype, defRssi.getDatatype());
                linkBag.addProperty(RSSI_Value, defRssi.getValue());
                linkBag.addProperty(RSSI_Unit, defRssi.getUnit());
            }
        } else {
            if (theLinktoAdd.getRssi().getDatatype() != null) {
                linkBag.addProperty(RSSI_Datatype, theLinktoAdd.getRssi().getDatatype());
            }
            if (theLinktoAdd.getRssi().getValue() != null) {
                linkBag.addProperty(RSSI_Value, theLinktoAdd.getRssi().getValue());
            }
            if (theLinktoAdd.getRssi().getUnit() != null) {
                linkBag.addProperty(RSSI_Unit, theLinktoAdd.getRssi().getUnit());
            }
        }

        addLinksHere.add(linkBag);


    }
}
