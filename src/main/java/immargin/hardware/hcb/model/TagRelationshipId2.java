package immargin.hardware.hcb.model;

import java.io.Serializable;



public class TagRelationshipId2 implements Serializable{

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
    
    private Maintable fkProdId;
    Tagcompare tagcompares;
    public Maintable getFkProdId() {
        return fkProdId;
    }
    public void setFkProdId(Maintable fkProdId) {
        this.fkProdId = fkProdId;
    }
    public Tagcompare getTagcompares() {
        return tagcompares;
    }
    public void setTagcompares(Tagcompare tagcompares) {
        this.tagcompares = tagcompares;
    }
    
    
  
}
