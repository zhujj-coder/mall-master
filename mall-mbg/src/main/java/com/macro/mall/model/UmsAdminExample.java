package com.macro.mall.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UmsAdminExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UmsAdminExample() {
        oredCriteria = new ArrayList<>();
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
            criteria = new ArrayList<>();
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

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNull() {
            addCriterion("username is null");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNotNull() {
            addCriterion("username is not null");
            return (Criteria) this;
        }

        public Criteria andUsernameEqualTo(String value) {
            addCriterion("username =", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotEqualTo(String value) {
            addCriterion("username <>", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThan(String value) {
            addCriterion("username >", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("username >=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThan(String value) {
            addCriterion("username <", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThanOrEqualTo(String value) {
            addCriterion("username <=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLike(String value) {
            addCriterion("username like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotLike(String value) {
            addCriterion("username not like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameIn(List<String> values) {
            addCriterion("username in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotIn(List<String> values) {
            addCriterion("username not in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameBetween(String value1, String value2) {
            addCriterion("username between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotBetween(String value1, String value2) {
            addCriterion("username not between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNull() {
            addCriterion("password is null");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNotNull() {
            addCriterion("password is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordEqualTo(String value) {
            addCriterion("password =", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotEqualTo(String value) {
            addCriterion("password <>", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThan(String value) {
            addCriterion("password >", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("password >=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThan(String value) {
            addCriterion("password <", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThanOrEqualTo(String value) {
            addCriterion("password <=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLike(String value) {
            addCriterion("password like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotLike(String value) {
            addCriterion("password not like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordIn(List<String> values) {
            addCriterion("password in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotIn(List<String> values) {
            addCriterion("password not in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordBetween(String value1, String value2) {
            addCriterion("password between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotBetween(String value1, String value2) {
            addCriterion("password not between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andIconIsNull() {
            addCriterion("icon is null");
            return (Criteria) this;
        }

        public Criteria andIconIsNotNull() {
            addCriterion("icon is not null");
            return (Criteria) this;
        }

        public Criteria andIconEqualTo(String value) {
            addCriterion("icon =", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconNotEqualTo(String value) {
            addCriterion("icon <>", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconGreaterThan(String value) {
            addCriterion("icon >", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconGreaterThanOrEqualTo(String value) {
            addCriterion("icon >=", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconLessThan(String value) {
            addCriterion("icon <", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconLessThanOrEqualTo(String value) {
            addCriterion("icon <=", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconLike(String value) {
            addCriterion("icon like", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconNotLike(String value) {
            addCriterion("icon not like", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconIn(List<String> values) {
            addCriterion("icon in", values, "icon");
            return (Criteria) this;
        }

        public Criteria andIconNotIn(List<String> values) {
            addCriterion("icon not in", values, "icon");
            return (Criteria) this;
        }

        public Criteria andIconBetween(String value1, String value2) {
            addCriterion("icon between", value1, value2, "icon");
            return (Criteria) this;
        }

        public Criteria andIconNotBetween(String value1, String value2) {
            addCriterion("icon not between", value1, value2, "icon");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andNickNameIsNull() {
            addCriterion("nick_name is null");
            return (Criteria) this;
        }

        public Criteria andNickNameIsNotNull() {
            addCriterion("nick_name is not null");
            return (Criteria) this;
        }

        public Criteria andNickNameEqualTo(String value) {
            addCriterion("nick_name =", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameNotEqualTo(String value) {
            addCriterion("nick_name <>", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameGreaterThan(String value) {
            addCriterion("nick_name >", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameGreaterThanOrEqualTo(String value) {
            addCriterion("nick_name >=", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameLessThan(String value) {
            addCriterion("nick_name <", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameLessThanOrEqualTo(String value) {
            addCriterion("nick_name <=", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameLike(String value) {
            addCriterion("nick_name like", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameNotLike(String value) {
            addCriterion("nick_name not like", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameIn(List<String> values) {
            addCriterion("nick_name in", values, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameNotIn(List<String> values) {
            addCriterion("nick_name not in", values, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameBetween(String value1, String value2) {
            addCriterion("nick_name between", value1, value2, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameNotBetween(String value1, String value2) {
            addCriterion("nick_name not between", value1, value2, "nickName");
            return (Criteria) this;
        }

        public Criteria andNoteIsNull() {
            addCriterion("note is null");
            return (Criteria) this;
        }

        public Criteria andNoteIsNotNull() {
            addCriterion("note is not null");
            return (Criteria) this;
        }

        public Criteria andNoteEqualTo(String value) {
            addCriterion("note =", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotEqualTo(String value) {
            addCriterion("note <>", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThan(String value) {
            addCriterion("note >", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThanOrEqualTo(String value) {
            addCriterion("note >=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThan(String value) {
            addCriterion("note <", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThanOrEqualTo(String value) {
            addCriterion("note <=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLike(String value) {
            addCriterion("note like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotLike(String value) {
            addCriterion("note not like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteIn(List<String> values) {
            addCriterion("note in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotIn(List<String> values) {
            addCriterion("note not in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteBetween(String value1, String value2) {
            addCriterion("note between", value1, value2, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotBetween(String value1, String value2) {
            addCriterion("note not between", value1, value2, "note");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeIsNull() {
            addCriterion("login_time is null");
            return (Criteria) this;
        }

        public Criteria andLoginTimeIsNotNull() {
            addCriterion("login_time is not null");
            return (Criteria) this;
        }

        public Criteria andLoginTimeEqualTo(Date value) {
            addCriterion("login_time =", value, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeNotEqualTo(Date value) {
            addCriterion("login_time <>", value, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeGreaterThan(Date value) {
            addCriterion("login_time >", value, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("login_time >=", value, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeLessThan(Date value) {
            addCriterion("login_time <", value, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeLessThanOrEqualTo(Date value) {
            addCriterion("login_time <=", value, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeIn(List<Date> values) {
            addCriterion("login_time in", values, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeNotIn(List<Date> values) {
            addCriterion("login_time not in", values, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeBetween(Date value1, Date value2) {
            addCriterion("login_time between", value1, value2, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeNotBetween(Date value1, Date value2) {
            addCriterion("login_time not between", value1, value2, "loginTime");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andAppIdIsNull() {
            addCriterion("app_id is null");
            return (Criteria) this;
        }

        public Criteria andAppIdIsNotNull() {
            addCriterion("app_id is not null");
            return (Criteria) this;
        }

        public Criteria andAppIdEqualTo(String value) {
            addCriterion("app_id =", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotEqualTo(String value) {
            addCriterion("app_id <>", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdGreaterThan(String value) {
            addCriterion("app_id >", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdGreaterThanOrEqualTo(String value) {
            addCriterion("app_id >=", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLessThan(String value) {
            addCriterion("app_id <", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLessThanOrEqualTo(String value) {
            addCriterion("app_id <=", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLike(String value) {
            addCriterion("app_id like", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotLike(String value) {
            addCriterion("app_id not like", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdIn(List<String> values) {
            addCriterion("app_id in", values, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotIn(List<String> values) {
            addCriterion("app_id not in", values, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdBetween(String value1, String value2) {
            addCriterion("app_id between", value1, value2, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotBetween(String value1, String value2) {
            addCriterion("app_id not between", value1, value2, "appId");
            return (Criteria) this;
        }

        public Criteria andAppSecretIsNull() {
            addCriterion("app_secret is null");
            return (Criteria) this;
        }

        public Criteria andAppSecretIsNotNull() {
            addCriterion("app_secret is not null");
            return (Criteria) this;
        }

        public Criteria andAppSecretEqualTo(String value) {
            addCriterion("app_secret =", value, "appSecret");
            return (Criteria) this;
        }

        public Criteria andAppSecretNotEqualTo(String value) {
            addCriterion("app_secret <>", value, "appSecret");
            return (Criteria) this;
        }

        public Criteria andAppSecretGreaterThan(String value) {
            addCriterion("app_secret >", value, "appSecret");
            return (Criteria) this;
        }

        public Criteria andAppSecretGreaterThanOrEqualTo(String value) {
            addCriterion("app_secret >=", value, "appSecret");
            return (Criteria) this;
        }

        public Criteria andAppSecretLessThan(String value) {
            addCriterion("app_secret <", value, "appSecret");
            return (Criteria) this;
        }

        public Criteria andAppSecretLessThanOrEqualTo(String value) {
            addCriterion("app_secret <=", value, "appSecret");
            return (Criteria) this;
        }

        public Criteria andAppSecretLike(String value) {
            addCriterion("app_secret like", value, "appSecret");
            return (Criteria) this;
        }

        public Criteria andAppSecretNotLike(String value) {
            addCriterion("app_secret not like", value, "appSecret");
            return (Criteria) this;
        }

        public Criteria andAppSecretIn(List<String> values) {
            addCriterion("app_secret in", values, "appSecret");
            return (Criteria) this;
        }

        public Criteria andAppSecretNotIn(List<String> values) {
            addCriterion("app_secret not in", values, "appSecret");
            return (Criteria) this;
        }

        public Criteria andAppSecretBetween(String value1, String value2) {
            addCriterion("app_secret between", value1, value2, "appSecret");
            return (Criteria) this;
        }

        public Criteria andAppSecretNotBetween(String value1, String value2) {
            addCriterion("app_secret not between", value1, value2, "appSecret");
            return (Criteria) this;
        }

        public Criteria andNoticeContentIsNull() {
            addCriterion("notice_content is null");
            return (Criteria) this;
        }

        public Criteria andNoticeContentIsNotNull() {
            addCriterion("notice_content is not null");
            return (Criteria) this;
        }

        public Criteria andNoticeContentEqualTo(String value) {
            addCriterion("notice_content =", value, "noticeContent");
            return (Criteria) this;
        }

        public Criteria andNoticeContentNotEqualTo(String value) {
            addCriterion("notice_content <>", value, "noticeContent");
            return (Criteria) this;
        }

        public Criteria andNoticeContentGreaterThan(String value) {
            addCriterion("notice_content >", value, "noticeContent");
            return (Criteria) this;
        }

        public Criteria andNoticeContentGreaterThanOrEqualTo(String value) {
            addCriterion("notice_content >=", value, "noticeContent");
            return (Criteria) this;
        }

        public Criteria andNoticeContentLessThan(String value) {
            addCriterion("notice_content <", value, "noticeContent");
            return (Criteria) this;
        }

        public Criteria andNoticeContentLessThanOrEqualTo(String value) {
            addCriterion("notice_content <=", value, "noticeContent");
            return (Criteria) this;
        }

        public Criteria andNoticeContentLike(String value) {
            addCriterion("notice_content like", value, "noticeContent");
            return (Criteria) this;
        }

        public Criteria andNoticeContentNotLike(String value) {
            addCriterion("notice_content not like", value, "noticeContent");
            return (Criteria) this;
        }

        public Criteria andNoticeContentIn(List<String> values) {
            addCriterion("notice_content in", values, "noticeContent");
            return (Criteria) this;
        }

        public Criteria andNoticeContentNotIn(List<String> values) {
            addCriterion("notice_content not in", values, "noticeContent");
            return (Criteria) this;
        }

        public Criteria andNoticeContentBetween(String value1, String value2) {
            addCriterion("notice_content between", value1, value2, "noticeContent");
            return (Criteria) this;
        }

        public Criteria andNoticeContentNotBetween(String value1, String value2) {
            addCriterion("notice_content not between", value1, value2, "noticeContent");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeIsNull() {
            addCriterion("notice_type is null");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeIsNotNull() {
            addCriterion("notice_type is not null");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeEqualTo(String value) {
            addCriterion("notice_type =", value, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeNotEqualTo(String value) {
            addCriterion("notice_type <>", value, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeGreaterThan(String value) {
            addCriterion("notice_type >", value, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeGreaterThanOrEqualTo(String value) {
            addCriterion("notice_type >=", value, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeLessThan(String value) {
            addCriterion("notice_type <", value, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeLessThanOrEqualTo(String value) {
            addCriterion("notice_type <=", value, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeLike(String value) {
            addCriterion("notice_type like", value, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeNotLike(String value) {
            addCriterion("notice_type not like", value, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeIn(List<String> values) {
            addCriterion("notice_type in", values, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeNotIn(List<String> values) {
            addCriterion("notice_type not in", values, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeBetween(String value1, String value2) {
            addCriterion("notice_type between", value1, value2, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeNotBetween(String value1, String value2) {
            addCriterion("notice_type not between", value1, value2, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeStartIsNull() {
            addCriterion("notice_start is null");
            return (Criteria) this;
        }

        public Criteria andNoticeStartIsNotNull() {
            addCriterion("notice_start is not null");
            return (Criteria) this;
        }

        public Criteria andNoticeStartEqualTo(String value) {
            addCriterion("notice_start =", value, "noticeStart");
            return (Criteria) this;
        }

        public Criteria andNoticeStartNotEqualTo(String value) {
            addCriterion("notice_start <>", value, "noticeStart");
            return (Criteria) this;
        }

        public Criteria andNoticeStartGreaterThan(String value) {
            addCriterion("notice_start >", value, "noticeStart");
            return (Criteria) this;
        }

        public Criteria andNoticeStartGreaterThanOrEqualTo(String value) {
            addCriterion("notice_start >=", value, "noticeStart");
            return (Criteria) this;
        }

        public Criteria andNoticeStartLessThan(String value) {
            addCriterion("notice_start <", value, "noticeStart");
            return (Criteria) this;
        }

        public Criteria andNoticeStartLessThanOrEqualTo(String value) {
            addCriterion("notice_start <=", value, "noticeStart");
            return (Criteria) this;
        }

        public Criteria andNoticeStartLike(String value) {
            addCriterion("notice_start like", value, "noticeStart");
            return (Criteria) this;
        }

        public Criteria andNoticeStartNotLike(String value) {
            addCriterion("notice_start not like", value, "noticeStart");
            return (Criteria) this;
        }

        public Criteria andNoticeStartIn(List<String> values) {
            addCriterion("notice_start in", values, "noticeStart");
            return (Criteria) this;
        }

        public Criteria andNoticeStartNotIn(List<String> values) {
            addCriterion("notice_start not in", values, "noticeStart");
            return (Criteria) this;
        }

        public Criteria andNoticeStartBetween(String value1, String value2) {
            addCriterion("notice_start between", value1, value2, "noticeStart");
            return (Criteria) this;
        }

        public Criteria andNoticeStartNotBetween(String value1, String value2) {
            addCriterion("notice_start not between", value1, value2, "noticeStart");
            return (Criteria) this;
        }

        public Criteria andNoticeEndIsNull() {
            addCriterion("notice_end is null");
            return (Criteria) this;
        }

        public Criteria andNoticeEndIsNotNull() {
            addCriterion("notice_end is not null");
            return (Criteria) this;
        }

        public Criteria andNoticeEndEqualTo(String value) {
            addCriterion("notice_end =", value, "noticeEnd");
            return (Criteria) this;
        }

        public Criteria andNoticeEndNotEqualTo(String value) {
            addCriterion("notice_end <>", value, "noticeEnd");
            return (Criteria) this;
        }

        public Criteria andNoticeEndGreaterThan(String value) {
            addCriterion("notice_end >", value, "noticeEnd");
            return (Criteria) this;
        }

        public Criteria andNoticeEndGreaterThanOrEqualTo(String value) {
            addCriterion("notice_end >=", value, "noticeEnd");
            return (Criteria) this;
        }

        public Criteria andNoticeEndLessThan(String value) {
            addCriterion("notice_end <", value, "noticeEnd");
            return (Criteria) this;
        }

        public Criteria andNoticeEndLessThanOrEqualTo(String value) {
            addCriterion("notice_end <=", value, "noticeEnd");
            return (Criteria) this;
        }

        public Criteria andNoticeEndLike(String value) {
            addCriterion("notice_end like", value, "noticeEnd");
            return (Criteria) this;
        }

        public Criteria andNoticeEndNotLike(String value) {
            addCriterion("notice_end not like", value, "noticeEnd");
            return (Criteria) this;
        }

        public Criteria andNoticeEndIn(List<String> values) {
            addCriterion("notice_end in", values, "noticeEnd");
            return (Criteria) this;
        }

        public Criteria andNoticeEndNotIn(List<String> values) {
            addCriterion("notice_end not in", values, "noticeEnd");
            return (Criteria) this;
        }

        public Criteria andNoticeEndBetween(String value1, String value2) {
            addCriterion("notice_end between", value1, value2, "noticeEnd");
            return (Criteria) this;
        }

        public Criteria andNoticeEndNotBetween(String value1, String value2) {
            addCriterion("notice_end not between", value1, value2, "noticeEnd");
            return (Criteria) this;
        }

        public Criteria andNoticeOnIsNull() {
            addCriterion("notice_on is null");
            return (Criteria) this;
        }

        public Criteria andNoticeOnIsNotNull() {
            addCriterion("notice_on is not null");
            return (Criteria) this;
        }

        public Criteria andNoticeOnEqualTo(Integer value) {
            addCriterion("notice_on =", value, "noticeOn");
            return (Criteria) this;
        }

        public Criteria andNoticeOnNotEqualTo(Integer value) {
            addCriterion("notice_on <>", value, "noticeOn");
            return (Criteria) this;
        }

        public Criteria andNoticeOnGreaterThan(Integer value) {
            addCriterion("notice_on >", value, "noticeOn");
            return (Criteria) this;
        }

        public Criteria andNoticeOnGreaterThanOrEqualTo(Integer value) {
            addCriterion("notice_on >=", value, "noticeOn");
            return (Criteria) this;
        }

        public Criteria andNoticeOnLessThan(Integer value) {
            addCriterion("notice_on <", value, "noticeOn");
            return (Criteria) this;
        }

        public Criteria andNoticeOnLessThanOrEqualTo(Integer value) {
            addCriterion("notice_on <=", value, "noticeOn");
            return (Criteria) this;
        }

        public Criteria andNoticeOnIn(List<Integer> values) {
            addCriterion("notice_on in", values, "noticeOn");
            return (Criteria) this;
        }

        public Criteria andNoticeOnNotIn(List<Integer> values) {
            addCriterion("notice_on not in", values, "noticeOn");
            return (Criteria) this;
        }

        public Criteria andNoticeOnBetween(Integer value1, Integer value2) {
            addCriterion("notice_on between", value1, value2, "noticeOn");
            return (Criteria) this;
        }

        public Criteria andNoticeOnNotBetween(Integer value1, Integer value2) {
            addCriterion("notice_on not between", value1, value2, "noticeOn");
            return (Criteria) this;
        }

        public Criteria andMchIdIsNull() {
            addCriterion("mch_id is null");
            return (Criteria) this;
        }

        public Criteria andMchIdIsNotNull() {
            addCriterion("mch_id is not null");
            return (Criteria) this;
        }

        public Criteria andMchIdEqualTo(String value) {
            addCriterion("mch_id =", value, "mchId");
            return (Criteria) this;
        }

        public Criteria andMchIdNotEqualTo(String value) {
            addCriterion("mch_id <>", value, "mchId");
            return (Criteria) this;
        }

        public Criteria andMchIdGreaterThan(String value) {
            addCriterion("mch_id >", value, "mchId");
            return (Criteria) this;
        }

        public Criteria andMchIdGreaterThanOrEqualTo(String value) {
            addCriterion("mch_id >=", value, "mchId");
            return (Criteria) this;
        }

        public Criteria andMchIdLessThan(String value) {
            addCriterion("mch_id <", value, "mchId");
            return (Criteria) this;
        }

        public Criteria andMchIdLessThanOrEqualTo(String value) {
            addCriterion("mch_id <=", value, "mchId");
            return (Criteria) this;
        }

        public Criteria andMchIdLike(String value) {
            addCriterion("mch_id like", value, "mchId");
            return (Criteria) this;
        }

        public Criteria andMchIdNotLike(String value) {
            addCriterion("mch_id not like", value, "mchId");
            return (Criteria) this;
        }

        public Criteria andMchIdIn(List<String> values) {
            addCriterion("mch_id in", values, "mchId");
            return (Criteria) this;
        }

        public Criteria andMchIdNotIn(List<String> values) {
            addCriterion("mch_id not in", values, "mchId");
            return (Criteria) this;
        }

        public Criteria andMchIdBetween(String value1, String value2) {
            addCriterion("mch_id between", value1, value2, "mchId");
            return (Criteria) this;
        }

        public Criteria andMchIdNotBetween(String value1, String value2) {
            addCriterion("mch_id not between", value1, value2, "mchId");
            return (Criteria) this;
        }

        public Criteria andMchKeyIsNull() {
            addCriterion("mch_key is null");
            return (Criteria) this;
        }

        public Criteria andMchKeyIsNotNull() {
            addCriterion("mch_key is not null");
            return (Criteria) this;
        }

        public Criteria andMchKeyEqualTo(String value) {
            addCriterion("mch_key =", value, "mchKey");
            return (Criteria) this;
        }

        public Criteria andMchKeyNotEqualTo(String value) {
            addCriterion("mch_key <>", value, "mchKey");
            return (Criteria) this;
        }

        public Criteria andMchKeyGreaterThan(String value) {
            addCriterion("mch_key >", value, "mchKey");
            return (Criteria) this;
        }

        public Criteria andMchKeyGreaterThanOrEqualTo(String value) {
            addCriterion("mch_key >=", value, "mchKey");
            return (Criteria) this;
        }

        public Criteria andMchKeyLessThan(String value) {
            addCriterion("mch_key <", value, "mchKey");
            return (Criteria) this;
        }

        public Criteria andMchKeyLessThanOrEqualTo(String value) {
            addCriterion("mch_key <=", value, "mchKey");
            return (Criteria) this;
        }

        public Criteria andMchKeyLike(String value) {
            addCriterion("mch_key like", value, "mchKey");
            return (Criteria) this;
        }

        public Criteria andMchKeyNotLike(String value) {
            addCriterion("mch_key not like", value, "mchKey");
            return (Criteria) this;
        }

        public Criteria andMchKeyIn(List<String> values) {
            addCriterion("mch_key in", values, "mchKey");
            return (Criteria) this;
        }

        public Criteria andMchKeyNotIn(List<String> values) {
            addCriterion("mch_key not in", values, "mchKey");
            return (Criteria) this;
        }

        public Criteria andMchKeyBetween(String value1, String value2) {
            addCriterion("mch_key between", value1, value2, "mchKey");
            return (Criteria) this;
        }

        public Criteria andMchKeyNotBetween(String value1, String value2) {
            addCriterion("mch_key not between", value1, value2, "mchKey");
            return (Criteria) this;
        }

        public Criteria andAuthorizerRefreshTokenIsNull() {
            addCriterion("authorizer_refresh_token is null");
            return (Criteria) this;
        }

        public Criteria andAuthorizerRefreshTokenIsNotNull() {
            addCriterion("authorizer_refresh_token is not null");
            return (Criteria) this;
        }

        public Criteria andAuthorizerRefreshTokenEqualTo(String value) {
            addCriterion("authorizer_refresh_token =", value, "authorizerRefreshToken");
            return (Criteria) this;
        }

        public Criteria andAuthorizerRefreshTokenNotEqualTo(String value) {
            addCriterion("authorizer_refresh_token <>", value, "authorizerRefreshToken");
            return (Criteria) this;
        }

        public Criteria andAuthorizerRefreshTokenGreaterThan(String value) {
            addCriterion("authorizer_refresh_token >", value, "authorizerRefreshToken");
            return (Criteria) this;
        }

        public Criteria andAuthorizerRefreshTokenGreaterThanOrEqualTo(String value) {
            addCriterion("authorizer_refresh_token >=", value, "authorizerRefreshToken");
            return (Criteria) this;
        }

        public Criteria andAuthorizerRefreshTokenLessThan(String value) {
            addCriterion("authorizer_refresh_token <", value, "authorizerRefreshToken");
            return (Criteria) this;
        }

        public Criteria andAuthorizerRefreshTokenLessThanOrEqualTo(String value) {
            addCriterion("authorizer_refresh_token <=", value, "authorizerRefreshToken");
            return (Criteria) this;
        }

        public Criteria andAuthorizerRefreshTokenLike(String value) {
            addCriterion("authorizer_refresh_token like", value, "authorizerRefreshToken");
            return (Criteria) this;
        }

        public Criteria andAuthorizerRefreshTokenNotLike(String value) {
            addCriterion("authorizer_refresh_token not like", value, "authorizerRefreshToken");
            return (Criteria) this;
        }

        public Criteria andAuthorizerRefreshTokenIn(List<String> values) {
            addCriterion("authorizer_refresh_token in", values, "authorizerRefreshToken");
            return (Criteria) this;
        }

        public Criteria andAuthorizerRefreshTokenNotIn(List<String> values) {
            addCriterion("authorizer_refresh_token not in", values, "authorizerRefreshToken");
            return (Criteria) this;
        }

        public Criteria andAuthorizerRefreshTokenBetween(String value1, String value2) {
            addCriterion("authorizer_refresh_token between", value1, value2, "authorizerRefreshToken");
            return (Criteria) this;
        }

        public Criteria andAuthorizerRefreshTokenNotBetween(String value1, String value2) {
            addCriterion("authorizer_refresh_token not between", value1, value2, "authorizerRefreshToken");
            return (Criteria) this;
        }

        public Criteria andPublishStatusIsNull() {
            addCriterion("publish_status is null");
            return (Criteria) this;
        }

        public Criteria andPublishStatusIsNotNull() {
            addCriterion("publish_status is not null");
            return (Criteria) this;
        }

        public Criteria andPublishStatusEqualTo(Integer value) {
            addCriterion("publish_status =", value, "publishStatus");
            return (Criteria) this;
        }

        public Criteria andPublishStatusNotEqualTo(Integer value) {
            addCriterion("publish_status <>", value, "publishStatus");
            return (Criteria) this;
        }

        public Criteria andPublishStatusGreaterThan(Integer value) {
            addCriterion("publish_status >", value, "publishStatus");
            return (Criteria) this;
        }

        public Criteria andPublishStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("publish_status >=", value, "publishStatus");
            return (Criteria) this;
        }

        public Criteria andPublishStatusLessThan(Integer value) {
            addCriterion("publish_status <", value, "publishStatus");
            return (Criteria) this;
        }

        public Criteria andPublishStatusLessThanOrEqualTo(Integer value) {
            addCriterion("publish_status <=", value, "publishStatus");
            return (Criteria) this;
        }

        public Criteria andPublishStatusIn(List<Integer> values) {
            addCriterion("publish_status in", values, "publishStatus");
            return (Criteria) this;
        }

        public Criteria andPublishStatusNotIn(List<Integer> values) {
            addCriterion("publish_status not in", values, "publishStatus");
            return (Criteria) this;
        }

        public Criteria andPublishStatusBetween(Integer value1, Integer value2) {
            addCriterion("publish_status between", value1, value2, "publishStatus");
            return (Criteria) this;
        }

        public Criteria andPublishStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("publish_status not between", value1, value2, "publishStatus");
            return (Criteria) this;
        }

        public Criteria andWxacodeUrlIsNull() {
            addCriterion("wxacode_url is null");
            return (Criteria) this;
        }

        public Criteria andWxacodeUrlIsNotNull() {
            addCriterion("wxacode_url is not null");
            return (Criteria) this;
        }

        public Criteria andWxacodeUrlEqualTo(String value) {
            addCriterion("wxacode_url =", value, "wxacodeUrl");
            return (Criteria) this;
        }

        public Criteria andWxacodeUrlNotEqualTo(String value) {
            addCriterion("wxacode_url <>", value, "wxacodeUrl");
            return (Criteria) this;
        }

        public Criteria andWxacodeUrlGreaterThan(String value) {
            addCriterion("wxacode_url >", value, "wxacodeUrl");
            return (Criteria) this;
        }

        public Criteria andWxacodeUrlGreaterThanOrEqualTo(String value) {
            addCriterion("wxacode_url >=", value, "wxacodeUrl");
            return (Criteria) this;
        }

        public Criteria andWxacodeUrlLessThan(String value) {
            addCriterion("wxacode_url <", value, "wxacodeUrl");
            return (Criteria) this;
        }

        public Criteria andWxacodeUrlLessThanOrEqualTo(String value) {
            addCriterion("wxacode_url <=", value, "wxacodeUrl");
            return (Criteria) this;
        }

        public Criteria andWxacodeUrlLike(String value) {
            addCriterion("wxacode_url like", value, "wxacodeUrl");
            return (Criteria) this;
        }

        public Criteria andWxacodeUrlNotLike(String value) {
            addCriterion("wxacode_url not like", value, "wxacodeUrl");
            return (Criteria) this;
        }

        public Criteria andWxacodeUrlIn(List<String> values) {
            addCriterion("wxacode_url in", values, "wxacodeUrl");
            return (Criteria) this;
        }

        public Criteria andWxacodeUrlNotIn(List<String> values) {
            addCriterion("wxacode_url not in", values, "wxacodeUrl");
            return (Criteria) this;
        }

        public Criteria andWxacodeUrlBetween(String value1, String value2) {
            addCriterion("wxacode_url between", value1, value2, "wxacodeUrl");
            return (Criteria) this;
        }

        public Criteria andWxacodeUrlNotBetween(String value1, String value2) {
            addCriterion("wxacode_url not between", value1, value2, "wxacodeUrl");
            return (Criteria) this;
        }

        public Criteria andWxacodePayUrlIsNull() {
            addCriterion("wxacode_pay_url is null");
            return (Criteria) this;
        }

        public Criteria andWxacodePayUrlIsNotNull() {
            addCriterion("wxacode_pay_url is not null");
            return (Criteria) this;
        }

        public Criteria andWxacodePayUrlEqualTo(String value) {
            addCriterion("wxacode_pay_url =", value, "wxacodePayUrl");
            return (Criteria) this;
        }

        public Criteria andWxacodePayUrlNotEqualTo(String value) {
            addCriterion("wxacode_pay_url <>", value, "wxacodePayUrl");
            return (Criteria) this;
        }

        public Criteria andWxacodePayUrlGreaterThan(String value) {
            addCriterion("wxacode_pay_url >", value, "wxacodePayUrl");
            return (Criteria) this;
        }

        public Criteria andWxacodePayUrlGreaterThanOrEqualTo(String value) {
            addCriterion("wxacode_pay_url >=", value, "wxacodePayUrl");
            return (Criteria) this;
        }

        public Criteria andWxacodePayUrlLessThan(String value) {
            addCriterion("wxacode_pay_url <", value, "wxacodePayUrl");
            return (Criteria) this;
        }

        public Criteria andWxacodePayUrlLessThanOrEqualTo(String value) {
            addCriterion("wxacode_pay_url <=", value, "wxacodePayUrl");
            return (Criteria) this;
        }

        public Criteria andWxacodePayUrlLike(String value) {
            addCriterion("wxacode_pay_url like", value, "wxacodePayUrl");
            return (Criteria) this;
        }

        public Criteria andWxacodePayUrlNotLike(String value) {
            addCriterion("wxacode_pay_url not like", value, "wxacodePayUrl");
            return (Criteria) this;
        }

        public Criteria andWxacodePayUrlIn(List<String> values) {
            addCriterion("wxacode_pay_url in", values, "wxacodePayUrl");
            return (Criteria) this;
        }

        public Criteria andWxacodePayUrlNotIn(List<String> values) {
            addCriterion("wxacode_pay_url not in", values, "wxacodePayUrl");
            return (Criteria) this;
        }

        public Criteria andWxacodePayUrlBetween(String value1, String value2) {
            addCriterion("wxacode_pay_url between", value1, value2, "wxacodePayUrl");
            return (Criteria) this;
        }

        public Criteria andWxacodePayUrlNotBetween(String value1, String value2) {
            addCriterion("wxacode_pay_url not between", value1, value2, "wxacodePayUrl");
            return (Criteria) this;
        }

        public Criteria andVipEndDateIsNull() {
            addCriterion("vip_end_date is null");
            return (Criteria) this;
        }

        public Criteria andVipEndDateIsNotNull() {
            addCriterion("vip_end_date is not null");
            return (Criteria) this;
        }

        public Criteria andVipEndDateEqualTo(Date value) {
            addCriterion("vip_end_date =", value, "vipEndDate");
            return (Criteria) this;
        }

        public Criteria andVipEndDateNotEqualTo(Date value) {
            addCriterion("vip_end_date <>", value, "vipEndDate");
            return (Criteria) this;
        }

        public Criteria andVipEndDateGreaterThan(Date value) {
            addCriterion("vip_end_date >", value, "vipEndDate");
            return (Criteria) this;
        }

        public Criteria andVipEndDateGreaterThanOrEqualTo(Date value) {
            addCriterion("vip_end_date >=", value, "vipEndDate");
            return (Criteria) this;
        }

        public Criteria andVipEndDateLessThan(Date value) {
            addCriterion("vip_end_date <", value, "vipEndDate");
            return (Criteria) this;
        }

        public Criteria andVipEndDateLessThanOrEqualTo(Date value) {
            addCriterion("vip_end_date <=", value, "vipEndDate");
            return (Criteria) this;
        }

        public Criteria andVipEndDateIn(List<Date> values) {
            addCriterion("vip_end_date in", values, "vipEndDate");
            return (Criteria) this;
        }

        public Criteria andVipEndDateNotIn(List<Date> values) {
            addCriterion("vip_end_date not in", values, "vipEndDate");
            return (Criteria) this;
        }

        public Criteria andVipEndDateBetween(Date value1, Date value2) {
            addCriterion("vip_end_date between", value1, value2, "vipEndDate");
            return (Criteria) this;
        }

        public Criteria andVipEndDateNotBetween(Date value1, Date value2) {
            addCriterion("vip_end_date not between", value1, value2, "vipEndDate");
            return (Criteria) this;
        }

        public Criteria andContactMobileIsNull() {
            addCriterion("contact_mobile is null");
            return (Criteria) this;
        }

        public Criteria andContactMobileIsNotNull() {
            addCriterion("contact_mobile is not null");
            return (Criteria) this;
        }

        public Criteria andContactMobileEqualTo(String value) {
            addCriterion("contact_mobile =", value, "contactMobile");
            return (Criteria) this;
        }

        public Criteria andContactMobileNotEqualTo(String value) {
            addCriterion("contact_mobile <>", value, "contactMobile");
            return (Criteria) this;
        }

        public Criteria andContactMobileGreaterThan(String value) {
            addCriterion("contact_mobile >", value, "contactMobile");
            return (Criteria) this;
        }

        public Criteria andContactMobileGreaterThanOrEqualTo(String value) {
            addCriterion("contact_mobile >=", value, "contactMobile");
            return (Criteria) this;
        }

        public Criteria andContactMobileLessThan(String value) {
            addCriterion("contact_mobile <", value, "contactMobile");
            return (Criteria) this;
        }

        public Criteria andContactMobileLessThanOrEqualTo(String value) {
            addCriterion("contact_mobile <=", value, "contactMobile");
            return (Criteria) this;
        }

        public Criteria andContactMobileLike(String value) {
            addCriterion("contact_mobile like", value, "contactMobile");
            return (Criteria) this;
        }

        public Criteria andContactMobileNotLike(String value) {
            addCriterion("contact_mobile not like", value, "contactMobile");
            return (Criteria) this;
        }

        public Criteria andContactMobileIn(List<String> values) {
            addCriterion("contact_mobile in", values, "contactMobile");
            return (Criteria) this;
        }

        public Criteria andContactMobileNotIn(List<String> values) {
            addCriterion("contact_mobile not in", values, "contactMobile");
            return (Criteria) this;
        }

        public Criteria andContactMobileBetween(String value1, String value2) {
            addCriterion("contact_mobile between", value1, value2, "contactMobile");
            return (Criteria) this;
        }

        public Criteria andContactMobileNotBetween(String value1, String value2) {
            addCriterion("contact_mobile not between", value1, value2, "contactMobile");
            return (Criteria) this;
        }

        public Criteria andContactAddressIsNull() {
            addCriterion("contact_address is null");
            return (Criteria) this;
        }

        public Criteria andContactAddressIsNotNull() {
            addCriterion("contact_address is not null");
            return (Criteria) this;
        }

        public Criteria andContactAddressEqualTo(String value) {
            addCriterion("contact_address =", value, "contactAddress");
            return (Criteria) this;
        }

        public Criteria andContactAddressNotEqualTo(String value) {
            addCriterion("contact_address <>", value, "contactAddress");
            return (Criteria) this;
        }

        public Criteria andContactAddressGreaterThan(String value) {
            addCriterion("contact_address >", value, "contactAddress");
            return (Criteria) this;
        }

        public Criteria andContactAddressGreaterThanOrEqualTo(String value) {
            addCriterion("contact_address >=", value, "contactAddress");
            return (Criteria) this;
        }

        public Criteria andContactAddressLessThan(String value) {
            addCriterion("contact_address <", value, "contactAddress");
            return (Criteria) this;
        }

        public Criteria andContactAddressLessThanOrEqualTo(String value) {
            addCriterion("contact_address <=", value, "contactAddress");
            return (Criteria) this;
        }

        public Criteria andContactAddressLike(String value) {
            addCriterion("contact_address like", value, "contactAddress");
            return (Criteria) this;
        }

        public Criteria andContactAddressNotLike(String value) {
            addCriterion("contact_address not like", value, "contactAddress");
            return (Criteria) this;
        }

        public Criteria andContactAddressIn(List<String> values) {
            addCriterion("contact_address in", values, "contactAddress");
            return (Criteria) this;
        }

        public Criteria andContactAddressNotIn(List<String> values) {
            addCriterion("contact_address not in", values, "contactAddress");
            return (Criteria) this;
        }

        public Criteria andContactAddressBetween(String value1, String value2) {
            addCriterion("contact_address between", value1, value2, "contactAddress");
            return (Criteria) this;
        }

        public Criteria andContactAddressNotBetween(String value1, String value2) {
            addCriterion("contact_address not between", value1, value2, "contactAddress");
            return (Criteria) this;
        }

        public Criteria andWxTemplateIdIsNull() {
            addCriterion("wx_template_id is null");
            return (Criteria) this;
        }

        public Criteria andWxTemplateIdIsNotNull() {
            addCriterion("wx_template_id is not null");
            return (Criteria) this;
        }

        public Criteria andWxTemplateIdEqualTo(String value) {
            addCriterion("wx_template_id =", value, "wxTemplateId");
            return (Criteria) this;
        }

        public Criteria andWxTemplateIdNotEqualTo(String value) {
            addCriterion("wx_template_id <>", value, "wxTemplateId");
            return (Criteria) this;
        }

        public Criteria andWxTemplateIdGreaterThan(String value) {
            addCriterion("wx_template_id >", value, "wxTemplateId");
            return (Criteria) this;
        }

        public Criteria andWxTemplateIdGreaterThanOrEqualTo(String value) {
            addCriterion("wx_template_id >=", value, "wxTemplateId");
            return (Criteria) this;
        }

        public Criteria andWxTemplateIdLessThan(String value) {
            addCriterion("wx_template_id <", value, "wxTemplateId");
            return (Criteria) this;
        }

        public Criteria andWxTemplateIdLessThanOrEqualTo(String value) {
            addCriterion("wx_template_id <=", value, "wxTemplateId");
            return (Criteria) this;
        }

        public Criteria andWxTemplateIdLike(String value) {
            addCriterion("wx_template_id like", value, "wxTemplateId");
            return (Criteria) this;
        }

        public Criteria andWxTemplateIdNotLike(String value) {
            addCriterion("wx_template_id not like", value, "wxTemplateId");
            return (Criteria) this;
        }

        public Criteria andWxTemplateIdIn(List<String> values) {
            addCriterion("wx_template_id in", values, "wxTemplateId");
            return (Criteria) this;
        }

        public Criteria andWxTemplateIdNotIn(List<String> values) {
            addCriterion("wx_template_id not in", values, "wxTemplateId");
            return (Criteria) this;
        }

        public Criteria andWxTemplateIdBetween(String value1, String value2) {
            addCriterion("wx_template_id between", value1, value2, "wxTemplateId");
            return (Criteria) this;
        }

        public Criteria andWxTemplateIdNotBetween(String value1, String value2) {
            addCriterion("wx_template_id not between", value1, value2, "wxTemplateId");
            return (Criteria) this;
        }

        public Criteria andSupportDeliveryIsNull() {
            addCriterion("support_delivery is null");
            return (Criteria) this;
        }

        public Criteria andSupportDeliveryIsNotNull() {
            addCriterion("support_delivery is not null");
            return (Criteria) this;
        }

        public Criteria andSupportDeliveryEqualTo(Integer value) {
            addCriterion("support_delivery =", value, "supportDelivery");
            return (Criteria) this;
        }

        public Criteria andSupportDeliveryNotEqualTo(Integer value) {
            addCriterion("support_delivery <>", value, "supportDelivery");
            return (Criteria) this;
        }

        public Criteria andSupportDeliveryGreaterThan(Integer value) {
            addCriterion("support_delivery >", value, "supportDelivery");
            return (Criteria) this;
        }

        public Criteria andSupportDeliveryGreaterThanOrEqualTo(Integer value) {
            addCriterion("support_delivery >=", value, "supportDelivery");
            return (Criteria) this;
        }

        public Criteria andSupportDeliveryLessThan(Integer value) {
            addCriterion("support_delivery <", value, "supportDelivery");
            return (Criteria) this;
        }

        public Criteria andSupportDeliveryLessThanOrEqualTo(Integer value) {
            addCriterion("support_delivery <=", value, "supportDelivery");
            return (Criteria) this;
        }

        public Criteria andSupportDeliveryIn(List<Integer> values) {
            addCriterion("support_delivery in", values, "supportDelivery");
            return (Criteria) this;
        }

        public Criteria andSupportDeliveryNotIn(List<Integer> values) {
            addCriterion("support_delivery not in", values, "supportDelivery");
            return (Criteria) this;
        }

        public Criteria andSupportDeliveryBetween(Integer value1, Integer value2) {
            addCriterion("support_delivery between", value1, value2, "supportDelivery");
            return (Criteria) this;
        }

        public Criteria andSupportDeliveryNotBetween(Integer value1, Integer value2) {
            addCriterion("support_delivery not between", value1, value2, "supportDelivery");
            return (Criteria) this;
        }

        public Criteria andFreightAmountIsNull() {
            addCriterion("freight_amount is null");
            return (Criteria) this;
        }

        public Criteria andFreightAmountIsNotNull() {
            addCriterion("freight_amount is not null");
            return (Criteria) this;
        }

        public Criteria andFreightAmountEqualTo(BigDecimal value) {
            addCriterion("freight_amount =", value, "freightAmount");
            return (Criteria) this;
        }

        public Criteria andFreightAmountNotEqualTo(BigDecimal value) {
            addCriterion("freight_amount <>", value, "freightAmount");
            return (Criteria) this;
        }

        public Criteria andFreightAmountGreaterThan(BigDecimal value) {
            addCriterion("freight_amount >", value, "freightAmount");
            return (Criteria) this;
        }

        public Criteria andFreightAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("freight_amount >=", value, "freightAmount");
            return (Criteria) this;
        }

        public Criteria andFreightAmountLessThan(BigDecimal value) {
            addCriterion("freight_amount <", value, "freightAmount");
            return (Criteria) this;
        }

        public Criteria andFreightAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("freight_amount <=", value, "freightAmount");
            return (Criteria) this;
        }

        public Criteria andFreightAmountIn(List<BigDecimal> values) {
            addCriterion("freight_amount in", values, "freightAmount");
            return (Criteria) this;
        }

        public Criteria andFreightAmountNotIn(List<BigDecimal> values) {
            addCriterion("freight_amount not in", values, "freightAmount");
            return (Criteria) this;
        }

        public Criteria andFreightAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("freight_amount between", value1, value2, "freightAmount");
            return (Criteria) this;
        }

        public Criteria andFreightAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("freight_amount not between", value1, value2, "freightAmount");
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