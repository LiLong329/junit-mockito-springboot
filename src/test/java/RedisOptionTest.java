import com.didispace.Application;
import com.didispace.MyBean;
import com.didispace.redis.IdGenerator;
import com.didispace.redis.RedisService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@PrepareForTest(IdGenerator.class)
public class RedisOptionTest  {
    private static final String TEST_KEY = "000000";

//    @Rule
//    public PowerMockRule rule = new PowerMockRule();

    @Autowired
    private MyBean myBean;

    @Test
    public void mockStaticMethod() throws Exception {
        // Given
        final long expectedId = 2L;
        mockStatic(IdGenerator.class);
        when(IdGenerator.generateNewId()).thenReturn(expectedId);

        // When
        final String message = myBean.generateMessage();

        // Then
        assertEquals(expectedId, message);
    }

    @Autowired
    @Qualifier("siteRedisService")
    private RedisService siteRedisService;

    @Autowired
    @Qualifier("userRedisService")
    private RedisService userRedisService;

    @Autowired
    @Qualifier("sessionRedisService")
    private RedisService sessionRedisService;

    @Test
    @Ignore
    public void test0() {
        siteRedisService.delete("0010023850");
    }

    @Test
    @Ignore
    public void test1() {
        userRedisService.delete("0010023850");
    }

    @Test
    @Ignore
    public void test2() {
        userRedisService.deleteAll();
    }

    @Test
    @Ignore
    public void testSessionRedis() {
        sessionRedisService.set(TEST_KEY, "hello!world!");
    }

    @Test
    @Ignore
    public void testResetExpireTime() {
        Long keyExpire = sessionRedisService.getKeyExpire(TEST_KEY);
        System.out.println(keyExpire);
        sessionRedisService.setKeyExpire(TEST_KEY, 15L);
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

    @Test
    public void testDeleteSession() {
        sessionRedisService.delete("2018032503");
        sessionRedisService.flushDB();
    }
    

}
