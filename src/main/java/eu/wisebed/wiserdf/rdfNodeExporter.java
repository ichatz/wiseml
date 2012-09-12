package eu.wisebed.wiserdf;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Data;
import eu.wisebed.wiseml.model.setup.Node;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import java.io.ByteArrayOutputStream;


public class rdfNodeExporter {
    private static Logger LOGGER = Logger.getLogger(rdfNodeExporter.class);
    private Model model;
    private String uri;

    public rdfNodeExporter(String theURI) {
        uri = theURI;
        model = ModelFactory.createDefaultModel();
        //  model.setNsPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
        //  model.setNsPrefix("wiserdf", uri);
    }


    public Model exportNode(Node node, Data reading, DateTime tstamp, String location) {
//        Resource resNode = model.createResource(uri + "/sensor");
        Resource resNode = model.createResource("http://purl.oclc.org/NET/ssnx/ssn#Sensor");
        Property nodeLocation = model.createProperty(uri + "/hasLocation");
        Property dataValue = model.createProperty(uri + "/hasValue");
        Property timeStamp = model.createProperty(uri + "/date");
        Property dataUnits = model.createProperty(uri + "/hasUnit");


        model.add(resNode, RDF.type, RDFS.Class);
        for (Capability cap : node.getCapabilities()) {
            if (reading.getKey().equals(cap.getDescription()) == false) continue;
            Resource sensor = model.createResource(uri + "/node/" + node.getId() + "/capability/" + cap.getName() + "/rdf/rdf-xml/limit/1");
            model.add(sensor, RDF.type, resNode);
//            sensor.addProperty(nodeLocation, location);
            sensor.addProperty(dataValue, reading.getValue(), XSDDatatype.XSDfloat);
            sensor.addProperty(timeStamp, tstamp.toString(), XSDDatatype.XSDdate);
            Resource ref;
            if (cap.getUnit().toString().startsWith("http"))
                ref = model.createResource(cap.getUnit().toString());
            else
                ref = model.createResource("http://" + cap.getUnit().toString());
            sensor.addProperty(dataUnits, ref);

            Resource ref1 = model.createResource(uri + "/" + location);
            sensor.addProperty(nodeLocation, ref1);

        }
        return this.model;
    }

    public String print() {
        String answer = "";
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            model.write(bos, "RDF/XML-ABBREV");
            answer = bos.toString();
            System.out.println(answer);
        } catch (Exception e) {
            LOGGER.info("Error in dumping to rdf file: " + e);
        }

        return answer;
    }
}
