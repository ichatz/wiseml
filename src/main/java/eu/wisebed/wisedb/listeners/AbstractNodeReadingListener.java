package eu.wisebed.wisedb.listeners;

import eu.wisebed.wisedb.model.NodeReading;

/**
 * Created by IntelliJ IDEA.
 * User: akribopo
 * Date: 11/9/11
 * Time: 1:11 AM
 * To change this template use File | Settings | File Templates.
 */
public interface AbstractNodeReadingListener {

    public void update(final NodeReading nodeReading);
}
