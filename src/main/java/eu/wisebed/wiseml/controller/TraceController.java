package eu.wisebed.wiseml.controller;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.JiBXException;
import eu.wisebed.wiseml.model.trace.Trace;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class TraceController {

    public Trace writeTraceAsFile(Trace trace, File file) {


        HashMap<Integer, Set<String>> mapTr = new HashMap<Integer, Set<String>>();
        HashMap<String, HashMap<Integer, Set<String>>> map = new HashMap<String, HashMap<Integer, Set<String>>>();
        Set<String> messages = new TreeSet<String>();

        for (String message : messages) {
            //messages.add(trace.getNode().getMessage().getData());
        }

        //mapTr.put(trace.getTimestamp(), messages);
        //map.put(trace.getNode().getId(), mapTr);

        List listTimestmps = new ArrayList();
        listTimestmps.addAll(mapTr.keySet());
        Collections.sort(listTimestmps);

        List finalList = new ArrayList();
        finalList.addAll(map.keySet());
        Collections.sort(finalList);


        try {
            // marshal object back out to file (with nice indentation, as UTF-8)...
            IBindingFactory bfact = BindingDirectory.getFactory(Trace.class);
            IMarshallingContext mctx = bfact.createMarshallingContext();
            mctx.setIndent(5);
            FileOutputStream output = new FileOutputStream(file);
            mctx.setOutput(output, null);
            mctx.marshalDocument(trace);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (JiBXException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return trace;
    }


}
