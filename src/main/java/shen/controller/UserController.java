package shen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import shen.dao.ResBean;
import shen.service.UserService;
import shen.utils.Util;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping("/currentUserName")
    public String currentUserName(){
        return Util.getCurrentUser().getNickname();
    }
    @RequestMapping("/currentUserId")
    public Integer currentUserId(){
        return Util.getCurrentUser().getId();
    }
    @RequestMapping("/currentUserEmail")
    public String currentUserEmail(){
        return Util.getCurrentUser().getEmail();
    }
    @RequestMapping("/isAdmin")
    public Boolean isAdmin(){
        List<GrantedAuthority> authorities= (List<GrantedAuthority>) Util.getCurrentUser().getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if(authority.getAuthority().contains("管理员")){
                return true;
            }
        }
        return false;
    }
    @RequestMapping(value = "/updateUserEmail",method = RequestMethod.PUT)
    public ResBean updateUserEmail(String email){
        if(userService.updateUserEmail(email)==1){
            return new ResBean("success","开启成功");
        }
        return  new ResBean("error","开启失败");
    }
}
