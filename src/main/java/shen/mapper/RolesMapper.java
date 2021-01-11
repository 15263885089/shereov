package shen.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import shen.dao.Role;

import java.util.List;

@Mapper
public interface RolesMapper {
    //增加user及其role
    int addRoles(@Param("roles") String[] roles, @Param("uid") Integer uid);

    //查询所有用户等级
    List<Role> getRolesByUid(Integer uid);
}
