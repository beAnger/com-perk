package com.yqy.common;

import java.util.function.Supplier;

public class AssertUtil {

    public static <T extends RuntimeException> void assertTrue(boolean b, Supplier<T> exception) {
        if (!b) {
            throw exception.get();
        }
    }

}
