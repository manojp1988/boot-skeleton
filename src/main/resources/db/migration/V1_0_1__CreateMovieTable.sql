create table Movies
(
    id          numeric(19, 0) identity not null,
    createAt    datetime,
    createdBy   varchar(255),
    modifiedAt  datetime,
    modifiedBy  varchar(255),
    version     numeric(19, 0),
    director    varchar(255),
    name        varchar(255),
    releaseDate datetime,
    primary key (id)
);


create table MoviesHistory
(
    id       numeric(19, 0) not null,
    REV      int            not null,
    REVTYPE  smallint,
    director varchar(255),
    name     varchar(255),
    primary key (id, REV)
);


create table REVINFO (REV int identity not null, REVTSTMP numeric(19,0), primary key (REV));

create table Users
(
    id              numeric(19, 0) identity not null,
    email           varchar(255),
    name            varchar(255),
    password        varchar(255),
    passwordExpired bit,
    primary key (id)
);

alter table MoviesHistory add constraint FKayhcsevemuo0sqlhguhjkaj5d foreign key (REV) references REVINFO;
