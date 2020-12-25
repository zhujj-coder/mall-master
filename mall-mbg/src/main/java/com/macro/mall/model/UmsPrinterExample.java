package com.macro.mall.model;

import java.util.ArrayList;
import java.util.List;

public class UmsPrinterExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UmsPrinterExample() {
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

        public Criteria andAdminIdIsNull() {
            addCriterion("admin_id is null");
            return (Criteria) this;
        }

        public Criteria andAdminIdIsNotNull() {
            addCriterion("admin_id is not null");
            return (Criteria) this;
        }

        public Criteria andAdminIdEqualTo(Long value) {
            addCriterion("admin_id =", value, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdNotEqualTo(Long value) {
            addCriterion("admin_id <>", value, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdGreaterThan(Long value) {
            addCriterion("admin_id >", value, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdGreaterThanOrEqualTo(Long value) {
            addCriterion("admin_id >=", value, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdLessThan(Long value) {
            addCriterion("admin_id <", value, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdLessThanOrEqualTo(Long value) {
            addCriterion("admin_id <=", value, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdIn(List<Long> values) {
            addCriterion("admin_id in", values, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdNotIn(List<Long> values) {
            addCriterion("admin_id not in", values, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdBetween(Long value1, Long value2) {
            addCriterion("admin_id between", value1, value2, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdNotBetween(Long value1, Long value2) {
            addCriterion("admin_id not between", value1, value2, "adminId");
            return (Criteria) this;
        }

        public Criteria andPrinterNameIsNull() {
            addCriterion("printer_name is null");
            return (Criteria) this;
        }

        public Criteria andPrinterNameIsNotNull() {
            addCriterion("printer_name is not null");
            return (Criteria) this;
        }

        public Criteria andPrinterNameEqualTo(String value) {
            addCriterion("printer_name =", value, "printerName");
            return (Criteria) this;
        }

        public Criteria andPrinterNameNotEqualTo(String value) {
            addCriterion("printer_name <>", value, "printerName");
            return (Criteria) this;
        }

        public Criteria andPrinterNameGreaterThan(String value) {
            addCriterion("printer_name >", value, "printerName");
            return (Criteria) this;
        }

        public Criteria andPrinterNameGreaterThanOrEqualTo(String value) {
            addCriterion("printer_name >=", value, "printerName");
            return (Criteria) this;
        }

        public Criteria andPrinterNameLessThan(String value) {
            addCriterion("printer_name <", value, "printerName");
            return (Criteria) this;
        }

        public Criteria andPrinterNameLessThanOrEqualTo(String value) {
            addCriterion("printer_name <=", value, "printerName");
            return (Criteria) this;
        }

        public Criteria andPrinterNameLike(String value) {
            addCriterion("printer_name like", value, "printerName");
            return (Criteria) this;
        }

        public Criteria andPrinterNameNotLike(String value) {
            addCriterion("printer_name not like", value, "printerName");
            return (Criteria) this;
        }

        public Criteria andPrinterNameIn(List<String> values) {
            addCriterion("printer_name in", values, "printerName");
            return (Criteria) this;
        }

        public Criteria andPrinterNameNotIn(List<String> values) {
            addCriterion("printer_name not in", values, "printerName");
            return (Criteria) this;
        }

        public Criteria andPrinterNameBetween(String value1, String value2) {
            addCriterion("printer_name between", value1, value2, "printerName");
            return (Criteria) this;
        }

        public Criteria andPrinterNameNotBetween(String value1, String value2) {
            addCriterion("printer_name not between", value1, value2, "printerName");
            return (Criteria) this;
        }

        public Criteria andPrinterSnIsNull() {
            addCriterion("printer_sn is null");
            return (Criteria) this;
        }

        public Criteria andPrinterSnIsNotNull() {
            addCriterion("printer_sn is not null");
            return (Criteria) this;
        }

        public Criteria andPrinterSnEqualTo(String value) {
            addCriterion("printer_sn =", value, "printerSn");
            return (Criteria) this;
        }

        public Criteria andPrinterSnNotEqualTo(String value) {
            addCriterion("printer_sn <>", value, "printerSn");
            return (Criteria) this;
        }

        public Criteria andPrinterSnGreaterThan(String value) {
            addCriterion("printer_sn >", value, "printerSn");
            return (Criteria) this;
        }

        public Criteria andPrinterSnGreaterThanOrEqualTo(String value) {
            addCriterion("printer_sn >=", value, "printerSn");
            return (Criteria) this;
        }

        public Criteria andPrinterSnLessThan(String value) {
            addCriterion("printer_sn <", value, "printerSn");
            return (Criteria) this;
        }

        public Criteria andPrinterSnLessThanOrEqualTo(String value) {
            addCriterion("printer_sn <=", value, "printerSn");
            return (Criteria) this;
        }

        public Criteria andPrinterSnLike(String value) {
            addCriterion("printer_sn like", value, "printerSn");
            return (Criteria) this;
        }

        public Criteria andPrinterSnNotLike(String value) {
            addCriterion("printer_sn not like", value, "printerSn");
            return (Criteria) this;
        }

        public Criteria andPrinterSnIn(List<String> values) {
            addCriterion("printer_sn in", values, "printerSn");
            return (Criteria) this;
        }

        public Criteria andPrinterSnNotIn(List<String> values) {
            addCriterion("printer_sn not in", values, "printerSn");
            return (Criteria) this;
        }

        public Criteria andPrinterSnBetween(String value1, String value2) {
            addCriterion("printer_sn between", value1, value2, "printerSn");
            return (Criteria) this;
        }

        public Criteria andPrinterSnNotBetween(String value1, String value2) {
            addCriterion("printer_sn not between", value1, value2, "printerSn");
            return (Criteria) this;
        }

        public Criteria andPrinterVoiceTypeIsNull() {
            addCriterion("printer_voice_type is null");
            return (Criteria) this;
        }

        public Criteria andPrinterVoiceTypeIsNotNull() {
            addCriterion("printer_voice_type is not null");
            return (Criteria) this;
        }

        public Criteria andPrinterVoiceTypeEqualTo(Integer value) {
            addCriterion("printer_voice_type =", value, "printerVoiceType");
            return (Criteria) this;
        }

        public Criteria andPrinterVoiceTypeNotEqualTo(Integer value) {
            addCriterion("printer_voice_type <>", value, "printerVoiceType");
            return (Criteria) this;
        }

        public Criteria andPrinterVoiceTypeGreaterThan(Integer value) {
            addCriterion("printer_voice_type >", value, "printerVoiceType");
            return (Criteria) this;
        }

        public Criteria andPrinterVoiceTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("printer_voice_type >=", value, "printerVoiceType");
            return (Criteria) this;
        }

        public Criteria andPrinterVoiceTypeLessThan(Integer value) {
            addCriterion("printer_voice_type <", value, "printerVoiceType");
            return (Criteria) this;
        }

        public Criteria andPrinterVoiceTypeLessThanOrEqualTo(Integer value) {
            addCriterion("printer_voice_type <=", value, "printerVoiceType");
            return (Criteria) this;
        }

        public Criteria andPrinterVoiceTypeIn(List<Integer> values) {
            addCriterion("printer_voice_type in", values, "printerVoiceType");
            return (Criteria) this;
        }

        public Criteria andPrinterVoiceTypeNotIn(List<Integer> values) {
            addCriterion("printer_voice_type not in", values, "printerVoiceType");
            return (Criteria) this;
        }

        public Criteria andPrinterVoiceTypeBetween(Integer value1, Integer value2) {
            addCriterion("printer_voice_type between", value1, value2, "printerVoiceType");
            return (Criteria) this;
        }

        public Criteria andPrinterVoiceTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("printer_voice_type not between", value1, value2, "printerVoiceType");
            return (Criteria) this;
        }

        public Criteria andPrinterStatusIsNull() {
            addCriterion("printer_status is null");
            return (Criteria) this;
        }

        public Criteria andPrinterStatusIsNotNull() {
            addCriterion("printer_status is not null");
            return (Criteria) this;
        }

        public Criteria andPrinterStatusEqualTo(Integer value) {
            addCriterion("printer_status =", value, "printerStatus");
            return (Criteria) this;
        }

        public Criteria andPrinterStatusNotEqualTo(Integer value) {
            addCriterion("printer_status <>", value, "printerStatus");
            return (Criteria) this;
        }

        public Criteria andPrinterStatusGreaterThan(Integer value) {
            addCriterion("printer_status >", value, "printerStatus");
            return (Criteria) this;
        }

        public Criteria andPrinterStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("printer_status >=", value, "printerStatus");
            return (Criteria) this;
        }

        public Criteria andPrinterStatusLessThan(Integer value) {
            addCriterion("printer_status <", value, "printerStatus");
            return (Criteria) this;
        }

        public Criteria andPrinterStatusLessThanOrEqualTo(Integer value) {
            addCriterion("printer_status <=", value, "printerStatus");
            return (Criteria) this;
        }

        public Criteria andPrinterStatusIn(List<Integer> values) {
            addCriterion("printer_status in", values, "printerStatus");
            return (Criteria) this;
        }

        public Criteria andPrinterStatusNotIn(List<Integer> values) {
            addCriterion("printer_status not in", values, "printerStatus");
            return (Criteria) this;
        }

        public Criteria andPrinterStatusBetween(Integer value1, Integer value2) {
            addCriterion("printer_status between", value1, value2, "printerStatus");
            return (Criteria) this;
        }

        public Criteria andPrinterStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("printer_status not between", value1, value2, "printerStatus");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andPrinterQrUrlIsNull() {
            addCriterion("printer_qr_url is null");
            return (Criteria) this;
        }

        public Criteria andPrinterQrUrlIsNotNull() {
            addCriterion("printer_qr_url is not null");
            return (Criteria) this;
        }

        public Criteria andPrinterQrUrlEqualTo(String value) {
            addCriterion("printer_qr_url =", value, "printerQrUrl");
            return (Criteria) this;
        }

        public Criteria andPrinterQrUrlNotEqualTo(String value) {
            addCriterion("printer_qr_url <>", value, "printerQrUrl");
            return (Criteria) this;
        }

        public Criteria andPrinterQrUrlGreaterThan(String value) {
            addCriterion("printer_qr_url >", value, "printerQrUrl");
            return (Criteria) this;
        }

        public Criteria andPrinterQrUrlGreaterThanOrEqualTo(String value) {
            addCriterion("printer_qr_url >=", value, "printerQrUrl");
            return (Criteria) this;
        }

        public Criteria andPrinterQrUrlLessThan(String value) {
            addCriterion("printer_qr_url <", value, "printerQrUrl");
            return (Criteria) this;
        }

        public Criteria andPrinterQrUrlLessThanOrEqualTo(String value) {
            addCriterion("printer_qr_url <=", value, "printerQrUrl");
            return (Criteria) this;
        }

        public Criteria andPrinterQrUrlLike(String value) {
            addCriterion("printer_qr_url like", value, "printerQrUrl");
            return (Criteria) this;
        }

        public Criteria andPrinterQrUrlNotLike(String value) {
            addCriterion("printer_qr_url not like", value, "printerQrUrl");
            return (Criteria) this;
        }

        public Criteria andPrinterQrUrlIn(List<String> values) {
            addCriterion("printer_qr_url in", values, "printerQrUrl");
            return (Criteria) this;
        }

        public Criteria andPrinterQrUrlNotIn(List<String> values) {
            addCriterion("printer_qr_url not in", values, "printerQrUrl");
            return (Criteria) this;
        }

        public Criteria andPrinterQrUrlBetween(String value1, String value2) {
            addCriterion("printer_qr_url between", value1, value2, "printerQrUrl");
            return (Criteria) this;
        }

        public Criteria andPrinterQrUrlNotBetween(String value1, String value2) {
            addCriterion("printer_qr_url not between", value1, value2, "printerQrUrl");
            return (Criteria) this;
        }

        public Criteria andCardnoIsNull() {
            addCriterion("cardNo is null");
            return (Criteria) this;
        }

        public Criteria andCardnoIsNotNull() {
            addCriterion("cardNo is not null");
            return (Criteria) this;
        }

        public Criteria andCardnoEqualTo(String value) {
            addCriterion("cardNo =", value, "cardno");
            return (Criteria) this;
        }

        public Criteria andCardnoNotEqualTo(String value) {
            addCriterion("cardNo <>", value, "cardno");
            return (Criteria) this;
        }

        public Criteria andCardnoGreaterThan(String value) {
            addCriterion("cardNo >", value, "cardno");
            return (Criteria) this;
        }

        public Criteria andCardnoGreaterThanOrEqualTo(String value) {
            addCriterion("cardNo >=", value, "cardno");
            return (Criteria) this;
        }

        public Criteria andCardnoLessThan(String value) {
            addCriterion("cardNo <", value, "cardno");
            return (Criteria) this;
        }

        public Criteria andCardnoLessThanOrEqualTo(String value) {
            addCriterion("cardNo <=", value, "cardno");
            return (Criteria) this;
        }

        public Criteria andCardnoLike(String value) {
            addCriterion("cardNo like", value, "cardno");
            return (Criteria) this;
        }

        public Criteria andCardnoNotLike(String value) {
            addCriterion("cardNo not like", value, "cardno");
            return (Criteria) this;
        }

        public Criteria andCardnoIn(List<String> values) {
            addCriterion("cardNo in", values, "cardno");
            return (Criteria) this;
        }

        public Criteria andCardnoNotIn(List<String> values) {
            addCriterion("cardNo not in", values, "cardno");
            return (Criteria) this;
        }

        public Criteria andCardnoBetween(String value1, String value2) {
            addCriterion("cardNo between", value1, value2, "cardno");
            return (Criteria) this;
        }

        public Criteria andCardnoNotBetween(String value1, String value2) {
            addCriterion("cardNo not between", value1, value2, "cardno");
            return (Criteria) this;
        }

        public Criteria andCopiesIsNull() {
            addCriterion("copies is null");
            return (Criteria) this;
        }

        public Criteria andCopiesIsNotNull() {
            addCriterion("copies is not null");
            return (Criteria) this;
        }

        public Criteria andCopiesEqualTo(Integer value) {
            addCriterion("copies =", value, "copies");
            return (Criteria) this;
        }

        public Criteria andCopiesNotEqualTo(Integer value) {
            addCriterion("copies <>", value, "copies");
            return (Criteria) this;
        }

        public Criteria andCopiesGreaterThan(Integer value) {
            addCriterion("copies >", value, "copies");
            return (Criteria) this;
        }

        public Criteria andCopiesGreaterThanOrEqualTo(Integer value) {
            addCriterion("copies >=", value, "copies");
            return (Criteria) this;
        }

        public Criteria andCopiesLessThan(Integer value) {
            addCriterion("copies <", value, "copies");
            return (Criteria) this;
        }

        public Criteria andCopiesLessThanOrEqualTo(Integer value) {
            addCriterion("copies <=", value, "copies");
            return (Criteria) this;
        }

        public Criteria andCopiesIn(List<Integer> values) {
            addCriterion("copies in", values, "copies");
            return (Criteria) this;
        }

        public Criteria andCopiesNotIn(List<Integer> values) {
            addCriterion("copies not in", values, "copies");
            return (Criteria) this;
        }

        public Criteria andCopiesBetween(Integer value1, Integer value2) {
            addCriterion("copies between", value1, value2, "copies");
            return (Criteria) this;
        }

        public Criteria andCopiesNotBetween(Integer value1, Integer value2) {
            addCriterion("copies not between", value1, value2, "copies");
            return (Criteria) this;
        }

        public Criteria andPrinterQrTitleIsNull() {
            addCriterion("printer_qr_title is null");
            return (Criteria) this;
        }

        public Criteria andPrinterQrTitleIsNotNull() {
            addCriterion("printer_qr_title is not null");
            return (Criteria) this;
        }

        public Criteria andPrinterQrTitleEqualTo(String value) {
            addCriterion("printer_qr_title =", value, "printerQrTitle");
            return (Criteria) this;
        }

        public Criteria andPrinterQrTitleNotEqualTo(String value) {
            addCriterion("printer_qr_title <>", value, "printerQrTitle");
            return (Criteria) this;
        }

        public Criteria andPrinterQrTitleGreaterThan(String value) {
            addCriterion("printer_qr_title >", value, "printerQrTitle");
            return (Criteria) this;
        }

        public Criteria andPrinterQrTitleGreaterThanOrEqualTo(String value) {
            addCriterion("printer_qr_title >=", value, "printerQrTitle");
            return (Criteria) this;
        }

        public Criteria andPrinterQrTitleLessThan(String value) {
            addCriterion("printer_qr_title <", value, "printerQrTitle");
            return (Criteria) this;
        }

        public Criteria andPrinterQrTitleLessThanOrEqualTo(String value) {
            addCriterion("printer_qr_title <=", value, "printerQrTitle");
            return (Criteria) this;
        }

        public Criteria andPrinterQrTitleLike(String value) {
            addCriterion("printer_qr_title like", value, "printerQrTitle");
            return (Criteria) this;
        }

        public Criteria andPrinterQrTitleNotLike(String value) {
            addCriterion("printer_qr_title not like", value, "printerQrTitle");
            return (Criteria) this;
        }

        public Criteria andPrinterQrTitleIn(List<String> values) {
            addCriterion("printer_qr_title in", values, "printerQrTitle");
            return (Criteria) this;
        }

        public Criteria andPrinterQrTitleNotIn(List<String> values) {
            addCriterion("printer_qr_title not in", values, "printerQrTitle");
            return (Criteria) this;
        }

        public Criteria andPrinterQrTitleBetween(String value1, String value2) {
            addCriterion("printer_qr_title between", value1, value2, "printerQrTitle");
            return (Criteria) this;
        }

        public Criteria andPrinterQrTitleNotBetween(String value1, String value2) {
            addCriterion("printer_qr_title not between", value1, value2, "printerQrTitle");
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