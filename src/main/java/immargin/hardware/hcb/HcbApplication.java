package immargin.hardware.hcb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class HcbApplication extends WebMvcConfigurationSupport {

	public static void main(String[] args) {
		SpringApplication.run(HcbApplication.class, args);
	}
	
}