package pro.yqy.authorization.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.yqy.authorization.model.bean.account.RegisterRequestBean;
import pro.yqy.authorization.service.AccountService;
import pro.yqy.component.web.common.BaseRestController;
import pro.yqy.component.web.common.ResultMessage;

@Tag(name = "用户账号接口")
@RestController
@AllArgsConstructor
@RequestMapping("/account")
public class AccountController extends BaseRestController {

    private final AccountService accountService;

    @PostMapping(value = "/register")
    @Operation(summary = "账号注册接口")
    public ResultMessage<String> register(@Validated @RequestBody RegisterRequestBean requestBean) {
        return ResultMessage.ok(accountService.register(requestBean));
    }

}
