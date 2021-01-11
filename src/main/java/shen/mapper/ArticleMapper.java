package shen.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import shen.dao.Article;

import java.util.List;

@Mapper
public interface ArticleMapper {
    //增加一篇新文章
    int addNewArticle(Article article);

    //文章修改
    int UpdateArticle(Article article);

    //通过state查询
    int getArticleCountByState(@Param("state") Integer state, @Param("uid") Integer uid, @Param("keywords") String keywords);

    //回收站查询
    List<Article> getArticleByState(@Param("state") Integer state, @Param("start") Integer start, @Param("count") Integer count, @Param("uid") Integer uid, @Param("keywords") String keywords);

    //回收站修改
    int updateArticleState(@Param("aids") Integer aids[], @Param("state") Integer state);

    //文章放入回收站
    int updateArticleStateById(@Param("articleId") Integer articleId, @Param("state") Integer state);

    //删除文章
    int deleteArticleById(@Param("aids") Integer[] aids);

    //获取文章类型
    Article getArticleById(Integer aid);

    //获取浏览数据 加1
    void pvIncrement(Integer aid);

    //数据日期
    void pvStatisticsPerDay();

    //获取最近日期
    List<String> getCategories(Integer uid);

    //获取最近数据
    List<Integer> getDataStatistics(Integer uid);


}
