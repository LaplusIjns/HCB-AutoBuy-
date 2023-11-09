package immargin.hardware.hcb.config;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import immargin.hardware.hcb.service.BlacklistService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TimeLimitFilter extends OncePerRequestFilter {

    private static final String[] PASS_URL = new String[] {".jpg",".png",".css",".js",".ico","/dailynew","/dailyprice","/Sinyadailynew","/Sinyadailyprice",Constant.BAN_PATH };
    
    private LoadingCache<String, Integer> requestCountsPerIpAddress;

    private int MAX_REQUESTS_PER_SECOND = 3;
    
    
    private static final Logger log = LoggerFactory.getLogger(TimeLimitFilter.class);
    
    @Autowired
    BlacklistService blacklistService;

    
    public TimeLimitFilter(){
        super();
        requestCountsPerIpAddress = Caffeine.newBuilder().
              expireAfterAccess(1, TimeUnit.SECONDS)
              .build(new CacheLoader<String, Integer>() {
          public Integer load(String key) {
              return 0;
          }
      });
      }
    
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
        if(isMaximumRequestsPerSecondExceeded(realIP)){
            log.info("ip: {} uri: {}, Too many requests",realIP,request.getRequestURI());
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Too many requests");
            return;
           }
        
        filterChain.doFilter(request, response);
        
    }
    
    private boolean isMaximumRequestsPerSecondExceeded(String clientIpAddress){
        Integer requests = 0;
        requests = requestCountsPerIpAddress.get(clientIpAddress);
        if(requests != null){
            if(requests > MAX_REQUESTS_PER_SECOND) {
              requestCountsPerIpAddress.asMap().remove(clientIpAddress);
              requestCountsPerIpAddress.put(clientIpAddress, requests);
              return true;
          }

        } else {
          requests = 0;
        }
        requests++;
        requestCountsPerIpAddress.put(clientIpAddress, requests);
        return false;
        }

}
