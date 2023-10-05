package immargin.hardware.hcb.DTO;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LastDTO2 {

    private String fkProdId;
    private Integer price;
    private Date uploadDate;
    
    @JsonProperty("fk_prod_id")
    public String getFkProdId() {
        return fkProdId;
    }
    public void setFkProdId(String fkProdId) {
        this.fkProdId = fkProdId;
    }
    
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    
    @JsonProperty("upload_date")
    public Date getUploadDate() {
        return uploadDate;
    }
    
    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }
}
