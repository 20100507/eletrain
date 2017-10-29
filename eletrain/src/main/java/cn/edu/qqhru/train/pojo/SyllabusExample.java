package cn.edu.qqhru.train.pojo;

import java.util.ArrayList;
import java.util.List;

public class SyllabusExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SyllabusExample() {
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

        public Criteria andSyllabusIdIsNull() {
            addCriterion("syllabus_id is null");
            return (Criteria) this;
        }

        public Criteria andSyllabusIdIsNotNull() {
            addCriterion("syllabus_id is not null");
            return (Criteria) this;
        }

        public Criteria andSyllabusIdEqualTo(Integer value) {
            addCriterion("syllabus_id =", value, "syllabusId");
            return (Criteria) this;
        }

        public Criteria andSyllabusIdNotEqualTo(Integer value) {
            addCriterion("syllabus_id <>", value, "syllabusId");
            return (Criteria) this;
        }

        public Criteria andSyllabusIdGreaterThan(Integer value) {
            addCriterion("syllabus_id >", value, "syllabusId");
            return (Criteria) this;
        }

        public Criteria andSyllabusIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("syllabus_id >=", value, "syllabusId");
            return (Criteria) this;
        }

        public Criteria andSyllabusIdLessThan(Integer value) {
            addCriterion("syllabus_id <", value, "syllabusId");
            return (Criteria) this;
        }

        public Criteria andSyllabusIdLessThanOrEqualTo(Integer value) {
            addCriterion("syllabus_id <=", value, "syllabusId");
            return (Criteria) this;
        }

        public Criteria andSyllabusIdIn(List<Integer> values) {
            addCriterion("syllabus_id in", values, "syllabusId");
            return (Criteria) this;
        }

        public Criteria andSyllabusIdNotIn(List<Integer> values) {
            addCriterion("syllabus_id not in", values, "syllabusId");
            return (Criteria) this;
        }

        public Criteria andSyllabusIdBetween(Integer value1, Integer value2) {
            addCriterion("syllabus_id between", value1, value2, "syllabusId");
            return (Criteria) this;
        }

        public Criteria andSyllabusIdNotBetween(Integer value1, Integer value2) {
            addCriterion("syllabus_id not between", value1, value2, "syllabusId");
            return (Criteria) this;
        }

        public Criteria andWeekIsNull() {
            addCriterion("week is null");
            return (Criteria) this;
        }

        public Criteria andWeekIsNotNull() {
            addCriterion("week is not null");
            return (Criteria) this;
        }

        public Criteria andWeekEqualTo(String value) {
            addCriterion("week =", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekNotEqualTo(String value) {
            addCriterion("week <>", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekGreaterThan(String value) {
            addCriterion("week >", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekGreaterThanOrEqualTo(String value) {
            addCriterion("week >=", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekLessThan(String value) {
            addCriterion("week <", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekLessThanOrEqualTo(String value) {
            addCriterion("week <=", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekLike(String value) {
            addCriterion("week like", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekNotLike(String value) {
            addCriterion("week not like", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekIn(List<String> values) {
            addCriterion("week in", values, "week");
            return (Criteria) this;
        }

        public Criteria andWeekNotIn(List<String> values) {
            addCriterion("week not in", values, "week");
            return (Criteria) this;
        }

        public Criteria andWeekBetween(String value1, String value2) {
            addCriterion("week between", value1, value2, "week");
            return (Criteria) this;
        }

        public Criteria andWeekNotBetween(String value1, String value2) {
            addCriterion("week not between", value1, value2, "week");
            return (Criteria) this;
        }

        public Criteria andAmfirstIsNull() {
            addCriterion("amfirst is null");
            return (Criteria) this;
        }

        public Criteria andAmfirstIsNotNull() {
            addCriterion("amfirst is not null");
            return (Criteria) this;
        }

        public Criteria andAmfirstEqualTo(String value) {
            addCriterion("amfirst =", value, "amfirst");
            return (Criteria) this;
        }

        public Criteria andAmfirstNotEqualTo(String value) {
            addCriterion("amfirst <>", value, "amfirst");
            return (Criteria) this;
        }

        public Criteria andAmfirstGreaterThan(String value) {
            addCriterion("amfirst >", value, "amfirst");
            return (Criteria) this;
        }

        public Criteria andAmfirstGreaterThanOrEqualTo(String value) {
            addCriterion("amfirst >=", value, "amfirst");
            return (Criteria) this;
        }

        public Criteria andAmfirstLessThan(String value) {
            addCriterion("amfirst <", value, "amfirst");
            return (Criteria) this;
        }

        public Criteria andAmfirstLessThanOrEqualTo(String value) {
            addCriterion("amfirst <=", value, "amfirst");
            return (Criteria) this;
        }

        public Criteria andAmfirstLike(String value) {
            addCriterion("amfirst like", value, "amfirst");
            return (Criteria) this;
        }

        public Criteria andAmfirstNotLike(String value) {
            addCriterion("amfirst not like", value, "amfirst");
            return (Criteria) this;
        }

        public Criteria andAmfirstIn(List<String> values) {
            addCriterion("amfirst in", values, "amfirst");
            return (Criteria) this;
        }

        public Criteria andAmfirstNotIn(List<String> values) {
            addCriterion("amfirst not in", values, "amfirst");
            return (Criteria) this;
        }

        public Criteria andAmfirstBetween(String value1, String value2) {
            addCriterion("amfirst between", value1, value2, "amfirst");
            return (Criteria) this;
        }

        public Criteria andAmfirstNotBetween(String value1, String value2) {
            addCriterion("amfirst not between", value1, value2, "amfirst");
            return (Criteria) this;
        }

        public Criteria andPmfirstIsNull() {
            addCriterion("pmfirst is null");
            return (Criteria) this;
        }

        public Criteria andPmfirstIsNotNull() {
            addCriterion("pmfirst is not null");
            return (Criteria) this;
        }

        public Criteria andPmfirstEqualTo(String value) {
            addCriterion("pmfirst =", value, "pmfirst");
            return (Criteria) this;
        }

        public Criteria andPmfirstNotEqualTo(String value) {
            addCriterion("pmfirst <>", value, "pmfirst");
            return (Criteria) this;
        }

        public Criteria andPmfirstGreaterThan(String value) {
            addCriterion("pmfirst >", value, "pmfirst");
            return (Criteria) this;
        }

        public Criteria andPmfirstGreaterThanOrEqualTo(String value) {
            addCriterion("pmfirst >=", value, "pmfirst");
            return (Criteria) this;
        }

        public Criteria andPmfirstLessThan(String value) {
            addCriterion("pmfirst <", value, "pmfirst");
            return (Criteria) this;
        }

        public Criteria andPmfirstLessThanOrEqualTo(String value) {
            addCriterion("pmfirst <=", value, "pmfirst");
            return (Criteria) this;
        }

        public Criteria andPmfirstLike(String value) {
            addCriterion("pmfirst like", value, "pmfirst");
            return (Criteria) this;
        }

        public Criteria andPmfirstNotLike(String value) {
            addCriterion("pmfirst not like", value, "pmfirst");
            return (Criteria) this;
        }

        public Criteria andPmfirstIn(List<String> values) {
            addCriterion("pmfirst in", values, "pmfirst");
            return (Criteria) this;
        }

        public Criteria andPmfirstNotIn(List<String> values) {
            addCriterion("pmfirst not in", values, "pmfirst");
            return (Criteria) this;
        }

        public Criteria andPmfirstBetween(String value1, String value2) {
            addCriterion("pmfirst between", value1, value2, "pmfirst");
            return (Criteria) this;
        }

        public Criteria andPmfirstNotBetween(String value1, String value2) {
            addCriterion("pmfirst not between", value1, value2, "pmfirst");
            return (Criteria) this;
        }

        public Criteria andClassIdIsNull() {
            addCriterion("class_id is null");
            return (Criteria) this;
        }

        public Criteria andClassIdIsNotNull() {
            addCriterion("class_id is not null");
            return (Criteria) this;
        }

        public Criteria andClassIdEqualTo(Integer value) {
            addCriterion("class_id =", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdNotEqualTo(Integer value) {
            addCriterion("class_id <>", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdGreaterThan(Integer value) {
            addCriterion("class_id >", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("class_id >=", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdLessThan(Integer value) {
            addCriterion("class_id <", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdLessThanOrEqualTo(Integer value) {
            addCriterion("class_id <=", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdIn(List<Integer> values) {
            addCriterion("class_id in", values, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdNotIn(List<Integer> values) {
            addCriterion("class_id not in", values, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdBetween(Integer value1, Integer value2) {
            addCriterion("class_id between", value1, value2, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdNotBetween(Integer value1, Integer value2) {
            addCriterion("class_id not between", value1, value2, "classId");
            return (Criteria) this;
        }

        public Criteria andNightIsNull() {
            addCriterion("night is null");
            return (Criteria) this;
        }

        public Criteria andNightIsNotNull() {
            addCriterion("night is not null");
            return (Criteria) this;
        }

        public Criteria andNightEqualTo(String value) {
            addCriterion("night =", value, "night");
            return (Criteria) this;
        }

        public Criteria andNightNotEqualTo(String value) {
            addCriterion("night <>", value, "night");
            return (Criteria) this;
        }

        public Criteria andNightGreaterThan(String value) {
            addCriterion("night >", value, "night");
            return (Criteria) this;
        }

        public Criteria andNightGreaterThanOrEqualTo(String value) {
            addCriterion("night >=", value, "night");
            return (Criteria) this;
        }

        public Criteria andNightLessThan(String value) {
            addCriterion("night <", value, "night");
            return (Criteria) this;
        }

        public Criteria andNightLessThanOrEqualTo(String value) {
            addCriterion("night <=", value, "night");
            return (Criteria) this;
        }

        public Criteria andNightLike(String value) {
            addCriterion("night like", value, "night");
            return (Criteria) this;
        }

        public Criteria andNightNotLike(String value) {
            addCriterion("night not like", value, "night");
            return (Criteria) this;
        }

        public Criteria andNightIn(List<String> values) {
            addCriterion("night in", values, "night");
            return (Criteria) this;
        }

        public Criteria andNightNotIn(List<String> values) {
            addCriterion("night not in", values, "night");
            return (Criteria) this;
        }

        public Criteria andNightBetween(String value1, String value2) {
            addCriterion("night between", value1, value2, "night");
            return (Criteria) this;
        }

        public Criteria andNightNotBetween(String value1, String value2) {
            addCriterion("night not between", value1, value2, "night");
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