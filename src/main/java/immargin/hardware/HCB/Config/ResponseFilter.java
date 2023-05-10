package immargin.hardware.HCB.Config;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import immargin.hardware.HCB.model.Blacklist;
import immargin.hardware.HCB.service.BlacklistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class ResponseFilter implements HandlerInterceptor {
    
    @Autowired
    BlacklistService blacklistService;
    
    
    private static final Logger log = LoggerFactory.getLogger(ResponseFilter.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String realIP =  IpUtils.getIpAddr(request);
        log.info("登入IP: {}",realIP);
        var blacklist =  blacklistService.findById(realIP);
        if(blacklist.isPresent()) {
            if(blacklist.get().getCountNumber().intValue()>=Constant.BAN_COUNT && !request.getRequestURI().equals(Constant.ICON_PATH) && !request.getRequestURI().equals(Constant.ERROR_PATH)) {
                blacklistService.update(blacklist.get(),request.getRequestURI());
                return false;
            }
        }
        
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
        String realIP =  IpUtils.getIpAddr(request);
        if(response.getStatus()==HttpServletResponse.SC_NOT_FOUND && request.getMethod().equals(Constant.URL_METHOD_GET) && !request.getRequestURI().equals(Constant.ICON_PATH) && !request.getRequestURI().equals(Constant.ERROR_PATH) 
                && !realIP.startsWith("127.0.0.1") && !realIP.startsWith("192.168") && !realIP.startsWith("0:0:0:0:0:0:0:1")
                ) {
            blacklistService.findById(realIP).ifPresentOrElse(
                    blacklist -> {
                        if (CommoUtils.getDayFromTwoDate(new Date(), blacklist.getUpdateTime()) <= Constant.TIME_INTERVAL ) {
                            blacklistService.update(blacklist,request.getRequestURI());
                        }
                    },
                    () -> {
                        blacklistService.save(new Blacklist(realIP, 1, new Date(),Constant.DOT+request.getRequestURI()));
                    });
        }
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable Exception ex) throws Exception {
        // TODO Auto-generated method stub 
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

   
    

}
