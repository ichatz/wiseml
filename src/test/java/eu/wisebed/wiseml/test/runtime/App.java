package eu.wisebed.wiseml.test.runtime;

import eu.wisebed.testbed.api.wsn.v22.SessionManagement;
import eu.wisebed.wiseml.controller.WiseMLController;
import eu.wisebed.wiseml.model.WiseML;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: ichatz
 * Date: 4/27/11
 * Time: 11:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class App {

    public static void main(String[] args) {
        String endpointUrl =
                "http://wisebed.itm.uni-luebeck.de:8888/sessions";

        SessionManagement sessionManagementService =
                eu.wisebed.testbed.api.wsn.WSNServiceHelper
                        .getSessionManagementService(endpointUrl);

        String wiseml = sessionManagementService.getNetwork();

        System.out.println("Got WiseML from URI: " + endpointUrl);
        System.out.println(wiseml);

        InputStream is = new ByteArrayInputStream(wiseml.getBytes());
        WiseMLController cnt = new WiseMLController();
        WiseML stp = cnt.loadWiseML(is);
    }

}