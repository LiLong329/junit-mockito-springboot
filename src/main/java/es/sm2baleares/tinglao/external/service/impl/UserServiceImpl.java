package es.sm2baleares.tinglao.external.service.impl;

import es.sm2baleares.tinglao.external.service.UserService;
import es.sm2baleares.tinglao.factory.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 类名称：UserServiceImpl<br>
 * 类描述：<br>
 * 创建时间：2018年04月14日<br>
 *
 * @author lichao
 * @version 1.0.0
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Override
    public Map<String,Object> login(String name, String password) {
        Map<String,Object> map = new HashMap<>();
        try {
            String result = HttpUtil.post();
            map.put("resultCode", result);
            LOGGER.debug("请求：name{} password{}",name,password);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("resultCode", "01");
        }
        return map;
    }
}
