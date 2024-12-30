package pro.yqy.authorization.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import pro.yqy.component.web.common.BaseRestController;

@Tag(name = "测试")
@RestController
@RequestMapping("/test")
public class TestController extends BaseRestController {

    @Operation(summary = "普通body请求+Param+Header+Path")
    @Parameters({
            @Parameter(name = "id", description = "文件id", in = ParameterIn.PATH),
            @Parameter(name = "token", description = "请求token", required = true, in = ParameterIn.HEADER),
            @Parameter(name = "name", description = "文件名称", in = ParameterIn.QUERY)
    })
    @PostMapping("/wow/{id}")
    public String bodyParamHeaderPath(@PathVariable("id") String id,
                                      @RequestHeader("token") String token,
                                      @RequestParam(value = "name", required = false) String name,
                                      @RequestBody String fileResp) {

        return id + "," + token + "," + name + "," + fileResp;
    }
}
