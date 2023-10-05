package immargin.hardware.hcb.DTO;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DailyDTO2 {

    private String prodname;
    private String fkProdId;
    private Date uploadDate;
    private Integer price;
    private Integer diff;
    public String getProdname() {
        return prodname;
    }
    public void setProdname(String prodname) {
        this.prodname = prodname;
    }
    
    @JsonProperty("fk_prod_id")
    public String getFkProdId() {
        return fkProdId;
    }
    public void setFkProdId(String fkProdId) {
        this.fkProdId = fkProdId;
    }
    @JsonProperty("upload_date")
    public Date getUploadDate() {
        return uploadDate;
    }
    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public Integer getDiff() {
        return diff;
    }
    public void setDiff(Integer diff) {
        this.diff = diff;
    }
    
    
    
}
