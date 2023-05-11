package immargin.hardware.HCB.DTO;

public interface DailyDTO {
    String getProdname();
    String getfk_prod_id();
//    String getFkProdId();
    java.util.Date getupload_date();
    Integer getprice();
    Integer getdiff();
}
