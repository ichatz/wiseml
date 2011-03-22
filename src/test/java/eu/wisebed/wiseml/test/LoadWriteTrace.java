package eu.wisebed.wiseml.test;

import eu.wisebed.wiseml.controller.TraceController;
import eu.wisebed.wiseml.model.setup.Node;
import eu.wisebed.wiseml.model.trace.Trace;
import eu.wisebed.wiseml.model.trace.Message;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LoadWriteTrace {
    public static void main(String[] args) {

        TraceController traceC = new TraceController();

        Trace trace = new Trace();

        Node node = new Node();

        Message message = new Message();
        message.setTimestamp(2);
        message.setData("connect");
        message.setData("disconnect");

        node.setId("urn:wisebed:node:tud:M4A�P100V");
        node.setMessage(message);

        File file = new File("data.xml");

        FileInputStream fileIn = null;


        try {
            fileIn = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        traceC.writeTraceAsFile(trace,file);

    }
}
