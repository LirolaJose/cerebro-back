alter table services_of_order add primary key (service_id, adorder_id);
alter table services_of_category add primary key (category_id, service_id);