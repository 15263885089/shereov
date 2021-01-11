package shen.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import shen.dao.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {
    //查询栏目信息
    List<Category> getAllCategories();

    //删除栏目信息
    int deleteCategoryByIds(@Param("ids") String[] ids);

    //修改栏目信息
    int updateCategoryById(Category category);

    //增加栏目信息
    int addCategory(Category category);
}
