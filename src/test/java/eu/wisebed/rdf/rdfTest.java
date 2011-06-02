package eu.wisebed.rdf;/*

/**
 *
 * @author Pantelis
 */

import eu.wisebed.wiseml.controller.WiseMLController;
import eu.wisebed.wiseml.model.WiseML;

import java.io.FileInputStream;

public class rdfTest {

    public static void main (String args[])
    {
        if (args.length<1) {
            System.out.println("wiseml file path in needed");
           return;

        }
        rdfController wiseClass;
        String dumpfilename = null;
        String dumpString = null;
        FileInputStream fileML = null;

            try {
                fileML = new FileInputStream(args[0]);
            }

            catch(Exception e){
                System.err.println(e);
            }

        WiseMLController wmlcontroller = new WiseMLController();
        WiseML wml = wmlcontroller.loadWiseMLFromFile(fileML);


        wiseClass = new rdfController("http://www.wisebed.eu/wiseml2rdf/", wml);
        wiseClass.createModel();
        dumpString = wiseClass.returnAsSTring();
        System.out.println(dumpString);
     }
}
