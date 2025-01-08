package pro.yqy.authorization.model.enumeration.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum RegisterType {
    phone((byte) 1, "手机注册", "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$"),
    email((byte) 2, "邮箱注册","^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")
    ;

    private final byte code;
    private final String desc;
    private final String regex;

    @JsonCreator
    public static RegisterType from(byte code) {
        return Stream.of(values()).filter(t -> t.getCode() == code).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return this.code + "->" + this.desc;
    }
}
