package com.lilong;

import es.sm2baleares.tinglao.SampleApplication;
import es.sm2baleares.tinglao.factory.HttpUtil;
import es.sm2baleares.tinglao.factory.StaticClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * 类名称：MockitoBasedTest<br>
 * 类描述：<br>
 * 创建时间：2018年04月20日<br>
 *
 * @author lichao
 * @version 1.0.0
 */
@PowerMockIgnore({"java.lang.management.*","javax.management.*","javax.xml.*","org.xml.sax.*","org.apache.xerces.*","org.w3c.*"})
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringRunner.class)
@SpringBootTest(classes = {SampleApplication.class, MyListenerProcessor.class})
@WebAppConfiguration
public abstract class BasedTest {
    protected     final  ClassLoader httpLoader ;
    public BasedTest(){
        httpLoader = HttpUtil.class.getClassLoader();
        System.out.println(httpLoader);
    }
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public  void main1() {
        ClassLoader httpLoader =  StaticClass.class.getClassLoader();
        System.out.println(httpLoader);
    }

}
