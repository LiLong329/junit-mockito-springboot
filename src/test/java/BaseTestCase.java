import com.didispace.Application;
import com.didispace.MyBean;
import com.didispace.redis.IdGenerator;
import com.didispace.redis.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Objects;

import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@EnableTransactionManagement
@PowerMockIgnore({"java.lang.management.*","javax.management.*","javax.xml.*","org.xml.sax.*","org.apache.xerces.*","org.w3c.*"})
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@PrepareForTest(IdGenerator.class)
@SpringBootTest(webEnvironment = MOCK,classes = {Application.class})
public class BaseTestCase {

    private static final String TEST_KEY = "000000";

    @Autowired
    private MyBean myBean;

    @Autowired
    @Qualifier("sessionRedisService")
    private RedisService sessionRedisService;

    @Test
    public void mockStaticMethod() throws Exception {
        // Given
        final long expectedId = 2L;
        mockStatic(IdGenerator.class);
        when(IdGenerator.generateNewId()).thenReturn(expectedId);

        // When
        final String message = myBean.generateMessage();
        System.out.printf(";;;"+ message);
        // Then
//        assertEquals(expectedId, message);
    }

    @Test
    public void testSaveSession() {
        sessionRedisService.set(TEST_KEY, "true");
    }

    @Test
    public void testGetSession() {
        String sessionToken = sessionRedisService.get(TEST_KEY);
        System.out.println(sessionToken == null ? "null" : "null字符串");
        if (Objects.isNull(sessionToken) || sessionToken.isEmpty()) {
            System.out.println("拦截!");
        } else {
            System.out.println("通过");
        }
    }
}