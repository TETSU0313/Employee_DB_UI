create table SALARY_LEVEL (
  JOB_LEVEL char(2) NOT NULL PRIMARY KEY,  
  SALARY_MINIMUM double,
  SALARY_MAXIMUM double);
 

insert into SALARY_LEVEL values ('52', 1900.00, 2400.00);
insert into SALARY_LEVEL values ('54', 2400.00, 2900.00);
insert into SALARY_LEVEL values ('55', 2900.00, 3400.00);
insert into SALARY_LEVEL values ('57', 3400.00, 3900.00);
insert into SALARY_LEVEL values ('59', 3900.00, 4400.00);


