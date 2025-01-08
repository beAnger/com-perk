package pro.yqy.biz.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.yqy.authorization.client.dubbo.DemoService;
import pro.yqy.component.web.common.BaseRestController;

@Tag(name = "测试")
@RestController
@RequestMapping("/test")
public class DemoController extends BaseRestController {

    @DubboReference
    private DemoService demoService;

    @Operation(summary = "wow")
    @GetMapping(value = "/wow", produces = MediaType.APPLICATION_JSON_VALUE)
    public String wow() {
        return demoService.sayHello("amazing!");
    }

}
