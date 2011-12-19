package eu.wisebed.wiserdf;

import com.hp.hpl.jena.rdf.model.Bag;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Link;
import eu.wisebed.wiseml.model.setup.Setup;

import java.util.Iterator;
import java.util.List;

public class Link2RDF extends Link {
    private static Model model;

    public Link2RDF(Link item) {
        this.setCapabilities(item.getCapabilities());
        this.setData(item.getData());
        this.setEncrypted(item.isEncrypted());
        this.setReadings(item.getReadings());
        this.setRssi(item.getRssi());
        this.setSetup(item.getSetup());
        this.setSource(item.getSource());
        this.setTarget(item.getTarget());
        this.setVirtual(item.isVirtual());
    }

    public Resource exportRDF(Model theModel, String uri, Setup theSetupOfLinks) {
        // set the Jena model reference
        model = theModel;

        // initialize resource and properties
        Resource resLink = model.createResource(uri + "Link");
        Property hasLinkCapabilities = model.createProperty(uri + "hasLinkCapabilities");
        //Property hasLinkData = model.createProperty(uri + "hasLinkData");
        //Property hasRSSI = model.createProperty(uri + "hasRSSI");
        Property hasSource = model.createProperty(uri + "hasSource");
        Property hasTarget = model.createProperty(uri + "hasTarget");
        Property isEncrypted = model.createProperty(uri + "isEncrypted");
        Property isVirtual = model.createProperty(uri + "isVirtual");

        //System.out.println(this.getSource());
        //System.out.println(this.getTarget());

        model.add(resLink, RDF.type, RDFS.Class);
        Resource newLink = model.createResource(uri + "Link" + "/" + this.getSource() + "--" + this.getTarget());
        model.add(newLink, RDF.type, resLink);

        newLink.addProperty(isEncrypted, (this.isEncrypted()) ? "TRUE" : "FALSE");
        newLink.addProperty(isVirtual, (this.isVirtual()) ? "TRUE" : "FALSE");
        newLink.addProperty(hasSource, this.getSource());
        newLink.addProperty(hasTarget, this.getTarget());
        //newLink.addProperty(hasRSSI, this.getRssi() == null ? "NullRSSI" : this.getRssi().toString());

        // if null capabilities
        // the default capabilities will be used

        List<Capability> linkCaps = null;

        if ((this.getCapabilities() != null) && theSetupOfLinks.getDefaults() != null) {
            if (this.getCapabilities() == null) {
                linkCaps = theSetupOfLinks.getDefaults().getLink().getCapabilities();
            } else {
                linkCaps = this.getCapabilities();
            }
        }


        if (linkCaps != null) {
            Iterator iterCaps = linkCaps.iterator();
            Bag capabilitiesBag = model.createBag(uri + "capabilities_of_link_" + this.getSource() + "_" + this.getTarget());
            while (iterCaps.hasNext()) {
                Capability2RDF newCap = (Capability2RDF) iterCaps.next();
                Resource resCap = newCap.exportRDF(model, uri);
                capabilitiesBag.add(resCap);
            }


            newLink.addProperty(hasLinkCapabilities, capabilitiesBag);
        }
        // removed the following code that exports data - we assume data is only returned in trace
        // i.e. dynamically, within the addRDFLink function called in TimeStamp objects - this may change

        /*List<Data> linkData = this.getData();
        Iterator iterData = linkData.iterator();
        Bag dataBag = model.createBag(uri + "data_of_link_" + this.getSource() + "_" + this.getTarget());
        while (iterData.hasNext()) {
            Data newData = (Data) iterData.next();
            Resource resData = newData.exportRDF(model, uri);
            dataBag.add(resData);
        }
        newLink.addProperty(hasLinkData, dataBag); */

        return newLink;

    }

    public void addTimeStamp(int TSValue, String theURI, Model theModel) {
        Property appearsInTimeStamp = theModel.createProperty(theURI + "linkAppearsInTimeStamp");
        Resource theTS = theModel.getResource(theURI + "TimeStamp" + "/" + "TimeStamp_" + TSValue);
        Resource rdflink = theModel.getResource(theURI + "Link" + "/" + this.getSource() + "--" + this.getTarget());
        rdflink.addProperty(appearsInTimeStamp, theTS);
    }

    // adding data is now a responsibility of TimeStamp for bothe Nodes and Links
    // performed dynamically (not when iterating through Nodes + Links in the Setup
    // same for RSSI

    /* public void addDataItem(int TSValue, String theURI, Resource resLink, String key, String value) {
        Property linkHasKeyValuePair = model.createProperty(theURI + "linkHasKeyValuePair");
        //Resource rdfnode = model.getResource(theURI + "Node" + "/" + nodeID);
        if (value == null) {
            value = "NULL_VALUE";
        }
        if (key == null) {
            key = "NULL_KEY";
        }
        resLink.addProperty(linkHasKeyValuePair, model.createLiteral("KEY:_" + key + "_VALUE:_" + value + "_IN_TimeStamp_" + TSValue));
    } */

    /*public void addRssi(int TSValue, Resource resLink, String rssiValue, String theURI)
    {
        Property linkHasRssiValue = model.createProperty(theURI + "linkHasRssiValue");
        resLink.addProperty(linkHasRssiValue, model.createLiteral("RSSI_VALUE:_" + rssiValue + "_IN_TimeStamp_" + TSValue ));
    } */

}
