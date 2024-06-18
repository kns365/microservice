delete from TBL_MSA_AUT_ROLEPRIVILEGE;
delete from TBL_MSA_AUT_USERROLE;
delete from TBL_MSA_AUT_PRIVILEGE;
delete from TBL_MSA_AUT_ROLE;
delete from TBL_MSA_AUT_USER;

ALTER TABLE TBL_MSA_AUT_USER MODIFY(ID GENERATED AS IDENTITY (START WITH 1));
ALTER TABLE TBL_MSA_AUT_ROLE MODIFY(ID GENERATED AS IDENTITY (START WITH 1));
ALTER TABLE TBL_MSA_AUT_PRIVILEGE MODIFY(ID GENERATED AS IDENTITY (START WITH 1));

insert into TBL_MSA_AUT_USER (EMAIL, ENABLED, FULL_NAME, PASSWORD, USERNAME) select EMAIL, ENABLED, FULL_NAME, PASSWORD, USERNAME from TBL_PTL_APP_USER;
insert into TBL_MSA_AUT_ROLE (NAME) select NAME from TBL_PTL_APP_ROLE;
insert into TBL_MSA_AUT_PRIVILEGE (NAME) select NAME from TBL_PTL_APP_PRIVILEGE;
insert into TBL_MSA_AUT_USERROLE (USER_ID,ROLE_ID) select USER_ID,ROLE_ID from TBL_PTL_APP_USERROLE;
insert into TBL_MSA_AUT_ROLEPRIVILEGE (ROLE_ID,PRIVILEGE_ID) select ROLE_ID,PRIVILEGE_ID from TBL_PTL_APP_ROLEPRIVILEGE where ROLE_ID=1;
commit;

insert into TBL_MSA_AUT_ROLEPRIVILEGE (ROLE_ID,PRIVILEGE_ID) (select 1,p.id from TBL_MSA_AUT_PRIVILEGE p);
insert into TBL_PTL_APP_ROLEPRIVILEGE (ROLE_ID,PRIVILEGE_ID) (select 1,p.id from TBL_PTL_APP_PRIVILEGE p);
commit;



insert into TBL_PTL_APP_ROLE (CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, NAME)
values ('system', sysdate, null, null, 'ROLE_ADMIN');

insert into TBL_PTL_APP_ROLE (CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, NAME)
values ('system', sysdate, null, null, 'ROLE_USER');
commit;

insert into TBL_PTL_APP_USER (CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, EMAIL, ENABLED, FULL_NAME, PASSWORD, USERNAME)
values ('system', sysdate, null, null, 'admin@mail.com', 1, 'Administration', '$2a$10$yeYwR.3xea2w6KfKF0mw0OZdTnylW8cHEQfGxRB8herkObSR0Xhmm', 'admin');

insert into TBL_PTL_APP_USER (CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, EMAIL, ENABLED, FULL_NAME, PASSWORD, USERNAME)
values ('system', sysdate, null, null, 'user@mail.com', 1, 'User', '$2a$10$yeYwR.3xea2w6KfKF0mw0OZdTnylW8cHEQfGxRB8herkObSR0Xhmm', 'user');
commit;

insert into TBL_PTL_APP_USERROLE (USER_ID, ROLE_ID)
values (1, 1);
insert into TBL_PTL_APP_USERROLE (USER_ID, ROLE_ID)
values (2, 2);
commit;

insert into TBL_PTL_APP_PRIVILEGE (CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, NAME)
values ('system', sysdate, null, null, 'USER');
insert into TBL_PTL_APP_PRIVILEGE (CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, NAME)
values ('system', sysdate, null, null, 'USER_CREATE');
insert into TBL_PTL_APP_PRIVILEGE (CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, NAME)
values ('system', sysdate, null, null, 'USER_EDIT');
insert into TBL_PTL_APP_PRIVILEGE (CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, NAME)
values ('system', sysdate, null, null, 'USER_DELETE');
insert into TBL_PTL_APP_PRIVILEGE (CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, NAME)
values ('system', sysdate, null, null, 'ROLE');
insert into TBL_PTL_APP_PRIVILEGE (CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, NAME)
values ('system', sysdate, null, null, 'ROLE_CREATE');
insert into TBL_PTL_APP_PRIVILEGE (CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, NAME)
values ('system', sysdate, null, null, 'ROLE_EDIT');
insert into TBL_PTL_APP_PRIVILEGE (CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, NAME)
values ('system', sysdate, null, null, 'ROLE_DELETE');
insert into TBL_PTL_APP_PRIVILEGE (CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, NAME)
values ('system', sysdate, null, null, 'PRIVILEGE');
insert into TBL_PTL_APP_PRIVILEGE (CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, NAME)
values ('system', sysdate, null, null, 'PRIVILEGE_CREATE');
insert into TBL_PTL_APP_PRIVILEGE (CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, NAME)
values ('system', sysdate, null, null, 'PRIVILEGE_EDIT');
insert into TBL_PTL_APP_PRIVILEGE (CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, NAME)
values ('system', sysdate, null, null, 'PRIVILEGE_DELETE');
insert into TBL_PTL_APP_PRIVILEGE (CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, NAME)
values ('system', sysdate, null, null, 'AUDITLOG');
insert into TBL_PTL_APP_PRIVILEGE (CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, NAME)
values ('system', sysdate, null, null, 'AUDITLOG_CREATE');
insert into TBL_PTL_APP_PRIVILEGE (CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, NAME)
values ('system', sysdate, null, null, 'AUDITLOG_EDIT');
insert into TBL_PTL_APP_PRIVILEGE (CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, NAME)
values ('system', sysdate, null, null, 'AUDITLOG_DELETE');
commit;

delete from table TBL_PTL_APP_ROLEPRIVILEGE;
Insert into TBL_PTL_APP_ROLEPRIVILEGE (ROLE_ID, PRIVILEGE_ID)
select 1, p.ID
from TBL_PTL_APP_PRIVILEGE p;
commit;

