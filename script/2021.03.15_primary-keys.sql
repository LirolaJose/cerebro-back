alter table services_of_order add primary key (services_id, adorder_id);
alter table services_of_category add primary key (category_id, services_id);