package pro.yqy.component.web.util;

import pro.yqy.component.web.singleton.SingletonItem;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Objects;

public class StringUtils {

    public static String toString(Throwable e) {
        try (StringWriter stringWriter = new StringWriter();
             PrintWriter printWriter = new PrintWriter(stringWriter)) {
            e.printStackTrace(printWriter);
            stringWriter.flush();
            printWriter.flush();
            return stringWriter.toString();
        } catch (IOException ioException) {
            return ioException.getMessage();
        }
    }

    public static String toString(List<String> list) {
        return toString(list.toArray(new String[0]), ",");
    }

    public static String toString(String[] arrayString, String separator) {
        if (arrayString != null && arrayString.length != 0) {
            StringBuilder buffer = new StringBuilder();

            for (String string : arrayString) {
                buffer.append(string);
                buffer.append(separator);
            }

            buffer.deleteCharAt(buffer.length() - 1);
            return buffer.toString();
        } else {
            return SingletonItem.EMPTY_STRING;
        }
    }

    public static String toString(Object obj) {
        if (Objects.isNull(obj)) {
            return SingletonItem.EMPTY_STRING;
        }
        return obj instanceof String e ? e : obj + "";
    }
}
