package pro.yqy.component.mail;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "boot.mail")
public class EmailProperties {
    private String host;
    private Integer port;
    private String username;
    private String password;

    private Properties properties;
}
