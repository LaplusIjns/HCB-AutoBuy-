package immargin.hardware.hcb.config;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

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
    
    
//    @Bean LayoutDialect layoutDialect() {
//        return new LayoutDialect();
//    }
//    
//    @Bean
//    public SpringTemplateEngine templateEngine() {
//        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//        templateEngine.addDialect(new LayoutDialect());
//        templateEngine.addTemplateResolver(htmlTemplateResolver());
//        return templateEngine;
//    }
//    
//    @Bean
//    public ClassLoaderTemplateResolver classLoaderTemplateResolver() {
//        ClassLoaderTemplateResolver secondaryTemplateResolver = new ClassLoaderTemplateResolver();
//        secondaryTemplateResolver.setPrefix("/templates/");
//        secondaryTemplateResolver.setSuffix(".html");
//        secondaryTemplateResolver.setTemplateMode(TemplateMode.HTML);
//        secondaryTemplateResolver.setCharacterEncoding("UTF-8");
//        secondaryTemplateResolver.setOrder(1);
//        secondaryTemplateResolver.setCheckExistence(true);
//        return secondaryTemplateResolver;
//    }
    
//    @Bean
//    public SpringResourceTemplateResolver templateResolver() {
//        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
//        templateResolver.setCacheable(false);
//        templateResolver.setPrefix("classpath:/templates/");
//        templateResolver.setSuffix(".html");
//        templateResolver.setCharacterEncoding("UTF-8");
//        templateResolver.setTemplateMode(TemplateMode.HTML);
//        return templateResolver;
//    }
//
//    @Bean
//    public SpringTemplateEngine templateEngine() {
//        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
//        springTemplateEngine.addTemplateResolver(templateResolver());
//        springTemplateEngine.addDialect(new LayoutDialect());
//        return springTemplateEngine;
//    }
//
//    
//    @Bean
//    public ThymeleafViewResolver viewResolver() {
//        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
//        viewResolver.setTemplateEngine(templateEngine());
//        viewResolver.setOrder(1);
//        return viewResolver;
//    }
}
