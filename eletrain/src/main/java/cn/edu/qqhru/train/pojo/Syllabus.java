package cn.edu.qqhru.train.pojo;

public class Syllabus {
    private Integer syllabusId;

    private String week;

    private String amfirst;

    private String pmfirst;

    private Integer classId;

    private String night;

    public Integer getSyllabusId() {
        return syllabusId;
    }

    public void setSyllabusId(Integer syllabusId) {
        this.syllabusId = syllabusId;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week == null ? null : week.trim();
    }

    public String getAmfirst() {
        return amfirst;
    }

    public void setAmfirst(String amfirst) {
        this.amfirst = amfirst == null ? null : amfirst.trim();
    }

    public String getPmfirst() {
        return pmfirst;
    }

    public void setPmfirst(String pmfirst) {
        this.pmfirst = pmfirst == null ? null : pmfirst.trim();
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getNight() {
        return night;
    }

    public void setNight(String night) {
        this.night = night == null ? null : night.trim();
    }
}