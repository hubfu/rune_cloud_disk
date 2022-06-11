package com.boot.entity;

import java.util.ArrayList;
import java.util.List;

public class DepotExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DepotExample() {
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

        public Criteria andUsedCapacityIsNull() {
            addCriterion("used_capacity is null");
            return (Criteria) this;
        }

        public Criteria andUsedCapacityIsNotNull() {
            addCriterion("used_capacity is not null");
            return (Criteria) this;
        }

        public Criteria andUsedCapacityEqualTo(Long value) {
            addCriterion("used_capacity =", value, "usedCapacity");
            return (Criteria) this;
        }

        public Criteria andUsedCapacityNotEqualTo(Long value) {
            addCriterion("used_capacity <>", value, "usedCapacity");
            return (Criteria) this;
        }

        public Criteria andUsedCapacityGreaterThan(Long value) {
            addCriterion("used_capacity >", value, "usedCapacity");
            return (Criteria) this;
        }

        public Criteria andUsedCapacityGreaterThanOrEqualTo(Long value) {
            addCriterion("used_capacity >=", value, "usedCapacity");
            return (Criteria) this;
        }

        public Criteria andUsedCapacityLessThan(Long value) {
            addCriterion("used_capacity <", value, "usedCapacity");
            return (Criteria) this;
        }

        public Criteria andUsedCapacityLessThanOrEqualTo(Long value) {
            addCriterion("used_capacity <=", value, "usedCapacity");
            return (Criteria) this;
        }

        public Criteria andUsedCapacityIn(List<Long> values) {
            addCriterion("used_capacity in", values, "usedCapacity");
            return (Criteria) this;
        }

        public Criteria andUsedCapacityNotIn(List<Long> values) {
            addCriterion("used_capacity not in", values, "usedCapacity");
            return (Criteria) this;
        }

        public Criteria andUsedCapacityBetween(Long value1, Long value2) {
            addCriterion("used_capacity between", value1, value2, "usedCapacity");
            return (Criteria) this;
        }

        public Criteria andUsedCapacityNotBetween(Long value1, Long value2) {
            addCriterion("used_capacity not between", value1, value2, "usedCapacity");
            return (Criteria) this;
        }

        public Criteria andMaxCapacityIsNull() {
            addCriterion("max_capacity is null");
            return (Criteria) this;
        }

        public Criteria andMaxCapacityIsNotNull() {
            addCriterion("max_capacity is not null");
            return (Criteria) this;
        }

        public Criteria andMaxCapacityEqualTo(Long value) {
            addCriterion("max_capacity =", value, "maxCapacity");
            return (Criteria) this;
        }

        public Criteria andMaxCapacityNotEqualTo(Long value) {
            addCriterion("max_capacity <>", value, "maxCapacity");
            return (Criteria) this;
        }

        public Criteria andMaxCapacityGreaterThan(Long value) {
            addCriterion("max_capacity >", value, "maxCapacity");
            return (Criteria) this;
        }

        public Criteria andMaxCapacityGreaterThanOrEqualTo(Long value) {
            addCriterion("max_capacity >=", value, "maxCapacity");
            return (Criteria) this;
        }

        public Criteria andMaxCapacityLessThan(Long value) {
            addCriterion("max_capacity <", value, "maxCapacity");
            return (Criteria) this;
        }

        public Criteria andMaxCapacityLessThanOrEqualTo(Long value) {
            addCriterion("max_capacity <=", value, "maxCapacity");
            return (Criteria) this;
        }

        public Criteria andMaxCapacityIn(List<Long> values) {
            addCriterion("max_capacity in", values, "maxCapacity");
            return (Criteria) this;
        }

        public Criteria andMaxCapacityNotIn(List<Long> values) {
            addCriterion("max_capacity not in", values, "maxCapacity");
            return (Criteria) this;
        }

        public Criteria andMaxCapacityBetween(Long value1, Long value2) {
            addCriterion("max_capacity between", value1, value2, "maxCapacity");
            return (Criteria) this;
        }

        public Criteria andMaxCapacityNotBetween(Long value1, Long value2) {
            addCriterion("max_capacity not between", value1, value2, "maxCapacity");
            return (Criteria) this;
        }

        public Criteria andBuyCapacityIsNull() {
            addCriterion("buy_capacity is null");
            return (Criteria) this;
        }

        public Criteria andBuyCapacityIsNotNull() {
            addCriterion("buy_capacity is not null");
            return (Criteria) this;
        }

        public Criteria andBuyCapacityEqualTo(Long value) {
            addCriterion("buy_capacity =", value, "buyCapacity");
            return (Criteria) this;
        }

        public Criteria andBuyCapacityNotEqualTo(Long value) {
            addCriterion("buy_capacity <>", value, "buyCapacity");
            return (Criteria) this;
        }

        public Criteria andBuyCapacityGreaterThan(Long value) {
            addCriterion("buy_capacity >", value, "buyCapacity");
            return (Criteria) this;
        }

        public Criteria andBuyCapacityGreaterThanOrEqualTo(Long value) {
            addCriterion("buy_capacity >=", value, "buyCapacity");
            return (Criteria) this;
        }

        public Criteria andBuyCapacityLessThan(Long value) {
            addCriterion("buy_capacity <", value, "buyCapacity");
            return (Criteria) this;
        }

        public Criteria andBuyCapacityLessThanOrEqualTo(Long value) {
            addCriterion("buy_capacity <=", value, "buyCapacity");
            return (Criteria) this;
        }

        public Criteria andBuyCapacityIn(List<Long> values) {
            addCriterion("buy_capacity in", values, "buyCapacity");
            return (Criteria) this;
        }

        public Criteria andBuyCapacityNotIn(List<Long> values) {
            addCriterion("buy_capacity not in", values, "buyCapacity");
            return (Criteria) this;
        }

        public Criteria andBuyCapacityBetween(Long value1, Long value2) {
            addCriterion("buy_capacity between", value1, value2, "buyCapacity");
            return (Criteria) this;
        }

        public Criteria andBuyCapacityNotBetween(Long value1, Long value2) {
            addCriterion("buy_capacity not between", value1, value2, "buyCapacity");
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