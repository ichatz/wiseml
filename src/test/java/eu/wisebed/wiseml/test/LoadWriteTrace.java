package eu.wisebed.wiseml.test;

import eu.wisebed.wiseml.controller.TraceController;
import eu.wisebed.wiseml.model.scenario.Timestamp;
import eu.wisebed.wiseml.model.setup.Data;
import eu.wisebed.wiseml.model.trace.Message;
import eu.wisebed.wiseml.model.trace.Trace;
import org.jibx.runtime.JiBXException;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

public class LoadWriteTrace {
    public static void main(String[] args) throws JiBXException, FileNotFoundException {

        TraceController traceC = new TraceController();
        Trace trace = new Trace();
        List l = new LinkedList();
        eu.wisebed.wiseml.model.scenario.Timestamp t = new Timestamp();
        t.setValue("1");
        l.add(t);
        Data d1 = new Data();
        d1.setKey("light");
        d1.setValue("3.24");
        Data d2 = new Data();
        d2.setKey("light");
        d2.setValue("3.24");
        l.add(d1);
        l.add(d2);
        trace.setChildren(l);
        Message message = new Message();
        message.setTimestamp(2);
        message.setData("connect");
        l.add(message);
        System.out.println(traceC.writeTraceAsString(trace));

    }
}
