create table if not exists company (
    id                        bigint not null auto_increment,
    name                      varchar(255),
    constraint pk_company primary key (id))
  ;

create table computer (
    id                        bigint not null auto_increment,
    name                      varchar(255),
    introduced                DATE NULL,
    discontinued              DATE NULL,
    company_id                bigint default NULL,
    constraint pk_computer primary key (id))
  ;

alter table computer add constraint fk_computer_company_1 foreign key (company_id) references company (id) on delete restrict on update restrict;
create index ix_computer_company_1 on computer (company_id);