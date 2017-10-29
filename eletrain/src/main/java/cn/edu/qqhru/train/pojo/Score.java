package cn.edu.qqhru.train.pojo;

public class Score {
    private Integer scoreId;

    private Integer studentId;

    private Float theoryscore;

    private Float practicescore;

    private Float total;

    private String common;

    private Integer courseId;

    private Integer classesId;

    private Integer teacherId;

    private String certificateno;

    public Integer getScoreId() {
        return scoreId;
    }

    public void setScoreId(Integer scoreId) {
        this.scoreId = scoreId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Float getTheoryscore() {
        return theoryscore;
    }

    public void setTheoryscore(Float theoryscore) {
        this.theoryscore = theoryscore;
    }

    public Float getPracticescore() {
        return practicescore;
    }

    public void setPracticescore(Float practicescore) {
        this.practicescore = practicescore;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common == null ? null : common.trim();
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getClassesId() {
        return classesId;
    }

    public void setClassesId(Integer classesId) {
        this.classesId = classesId;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getCertificateno() {
        return certificateno;
    }

    public void setCertificateno(String certificateno) {
        this.certificateno = certificateno == null ? null : certificateno.trim();
    }
}