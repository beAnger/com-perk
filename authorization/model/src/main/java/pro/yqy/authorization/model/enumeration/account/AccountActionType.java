package pro.yqy.authorization.model.enumeration.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum AccountActionType {
    register((byte) 1, "注册"),
    login((byte) 2, "登录"),
    reset_password((byte) 3, "重置密码"),
    ;
    private final byte code;

    private final String desc;

    @JsonCreator
    public static AccountActionType from(byte code) {
        return Stream.of(values()).filter(t -> t.getCode() == code).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return this.code + "->" + this.desc;
    }
}
