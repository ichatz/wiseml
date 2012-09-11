package eu.wisebed.wiserdf.test;


import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Data;
import eu.wisebed.wiseml.model.setup.Node;
import eu.wisebed.wiserdf.rdfNodeExporter;
import org.joda.time.DateTime;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class rdfTest2 {

    public static void main(String args[]) {
        Node nd = new Node();
        nd.setId("urn:wisebed:ctitestbed:0x9979");
        eu.wisebed.wiseml.model.setup.Position pos = new eu.wisebed.wiseml.model.setup.Position();
        pos.setX((float) 1.1);
        pos.setY((float) 1.5);

        nd.setPosition(pos);
        List<Capability> caps = new ArrayList<Capability>();
        Capability cap = new Capability();
        cap.setName("urn:wisebed:node:capability:temperature");
        cap.setUnit("C");
        caps.add(cap);
        cap.setName("urn:wisebed:node:capability:room");
        cap = new Capability();
        cap.setName("urn:wisebed:node:capability:pir");
        cap.setUnit("boolean");
        nd.setCapabilities(caps);
        caps.add(cap);
        rdfNodeExporter ndf = new rdfNodeExporter("http://uberdust.cti.gr");
        HashMap<String, Float> readings = new HashMap<String, Float>();
        Data d = new Data();
        d.setKey("urn:wisebed:node:capability:pir");
        d.setValue("1.2");
        ndf.exportNode(nd, d, new DateTime(), "OII1");
        d = new Data();
        d.setKey("urn:wisebed:node:capability:temperature");
        d.setValue("13.2");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        ndf.exportNode(nd, d, new DateTime(), "OII3").write(bos, "RDF/XML-ABBREV");
        System.out.println(bos.toString());
    }
}