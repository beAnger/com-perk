package pro.yqy.authorization.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.yqy.authorization.model.bean.account.RegisterRequestBean;
import pro.yqy.authorization.service.AccountService;
import pro.yqy.component.web.singleton.SingletonItem;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Override
    public String register(RegisterRequestBean requestBean) throws Exception {
        log.info("{}", SingletonItem.OBJECT_MAPPER.writeValueAsString(requestBean));
        boolean matches = requestBean.getIdentity().matches(requestBean.getRegisterType().getRegex());

        return SingletonItem.OPERATE_SUCCESS;
    }
}
