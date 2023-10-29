package immargin.hardware.hcb.config;

import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import immargin.hardware.hcb.model.Blacklist;
import immargin.hardware.hcb.service.BlacklistService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class BlacklistFilter extends OncePerRequestFilter {
  
    
    private static final Logger log = LoggerFactory.getLogger(BlacklistFilter.class);
    
    @Autowired
    BlacklistService blacklistService;
    
    private static final String[] PASS_URL = new String[] {".jpg",".png",".css",".js",".ico",Constant.BAN_PATH };


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String url = request.getRequestURI();
        for( String passURL : PASS_URL) {
            if(url.contains(passURL) ) {
                return true;     
            }                
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String realIP =  IpUtils.getIpAddr(request);
        
        
        log.info("request ip : {} ,uri: {}",realIP,request.getRequestURI());
        
        if(request.getMethod().equals("PROPFIND") ) {
            response.sendRedirect(Constant.BAN_PATH);
            return;
        }
        
        if(inBlacklist(realIP, request)) {
            log.info("已在黑名單重導 ip : {} ,uri: {}",realIP,request.getRequestURI());
            response.sendRedirect(Constant.BAN_PATH);
            return;
        }
        filterChain.doFilter(request, response);
        
        if(isNotFound(realIP, request, response)) {
            log.info("錯誤路徑 預備BanIP: {} ,uri: {}",realIP,request.getRequestURI());
            blacklistService.findById(realIP).ifPresentOrElse(
                    blacklistvar -> {
                        if (CommoUtils.getDayFromTwoDate(new Date(), blacklistvar.getUpdateTime()) <= Constant.TIME_INTERVAL ) {
                            blacklistService.update(blacklistvar,request.getRequestURI());
                        }
                    },
                    () -> 
                        blacklistService.save(new Blacklist(realIP, 1, new Date(),Constant.DOT+request.getRequestURI()))
                    );
        }
        
    }
    
    private boolean inBlacklist(String realIP,HttpServletRequest request) {
        var blacklist =  blacklistService.findById(realIP);
        if(blacklist.isPresent() && (blacklist.get().getCountNumber().intValue()>=Constant.BAN_COUNT 
                && !request.getRequestURI().equals(Constant.ICON_PATH) && !request.getRequestURI().equals(Constant.ERROR_PATH) && !request.getRequestURI().equals(Constant.BAN_PATH) ) 
                ) {
            blacklistService.update(blacklist.get(),request.getRequestURI());
            return true;
        }else {
            return false;
        }
    }
    
    private boolean isNotFound(String realIP,HttpServletRequest request, HttpServletResponse response) {
        if(response.getStatus()==HttpServletResponse.SC_NOT_FOUND && request.getMethod().equals(Constant.URL_METHOD_GET) 
                && !request.getRequestURI().equals(Constant.ICON_PATH) && !request.getRequestURI().equals(Constant.ERROR_PATH)  && !request.getRequestURI().equals(Constant.BAN_PATH)
                && !realIP.startsWith("127.0.0.1") && !realIP.startsWith("192.168") && !realIP.startsWith("0:0:0:0:0:0:0:1")
                ) {
            return true;
        }else {
            return false;
        }
    }
    
    
    
}
