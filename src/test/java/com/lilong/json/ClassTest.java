package com.lilong.json;

import com.lilong.DeClassLoader;
import es.sm2baleares.tinglao.factory.HttpUtil;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 类名称：ClassTest<br>
 * 类描述：<br>
 * 创建时间：2018年04月19日<br>
 *
 * @author lichao
 * @version 1.0.0
 */
public class ClassTest {

    public static void main(String[] args) throws Exception {
//        DeClassLoader deClassLoader = new DeClassLoader("");
        String path = ClassTest.class.getClassLoader().getResource("data/case").getPath();

//        Thread.currentThread().getContextClassLoader().
        DeClassLoader deClassLoader = new DeClassLoader(path);
        Class httpUtil = deClassLoader.loadClass("es.sm2baleares.tinglao.factory.SS");
        Method getMethod = httpUtil.getDeclaredMethod("post", null);
        Object hu = httpUtil.newInstance();
        Object o = getMethod.invoke(hu, null);

        System.out.println(o);

    }
}
