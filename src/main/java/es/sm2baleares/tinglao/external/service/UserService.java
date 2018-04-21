package es.sm2baleares.tinglao.external.service;

import java.util.Map;

/**
 * 类名称：UserService<br>
 * 类描述：<br>
 * 创建时间：2018年04月14日<br>
 *
 * @author lichao
 * @version 1.0.0
 */
public interface UserService {

    Map<String,Object> login(String name, String password);
}
