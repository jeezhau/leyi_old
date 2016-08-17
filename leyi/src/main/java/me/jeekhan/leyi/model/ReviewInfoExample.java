package me.jeekhan.leyi.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReviewInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ReviewInfoExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andObjNameIsNull() {
            addCriterion("obj_name is null");
            return (Criteria) this;
        }

        public Criteria andObjNameIsNotNull() {
            addCriterion("obj_name is not null");
            return (Criteria) this;
        }

        public Criteria andObjNameEqualTo(String value) {
            addCriterion("obj_name =", value, "objName");
            return (Criteria) this;
        }

        public Criteria andObjNameNotEqualTo(String value) {
            addCriterion("obj_name <>", value, "objName");
            return (Criteria) this;
        }

        public Criteria andObjNameGreaterThan(String value) {
            addCriterion("obj_name >", value, "objName");
            return (Criteria) this;
        }

        public Criteria andObjNameGreaterThanOrEqualTo(String value) {
            addCriterion("obj_name >=", value, "objName");
            return (Criteria) this;
        }

        public Criteria andObjNameLessThan(String value) {
            addCriterion("obj_name <", value, "objName");
            return (Criteria) this;
        }

        public Criteria andObjNameLessThanOrEqualTo(String value) {
            addCriterion("obj_name <=", value, "objName");
            return (Criteria) this;
        }

        public Criteria andObjNameLike(String value) {
            addCriterion("obj_name like", value, "objName");
            return (Criteria) this;
        }

        public Criteria andObjNameNotLike(String value) {
            addCriterion("obj_name not like", value, "objName");
            return (Criteria) this;
        }

        public Criteria andObjNameIn(List<String> values) {
            addCriterion("obj_name in", values, "objName");
            return (Criteria) this;
        }

        public Criteria andObjNameNotIn(List<String> values) {
            addCriterion("obj_name not in", values, "objName");
            return (Criteria) this;
        }

        public Criteria andObjNameBetween(String value1, String value2) {
            addCriterion("obj_name between", value1, value2, "objName");
            return (Criteria) this;
        }

        public Criteria andObjNameNotBetween(String value1, String value2) {
            addCriterion("obj_name not between", value1, value2, "objName");
            return (Criteria) this;
        }

        public Criteria andKeyIdIsNull() {
            addCriterion("key_id is null");
            return (Criteria) this;
        }

        public Criteria andKeyIdIsNotNull() {
            addCriterion("key_id is not null");
            return (Criteria) this;
        }

        public Criteria andKeyIdEqualTo(Integer value) {
            addCriterion("key_id =", value, "keyId");
            return (Criteria) this;
        }

        public Criteria andKeyIdNotEqualTo(Integer value) {
            addCriterion("key_id <>", value, "keyId");
            return (Criteria) this;
        }

        public Criteria andKeyIdGreaterThan(Integer value) {
            addCriterion("key_id >", value, "keyId");
            return (Criteria) this;
        }

        public Criteria andKeyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("key_id >=", value, "keyId");
            return (Criteria) this;
        }

        public Criteria andKeyIdLessThan(Integer value) {
            addCriterion("key_id <", value, "keyId");
            return (Criteria) this;
        }

        public Criteria andKeyIdLessThanOrEqualTo(Integer value) {
            addCriterion("key_id <=", value, "keyId");
            return (Criteria) this;
        }

        public Criteria andKeyIdIn(List<Integer> values) {
            addCriterion("key_id in", values, "keyId");
            return (Criteria) this;
        }

        public Criteria andKeyIdNotIn(List<Integer> values) {
            addCriterion("key_id not in", values, "keyId");
            return (Criteria) this;
        }

        public Criteria andKeyIdBetween(Integer value1, Integer value2) {
            addCriterion("key_id between", value1, value2, "keyId");
            return (Criteria) this;
        }

        public Criteria andKeyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("key_id not between", value1, value2, "keyId");
            return (Criteria) this;
        }

        public Criteria andReviewInfoIsNull() {
            addCriterion("review_info is null");
            return (Criteria) this;
        }

        public Criteria andReviewInfoIsNotNull() {
            addCriterion("review_info is not null");
            return (Criteria) this;
        }

        public Criteria andReviewInfoEqualTo(String value) {
            addCriterion("review_info =", value, "reviewInfo");
            return (Criteria) this;
        }

        public Criteria andReviewInfoNotEqualTo(String value) {
            addCriterion("review_info <>", value, "reviewInfo");
            return (Criteria) this;
        }

        public Criteria andReviewInfoGreaterThan(String value) {
            addCriterion("review_info >", value, "reviewInfo");
            return (Criteria) this;
        }

        public Criteria andReviewInfoGreaterThanOrEqualTo(String value) {
            addCriterion("review_info >=", value, "reviewInfo");
            return (Criteria) this;
        }

        public Criteria andReviewInfoLessThan(String value) {
            addCriterion("review_info <", value, "reviewInfo");
            return (Criteria) this;
        }

        public Criteria andReviewInfoLessThanOrEqualTo(String value) {
            addCriterion("review_info <=", value, "reviewInfo");
            return (Criteria) this;
        }

        public Criteria andReviewInfoLike(String value) {
            addCriterion("review_info like", value, "reviewInfo");
            return (Criteria) this;
        }

        public Criteria andReviewInfoNotLike(String value) {
            addCriterion("review_info not like", value, "reviewInfo");
            return (Criteria) this;
        }

        public Criteria andReviewInfoIn(List<String> values) {
            addCriterion("review_info in", values, "reviewInfo");
            return (Criteria) this;
        }

        public Criteria andReviewInfoNotIn(List<String> values) {
            addCriterion("review_info not in", values, "reviewInfo");
            return (Criteria) this;
        }

        public Criteria andReviewInfoBetween(String value1, String value2) {
            addCriterion("review_info between", value1, value2, "reviewInfo");
            return (Criteria) this;
        }

        public Criteria andReviewInfoNotBetween(String value1, String value2) {
            addCriterion("review_info not between", value1, value2, "reviewInfo");
            return (Criteria) this;
        }

        public Criteria andReviewOprIsNull() {
            addCriterion("review_opr is null");
            return (Criteria) this;
        }

        public Criteria andReviewOprIsNotNull() {
            addCriterion("review_opr is not null");
            return (Criteria) this;
        }

        public Criteria andReviewOprEqualTo(Integer value) {
            addCriterion("review_opr =", value, "reviewOpr");
            return (Criteria) this;
        }

        public Criteria andReviewOprNotEqualTo(Integer value) {
            addCriterion("review_opr <>", value, "reviewOpr");
            return (Criteria) this;
        }

        public Criteria andReviewOprGreaterThan(Integer value) {
            addCriterion("review_opr >", value, "reviewOpr");
            return (Criteria) this;
        }

        public Criteria andReviewOprGreaterThanOrEqualTo(Integer value) {
            addCriterion("review_opr >=", value, "reviewOpr");
            return (Criteria) this;
        }

        public Criteria andReviewOprLessThan(Integer value) {
            addCriterion("review_opr <", value, "reviewOpr");
            return (Criteria) this;
        }

        public Criteria andReviewOprLessThanOrEqualTo(Integer value) {
            addCriterion("review_opr <=", value, "reviewOpr");
            return (Criteria) this;
        }

        public Criteria andReviewOprIn(List<Integer> values) {
            addCriterion("review_opr in", values, "reviewOpr");
            return (Criteria) this;
        }

        public Criteria andReviewOprNotIn(List<Integer> values) {
            addCriterion("review_opr not in", values, "reviewOpr");
            return (Criteria) this;
        }

        public Criteria andReviewOprBetween(Integer value1, Integer value2) {
            addCriterion("review_opr between", value1, value2, "reviewOpr");
            return (Criteria) this;
        }

        public Criteria andReviewOprNotBetween(Integer value1, Integer value2) {
            addCriterion("review_opr not between", value1, value2, "reviewOpr");
            return (Criteria) this;
        }

        public Criteria andReviewTimeIsNull() {
            addCriterion("review_time is null");
            return (Criteria) this;
        }

        public Criteria andReviewTimeIsNotNull() {
            addCriterion("review_time is not null");
            return (Criteria) this;
        }

        public Criteria andReviewTimeEqualTo(Date value) {
            addCriterion("review_time =", value, "reviewTime");
            return (Criteria) this;
        }

        public Criteria andReviewTimeNotEqualTo(Date value) {
            addCriterion("review_time <>", value, "reviewTime");
            return (Criteria) this;
        }

        public Criteria andReviewTimeGreaterThan(Date value) {
            addCriterion("review_time >", value, "reviewTime");
            return (Criteria) this;
        }

        public Criteria andReviewTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("review_time >=", value, "reviewTime");
            return (Criteria) this;
        }

        public Criteria andReviewTimeLessThan(Date value) {
            addCriterion("review_time <", value, "reviewTime");
            return (Criteria) this;
        }

        public Criteria andReviewTimeLessThanOrEqualTo(Date value) {
            addCriterion("review_time <=", value, "reviewTime");
            return (Criteria) this;
        }

        public Criteria andReviewTimeIn(List<Date> values) {
            addCriterion("review_time in", values, "reviewTime");
            return (Criteria) this;
        }

        public Criteria andReviewTimeNotIn(List<Date> values) {
            addCriterion("review_time not in", values, "reviewTime");
            return (Criteria) this;
        }

        public Criteria andReviewTimeBetween(Date value1, Date value2) {
            addCriterion("review_time between", value1, value2, "reviewTime");
            return (Criteria) this;
        }

        public Criteria andReviewTimeNotBetween(Date value1, Date value2) {
            addCriterion("review_time not between", value1, value2, "reviewTime");
            return (Criteria) this;
        }

        public Criteria andResultIsNull() {
            addCriterion("result is null");
            return (Criteria) this;
        }

        public Criteria andResultIsNotNull() {
            addCriterion("result is not null");
            return (Criteria) this;
        }

        public Criteria andResultEqualTo(String value) {
            addCriterion("result =", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotEqualTo(String value) {
            addCriterion("result <>", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultGreaterThan(String value) {
            addCriterion("result >", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultGreaterThanOrEqualTo(String value) {
            addCriterion("result >=", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLessThan(String value) {
            addCriterion("result <", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLessThanOrEqualTo(String value) {
            addCriterion("result <=", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLike(String value) {
            addCriterion("result like", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotLike(String value) {
            addCriterion("result not like", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultIn(List<String> values) {
            addCriterion("result in", values, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotIn(List<String> values) {
            addCriterion("result not in", values, "result");
            return (Criteria) this;
        }

        public Criteria andResultBetween(String value1, String value2) {
            addCriterion("result between", value1, value2, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotBetween(String value1, String value2) {
            addCriterion("result not between", value1, value2, "result");
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