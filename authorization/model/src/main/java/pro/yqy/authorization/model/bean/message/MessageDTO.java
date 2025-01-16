package pro.yqy.authorization.model.bean.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MessageDTO {
    private String to;

    private String subject;

    private String body;
}
