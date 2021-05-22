package com.macro.mall.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UmsUploadFileExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UmsUploadFileExample() {
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

        public Criteria andShareAdminIdIsNull() {
            addCriterion("share_admin_id is null");
            return (Criteria) this;
        }

        public Criteria andShareAdminIdIsNotNull() {
            addCriterion("share_admin_id is not null");
            return (Criteria) this;
        }

        public Criteria andShareAdminIdEqualTo(Long value) {
            addCriterion("share_admin_id =", value, "shareAdminId");
            return (Criteria) this;
        }

        public Criteria andShareAdminIdNotEqualTo(Long value) {
            addCriterion("share_admin_id <>", value, "shareAdminId");
            return (Criteria) this;
        }

        public Criteria andShareAdminIdGreaterThan(Long value) {
            addCriterion("share_admin_id >", value, "shareAdminId");
            return (Criteria) this;
        }

        public Criteria andShareAdminIdGreaterThanOrEqualTo(Long value) {
            addCriterion("share_admin_id >=", value, "shareAdminId");
            return (Criteria) this;
        }

        public Criteria andShareAdminIdLessThan(Long value) {
            addCriterion("share_admin_id <", value, "shareAdminId");
            return (Criteria) this;
        }

        public Criteria andShareAdminIdLessThanOrEqualTo(Long value) {
            addCriterion("share_admin_id <=", value, "shareAdminId");
            return (Criteria) this;
        }

        public Criteria andShareAdminIdIn(List<Long> values) {
            addCriterion("share_admin_id in", values, "shareAdminId");
            return (Criteria) this;
        }

        public Criteria andShareAdminIdNotIn(List<Long> values) {
            addCriterion("share_admin_id not in", values, "shareAdminId");
            return (Criteria) this;
        }

        public Criteria andShareAdminIdBetween(Long value1, Long value2) {
            addCriterion("share_admin_id between", value1, value2, "shareAdminId");
            return (Criteria) this;
        }

        public Criteria andShareAdminIdNotBetween(Long value1, Long value2) {
            addCriterion("share_admin_id not between", value1, value2, "shareAdminId");
            return (Criteria) this;
        }

        public Criteria andMobileNoIsNull() {
            addCriterion("mobile_no is null");
            return (Criteria) this;
        }

        public Criteria andMobileNoIsNotNull() {
            addCriterion("mobile_no is not null");
            return (Criteria) this;
        }

        public Criteria andMobileNoEqualTo(String value) {
            addCriterion("mobile_no =", value, "mobileNo");
            return (Criteria) this;
        }

        public Criteria andMobileNoNotEqualTo(String value) {
            addCriterion("mobile_no <>", value, "mobileNo");
            return (Criteria) this;
        }

        public Criteria andMobileNoGreaterThan(String value) {
            addCriterion("mobile_no >", value, "mobileNo");
            return (Criteria) this;
        }

        public Criteria andMobileNoGreaterThanOrEqualTo(String value) {
            addCriterion("mobile_no >=", value, "mobileNo");
            return (Criteria) this;
        }

        public Criteria andMobileNoLessThan(String value) {
            addCriterion("mobile_no <", value, "mobileNo");
            return (Criteria) this;
        }

        public Criteria andMobileNoLessThanOrEqualTo(String value) {
            addCriterion("mobile_no <=", value, "mobileNo");
            return (Criteria) this;
        }

        public Criteria andMobileNoLike(String value) {
            addCriterion("mobile_no like", value, "mobileNo");
            return (Criteria) this;
        }

        public Criteria andMobileNoNotLike(String value) {
            addCriterion("mobile_no not like", value, "mobileNo");
            return (Criteria) this;
        }

        public Criteria andMobileNoIn(List<String> values) {
            addCriterion("mobile_no in", values, "mobileNo");
            return (Criteria) this;
        }

        public Criteria andMobileNoNotIn(List<String> values) {
            addCriterion("mobile_no not in", values, "mobileNo");
            return (Criteria) this;
        }

        public Criteria andMobileNoBetween(String value1, String value2) {
            addCriterion("mobile_no between", value1, value2, "mobileNo");
            return (Criteria) this;
        }

        public Criteria andMobileNoNotBetween(String value1, String value2) {
            addCriterion("mobile_no not between", value1, value2, "mobileNo");
            return (Criteria) this;
        }

        public Criteria andWechatNoIsNull() {
            addCriterion("wechat_no is null");
            return (Criteria) this;
        }

        public Criteria andWechatNoIsNotNull() {
            addCriterion("wechat_no is not null");
            return (Criteria) this;
        }

        public Criteria andWechatNoEqualTo(String value) {
            addCriterion("wechat_no =", value, "wechatNo");
            return (Criteria) this;
        }

        public Criteria andWechatNoNotEqualTo(String value) {
            addCriterion("wechat_no <>", value, "wechatNo");
            return (Criteria) this;
        }

        public Criteria andWechatNoGreaterThan(String value) {
            addCriterion("wechat_no >", value, "wechatNo");
            return (Criteria) this;
        }

        public Criteria andWechatNoGreaterThanOrEqualTo(String value) {
            addCriterion("wechat_no >=", value, "wechatNo");
            return (Criteria) this;
        }

        public Criteria andWechatNoLessThan(String value) {
            addCriterion("wechat_no <", value, "wechatNo");
            return (Criteria) this;
        }

        public Criteria andWechatNoLessThanOrEqualTo(String value) {
            addCriterion("wechat_no <=", value, "wechatNo");
            return (Criteria) this;
        }

        public Criteria andWechatNoLike(String value) {
            addCriterion("wechat_no like", value, "wechatNo");
            return (Criteria) this;
        }

        public Criteria andWechatNoNotLike(String value) {
            addCriterion("wechat_no not like", value, "wechatNo");
            return (Criteria) this;
        }

        public Criteria andWechatNoIn(List<String> values) {
            addCriterion("wechat_no in", values, "wechatNo");
            return (Criteria) this;
        }

        public Criteria andWechatNoNotIn(List<String> values) {
            addCriterion("wechat_no not in", values, "wechatNo");
            return (Criteria) this;
        }

        public Criteria andWechatNoBetween(String value1, String value2) {
            addCriterion("wechat_no between", value1, value2, "wechatNo");
            return (Criteria) this;
        }

        public Criteria andWechatNoNotBetween(String value1, String value2) {
            addCriterion("wechat_no not between", value1, value2, "wechatNo");
            return (Criteria) this;
        }

        public Criteria andCardNoIsNull() {
            addCriterion("card_no is null");
            return (Criteria) this;
        }

        public Criteria andCardNoIsNotNull() {
            addCriterion("card_no is not null");
            return (Criteria) this;
        }

        public Criteria andCardNoEqualTo(String value) {
            addCriterion("card_no =", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoNotEqualTo(String value) {
            addCriterion("card_no <>", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoGreaterThan(String value) {
            addCriterion("card_no >", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoGreaterThanOrEqualTo(String value) {
            addCriterion("card_no >=", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoLessThan(String value) {
            addCriterion("card_no <", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoLessThanOrEqualTo(String value) {
            addCriterion("card_no <=", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoLike(String value) {
            addCriterion("card_no like", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoNotLike(String value) {
            addCriterion("card_no not like", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoIn(List<String> values) {
            addCriterion("card_no in", values, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoNotIn(List<String> values) {
            addCriterion("card_no not in", values, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoBetween(String value1, String value2) {
            addCriterion("card_no between", value1, value2, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoNotBetween(String value1, String value2) {
            addCriterion("card_no not between", value1, value2, "cardNo");
            return (Criteria) this;
        }

        public Criteria andBankNameIsNull() {
            addCriterion("bank_name is null");
            return (Criteria) this;
        }

        public Criteria andBankNameIsNotNull() {
            addCriterion("bank_name is not null");
            return (Criteria) this;
        }

        public Criteria andBankNameEqualTo(String value) {
            addCriterion("bank_name =", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotEqualTo(String value) {
            addCriterion("bank_name <>", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameGreaterThan(String value) {
            addCriterion("bank_name >", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameGreaterThanOrEqualTo(String value) {
            addCriterion("bank_name >=", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameLessThan(String value) {
            addCriterion("bank_name <", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameLessThanOrEqualTo(String value) {
            addCriterion("bank_name <=", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameLike(String value) {
            addCriterion("bank_name like", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotLike(String value) {
            addCriterion("bank_name not like", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameIn(List<String> values) {
            addCriterion("bank_name in", values, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotIn(List<String> values) {
            addCriterion("bank_name not in", values, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameBetween(String value1, String value2) {
            addCriterion("bank_name between", value1, value2, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotBetween(String value1, String value2) {
            addCriterion("bank_name not between", value1, value2, "bankName");
            return (Criteria) this;
        }

        public Criteria andFileListIdCardsIsNull() {
            addCriterion("file_list_id_cards is null");
            return (Criteria) this;
        }

        public Criteria andFileListIdCardsIsNotNull() {
            addCriterion("file_list_id_cards is not null");
            return (Criteria) this;
        }

        public Criteria andFileListIdCardsEqualTo(String value) {
            addCriterion("file_list_id_cards =", value, "fileListIdCards");
            return (Criteria) this;
        }

        public Criteria andFileListIdCardsNotEqualTo(String value) {
            addCriterion("file_list_id_cards <>", value, "fileListIdCards");
            return (Criteria) this;
        }

        public Criteria andFileListIdCardsGreaterThan(String value) {
            addCriterion("file_list_id_cards >", value, "fileListIdCards");
            return (Criteria) this;
        }

        public Criteria andFileListIdCardsGreaterThanOrEqualTo(String value) {
            addCriterion("file_list_id_cards >=", value, "fileListIdCards");
            return (Criteria) this;
        }

        public Criteria andFileListIdCardsLessThan(String value) {
            addCriterion("file_list_id_cards <", value, "fileListIdCards");
            return (Criteria) this;
        }

        public Criteria andFileListIdCardsLessThanOrEqualTo(String value) {
            addCriterion("file_list_id_cards <=", value, "fileListIdCards");
            return (Criteria) this;
        }

        public Criteria andFileListIdCardsLike(String value) {
            addCriterion("file_list_id_cards like", value, "fileListIdCards");
            return (Criteria) this;
        }

        public Criteria andFileListIdCardsNotLike(String value) {
            addCriterion("file_list_id_cards not like", value, "fileListIdCards");
            return (Criteria) this;
        }

        public Criteria andFileListIdCardsIn(List<String> values) {
            addCriterion("file_list_id_cards in", values, "fileListIdCards");
            return (Criteria) this;
        }

        public Criteria andFileListIdCardsNotIn(List<String> values) {
            addCriterion("file_list_id_cards not in", values, "fileListIdCards");
            return (Criteria) this;
        }

        public Criteria andFileListIdCardsBetween(String value1, String value2) {
            addCriterion("file_list_id_cards between", value1, value2, "fileListIdCards");
            return (Criteria) this;
        }

        public Criteria andFileListIdCardsNotBetween(String value1, String value2) {
            addCriterion("file_list_id_cards not between", value1, value2, "fileListIdCards");
            return (Criteria) this;
        }

        public Criteria andFileListBusinessIsNull() {
            addCriterion("file_list_business is null");
            return (Criteria) this;
        }

        public Criteria andFileListBusinessIsNotNull() {
            addCriterion("file_list_business is not null");
            return (Criteria) this;
        }

        public Criteria andFileListBusinessEqualTo(String value) {
            addCriterion("file_list_business =", value, "fileListBusiness");
            return (Criteria) this;
        }

        public Criteria andFileListBusinessNotEqualTo(String value) {
            addCriterion("file_list_business <>", value, "fileListBusiness");
            return (Criteria) this;
        }

        public Criteria andFileListBusinessGreaterThan(String value) {
            addCriterion("file_list_business >", value, "fileListBusiness");
            return (Criteria) this;
        }

        public Criteria andFileListBusinessGreaterThanOrEqualTo(String value) {
            addCriterion("file_list_business >=", value, "fileListBusiness");
            return (Criteria) this;
        }

        public Criteria andFileListBusinessLessThan(String value) {
            addCriterion("file_list_business <", value, "fileListBusiness");
            return (Criteria) this;
        }

        public Criteria andFileListBusinessLessThanOrEqualTo(String value) {
            addCriterion("file_list_business <=", value, "fileListBusiness");
            return (Criteria) this;
        }

        public Criteria andFileListBusinessLike(String value) {
            addCriterion("file_list_business like", value, "fileListBusiness");
            return (Criteria) this;
        }

        public Criteria andFileListBusinessNotLike(String value) {
            addCriterion("file_list_business not like", value, "fileListBusiness");
            return (Criteria) this;
        }

        public Criteria andFileListBusinessIn(List<String> values) {
            addCriterion("file_list_business in", values, "fileListBusiness");
            return (Criteria) this;
        }

        public Criteria andFileListBusinessNotIn(List<String> values) {
            addCriterion("file_list_business not in", values, "fileListBusiness");
            return (Criteria) this;
        }

        public Criteria andFileListBusinessBetween(String value1, String value2) {
            addCriterion("file_list_business between", value1, value2, "fileListBusiness");
            return (Criteria) this;
        }

        public Criteria andFileListBusinessNotBetween(String value1, String value2) {
            addCriterion("file_list_business not between", value1, value2, "fileListBusiness");
            return (Criteria) this;
        }

        public Criteria andFileListPermitIsNull() {
            addCriterion("file_list_permit is null");
            return (Criteria) this;
        }

        public Criteria andFileListPermitIsNotNull() {
            addCriterion("file_list_permit is not null");
            return (Criteria) this;
        }

        public Criteria andFileListPermitEqualTo(String value) {
            addCriterion("file_list_permit =", value, "fileListPermit");
            return (Criteria) this;
        }

        public Criteria andFileListPermitNotEqualTo(String value) {
            addCriterion("file_list_permit <>", value, "fileListPermit");
            return (Criteria) this;
        }

        public Criteria andFileListPermitGreaterThan(String value) {
            addCriterion("file_list_permit >", value, "fileListPermit");
            return (Criteria) this;
        }

        public Criteria andFileListPermitGreaterThanOrEqualTo(String value) {
            addCriterion("file_list_permit >=", value, "fileListPermit");
            return (Criteria) this;
        }

        public Criteria andFileListPermitLessThan(String value) {
            addCriterion("file_list_permit <", value, "fileListPermit");
            return (Criteria) this;
        }

        public Criteria andFileListPermitLessThanOrEqualTo(String value) {
            addCriterion("file_list_permit <=", value, "fileListPermit");
            return (Criteria) this;
        }

        public Criteria andFileListPermitLike(String value) {
            addCriterion("file_list_permit like", value, "fileListPermit");
            return (Criteria) this;
        }

        public Criteria andFileListPermitNotLike(String value) {
            addCriterion("file_list_permit not like", value, "fileListPermit");
            return (Criteria) this;
        }

        public Criteria andFileListPermitIn(List<String> values) {
            addCriterion("file_list_permit in", values, "fileListPermit");
            return (Criteria) this;
        }

        public Criteria andFileListPermitNotIn(List<String> values) {
            addCriterion("file_list_permit not in", values, "fileListPermit");
            return (Criteria) this;
        }

        public Criteria andFileListPermitBetween(String value1, String value2) {
            addCriterion("file_list_permit between", value1, value2, "fileListPermit");
            return (Criteria) this;
        }

        public Criteria andFileListPermitNotBetween(String value1, String value2) {
            addCriterion("file_list_permit not between", value1, value2, "fileListPermit");
            return (Criteria) this;
        }

        public Criteria andCreateStatusIsNull() {
            addCriterion("create_status is null");
            return (Criteria) this;
        }

        public Criteria andCreateStatusIsNotNull() {
            addCriterion("create_status is not null");
            return (Criteria) this;
        }

        public Criteria andCreateStatusEqualTo(Integer value) {
            addCriterion("create_status =", value, "createStatus");
            return (Criteria) this;
        }

        public Criteria andCreateStatusNotEqualTo(Integer value) {
            addCriterion("create_status <>", value, "createStatus");
            return (Criteria) this;
        }

        public Criteria andCreateStatusGreaterThan(Integer value) {
            addCriterion("create_status >", value, "createStatus");
            return (Criteria) this;
        }

        public Criteria andCreateStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_status >=", value, "createStatus");
            return (Criteria) this;
        }

        public Criteria andCreateStatusLessThan(Integer value) {
            addCriterion("create_status <", value, "createStatus");
            return (Criteria) this;
        }

        public Criteria andCreateStatusLessThanOrEqualTo(Integer value) {
            addCriterion("create_status <=", value, "createStatus");
            return (Criteria) this;
        }

        public Criteria andCreateStatusIn(List<Integer> values) {
            addCriterion("create_status in", values, "createStatus");
            return (Criteria) this;
        }

        public Criteria andCreateStatusNotIn(List<Integer> values) {
            addCriterion("create_status not in", values, "createStatus");
            return (Criteria) this;
        }

        public Criteria andCreateStatusBetween(Integer value1, Integer value2) {
            addCriterion("create_status between", value1, value2, "createStatus");
            return (Criteria) this;
        }

        public Criteria andCreateStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("create_status not between", value1, value2, "createStatus");
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

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
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