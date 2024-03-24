package immargin.hardware.hcb.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import immargin.hardware.hcb.config.Constant;
import immargin.hardware.hcb.model.Blacklist;
import immargin.hardware.hcb.repository.BlacklistRepository;

@Service
@Transactional
public class BlacklistService {

    @Autowired
    BlacklistRepository blacklistRepository;

    public Optional<Blacklist> findById(String id){
        try {
            return blacklistRepository.findById(id);
        }catch (JpaSystemException e) {  
            return fixDuplicateRows(id,0);
        }
    }
    public Optional<Blacklist> fixDuplicateRows(String id,int countTime) {
        try {
            if(countTime==5) {
                return Optional.of(new Blacklist(id, 100, new Date(),""));
            }
            List<Blacklist> findList = blacklistRepository.findAllById( List.of(id) );
            blacklistRepository.deleteAllById(List.of(id));
            int count = 0;
            StringBuilder path = new StringBuilder();
            for (Blacklist blacklist : findList) {
                count += blacklist.getCountNumber().intValue();
                path.append(blacklist.getUrlPath());
            }
            Blacklist result = new Blacklist(id, count, new Date(), path.toString());
            blacklistRepository.saveAndFlush(result);
            
            Optional<Blacklist> returnValue = Optional.of(result);
            return returnValue;
            
        } catch (Exception e) {
            countTime++;
            if(countTime==5) {
                return Optional.of(new Blacklist(id, 100, new Date(), ""));
            }
            return fixDuplicateRows(id,countTime);
        }
    }

    public Blacklist update(Blacklist blacklist,String urlPath) {
        String[] urlPaths = blacklist.getUrlPath().split(",");
        boolean notIn = true;
        for (String string : urlPaths) {
            if(urlPath.contains(string))
                 notIn = false;
        }
        
        if(notIn)
            blacklist.setUrlPath(blacklist.getUrlPath().concat(Constant.DOT).concat(urlPath));
        
        blacklist.setCountNumber(blacklist.getCountNumber() + 1);
        blacklist.setUpdateTime(new Date());
        return blacklistRepository.saveAndFlush(blacklist);
        
    }
    
    public Blacklist save(Blacklist blacklist) {
        return blacklistRepository.saveAndFlush(blacklist);
    }

}
