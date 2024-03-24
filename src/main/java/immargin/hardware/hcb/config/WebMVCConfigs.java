package immargin.hardware.hcb.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMVCConfigs implements WebMvcConfigurer {
    
    @Autowired
    BlacklistFilter blacklistFilter;
    
    @Autowired
    TimeLimitFilter timeLimitFilter;

    @Bean
    FilterRegistrationBean<BlacklistFilter> loggingFilter(){
        
        FilterRegistrationBean<BlacklistFilter> registrationBean 
          = new FilterRegistrationBean<>();
            
        registrationBean.setFilter(blacklistFilter);
        registrationBean.setOrder(2);

        return registrationBean;    
    }
    
    @Bean
    FilterRegistrationBean<TimeLimitFilter> timeFilter(){
        
        FilterRegistrationBean<TimeLimitFilter> registrationBean 
          = new FilterRegistrationBean<>();
            
        registrationBean.setFilter(timeLimitFilter);
        registrationBean.setOrder(1);

        return registrationBean;    
    }
    
//    @Bean
    
    
}
