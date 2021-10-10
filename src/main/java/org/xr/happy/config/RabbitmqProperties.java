package org.xr.happy.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Steven
 */
@Data
@Configuration
@Component
@PropertySource(value = "classpath:/rabbitmqinit.yml",encoding = "utf-8",factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "rabbitmq")

public class RabbitmqProperties {

    private String type;

    private String routingKey;

    private String exchange;

    private List<String> queueNames;


}
