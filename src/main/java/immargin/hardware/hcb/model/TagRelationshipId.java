package immargin.hardware.hcb.model;

import java.io.Serializable;



public class TagRelationshipId implements Serializable{

//    private String fkProdId;
//    private String fkTag;
//    
//    public String getFkProdId() {
//        return fkProdId;
//    }
//    public void setFkProdId(String fkProdId) {
//        this.fkProdId = fkProdId;
//    }
//    public String getFkTag() {
//        return fkTag;
//    }
//    public void setFkTag(String fkTag) {
//        this.fkTag = fkTag;
//    }
    private SinyaTagcompare sinyaTagcompares;
    private Sinyamaintable fkProdId;
    public SinyaTagcompare getSinyaTagcompares() {
        return sinyaTagcompares;
    }
    public void setSinyaTagcompares(SinyaTagcompare sinyaTagcompares) {
        this.sinyaTagcompares = sinyaTagcompares;
    }
    public Sinyamaintable getFkProdId() {
        return fkProdId;
    }
    public void setFkProdId(Sinyamaintable fkProdId) {
        this.fkProdId = fkProdId;
    }
    
    

    

    
}
