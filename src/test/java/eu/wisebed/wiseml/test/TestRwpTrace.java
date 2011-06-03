package eu.wisebed.wiseml.test;

import eu.wisebed.wiseml.controller.WiseMLController;
import eu.wisebed.wiseml.model.WiseML;

import java.io.File;
import java.io.FileInputStream;

/**
 * Test that the trace section is unmarshalled properly.
 */
public class TestRwpTrace {

    public static void main(String[] args) {
        WiseMLController cnt = new WiseMLController();

        String bonnMotionWiseML = "rwp_100.wiseml";
        try {// Load a WiseML file from the test resources
            long timer = System.currentTimeMillis();
            WiseML trc = cnt.loadWiseMLFromFile(new FileInputStream(new File(bonnMotionWiseML)));
            System.out.println("Time (ms): " + (System.currentTimeMillis() - timer));
            System.out.println("Loaded elements: " + trc.getTrace().getChildren().size());
        } catch (Throwable e) {
            System.err.println("Unable to load WiseML from " + bonnMotionWiseML + ": " + e.toString());
            e.printStackTrace();
        }
    }

}
