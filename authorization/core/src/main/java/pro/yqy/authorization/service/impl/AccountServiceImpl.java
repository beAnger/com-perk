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

import pro.yqy.authorization.model.constant.account.AccountRedisKey;

@Slf4j
@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final RedisCache redisCache;

    @Override
    public String register(RegisterRequestBean requestBean) {
        Boolean exists = redisCache.setNx(
                AccountRedisKey.REGISTER_ACCOUNT_PREFIX_KEY + requestBean.getIdentity(), 1L, "1"
        );
        if (Objects.isNull(exists) || !exists) {
            throw new RestException(AuthorizationError.request_too_frequent);
        }

        boolean identityLegal = requestBean.getIdentity().matches(requestBean.getRegisterType().getRegex());
        if (!identityLegal) {
            throw new RestException(AuthorizationError.account_format_incorrect);
        }

        return SingletonItem.OPERATE_SUCCESS;
    }
}
