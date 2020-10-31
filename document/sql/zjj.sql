alter table pms_product_category add column admin_id bigint(20) comment '商户用户ID';
alter table pms_product_category add index IDX_ADMIN_ID(admin_id);

alter table pms_brand add column admin_id bigint(20) comment '商户用户ID';
alter table pms_brand add index IDX_ADMIN_ID(admin_id);

alter table pms_product_attribute add column admin_id bigint(20) comment '商户用户ID';
alter table pms_product_attribute add index IDX_ADMIN_ID(admin_id);