package pro.yqy.authorization.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import pro.yqy.authorization.model.bean.account.RegisterRequestBean;
import pro.yqy.authorization.model.bean.account.SendVerificationCodeRequestBean;

public interface AccountService {

    String sendVerificationCode(HttpServletRequest request,
                                @NotNull SendVerificationCodeRequestBean requestBean);

    String register(@NotNull RegisterRequestBean requestBean);
}
