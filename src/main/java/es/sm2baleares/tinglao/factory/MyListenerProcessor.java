//package es.sm2baleares.tinglao.factory;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.BeanPostProcessor;
//import org.springframework.stereotype.Component;
//import org.springframework.util.ReflectionUtils;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//
///**
// * 类名称：MyListenerProcessor<br>
// * 类描述：<br>
// * 创建时间：2018年04月18日<br>
// *
// * @author lichao
// * @version 1.0.0
// */
//@Component
//public class MyListenerProcessor implements BeanPostProcessor {
//    @Override
//    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        return bean;
//    }
//
//    @Override
//    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
////        System.out.println("初始化class：\t"+bean.getClass().getName());
//        Field[] fields = bean.getClass().getDeclaredFields();
//        for (Field field :   fields) {
//            field.setAccessible(true);//获取访问私有变量的权限
//            if (field.isAnnotationPresent(MyAnnotion.class)){
//                MyAnnotion an = field.getAnnotation(MyAnnotion.class);
//                System.out.println(an.value());
//                try {
//                    field.set(bean,999);
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return bean;
//    }
//}