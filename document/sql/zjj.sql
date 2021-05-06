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
alter table ums_integration_consume_setting add column admin_id bigint(20) comment '商户用户ID';
alter table ums_integration_consume_setting add column image_url varchar(128) default '' comment '支付头像URL';

# appId app_secret

alter table ums_admin add column app_id varchar(64) comment 'appId';
alter table ums_admin add column app_secret varchar(64) comment 'appSecret';

alter table ums_admin add column notice_on int default 0 comment '是否开启通知0 关闭 1 开启';
alter table ums_admin add column notice_content varchar(128) comment '通知内容';
alter table ums_admin add column notice_type varchar(2) comment '通知方式：1 每天 2 指定时间';
alter table ums_admin add column notice_start varchar(128) comment 'hh:mm 或 yyyy-MM-DD hh:mm';
alter table ums_admin add column notice_end varchar(128) comment '时间段';
alter table ums_admin add column mch_id varchar(128) comment '商户ID';
alter table ums_admin add column mch_key varchar(128) comment '商户秘钥';


alter table ums_member add column take_code varchar(64) comment '取货码';
alter table ums_member add column admin_id bigint(20) comment '商户用户ID';
alter table ums_member add index IDX_ADMIN_ID(admin_id);

drop table  ums_printer;
create table ums_printer(
  id bigint auto_increment
    primary key,
  admin_id bigint not null,
  printer_name varchar(64) comment '打印机名称',
  printer_sn varchar(128)  comment '打印机sn',
  printer_voice_type  int(1) default '1' comment '0真人语音（大） 1真人语音（中） 2真人语音（小） 3 嘀嘀声 4 静音',
  printer_status  int(1) default '1' comment '0 表示离线1 表示在线正常2 表示在线异常',
  title varchar(128) comment '统一打印标题',
  printer_qr_title varchar(256) comment '二维码地址',
  printer_qr_url varchar(256) comment '二维码地址',
  cardNo varchar(256) comment '手机号',
  copies int(2) default 1 comment '打印份数',
  index IDX_ADMIN_ID(admin_id)
)comment '商户打印机表';
alter table ums_printer add column  printer_qr_title varchar(256) comment '二维码地址';
alter table ums_printer add column  printer_factory int not null comment '1:芯烨 2：飞鹅';
alter table ums_printer add column  printer_key varchar(128)  comment '打印机识别号，飞鹅有';
alter table ums_admin add column  authorizer_refresh_token  varchar(128) default '' comment '刷新令牌';
alter table ums_admin add column  wxacode_url  varchar(256) default '' comment '小程序码，不带参数';
alter table ums_admin add column  wxacode_pay_url  varchar(256) default '' comment '小程序码，支付码';
alter table ums_admin add column  publish_status  int(1) default '0' comment '是否已发布小程序:0未发布 1已发布';
alter table ums_admin add column  vip_end_date  datetime default null comment '会员到期日志';
alter table ums_admin add column  contact_mobile  varchar(64) default null comment '取货手机号';
alter table ums_admin add column  contact_address  varchar(256) default null comment '取货地址';
alter table ums_admin add column  wx_template_id  varchar(256) default null comment '小程序消息模板id';
alter table oms_cart_item add column  buy_limit  int default 0 comment 'null 或0 不限量';
alter table oms_cart_item add column  flash_relation_id  bigint default 0 comment '秒杀关系表主键';
alter table oms_order_item add column  flash_relation_id  bigint default 0 comment '秒杀关系表主键';
alter table sms_flash_promotion_product_relation add column flash_promotion_stock int default 0 comment '剩余库存' ;


alter table oms_order add column  flash_relation_id  bigint default 0 comment '秒杀关系表主键';






