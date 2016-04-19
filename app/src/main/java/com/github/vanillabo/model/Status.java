package com.github.vanillabo.model;

import java.util.List;

/**
 * Created by alan on 16/4/13.
 */
public class Status {

    public String created_at; // 微博创建时间

    public long id; // int64 微博ID

    public long mid; // int64 微博MID

    public String idstr; // 字符串型的微博ID

    public String text; // 微博信息内容

    public String source; // 微博来源

    public boolean favorited; //是否已收藏，true：是，false：否

    public boolean truncated; //是否被截断，true：是，false：否

    public String thumbnail_pic; //缩略图片地址，没有时不返回此字段

    public String bmiddle_pic; //中等尺寸图片地址，没有时不返回此字段

    public String original_pic; //原始图片地址，没有时不返回此字段

    public List<PicUrl> pic_urls;

    public WeiboUser user; //微博作者的用户信息字段

    public Status retweeted_status; // 被转发的原微博信息字段，当该微博为转发微博时返回

    public int reposts_count; //转发数

    public int comments_count; //评论数

    public int attitudes_count; //表态数
}
