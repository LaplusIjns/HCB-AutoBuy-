package immargin.hardware.hcb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMVCConfigs implements WebMvcConfigurer
{
    
    @Autowired
    ResponseFilter responseFilter;
    
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS =
    {
        "classpath:/META-INF/resources/",
         "classpath:/resources/",
        "classpath:/static/", 

    };

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {

        registry.addResourceHandler("/**")
        .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(responseFilter);
    }

}
