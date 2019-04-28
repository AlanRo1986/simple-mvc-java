package com.lanxinbase.system.utils;

/**
 * Created by alan on 2018/12/12.
 */
public class OutPut {
    protected static void out(Object o) {
        if (o == null) {
            System.out.println("null");
        } else {
            System.out.println(o.toString());
        }
    }
}
