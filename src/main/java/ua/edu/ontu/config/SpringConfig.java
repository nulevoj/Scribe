package ua.edu.ontu.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("ua.edu.ontu")
@PropertySource("application.properties")
public class SpringConfig {


}
