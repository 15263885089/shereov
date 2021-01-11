package shen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shen.dao.Role;
import shen.dao.User;
import shen.mapper.RolesMapper;
import shen.mapper.UserMapper;
import shen.utils.Util;


import java.util.List;

@Service
@Transactional
public class UserService implements UserDetailsService {
    @Autowired(required = false)
    UserMapper userMapper;
    @Autowired(required = false)
    RolesMapper rolesMapper;
    @Autowired(required = false)
    PasswordEncoder passwordEncoder;

    @Override
    //用户名校验
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userMapper.loadUserByUsername(s);
        if (user == null) {
            //避免返回null，这里返回一个不含有任何值的User对象，在后期的密码比对过程中一样会验证失败
            return new User();
        }
        //查询用户的角色信息，并返回存入user
        List<Role> roles = rolesMapper.getRolesByUid(user.getId());
        user.setRoles(roles);
        return user;
    }

    /**
     * @param user
     * @return 0表示成功
     * 1表示用户名重复
     * 2表示失败
     */
    public int reg(User user) {
        User loadUserByUsername = userMapper.loadUserByUsername(user.getUsername());
        //用这个用户名能否读出数据，能代表用户名重复
        if (loadUserByUsername != null) {
            //重复
            return 1;
        }
        //插入用户,插入之前先对密码进行加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        long reg = userMapper.reg(user);
        //配置用户的角色，默认都是普通用户
        String[] roles = new String[]{"2"};
        int i = rolesMapper.addRoles(roles, user.getId());
        boolean b = i == roles.length && reg == 1;
        if (b) {
            //成功
            return 0;
        } else {
            //失败
            return 2;
        }
    }

    /**
     * 修改用户邮箱
     *
     * @return
     */
    public int updateUserEmail(String email) {
        return userMapper.updateUserEmail(email, Util.getCurrentUser().getId());
    }

    /**
     * 用户昵称
     *
     * @return
     */
    public List<User> getUserByNickname(String nickname) {
        List<User> list = userMapper.getUserByNickname(nickname);
        return list;
    }

    /**
     * 用户权限查询
     *
     * @return
     */
    public List<Role> getAllRole() {
        return userMapper.getAllRole();
    }

    /**
     * 用户禁用与开启
     *
     * @return
     */
    public int updateUserEnabled(Boolean enabled, int uid) {
        return userMapper.updateUserEnabled(enabled, uid);
    }

    /**
     * 删除用户
     *
     * @return
     */
    public int deleteUserById(int uid) {
        return userMapper.deleteUserById(uid);
    }

    /**
     * 设置用户权限
     *
     * @return
     */
    public int updateUserRoles(Integer[] rids, int id) {
        int i = userMapper.deleteUserRolesByUid(id);
        return userMapper.setUserRoles(rids, id);
    }

    public User getUserById(int id) {
        return userMapper.getUserById(id);
    }
}
