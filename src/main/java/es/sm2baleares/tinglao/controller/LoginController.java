package es.sm2baleares.tinglao.controller;

import es.sm2baleares.tinglao.external.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * 类名称：LoginController<br>
 * 类描述：<br>
 * 创建时间：2018年04月14日<br>
 *
 * @author lichao
 * @version 1.0.0
 */
@Controller
public class LoginController {

    private UserService userService;
//    @MyAnnotion(num = 1000)
    int myName;
    public LoginController(@Autowired UserService userService){
        this.userService = userService;
    }
    @RequestMapping("/login")
    public Map<String,Object> login(String name, String password){
        System.out.println("----->>>>name:\t"+ name +"\tpassword:\t"+ password);
        return userService.login(name,password);
    }

}
