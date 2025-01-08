package pro.yqy.authorization.service;

import jakarta.validation.constraints.NotNull;
import pro.yqy.authorization.model.bean.account.RegisterRequestBean;

public interface AccountService {

    String register(@NotNull RegisterRequestBean requestBean) throws Exception;

}
