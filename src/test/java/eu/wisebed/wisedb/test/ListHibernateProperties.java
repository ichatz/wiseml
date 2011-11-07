package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;

import java.util.Properties;

public class ListHibernateProperties {

    public static void main(String[] args) {
        // get properties
        Properties props = HibernateUtil.getInstance().getConfiguration().getProperties();
        props.list(System.out);
    }
}
