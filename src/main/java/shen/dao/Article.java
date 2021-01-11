package shen.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    private int id;
    private String title;
    private String mdContent; //md源码
    private String htmlContent;//html源码
    private String summary; // 内容
    private int cid;
    private int uid;
    private Timestamp publishDate;  // 发表时间
    private Timestamp editTime;  // 修改时间
    private int state;  //0表示草稿箱，1表示已发表，2表示已删除
    private int pageView; //浏览量
    private String[] dynamicTags;
    private List<Tags> tags;
    private String stateStr;
    private String nickname;
    private String cateName;

}
