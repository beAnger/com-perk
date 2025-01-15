package pro.yqy.authorization.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;
import pro.yqy.authorization.constant.AuthorizationError;
import pro.yqy.authorization.model.bean.account.RegisterRequestBean;
import pro.yqy.authorization.model.bean.account.SendVerificationCodeRequestBean;
import pro.yqy.authorization.model.bean.message.MessageDTO;
import pro.yqy.authorization.model.constant.account.AccountRedisKey;
import pro.yqy.authorization.service.AccountService;
import pro.yqy.authorization.service.MessageSender;
import pro.yqy.component.redis.RedisCache;
import pro.yqy.component.web.error.enumeration.RestStatus;
import pro.yqy.component.web.exception.RestException;
import pro.yqy.component.web.util.IPUtils;
import pro.yqy.component.web.util.StringUtils;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final RedisCache redisCache;

    private final ApplicationContext applicationContext;

    @Override
    public String sendVerificationCode(HttpServletRequest request,
                                       @NotNull SendVerificationCodeRequestBean requestBean) {

        boolean identityLegal = requestBean.getIdentity().matches(requestBean.getChannelType().getRegex());
        if (!identityLegal) {
            throw new RestException(AuthorizationError.illegal_identity_format);
        }

        String resendKey = MessageFormat.format(
                "{0}{1}:{2}",
                AccountRedisKey.REGISTER_ACCOUNT_PREFIX_KEY,
                requestBean.getActionType().name(),
                IPUtils.getRemoteAddr(request)
        );

        Boolean exists = redisCache.setNx(resendKey, 60L, TimeUnit.SECONDS, requestBean.getIdentity());
        if (Objects.isNull(exists) || !exists) {
            throw new RestException(AuthorizationError.request_too_frequently);
        }
        MessageSender messageSender;
        try {
            messageSender = applicationContext.getBean(
                    requestBean.getChannelType().getSenderBeanName(), MessageSender.class
            );
        } catch (Exception e) {
            throw new RestException(AuthorizationError.no_such_message_send_channel);
        }
        String randomCode = StringUtils.generateRandomCode(6);

        MessageDTO message = new MessageDTO(null, requestBean.getIdentity(), "验证码", randomCode, null);
        messageSender.send(message);

        return RestStatus.SUCCESS.message();
    }

    @Override
    public String register(RegisterRequestBean requestBean) {
        boolean identityLegal = requestBean.getIdentity().matches(requestBean.getChannelType().getRegex());
        if (!identityLegal) {
            throw new RestException(AuthorizationError.illegal_identity_format);
        }

        return RestStatus.SUCCESS.message();
    }
}
