//package es.sm2baleares.tinglao.factory;
//
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//
///**
// * 类名称：ApplicationStartListener<br>
// * 类描述：<br>
// * 创建时间：2018年04月18日<br>
// *
// * @author lichao
// * @version 1.0.0
// */
//public class ApplicationStartListener implements ApplicationListener<ContextRefreshedEvent> {
//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
//        String[] beanNamesForAnnotation= applicationContext.getBeanNamesForAnnotation(MyAnnotion.class);
//        if(beanNamesForAnnotation != null){
//            for (String name: beanNamesForAnnotation  ) {
//                System.out.println("初始化时我被调用了。" + name);
//            }
//
//        }
//
//    }
//}
