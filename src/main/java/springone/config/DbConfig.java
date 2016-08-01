package springone.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@ConfigurationProperties("db")
public class DbConfig extends HashMap<Object, Object> {
}
