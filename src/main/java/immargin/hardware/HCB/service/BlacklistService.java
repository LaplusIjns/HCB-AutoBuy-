package immargin.hardware.HCB.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import immargin.hardware.HCB.Config.Constant;
import immargin.hardware.HCB.model.Blacklist;
import immargin.hardware.HCB.repository.BlacklistRepository;

@Service
@Component
public class BlacklistService {

    @Autowired
    BlacklistRepository blacklistRepository;

    public Optional<Blacklist> findById(String id){
        return blacklistRepository.findById(id);
    }

    public Blacklist update(Blacklist blacklist,String urlPath) {
        String[] urlPaths = blacklist.getUrlPath().split(",");
        boolean notIn = true;
        for (String string : urlPaths) {
            if(string.equals(urlPath))
                notIn = false;
        }
        
        if(notIn)
        blacklist.setUrlPath(blacklist.getUrlPath()+Constant.DOT+urlPath);
        blacklist.setCountNumber(blacklist.getCountNumber() + 1);
        blacklist.setUpdateTime(new Date());
        return blacklistRepository.save(blacklist);
    }
    
    public Blacklist save(Blacklist blacklist) {
        return blacklistRepository.save(blacklist);
    }

}
