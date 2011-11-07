package eu.wisebed.wiseml.test;

import eu.wisebed.wiseml.controller.WiseMLController;
import eu.wisebed.wiseml.model.WiseML;

import java.io.File;
import java.io.FileInputStream;

/**
 * Test that the setup section is unmarshalled properly.
 */
public class TestSessionMgtService {
    public static void main(String[] args) {

        WiseMLController cnt = new WiseMLController();

        String getNetworksWiseML = "tr.getnetwork.wiseml";

//        String endpointUrl = "http://wisebed.itm.uni-luebeck.de:8888/sessions";
//
//        SessionManagement sessionManagementService =
//                eu.wisebed.testbed.api.wsn.WSNServiceHelper
//                        .getSessionManagement(endpointUrl);
//
//        String wiseml = sessionManagementService.getNetwork();
//
//        System.err.println("Got WiseML from URI: " + endpointUrl);
//        System.err.println(wiseml);
//        System.err.println("---");
//        InputStream is = new ByteArrayInputStream(wiseml.getBytes());
        try {
            WiseML stp = cnt.loadWiseMLFromFile(new FileInputStream(new File(getNetworksWiseML)));
            System.out.println(stp.getVersion());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
