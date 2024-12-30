package pro.yqy.component.web.property;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Slf4j
@Component("restLogProperties")
@ConfigurationProperties(
        prefix = "boot.restlog"
)
public class RestLogProperties {
    private boolean enable;

    private String excludeUrls;
}
