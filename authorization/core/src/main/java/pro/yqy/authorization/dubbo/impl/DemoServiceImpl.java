package pro.yqy.authorization.dubbo.impl;

import org.apache.dubbo.config.annotation.DubboService;
import pro.yqy.authorization.client.dubbo.DemoService;

@DubboService
public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }

}
