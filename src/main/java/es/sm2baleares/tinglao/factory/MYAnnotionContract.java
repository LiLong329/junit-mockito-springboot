//package es.sm2baleares.tinglao.factory;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Method;
//
///**
// * 类名称：MYAnnotionContract<br>
// * 类描述：<br>
// * 创建时间：2018年04月18日<br>
// *
// * @author lichao
// * @version 1.0.0
// */
//@Aspect
//@Component
//public class MYAnnotionContract {
//
//
//    @Pointcut("@annotation(es.sm2baleares.tinglao.factory.MyAnnotion)")
//    public void logPointCut() {
//
//    }
//
//    @Before("logPointCut()")
//    public void saveSysLog(JoinPoint joinPoint) {
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        Method method = signature.getMethod();
//
//        MyAnnotion myAnnotion = method.getAnnotation(MyAnnotion.class);
//        if(myAnnotion != null){
//            //注解上的描述
//            System.out.println(myAnnotion.value());
////			sysLog.setOperation(syslog.value());
//        }
//
//        //请求的方法名
//        String className = joinPoint.getTarget().getClass().getName();
//        String methodName = signature.getName();
//        System.out.println(className + "." + methodName + "()");
//
//        //请求的参数
//        Object[] args = joinPoint.getArgs();
////        String params = JSON.toJSONString(args[0]);
//        System.out.println(args);
//
//
//
//    }
//
//}
