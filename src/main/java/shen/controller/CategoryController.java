package shen.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import shen.dao.Category;
import shen.dao.ResBean;
import shen.mapper.CategoryMapper;
import shen.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired(required = false)
    CategoryService categoryService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @RequestMapping(value = "/{ids}", method = RequestMethod.DELETE)
    public ResBean deleteCategoryByIds(@PathVariable String ids) {
        boolean result = categoryService.deleteCategoryByIds(ids);
        if (result) {
            return new ResBean("success", "刪除成功");
        }
        return new ResBean("error", "刪除失败");

    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public ResBean updateCategoryById(Category category) {
        if (category.getCateName() == null || "".equals(category.getCateName())) {
            return new ResBean("error", "修改栏目信息");
        }
        int result = categoryService.updateCategoryById(category);
        if (result == 1) {
            return new ResBean("success", "修改成功");
        }
        return new ResBean("error", "修改失败");
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResBean addCategory(Category category) {
        int i = categoryService.addCategory(category);
        if (i == 1) {
            return new ResBean("success", "增加成功");
        }
        return new ResBean("error", "增加失败");
    }
}