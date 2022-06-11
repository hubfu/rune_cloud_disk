package com.boot.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShareGroupExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ShareGroupExample() {
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

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("user_id like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("user_id not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(Date value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(Date value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(Date value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(Date value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<Date> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<Date> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(Date value1, Date value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andIsEncryptIsNull() {
            addCriterion("is_encrypt is null");
            return (Criteria) this;
        }

        public Criteria andIsEncryptIsNotNull() {
            addCriterion("is_encrypt is not null");
            return (Criteria) this;
        }

        public Criteria andIsEncryptEqualTo(Boolean value) {
            addCriterion("is_encrypt =", value, "isEncrypt");
            return (Criteria) this;
        }

        public Criteria andIsEncryptNotEqualTo(Boolean value) {
            addCriterion("is_encrypt <>", value, "isEncrypt");
            return (Criteria) this;
        }

        public Criteria andIsEncryptGreaterThan(Boolean value) {
            addCriterion("is_encrypt >", value, "isEncrypt");
            return (Criteria) this;
        }

        public Criteria andIsEncryptGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_encrypt >=", value, "isEncrypt");
            return (Criteria) this;
        }

        public Criteria andIsEncryptLessThan(Boolean value) {
            addCriterion("is_encrypt <", value, "isEncrypt");
            return (Criteria) this;
        }

        public Criteria andIsEncryptLessThanOrEqualTo(Boolean value) {
            addCriterion("is_encrypt <=", value, "isEncrypt");
            return (Criteria) this;
        }

        public Criteria andIsEncryptIn(List<Boolean> values) {
            addCriterion("is_encrypt in", values, "isEncrypt");
            return (Criteria) this;
        }

        public Criteria andIsEncryptNotIn(List<Boolean> values) {
            addCriterion("is_encrypt not in", values, "isEncrypt");
            return (Criteria) this;
        }

        public Criteria andIsEncryptBetween(Boolean value1, Boolean value2) {
            addCriterion("is_encrypt between", value1, value2, "isEncrypt");
            return (Criteria) this;
        }

        public Criteria andIsEncryptNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_encrypt not between", value1, value2, "isEncrypt");
            return (Criteria) this;
        }

        public Criteria andExtractionCodeIsNull() {
            addCriterion("extraction_code is null");
            return (Criteria) this;
        }

        public Criteria andExtractionCodeIsNotNull() {
            addCriterion("extraction_code is not null");
            return (Criteria) this;
        }

        public Criteria andExtractionCodeEqualTo(String value) {
            addCriterion("extraction_code =", value, "extractionCode");
            return (Criteria) this;
        }

        public Criteria andExtractionCodeNotEqualTo(String value) {
            addCriterion("extraction_code <>", value, "extractionCode");
            return (Criteria) this;
        }

        public Criteria andExtractionCodeGreaterThan(String value) {
            addCriterion("extraction_code >", value, "extractionCode");
            return (Criteria) this;
        }

        public Criteria andExtractionCodeGreaterThanOrEqualTo(String value) {
            addCriterion("extraction_code >=", value, "extractionCode");
            return (Criteria) this;
        }

        public Criteria andExtractionCodeLessThan(String value) {
            addCriterion("extraction_code <", value, "extractionCode");
            return (Criteria) this;
        }

        public Criteria andExtractionCodeLessThanOrEqualTo(String value) {
            addCriterion("extraction_code <=", value, "extractionCode");
            return (Criteria) this;
        }

        public Criteria andExtractionCodeLike(String value) {
            addCriterion("extraction_code like", value, "extractionCode");
            return (Criteria) this;
        }

        public Criteria andExtractionCodeNotLike(String value) {
            addCriterion("extraction_code not like", value, "extractionCode");
            return (Criteria) this;
        }

        public Criteria andExtractionCodeIn(List<String> values) {
            addCriterion("extraction_code in", values, "extractionCode");
            return (Criteria) this;
        }

        public Criteria andExtractionCodeNotIn(List<String> values) {
            addCriterion("extraction_code not in", values, "extractionCode");
            return (Criteria) this;
        }

        public Criteria andExtractionCodeBetween(String value1, String value2) {
            addCriterion("extraction_code between", value1, value2, "extractionCode");
            return (Criteria) this;
        }

        public Criteria andExtractionCodeNotBetween(String value1, String value2) {
            addCriterion("extraction_code not between", value1, value2, "extractionCode");
            return (Criteria) this;
        }

        public Criteria andSharePathIsNull() {
            addCriterion("share_path is null");
            return (Criteria) this;
        }

        public Criteria andSharePathIsNotNull() {
            addCriterion("share_path is not null");
            return (Criteria) this;
        }

        public Criteria andSharePathEqualTo(String value) {
            addCriterion("share_path =", value, "sharePath");
            return (Criteria) this;
        }

        public Criteria andSharePathNotEqualTo(String value) {
            addCriterion("share_path <>", value, "sharePath");
            return (Criteria) this;
        }

        public Criteria andSharePathGreaterThan(String value) {
            addCriterion("share_path >", value, "sharePath");
            return (Criteria) this;
        }

        public Criteria andSharePathGreaterThanOrEqualTo(String value) {
            addCriterion("share_path >=", value, "sharePath");
            return (Criteria) this;
        }

        public Criteria andSharePathLessThan(String value) {
            addCriterion("share_path <", value, "sharePath");
            return (Criteria) this;
        }

        public Criteria andSharePathLessThanOrEqualTo(String value) {
            addCriterion("share_path <=", value, "sharePath");
            return (Criteria) this;
        }

        public Criteria andSharePathLike(String value) {
            addCriterion("share_path like", value, "sharePath");
            return (Criteria) this;
        }

        public Criteria andSharePathNotLike(String value) {
            addCriterion("share_path not like", value, "sharePath");
            return (Criteria) this;
        }

        public Criteria andSharePathIn(List<String> values) {
            addCriterion("share_path in", values, "sharePath");
            return (Criteria) this;
        }

        public Criteria andSharePathNotIn(List<String> values) {
            addCriterion("share_path not in", values, "sharePath");
            return (Criteria) this;
        }

        public Criteria andSharePathBetween(String value1, String value2) {
            addCriterion("share_path between", value1, value2, "sharePath");
            return (Criteria) this;
        }

        public Criteria andSharePathNotBetween(String value1, String value2) {
            addCriterion("share_path not between", value1, value2, "sharePath");
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