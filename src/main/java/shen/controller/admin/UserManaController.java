package shen.controller.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import shen.dao.ResBean;
import shen.dao.Role;
import shen.dao.User;
import shen.service.UserService;

import java.util.List;


@RestController
@RequestMapping("/admin")
public class UserManaController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<User> getUserByNickname(String nickname) {
        return userService.getUserByNickname(nickname);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public List<Role> getAllRole() {
        return userService.getAllRole();
    }

    @RequestMapping(value = "/user/enabled", method = RequestMethod.PUT)
    public ResBean updateUserEnabled(Boolean enabled, int uid) {
        if (userService.updateUserEnabled(enabled, uid) == 1) {
            return new ResBean("success", "更新成功!");
        } else {
            return new ResBean("error", "更新失败!");
        }
    }

    @RequestMapping(value = "/user/{uid}", method = RequestMethod.DELETE)
    public ResBean deleteUserById(@PathVariable int uid) {
        if (userService.deleteUserById(uid) == 1) {
            return new ResBean("success", "删除成功!");
        } else {
            return new ResBean("error", "删除失败!");
        }
    }

    @RequestMapping(value = "/user/role", method = RequestMethod.PUT)
    public ResBean updateUserRoles(Integer[] rids, int id) {
        if (userService.updateUserRoles(rids, id) == rids.length) {
            return new ResBean("success", "更新成功!");
        } else {
            return new ResBean("error", "更新失败!");
        }
    }
}
