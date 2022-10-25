package immargin.hardware.HCB.model;

import java.io.Serializable;



public class TagRelationshipId implements Serializable{

    private String fk_prod_id;
    private String fk_tag;

    public String getFk_prod_id() {
        return fk_prod_id;
    }

    public void setFk_prod_id(String fk_prod_id) {
        this.fk_prod_id = fk_prod_id;
    }

    public String getFk_tag() {
        return fk_tag;
    }

    public void setFk_tag(String fk_tag) {
        this.fk_tag = fk_tag;
    }

    
}
