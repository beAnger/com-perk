package pro.yqy.component.web.singleton;

import com.fasterxml.jackson.databind.ObjectMapper;

public interface SingletonItem {

    ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    String EMPTY_STRING = "";

    String OPERATE_SUCCESS = "操作成功";
}
