package eu.wisebed.wiserdf.test;

import eu.wisebed.wiserdf.RDFExporter;

public class rdfTest {

    public static void main(String args[]) {
        if (args.length < 2) {
            System.out.println("input WiseML file path in needed");
            System.out.println("output RFD file path in needed");
            return;

        }
        {

            RDFExporter rdfex = new RDFExporter("http://www.wisebed.eu/wiseml2rdf/", args[0], args[1]);
            rdfex.createRDF();


        }
    }
}
