package project;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@ConfigurationPropertiesScan(basePackageClasses = UserProject.class)
@EnableCaching
public class UserProject {
    public static void main(String[] args) {
        SpringApplication.run(UserProject.class, args);
    }
}