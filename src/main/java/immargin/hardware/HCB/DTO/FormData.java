package immargin.hardware.HCB.DTO;

import java.util.Date;
import java.util.List;

public class FormData {

    private Date startUpdate;
    private Date endUpdate;
    private Date startDate;
    private Date endDate;
    private String prodName;
    private Boolean expiredProd;
    private Boolean filterProd;
    private Integer minPrice;
    private Integer maxPrice;
    private Integer page;
    private List<String> sortStrategy;
    
    public List<String> getSortStrategy() {
        return sortStrategy;
    }
    public void setSortStrategy(List<String> sortStrategy) {
        this.sortStrategy = sortStrategy;
    }
    
    public String getProdName() {
        return prodName;
    }
    public void setProdName(String prodName) {
        this.prodName = prodName;
    }
    public Boolean getExpiredProd() {
        return expiredProd;
    }
    public void setExpiredProd(Boolean expiredProd) {
        this.expiredProd = expiredProd;
    }
    public Boolean getFilterProd() {
        return filterProd;
    }
    public void setFilterProd(Boolean filterProd) {
        this.filterProd = filterProd;
    }
    public Integer getMinPrice() {
        return minPrice;
    }
    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }
    public Integer getMaxPrice() {
        return maxPrice;
    }
    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }
    
    
    
    public Date getStartUpdate() {
        return startUpdate;
    }
    public void setStartUpdate(Date startUpdate) {
        this.startUpdate = startUpdate;
    }
    public Date getEndUpdate() {
        return endUpdate;
    }
    public void setEndUpdate(Date endUpdate) {
        this.endUpdate = endUpdate;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public Integer getPage() {
        return page;
    }
    public void setPage(Integer page) {
        this.page = page;
    }
    @Override
    public String toString() {
        return "FormData [startUpdate=" + startUpdate + ", endUpdate=" + endUpdate + ", startDate=" + startDate
                + ", endDate=" + endDate + ", prodName=" + prodName + ", minPrice=" + minPrice + ", maxPrice="
                + maxPrice + ", page=" + page + "]";
    }
    
    
    
}
