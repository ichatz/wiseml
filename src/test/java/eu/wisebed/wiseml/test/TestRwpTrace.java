package eu.wisebed.wiseml.test;

import eu.wisebed.wiseml.controller.WiseMLController;
import eu.wisebed.wiseml.model.WiseML;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by IntelliJ IDEA.
 * User: ichatz
 * Date: 6/3/11
 * Time: 3:20 AM
 * To change this template use File | Settings | File Templates.
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
