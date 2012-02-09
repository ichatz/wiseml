package eu.wisebed.wisedb.model;

import org.hibernate.annotations.Entity;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 1/23/12
 * Time: 5:25 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="semantics")
public class Semantic implements Serializable {


    private static final long serialVersionUID = 4869843728436653289L;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public String getSemantic() {
        return semantic;
    }

    public void setSemantic(String semantic) {
        this.semantic = semantic;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String semantic;
    private String node;
    private String value;


}
