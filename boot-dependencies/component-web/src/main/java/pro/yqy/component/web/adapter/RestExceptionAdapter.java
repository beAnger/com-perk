package pro.yqy.component.web.adapter;

import pro.yqy.component.web.exception.RestException;

public interface RestExceptionAdapter {

    RestException handle(RestException e);

}
