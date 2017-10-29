package cn.edu.qqhru.train.pojo;

import java.util.Date;

public class Plan {
    private Integer planId;

    private Integer adminId;

    private Date createtime;

    private Integer sign;

    private String examName;

    private String pname;

    private Date updatetime;

    private String planAim;

    private String ability;

    private String scale;

    private String planPattern;

    private String examPattern;

    private String planContent;

    private String planRequirement;

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName == null ? null : examName.trim();
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname == null ? null : pname.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getPlanAim() {
        return planAim;
    }

    public void setPlanAim(String planAim) {
        this.planAim = planAim == null ? null : planAim.trim();
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability == null ? null : ability.trim();
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale == null ? null : scale.trim();
    }

    public String getPlanPattern() {
        return planPattern;
    }

    public void setPlanPattern(String planPattern) {
        this.planPattern = planPattern == null ? null : planPattern.trim();
    }

    public String getExamPattern() {
        return examPattern;
    }

    public void setExamPattern(String examPattern) {
        this.examPattern = examPattern == null ? null : examPattern.trim();
    }

    public String getPlanContent() {
        return planContent;
    }

    public void setPlanContent(String planContent) {
        this.planContent = planContent == null ? null : planContent.trim();
    }

    public String getPlanRequirement() {
        return planRequirement;
    }

    public void setPlanRequirement(String planRequirement) {
        this.planRequirement = planRequirement == null ? null : planRequirement.trim();
    }
}