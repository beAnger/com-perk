package pro.yqy.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.yqy.authorization.client.dubbo.AuthenticationService;
import pro.yqy.component.web.common.BaseRestController;

@Tag(name = "测试")
@RestController
@RequestMapping("/test")
public class DemoController extends BaseRestController {

    @DubboReference(mock = "return null", retries = 3, timeout = 3000)
    private AuthenticationService authenticationService;

    @Operation(summary = "wow")
    @GetMapping(value = "/wow", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object wow() {
        return authenticationService.authentication("amazing!");
    }

}
