package immargin.hardware.hcb.model;

import java.util.Date;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Component
@Entity
@Table(name = "blacklist" ,schema ="PUBLIC")
public class Blacklist {

    @Id
    private String ipAddr;
    
    private Integer countNumber;
    
    private Date updateTime;
    
    private String urlPath;

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public Integer getCountNumber() {
        return countNumber;
    }

    public void setCountNumber(Integer countNumber) {
        this.countNumber = countNumber;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public Blacklist(String ipAddr, Integer countNumber, Date updateTime) {
        super();
        this.ipAddr = ipAddr;
        this.countNumber = countNumber;
        this.updateTime = updateTime;
    }
    
    

    public Blacklist(String ipAddr, Integer countNumber, Date updateTime, String urlPath) {
        super();
        this.ipAddr = ipAddr;
        this.countNumber = countNumber;
        this.updateTime = updateTime;
        this.urlPath = urlPath;
    }

    public Blacklist() {
        super();
    }

    @Override
    public String toString() {
        return "Blacklist [ipAddr=" + ipAddr + ", countNumber=" + countNumber + ", updateTime=" + updateTime + "]";
    }
    
    
    
}
