alter table oms_order add column admin_id bigint(20) comment '商户用户ID';
alter table oms_order add index IDX_ADMIN_ID(admin_id);


