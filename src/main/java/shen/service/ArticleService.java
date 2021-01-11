package shen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shen.dao.Article;
import shen.mapper.ArticleMapper;
import shen.mapper.TagsMapper;
import shen.utils.Util;
import java.util.List;
import java.sql.Timestamp;

@Service
@Transactional
public class ArticleService {
    @Autowired(required = false)
    ArticleMapper articleMapper;
    @Autowired(required = false)
    TagsMapper tagsMapper;

    public int addNewArticle(Article article) {
        //处理文章摘要 如果没写的话就截取前50个字符
        if (article.getSummary() == null || "".equals(article.getSummary())) {
            //直接截取
            String stripHtml = stripHtml(article.getHtmlContent());
            article.setSummary(stripHtml.substring(0, stripHtml.length() > 50 ? 50 : stripHtml.length()));
        }
        //id= -1就是刚添加的 ，有id就是更新
        if (article.getId() == -1) {
            //添加操作
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            if (article.getState() == 1) {
                //设置发表日期
                article.setPublishDate(timestamp);

            }
            article.setEditTime(timestamp);
            //设置当前用户，同时添加到数据库
            article.setUid(Util.getCurrentUser().getId());
            int i = articleMapper.addNewArticle(article);
            //文章标签
            String[] dynamicTags = article.getDynamicTags();
            //通过id绑定文章跟tag两个类
            if (dynamicTags != null && dynamicTags.length > 0) {
                int tags = addTagsToArticle(dynamicTags, article.getId());
                if (tags == -1) {
                    return tags;
                }
            }
            return i;
        } else {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            if (article.getState() == 1) {
                //设置发表日期
                article.setPublishDate(timestamp);
            }
            //更新
            article.setEditTime(new Timestamp(System.currentTimeMillis()));
            int i = articleMapper.UpdateArticle(article);
            //修改标签
            String[] dynamicTags = article.getDynamicTags();
            if (dynamicTags != null && dynamicTags.length > 0) {
                int tags = addTagsToArticle(dynamicTags, article.getId());
                if (tags == -1) ;
                {
                    return tags;
                }
            }
            return i;
        }


    }

    private int addTagsToArticle(String[] dynamicTags, int aid) {
        //1.删除该文章目前所有的标签
        tagsMapper.deleteTagsByAid(aid);
        //2.将上传上来的标签全部存入数据库
        tagsMapper.saveTags(dynamicTags);
        //3.查询这些标签的id
        List<Integer> tagsIdByTagName = tagsMapper.getTagsIdByTagName(dynamicTags);
        //4.重新给文章设置标签
        int i = tagsMapper.saveTags2ArticleTags(tagsIdByTagName, aid);
        return i == dynamicTags.length ? i : -1;
    }

    //获取html源码<p><br>\\标签下的内容
    private String stripHtml(String content) {
        content = content.replaceAll("<p .*?>", "");
        content = content.replaceAll("<br\\s*/?>", "");
        content = content.replaceAll("\\<.*?>", "");
        return content;
    }


    /**
     * 回收站中的文章信息
     *
     * @return
     */
    public List<Article> getArticleByState(Integer state, Integer page, Integer count, String keywords) {
        int start = (page - 1) * count;
        int id = Util.getCurrentUser().getId();
        return articleMapper.getArticleByState(state, start, count, id, keywords);
    }

    /**
     * 回收站中的文章数量
     *
     * @return
     */
    public int getArticleCountByState(Integer state, int uid, String keywords) {
        return articleMapper.getArticleCountByState(state, uid, keywords);
    }

    /**
     * 文章 进入回收站
     *
     * @return
     */
    public int updateArticleState(Integer[] aids, Integer state) {
        if (state == 2) {
            //回收站中的文章删除
            return articleMapper.deleteArticleById(aids);
        } else {
            return articleMapper.updateArticleState(aids, 2);//放入到回收站中
        }
    }

    /**
     * 文章  从回收站还原在原处
     *
     * @return
     */
    public int restoreArticle(Integer articleId) {
        return articleMapper.updateArticleStateById(articleId, 1);
    }

    /**
     * 文章浏览 pageView+1
     *
     * @return
     */
    public Article getArticleById(int id) {
        Article articleById = articleMapper.getArticleById(id);
        articleMapper.pvIncrement(id);
        return articleById;
    }

    /**
     * 获取pageView的数据以及日期
     */
    public void pvStatisticsPerDay() {
        articleMapper.pvStatisticsPerDay();
    }


    /**
     * 获取最近七天的日期
     *
     * @return
     */
    public List<String> getCategories() {
        return articleMapper.getCategories(Util.getCurrentUser().getId());
    }

    /**
     * 获取最近七天的数据
     *
     * @return
     */
    public List<String> getDataStatistics() {
        return articleMapper.getCategories(Util.getCurrentUser().getId());
    }
}
