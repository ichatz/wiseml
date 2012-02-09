package eu.wisebed.wisedb.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/9/12
 * Time: 2:30 PM
 */
@Embeddable
public class LinkPK implements Serializable {


    /**
     * the source of an object Link.
     */
    @ManyToOne
    @Column(name="link_source", nullable=false)
    private String source;

    /**
     * the target of an object Link.
     */
    @ManyToOne
    @Column(name="link_target", nullable=false)
    private String target;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
