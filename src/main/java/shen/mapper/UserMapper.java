package shen.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import shen.dao.Role;
import shen.dao.User;

import java.util.List;

@Mapper
public interface UserMapper {
    //用户名查询
    User loadUserByUsername(@Param("username") String username);

    //添加用户名，密码，昵称
    long reg(User user);

    //添加邮箱
    int updateUserEmail(@Param("email") String email, @Param("id") Integer id);

    // 昵称查询
    List<User> getUserByNickname(@Param("nickname") String nickname);

    //roles查询
    List<Role> getAllRole();

    //用户禁用 1可用 0禁用
    int updateUserEnabled(@Param("enabled") Boolean enabled, @Param("uid") Integer uid);

    //删除用户
    int deleteUserById(Integer id);

    //删除roles_user
    int deleteUserRolesByUid(Integer id);

    //增加
    int setUserRoles(@Param("rids") Integer[] rids, @Param("id") Integer id);

    //Id查询
    User getUserById(@Param("id") Integer id);
}
