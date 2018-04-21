package com.lilong;

import es.sm2baleares.tinglao.SampleApplication;
import es.sm2baleares.tinglao.controller.LoginController;
import es.sm2baleares.tinglao.external.service.UserService;
import es.sm2baleares.tinglao.factory.HttpUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.AopTestUtils;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * 类名称：SpringTest<br>
 * 类描述：<br>
 * 创建时间：2018年04月18日<br>
 *
 * @author lichao
 * @version 1.0.0
 */

//@RunWith(SpringJUnit4ClassRunner.class)
//@PrepareForTest(HttpUtil.class)
//@SpringBootTest(classes = {SampleApplication.class, MyListenerProcessor.class})
//@PowerMockIgnore( {"javax.management.*"})
//@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)


@PowerMockIgnore({"java.lang.management.*","javax.management.*","javax.xml.*","org.xml.sax.*","org.apache.xerces.*","org.w3c.*"})
@RunWith(PowerMockRunner.class)
@PrepareForTest(HttpUtil.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {SampleApplication.class, MyListenerProcessor.class})
public class SpringTest {
    @MockData(value = "data/case/test.json", expression = "$.openMock")
    Boolean openMock;
    @MockList(value = "data/case/test.json", expression = "$.list")
    List<User> stuList;

//    @InjectMocks
    @Autowired
    private LoginController loginController;

    @Autowired
    private UserService userService;

    @Mock
    private UserService userServiceMock;

    @Before
    public void setUp(){
        mockStatic(HttpUtil.class);
        when(HttpUtil.post()).thenReturn("mock_post");
    }

    private void setMock(Boolean mockMode){
        if (mockMode){
            MockitoAnnotations.initMocks(this);
            LoginController loginControllerAop = AopTestUtils.getTargetObject(loginController);
            ReflectionTestUtils.setField(loginControllerAop,"userService",userServiceMock);
            Map<String,Object> map = new HashMap<>();
            map.put("resultCode", "00");
            when(userServiceMock.login(Mockito.anyString(),Mockito.anyString())).thenReturn(map);
        }
    }


    @Test
    public void test() {
        setMock(openMock);
        stuList.forEach(s -> {
                    Map result = loginController.login(s.getName(), s.getPassword());
                    System.out.println(result);
                }
        );
    }


}
