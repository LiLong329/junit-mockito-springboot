package com.lilong.json;

import org.junit.Test;

import java.lang.reflect.Field;
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

    private List<String> list;

    @Test
    public void testA(){
        Type t = null;
        try {
            t = ClassTest.class.getDeclaredField("list").getGenericType();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        if (ParameterizedType.class.isAssignableFrom(t.getClass())) {
            for (Type t1:((ParameterizedType)t).getActualTypeArguments()) {
                System.out.print(t1 + ",");
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        Class clazz = String.class;
        List<String> list = new ArrayList<>();
//        Type t = list.getClass().getGenericSuperclass();
//        System.out.println(t);

        Field[] fs = clazz.getDeclaredFields(); // 得到所有的fields

        for(Field f : fs)
        {
            Type genericType = f.getGenericType();

            for (Type t1:((ParameterizedType)genericType).getActualTypeArguments()) {
                System.out.print(t1 + ",");
            }

            Class fieldClazz = f.getType(); // 得到field的class及类型全路径

            if(fieldClazz.isPrimitive())  continue;  //【1】 //判断是否为基本类型

            if(fieldClazz.getName().startsWith("java.lang")) continue; //getName()返回field的类型全路径；

            if(fieldClazz.isAssignableFrom(List.class)) //【2】
            {
                Type fc = f.getGenericType(); // 关键的地方，如果是List类型，得到其Generic的类型

                if(fc == null) continue;

                if(fc instanceof ParameterizedType) // 【3】如果是泛型参数的类型
                {
                    ParameterizedType pt = (ParameterizedType) fc;

                    Class genericClazz = (Class)pt.getActualTypeArguments()[0]; //【4】 得到泛型里的class类型对象。

//                    m.put(f.getName(), genericClazz);
//
//                    Map<String, Class> m1 = prepareMap(genericClazz);
//
//                    m.putAll(m1);
                }
            }
        }

        Field declaredFields = null;
        try {
            declaredFields = list.getClass().getDeclaredField("");
            Type type = declaredFields.getGenericType();
            System.out.println(type);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

//        for (Field re :  declaredFields) {
//            Type type = re.getGenericType();
//            System.out.println(type);
//        }
//        String b = "ss";
//        boolean flag = a.isAssignableFrom(b.getClass());
//        System.out.println(flag);
    }
}
