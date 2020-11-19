alter table pms_product_category add column admin_id bigint(20) comment '商户用户ID';
alter table pms_product_category add index IDX_ADMIN_ID(admin_id);

alter table pms_brand add column admin_id bigint(20) comment '商户用户ID';
alter table pms_brand add index IDX_ADMIN_ID(admin_id);

alter table pms_product_attribute add column admin_id bigint(20) comment '商户用户ID';
alter table pms_product_attribute add index IDX_ADMIN_ID(admin_id);

alter table pms_product_attribute_category add column admin_id bigint(20) comment '商户用户ID';
alter table pms_product_attribute_category add index IDX_ADMIN_ID(admin_id);

alter table pms_product add column admin_id bigint(20) comment '商户用户ID';
alter table pms_product add index IDX_ADMIN_ID(admin_id);

alter table oms_cart_item add column admin_id bigint(20) comment '商户用户ID';
alter table oms_cart_item add index IDX_ADMIN_ID(admin_id);


# 营销部分
alter table sms_flash_promotion add column admin_id bigint(20) comment '商户用户ID';
alter table sms_flash_promotion add index IDX_ADMIN_ID(admin_id);
alter table sms_flash_promotion_session add column admin_id bigint(20) comment '商户用户ID';
alter table sms_flash_promotion_session add index IDX_ADMIN_ID(admin_id);
alter table sms_coupon add column admin_id bigint(20) comment '商户用户ID';
alter table sms_coupon add index IDX_ADMIN_ID(admin_id);
alter table sms_home_brand add column admin_id bigint(20) comment '商户用户ID';
alter table sms_home_brand add index IDX_ADMIN_ID(admin_id);
alter table sms_home_new_product add column admin_id bigint(20) comment '商户用户ID';
alter table sms_home_new_product add index IDX_ADMIN_ID(admin_id);
alter table sms_home_recommend_product add column admin_id bigint(20) comment '商户用户ID';
alter table sms_home_recommend_product add index IDX_ADMIN_ID(admin_id);
alter table sms_home_recommend_subject add column admin_id bigint(20) comment '商户用户ID';
alter table sms_home_recommend_subject add index IDX_ADMIN_ID(admin_id);
alter table sms_home_advertise add column admin_id bigint(20) comment '商户用户ID';
alter table sms_home_advertise add index IDX_ADMIN_ID(admin_id);


# appId app_secret

alter table ums_admin add column app_id varchar(64) comment 'appId';
alter table ums_admin add column app_secret varchar(64) comment 'appSecret';


alter table ums_member add column admin_id bigint(20) comment '商户用户ID';
alter table ums_member add index IDX_ADMIN_ID(admin_id);
