package cn.edu.qqhru.train.pojo;

import java.util.Date;

public class Notice {
    private Integer noticeId;

    private Integer adminId;

    private String title;

    private String content;

    private Date createtime;

    private Integer isReleased;

    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getIsReleased() {
        return isReleased;
    }

    public void setIsReleased(Integer isReleased) {
        this.isReleased = isReleased;
    }
}