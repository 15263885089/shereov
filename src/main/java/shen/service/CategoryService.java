package shen.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shen.dao.Category;
import shen.mapper.CategoryMapper;

import java.sql.Timestamp;
import java.util.List;


@Service
@Transactional
public class CategoryService {
    @Autowired(required = false)
    CategoryMapper categoryMapper;

    /**
     * 获取文章种类信息
     *
     * @return
     */
    public List<Category> getAllCategories() {
        return categoryMapper.getAllCategories();
    }
    /**
     * 删除文章种类
     *
     * @return
     */
    public boolean deleteCategoryByIds(String ids) {
        String[] split = ids.split(",");
        int result = categoryMapper.deleteCategoryByIds(split);
        return result == split.length;
    }
    /**
     * 修改
     *
     * @return
     */
    public int updateCategoryById(Category category) {
        return categoryMapper.updateCategoryById(category);
    }
    /**
     * 增加
     *
     * @return
     */
    public int addCategory(Category category) {
        category.setDate(new Timestamp(System.currentTimeMillis()));
        return categoryMapper.addCategory(category);
    }
}
