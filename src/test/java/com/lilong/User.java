package com.lilong;

/**
 * 类名称：Stu<br>
 * 类描述：<br>
 * 创建时间：2018年04月18日<br>
 *
 * @author lichao
 * @version 1.0.0
 */
public class User {
    private String name;
    ;
    private String code;

    private String password;

    private Boolean mock;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getMock() {
        return mock;
    }

    public void setMock(Boolean mock) {
        this.mock = mock;
    }
}
