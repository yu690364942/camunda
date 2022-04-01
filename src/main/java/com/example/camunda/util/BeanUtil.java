package com.example.camunda.util;

import com.alibaba.druid.sql.PagerUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.lang.reflect.Field;

/**
 * <p>TiTle: BeanUtil</p>
 * <p>Description: BeanUtil</p>
 * <p>Company: www.nbcb.cn</p>
 *
 * @author yhq
 * @version 1.0
 * @since 2022/4/1
 */
public class BeanUtil {
    public static void main(String[] args) {
        final B b = new B(1);
        final C c = new C();
        try {
            transfer(b,c,b.getClass());
            System.out.println(c);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static  <D,S> void transfer(S src,D dst,Class<?> clazz) throws IllegalAccessException {
        for (Field declaredField : clazz.getDeclaredFields()) {
            declaredField.setAccessible(true);
            final Object o = declaredField.get(src);
            declaredField.set(dst,o);
        }
        if(clazz != Object.class){
            transfer(src,dst,clazz.getSuperclass());
        }
    }

}

class A{
    private int i = 1;
    private List<String> list = new ArrayList<>();
    private String s;
    public A(){

    }

    public A(int i){
        list.add("123");
        list.add("456");
        list.add("789");
    }
}

class B extends A{
    private HashMap map = new HashMap();
    public B(int i){
        super(i);
        map.put("123","123");
        map.put("12","12");
    }
    public B(){

    }
}

class C extends B{
    C(){

    }
}



