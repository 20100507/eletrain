package cn.edu.qqhru.train.pojo;

import java.util.ArrayList;
import java.util.List;

public class DeviceExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DeviceExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andDeviceIdIsNull() {
            addCriterion("device_id is null");
            return (Criteria) this;
        }

        public Criteria andDeviceIdIsNotNull() {
            addCriterion("device_id is not null");
            return (Criteria) this;
        }

        public Criteria andDeviceIdEqualTo(Integer value) {
            addCriterion("device_id =", value, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdNotEqualTo(Integer value) {
            addCriterion("device_id <>", value, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdGreaterThan(Integer value) {
            addCriterion("device_id >", value, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("device_id >=", value, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdLessThan(Integer value) {
            addCriterion("device_id <", value, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdLessThanOrEqualTo(Integer value) {
            addCriterion("device_id <=", value, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdIn(List<Integer> values) {
            addCriterion("device_id in", values, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdNotIn(List<Integer> values) {
            addCriterion("device_id not in", values, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdBetween(Integer value1, Integer value2) {
            addCriterion("device_id between", value1, value2, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdNotBetween(Integer value1, Integer value2) {
            addCriterion("device_id not between", value1, value2, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDnameIsNull() {
            addCriterion("dname is null");
            return (Criteria) this;
        }

        public Criteria andDnameIsNotNull() {
            addCriterion("dname is not null");
            return (Criteria) this;
        }

        public Criteria andDnameEqualTo(String value) {
            addCriterion("dname =", value, "dname");
            return (Criteria) this;
        }

        public Criteria andDnameNotEqualTo(String value) {
            addCriterion("dname <>", value, "dname");
            return (Criteria) this;
        }

        public Criteria andDnameGreaterThan(String value) {
            addCriterion("dname >", value, "dname");
            return (Criteria) this;
        }

        public Criteria andDnameGreaterThanOrEqualTo(String value) {
            addCriterion("dname >=", value, "dname");
            return (Criteria) this;
        }

        public Criteria andDnameLessThan(String value) {
            addCriterion("dname <", value, "dname");
            return (Criteria) this;
        }

        public Criteria andDnameLessThanOrEqualTo(String value) {
            addCriterion("dname <=", value, "dname");
            return (Criteria) this;
        }

        public Criteria andDnameLike(String value) {
            addCriterion("dname like", value, "dname");
            return (Criteria) this;
        }

        public Criteria andDnameNotLike(String value) {
            addCriterion("dname not like", value, "dname");
            return (Criteria) this;
        }

        public Criteria andDnameIn(List<String> values) {
            addCriterion("dname in", values, "dname");
            return (Criteria) this;
        }

        public Criteria andDnameNotIn(List<String> values) {
            addCriterion("dname not in", values, "dname");
            return (Criteria) this;
        }

        public Criteria andDnameBetween(String value1, String value2) {
            addCriterion("dname between", value1, value2, "dname");
            return (Criteria) this;
        }

        public Criteria andDnameNotBetween(String value1, String value2) {
            addCriterion("dname not between", value1, value2, "dname");
            return (Criteria) this;
        }

        public Criteria andDcountIsNull() {
            addCriterion("dcount is null");
            return (Criteria) this;
        }

        public Criteria andDcountIsNotNull() {
            addCriterion("dcount is not null");
            return (Criteria) this;
        }

        public Criteria andDcountEqualTo(Integer value) {
            addCriterion("dcount =", value, "dcount");
            return (Criteria) this;
        }

        public Criteria andDcountNotEqualTo(Integer value) {
            addCriterion("dcount <>", value, "dcount");
            return (Criteria) this;
        }

        public Criteria andDcountGreaterThan(Integer value) {
            addCriterion("dcount >", value, "dcount");
            return (Criteria) this;
        }

        public Criteria andDcountGreaterThanOrEqualTo(Integer value) {
            addCriterion("dcount >=", value, "dcount");
            return (Criteria) this;
        }

        public Criteria andDcountLessThan(Integer value) {
            addCriterion("dcount <", value, "dcount");
            return (Criteria) this;
        }

        public Criteria andDcountLessThanOrEqualTo(Integer value) {
            addCriterion("dcount <=", value, "dcount");
            return (Criteria) this;
        }

        public Criteria andDcountIn(List<Integer> values) {
            addCriterion("dcount in", values, "dcount");
            return (Criteria) this;
        }

        public Criteria andDcountNotIn(List<Integer> values) {
            addCriterion("dcount not in", values, "dcount");
            return (Criteria) this;
        }

        public Criteria andDcountBetween(Integer value1, Integer value2) {
            addCriterion("dcount between", value1, value2, "dcount");
            return (Criteria) this;
        }

        public Criteria andDcountNotBetween(Integer value1, Integer value2) {
            addCriterion("dcount not between", value1, value2, "dcount");
            return (Criteria) this;
        }

        public Criteria andInfoIsNull() {
            addCriterion("info is null");
            return (Criteria) this;
        }

        public Criteria andInfoIsNotNull() {
            addCriterion("info is not null");
            return (Criteria) this;
        }

        public Criteria andInfoEqualTo(String value) {
            addCriterion("info =", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoNotEqualTo(String value) {
            addCriterion("info <>", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoGreaterThan(String value) {
            addCriterion("info >", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoGreaterThanOrEqualTo(String value) {
            addCriterion("info >=", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoLessThan(String value) {
            addCriterion("info <", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoLessThanOrEqualTo(String value) {
            addCriterion("info <=", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoLike(String value) {
            addCriterion("info like", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoNotLike(String value) {
            addCriterion("info not like", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoIn(List<String> values) {
            addCriterion("info in", values, "info");
            return (Criteria) this;
        }

        public Criteria andInfoNotIn(List<String> values) {
            addCriterion("info not in", values, "info");
            return (Criteria) this;
        }

        public Criteria andInfoBetween(String value1, String value2) {
            addCriterion("info between", value1, value2, "info");
            return (Criteria) this;
        }

        public Criteria andInfoNotBetween(String value1, String value2) {
            addCriterion("info not between", value1, value2, "info");
            return (Criteria) this;
        }

        public Criteria andToolIsNull() {
            addCriterion("tool is null");
            return (Criteria) this;
        }

        public Criteria andToolIsNotNull() {
            addCriterion("tool is not null");
            return (Criteria) this;
        }

        public Criteria andToolEqualTo(String value) {
            addCriterion("tool =", value, "tool");
            return (Criteria) this;
        }

        public Criteria andToolNotEqualTo(String value) {
            addCriterion("tool <>", value, "tool");
            return (Criteria) this;
        }

        public Criteria andToolGreaterThan(String value) {
            addCriterion("tool >", value, "tool");
            return (Criteria) this;
        }

        public Criteria andToolGreaterThanOrEqualTo(String value) {
            addCriterion("tool >=", value, "tool");
            return (Criteria) this;
        }

        public Criteria andToolLessThan(String value) {
            addCriterion("tool <", value, "tool");
            return (Criteria) this;
        }

        public Criteria andToolLessThanOrEqualTo(String value) {
            addCriterion("tool <=", value, "tool");
            return (Criteria) this;
        }

        public Criteria andToolLike(String value) {
            addCriterion("tool like", value, "tool");
            return (Criteria) this;
        }

        public Criteria andToolNotLike(String value) {
            addCriterion("tool not like", value, "tool");
            return (Criteria) this;
        }

        public Criteria andToolIn(List<String> values) {
            addCriterion("tool in", values, "tool");
            return (Criteria) this;
        }

        public Criteria andToolNotIn(List<String> values) {
            addCriterion("tool not in", values, "tool");
            return (Criteria) this;
        }

        public Criteria andToolBetween(String value1, String value2) {
            addCriterion("tool between", value1, value2, "tool");
            return (Criteria) this;
        }

        public Criteria andToolNotBetween(String value1, String value2) {
            addCriterion("tool not between", value1, value2, "tool");
            return (Criteria) this;
        }

        public Criteria andTcountIsNull() {
            addCriterion("tcount is null");
            return (Criteria) this;
        }

        public Criteria andTcountIsNotNull() {
            addCriterion("tcount is not null");
            return (Criteria) this;
        }

        public Criteria andTcountEqualTo(Integer value) {
            addCriterion("tcount =", value, "tcount");
            return (Criteria) this;
        }

        public Criteria andTcountNotEqualTo(Integer value) {
            addCriterion("tcount <>", value, "tcount");
            return (Criteria) this;
        }

        public Criteria andTcountGreaterThan(Integer value) {
            addCriterion("tcount >", value, "tcount");
            return (Criteria) this;
        }

        public Criteria andTcountGreaterThanOrEqualTo(Integer value) {
            addCriterion("tcount >=", value, "tcount");
            return (Criteria) this;
        }

        public Criteria andTcountLessThan(Integer value) {
            addCriterion("tcount <", value, "tcount");
            return (Criteria) this;
        }

        public Criteria andTcountLessThanOrEqualTo(Integer value) {
            addCriterion("tcount <=", value, "tcount");
            return (Criteria) this;
        }

        public Criteria andTcountIn(List<Integer> values) {
            addCriterion("tcount in", values, "tcount");
            return (Criteria) this;
        }

        public Criteria andTcountNotIn(List<Integer> values) {
            addCriterion("tcount not in", values, "tcount");
            return (Criteria) this;
        }

        public Criteria andTcountBetween(Integer value1, Integer value2) {
            addCriterion("tcount between", value1, value2, "tcount");
            return (Criteria) this;
        }

        public Criteria andTcountNotBetween(Integer value1, Integer value2) {
            addCriterion("tcount not between", value1, value2, "tcount");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}