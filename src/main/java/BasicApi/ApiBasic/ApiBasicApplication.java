package BasicApi.ApiBasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ApiBasicApplication extends SpringBootServletInitializer {

    // Configuração para WAR
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ApiBasicApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiBasicApplication.class, args);
    }
}
