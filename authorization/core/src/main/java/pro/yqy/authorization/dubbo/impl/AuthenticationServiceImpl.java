package pro.yqy.authorization.dubbo.impl;

import org.apache.dubbo.config.annotation.DubboService;
import pro.yqy.authorization.client.dubbo.AuthenticationService;

@DubboService
public class AuthenticationServiceImpl implements AuthenticationService {
    @Override
    public Object authentication(String token) {
        return null;
    }
}
