package immargin.hardware.hcb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;

@Configuration
public class WebMVCConfigs implements WebMvcConfigurer {
    
    @Autowired
    BlacklistFilter blacklistFilter;

    @Bean
    FilterRegistrationBean<BlacklistFilter> loggingFilter(){
        
        FilterRegistrationBean<BlacklistFilter> registrationBean 
          = new FilterRegistrationBean<>();
            
        registrationBean.setFilter(blacklistFilter);
        registrationBean.setOrder(1);
        return registrationBean;    
    }
    
    @Bean LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
    

}
