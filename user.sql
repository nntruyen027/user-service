/*==============================================================*/
/* DBMS name:      MySQL                                        */
/* Created on:     30/11/2024 22:30:19                          */
/*==============================================================*/
drop database if exists users;
create database users;
use users;

alter table ADDRESS 
   drop foreign key FK_ADDRESS_RELATIONS_USERS;
alter table ADDRESS 
   drop foreign key FK_ADDRESS_RELATIONS_USERS;
drop table if exists ADDRESS;
drop table if exists USERS;

/*==============================================================*/
/* Table: USERS                                                 */
/*==============================================================*/
create table USERS
(
   USER_ID              bigint not null ,
   ACCOUNT_ID           bigint not null ,
   FULLNAME             varchar(500) not null ,
   ISMALE               bool not null default true,
   PHONE                varchar(11) not null ,
   EMAIL                varchar(255) not null ,
   AVATAR               varchar(500) ,
   primary key (USER_ID)
);

/*==============================================================*/
/* Table: ADDRESS                                               */
/*==============================================================*/
create table ADDRESS
(
   ADDRESS_ID           bigint not null ,
   USER_ID              bigint not null ,
   TINH                 varchar(255) not null ,
   HUYEN                varchar(255) not null ,
   XA                   varchar(255) not null ,
   CHI_TIET             varchar(255) ,
   KINH_DO              varchar(255) ,
   VI_DO                varchar(255) ,
   ISDEFAULT            bool not null default true,
   primary key (ADDRESS_ID)
);

alter table ADDRESS add constraint FK_ADDRESS_RELATIONS_USERS foreign key (USER_ID)
      references USERS (USER_ID) on delete restrict on update restrict;

