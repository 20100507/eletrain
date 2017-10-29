package cn.edu.qqhru.train.pojo;

public class Classes {
    private Integer classesId;

    private String cname;

    private Integer capacity;

    private String createtime;

    private String starttime;

    private String classesNo;

    public Integer getClassesId() {
        return classesId;
    }

    public void setClassesId(Integer classesId) {
        this.classesId = classesId;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname == null ? null : cname.trim();
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime == null ? null : starttime.trim();
    }

    public String getClassesNo() {
        return classesNo;
    }

    public void setClassesNo(String classesNo) {
        this.classesNo = classesNo == null ? null : classesNo.trim();
    }
}