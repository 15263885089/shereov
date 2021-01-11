package shen.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shen.dao.Article;
import shen.dao.ResBean;
import shen.service.ArticleService;
import shen.utils.Util;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    @Autowired
    ArticleService articleService;

    /**
     * 上传文章
     *
     * @return 返回值为上传结果
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResBean addNewArticle(Article article) {
        int i = articleService.addNewArticle(article);
        if (i == 1) {
            return new ResBean("success", article.getId() + "");
        } else {
            return new ResBean("error", article.getState() == 0 ? "文章保存失败" : "文章发表失败");
        }
    }

    /**
     * 上传图片
     *
     * @return 返回值为图片的地址
     */
    public ResBean uploadImg(HttpServletRequest request, MultipartFile image) {
        StringBuffer stringBuffer = new StringBuffer();
        String filePath = "blogimg" + simpleDateFormat.format(new Date());
        String imgFolderPath = request.getServletContext().getRealPath(filePath);
        File imgFolder = new File(imgFolderPath);
        if (!imgFolder.exists()) {
            imgFolder.mkdirs();
        }
        stringBuffer.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath())
                .append(filePath);
        String imgName = UUID.randomUUID() + "_" + image.getOriginalFilename().replaceAll(" ", "");
        try {
            IOUtils.write(image.getBytes(), new FileOutputStream(new File(imgFolder, imgName)));
            stringBuffer.append("/").append(imgName);
            return new ResBean("success", stringBuffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResBean("error", "上传失败!");
    }

    /**
     * 回收站文章
     *
     * @return 返回值
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Map<String, Object> getArticleByState(@RequestParam(value = "state", defaultValue = "-1") Integer state,
                                                 @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                 @RequestParam(value = "count", defaultValue = "6") Integer count, String keywords) {
        int totalCount = articleService.getArticleCountByState(state, Util.getCurrentUser().getId(),keywords);
        List<Article> articles = articleService.getArticleByState(state, page, count,keywords);
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("articles", articles);
        return map;
    }
    /**
     * 文章查询
     *
     * @return 返回值
     */
    @RequestMapping(value = "/{aid}", method = RequestMethod.GET)
    public Article getArticleById(@PathVariable int aid) {
        return articleService.getArticleById(aid);
    }
    /**
     * 文章删除
     *
     * @return 返回值结果
     */
    @RequestMapping(value = "/dustbin", method = RequestMethod.PUT)
    public ResBean updateArticleState(Integer[] aids, Integer state) {
        if (articleService.updateArticleState(aids, state) == aids.length) {
            return new ResBean("success", "删除成功!");
        }
        return new ResBean("error", "删除失败!");
    }
    /**
     * 文章还原
     *
     * @return 返回值为还原结果
     */
    @RequestMapping(value = "/restore", method = RequestMethod.PUT)
    public ResBean restoreArticle(Integer articleId) {
        if (articleService.restoreArticle(articleId) == 1) {
            return new ResBean("success", "还原成功!");
        }
        return new ResBean("error", "还原失败!");
    }

    @RequestMapping("/dataStatistics")
    public Map<String,Object> dataStatistics() {
        Map<String, Object> map = new HashMap<>();
        List<String> categories = articleService.getCategories();
        List<String> dataStatistics = articleService.getDataStatistics();
        map.put("categories", categories);
        map.put("ds", dataStatistics);
        return map;
    }

}
