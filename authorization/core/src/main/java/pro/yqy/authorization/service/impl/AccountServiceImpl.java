package pro.yqy.authorization.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.yqy.authorization.constant.AuthorizationError;
import pro.yqy.authorization.model.bean.account.RegisterRequestBean;
import pro.yqy.authorization.service.AccountService;
import pro.yqy.component.redis.RedisCache;
import pro.yqy.component.web.exception.RestException;
import pro.yqy.component.web.singleton.SingletonItem;

import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final RedisCache redisCache;

    private static final String REGISTER_ACCOUNT_PREFIX_KEY = "authorization:register:account:";


    @Override
    public String register(RegisterRequestBean requestBean) {
        Boolean exists = redisCache.setNx(REGISTER_ACCOUNT_PREFIX_KEY + requestBean.getIdentity(), 1L, "1");
        if (Objects.isNull(exists) || !exists) {
            throw new RestException(AuthorizationError.request_too_frequent);
        }

        boolean matches = requestBean.getIdentity().matches(requestBean.getRegisterType().getRegex());
        if (!matches) {
            throw new RestException(AuthorizationError.account_format_incorrect);
        }

        log.info("register success: {}", redisCache.get(requestBean.getIdentity()).toString());
        return SingletonItem.OPERATE_SUCCESS;
    }
}
