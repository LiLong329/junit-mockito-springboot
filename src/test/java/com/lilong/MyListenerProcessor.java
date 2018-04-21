package com.lilong;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Scanner;

/**
 * 类名称：MyListenerProcessor<br>
 * 类描述：<br>
 * 创建时间：2018年04月18日<br>
 *
 * @author lichao
 * @version 1.0.0
 */
@Component
public class MyListenerProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * bean被完全初始化后进行回调
     * <p>
     * 修改记录:
     *
     * @param bean
     * @param beanName
     * @return java.lang.Object
     * @author lichao  2018/4/19
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            // 获取访问私有变量的权限
            field.setAccessible(true);

            // 单条 实体对象 或 string
            if (field.isAnnotationPresent(MockData.class)) {
                MockData mockData = field.getAnnotation(MockData.class);
                String path = mockData.value();
                if (path != null && !"".equals(path)) {
                    Class aClass = field.getType();
                    String jsonStr = jsonRead(path);
                    String expression = mockData.expression();
                    Object value = null;
                    if (expression != null && !"".equals(expression)) {
                        jsonStr = JSONPath.read(jsonStr, expression).toString();
                        if (!String.class.equals(aClass)) {
                            value = JSON.parseObject(jsonStr, aClass);
                        } else {
                            value = jsonStr;
                        }
                    } else if (!String.class.equals(aClass)) {
                        value = jsonStr;
                    }

                    if (value != null) {
                        try {
                            field.set(bean, value);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
            //  集合数据 返回list 或 string
            else if (field.isAnnotationPresent(MockList.class)) {
                MockList mockList = field.getAnnotation(MockList.class);
                String path = mockList.value();
                if (path != null && !"".equals(path)) {
                    Type genericType = field.getGenericType();
                    ParameterizedType param = (ParameterizedType) genericType;//强转换
                    Type[] argue = param.getActualTypeArguments();
                    Class aClass = Map.class;
                    Type type = argue[0];
                    if (! (type instanceof ParameterizedType)) {
                        aClass = (Class) type;
                    }

                    String jsonStr = jsonRead(path);
                    String expression = mockList.expression();
                    Object value = jsonStr;
                    if (!aClass.equals(String.class)) {
                        if (expression != null && !"".equals(expression)) {
                            jsonStr = JSONPath.read(jsonStr, expression).toString();
                        }
                        value = JSON.parseArray(jsonStr, aClass);
                    }
                    try {
                        field.set(bean, value);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return bean;
    }

    /**
     * 读取文件类容为字符串
     *
     * @return
     */
    public static String jsonRead(String filePath) {
        String realPath = MyListenerProcessor.class.getClassLoader().getResource(filePath).getPath();
        Scanner scanner = null;
        StringBuilder buffer = new StringBuilder();
        try {
            scanner = new Scanner(new File(realPath), "utf-8");
            while (scanner.hasNextLine()) {
                buffer.append(scanner.nextLine());
            }
        } catch (Exception e) {
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return buffer.toString();
    }

}

