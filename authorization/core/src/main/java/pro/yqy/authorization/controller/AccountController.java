package pro.yqy.authorization.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pro.yqy.authorization.model.bean.account.RegisterRequestBean;
import pro.yqy.authorization.service.AccountService;
import pro.yqy.component.web.common.BaseRestController;

@Tag(name = "用户账号接口")
@RestController
@AllArgsConstructor
@RequestMapping("/account")
public class AccountController extends BaseRestController {

    private final AccountService accountService;

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "账号注册接口")
    public String register(@Validated @RequestBody RegisterRequestBean requestBean) {
        return accountService.register(requestBean);
    }
}
