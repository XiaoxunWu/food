package com.demo.vo;

import java.io.Serializable;

/**
 * 公告（t_notice表对应的Java实体类）
 */
public class Notice implements Serializable {
    private Long id;//主键
    private String noticeName;//标题
    private String noticeText;//内容
    private String noticeDate;//创建时间
    private Long useId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getNoticeName() {
        return noticeName;
    }

    public void setNoticeName(String noticeName) {
        this.noticeName = noticeName;
    }
    public String getNoticeText() {
        return noticeText;
    }

    public void setNoticeText(String noticeText) {
        this.noticeText = noticeText;
    }

    public String getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(String noticeDate) {
        this.noticeDate = noticeDate;
    }

    public Long getUseId() {
        return useId;
    }

    public void setUseId(Long useId) {
        this.useId = useId;
    }
}
