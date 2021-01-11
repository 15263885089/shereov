package shen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shen.dao.ResBean;
import shen.dao.User;
import shen.service.UserService;

@RestController
public class LoginController {
    @Autowired
    UserService userService;

    @RequestMapping("/login_error")
    public ResBean loginError() {
        return new ResBean("error", "登录失败!");
    }

    @RequestMapping("/login_success")
    public ResBean loginSuccess() {
        return new ResBean("success", "登录成功!");
    }

    /**
     * 如果自动跳转到这个页面，说明用户未登录，返回相应的提示即可
     * <p>
     * 如果要支持表单登录，可以在这个方法中判断请求的类型，进而决定返回JSON还是HTML页面
     *
     * @return
     */
    @RequestMapping("/login_page")
    public ResBean loginPage() {
        return new ResBean("error", "尚未登录，请登录!");
    }

    @PostMapping("/reg")
    public ResBean reg(User user) {
        int result = userService.reg(user);
        if (result == 0) {
            //成功
            return new ResBean("success", "注册成功!");
        } else if (result == 1) {
            return new ResBean("error", "用户名重复，注册失败!");
        } else {
            //失败
            return new ResBean("error", "注册失败!");
        }
    }
}
