package com.lilong;

import es.sm2baleares.tinglao.external.service.UserService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 类名称：MockedBeanFactory<br>
 * 类描述：<br>
 * 创建时间：2018年04月20日<br>
 *
 * @author lichao
 * @version 1.0.0
 */
@Component
public class MockedBeanFactory {
    @Bean
    public UserService outerSerive(){
        return  Mockito.mock(UserService.class);
    }

}
