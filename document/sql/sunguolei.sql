alter table oms_order_return_reason add column admin_id bigint(20) comment '商户用户ID';
alter table oms_order_return_reason add index IDX_ADMIN_ID(admin_id);


