package shen.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TagsMapper {
    //删除
    int deleteTagsByAid(Integer aid);

    //添加
    int saveTags(@Param("tags") String[] tags);

    //查询
    List<Integer> getTagsIdByTagName(@Param("tagNames") String[] tagNames);

    //添加
    int saveTags2ArticleTags(@Param("tagIds") List<Integer> tagIds, @Param("aid") Integer aid);
}
