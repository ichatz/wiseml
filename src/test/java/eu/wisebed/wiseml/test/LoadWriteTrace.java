package eu.wisebed.wiseml.test;

import eu.wisebed.wiseml.controller.TraceController;
import eu.wisebed.wiseml.model.setup.Node;
import eu.wisebed.wiseml.model.trace.Message;
import eu.wisebed.wiseml.model.trace.Trace;
import org.jibx.runtime.JiBXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LoadWriteTrace {
    public static void main(String[] args) throws JiBXException, FileNotFoundException {

        TraceController traceC = new TraceController();

        Trace trace = new Trace();

        Node node = new Node();

        Message message = new Message();
        message.setTimestamp(2);
        message.setData("connect");
        message.setData("disconnect");

        node.setId("urn:wisebed:node:tud:M4A�P100V");
        node.setMessage(message);

        FileInputStream fileIn = null;
        System.out.println(traceC.writeTraceAsString(trace));


    }
}
