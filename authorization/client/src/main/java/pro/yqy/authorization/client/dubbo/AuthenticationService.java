package pro.yqy.authorization.client.dubbo;

public interface AuthenticationService {
    Object authentication(String token);
}
