package com.lilong;

import com.lilong.config.MockData;
import com.lilong.config.MockList;
import es.sm2baleares.tinglao.controller.LoginController;
import es.sm2baleares.tinglao.external.service.UserService;
import es.sm2baleares.tinglao.factory.HttpUtil;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.AopTestUtils;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * 类名称：LoginControllerTest<br>
 * 类描述：<br>
 * 创建时间：2018年04月20日<br>
 *
 * @author lichao
 * @version 1.0.0
 */
@PrepareForTest(HttpUtil.class)
public class LoginControllerTest extends BasedTest {


    @MockData(value = "data/case/test.json", expression = "$.description")
    String description;
    @MockData(value = "data/case/test.json", expression = "$.map")
    Map<String,Object> map;

    @MockList(value = "data/case/test.json", expression = "$.list")
    List<User> userList;
    @Autowired
    private LoginController loginController;
    @Autowired
    private UserService userService;
    @Mock
    private UserService userServiceMock;


    /**
     * 是否模拟
     *
     * @author lichao  2018/4/20
     * @param user
     * @return void
     */
    private void setMock(User user){
        if (user.getMock()){
            LoginController loginControllerAop = AopTestUtils.getTargetObject(loginController);
//            ReflectionTestUtils.setField(loginControllerAop,"userService",userServiceMock);
//            Map<String,Object> map = new HashMap<>();
//            map.put("resultCode", "00");
//            when(userServiceMock.login(Mockito.anyString(),Mockito.anyString())).thenReturn(map);

            mockStatic(HttpUtil.class);
            when(HttpUtil.getJsonUtf8MimeType(Mockito.anyMap())).thenReturn("ssss");

            JSONObject caLoginUrlJson = JSONObject.fromObject(new HashMap<>());
            when(HttpUtil.getJSONObjectByPost(Mockito.any(Map.class), Mockito.any(Map.class), Mockito.anyString(), eq("utf-8"))).thenReturn(caLoginUrlJson);

            System.out.println("----");
        }
    }

    private void restMock(User user){
        if (user.getMock()){
            LoginController loginControllerAop = AopTestUtils.getTargetObject(loginController);
            ReflectionTestUtils.setField(loginControllerAop,"userService",userService);

//            ThreadSafeMockingProgress.mockingProgress().reset();


//            ClassLoader staticClass = StaticClass.class.getClassLoader();
//            ClassLoader classLoader = HttpUtil.class.getClassLoader();
////            classLoader.findLoadedClass();
//            System.out.println(httpLoader);
//            System.out.println(super.httpLoader.equals(classLoader));
//            Thread.currentThread().setContextClassLoader(httpLoader);
        }
    }

    @Test
    public void test() {
        System.out.println(description);
        System.out.println(map.get("key"));
        userList.forEach(s -> {
                   System.out.println(s.getMock()?"模拟数据":"真实数据");
                    setMock(s);

                    Map<String,Object> result = loginController.login(s.getName(), s.getPassword());
                    System.out.println("返回值:\t"+result.get("resultCode"));
                   // assertTrue(s.getCode().equals(result.get("resultCode")));
                    restMock(s);
                    System.err.println("----------------------");
                }
        );

    }


}
