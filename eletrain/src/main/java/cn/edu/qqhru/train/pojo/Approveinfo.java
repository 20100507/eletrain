package cn.edu.qqhru.train.pojo;

import java.io.Serializable;
import java.util.Date;

public class Approveinfo implements Serializable{
    /** serialVersionUID*/
	private static final long serialVersionUID = 5060752086645505409L;

	private Integer approveinfoId;

    private Integer adminId;

    private Date approvedate;

    private Boolean approval;

    private String comment;

    private Integer applicationId;

    public Integer getApproveinfoId() {
        return approveinfoId;
    }

    public void setApproveinfoId(Integer approveinfoId) {
        this.approveinfoId = approveinfoId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Date getApprovedate() {
        return approvedate;
    }

    public void setApprovedate(Date approvedate) {
        this.approvedate = approvedate;
    }

    public Boolean getApproval() {
        return approval;
    }

    public void setApproval(Boolean approval) {
        this.approval = approval;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }
}