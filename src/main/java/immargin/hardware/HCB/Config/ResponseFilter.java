package immargin.hardware.HCB.Config;


import java.util.Date;

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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        
        var blacklist =  blacklistService.findById(request.getLocalAddr());
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
        if(response.getStatus()==HttpServletResponse.SC_NOT_FOUND && request.getMethod().equals(Constant.URL_METHOD_GET) && !request.getRequestURI().equals(Constant.ICON_PATH) && !request.getRequestURI().equals(Constant.ERROR_PATH) ) {
            blacklistService.findById(request.getLocalAddr()).ifPresentOrElse(
                    blacklist -> {
                        if (CommoUtils.getDayFromTwoDate(new Date(), blacklist.getUpdateTime()) <= Constant.TIME_INTERVAL ) {
                            blacklistService.update(blacklist,request.getRequestURI());
                        }
                    },
                    () -> {
                        blacklistService.save(new Blacklist(request.getLocalAddr(), 1, new Date(),Constant.DOT+request.getRequestURI()));
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
