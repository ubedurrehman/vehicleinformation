-- we don't know how to generate root <with-no-name> (class Root) :(
grant alter, alter routine, create, create routine, create tablespace, create temporary tables, create user, create view, delete, delete history, drop, event, execute, file, index, insert, lock tables, process, references, reload, replication client, replication slave, select, show databases, show view, shutdown, super, trigger, update, grant option on *.* to root@'127.0.0.1';

grant alter, alter routine, create, create routine, create tablespace, create temporary tables, create user, create view, delete, delete history, drop, event, execute, file, index, insert, lock tables, process, references, reload, replication client, replication slave, select, show databases, show view, shutdown, super, trigger, update, grant option on *.* to root@'::1';

grant alter, alter routine, create, create routine, create tablespace, create temporary tables, create user, create view, delete, delete history, drop, event, execute, file, index, insert, lock tables, process, references, reload, replication client, replication slave, select, show databases, show view, shutdown, super, trigger, update, grant option on *.* to root@localhost;

create table bodytype
(
    bodyTypeTitle varchar(255) null
);

create table manufacturer
(
    mName varchar(255) null
);

create table vehicleinfo
(
    vehicleNo      varchar(20) not null
        primary key,
    bodyType       varchar(50) null,
    manufacturer   varchar(50) null,
    engineCapacity int         null,
    numOfSeats     int         null,
    engineType     varchar(50) null,
    engine         varchar(50) null,
    color          varchar(30) null
);

