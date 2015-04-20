
    create table address (
        id_address bigint not null,
        number integer,
        street varchar(255),
        id_city bigint,
        primary key (id_address)
    );

    create table city (
        id_city bigint not null,
        name varchar(255),
        id_state bigint,
        primary key (id_city)
    );

    create table company (
        id_company bigint not null,
        name varchar(255),
        id_head_office_address bigint,
        primary key (id_company)
    );

    create table country (
        id_country bigint not null,
        name varchar(255),
        id_capital_city bigint,
        primary key (id_country)
    );

    create table employee (
        id_employee bigint not null,
        firstname varchar(255),
        lastname varchar(255),
        current_address_id bigint,
        permanent_address_id bigint,
        primary key (id_employee)
    );

    create table office_address (
        id_company bigint not null,
        id_address bigint not null,
        primary key (id_company, id_address)
    );

    create table state (
        id_state bigint not null,
        name varchar(255),
        id_country bigint,
        primary key (id_state)
    );

    alter table address 
        add constraint FK_bv6uiusph2ko0kisd95b023xg 
        foreign key (id_city) 
        references city;

    alter table city 
        add constraint FK_5bjqku7o9njvgvkp8kt1ypa3w 
        foreign key (id_state) 
        references state;

    alter table company 
        add constraint FK_5x4qdoor9uutwbwjjddnhruxn 
        foreign key (id_head_office_address) 
        references address;

    alter table country 
        add constraint FK_cvyav791ogvbofswrjkd4cnni 
        foreign key (id_capital_city) 
        references city;

    alter table employee 
        add constraint FK_logkcvdaee6xywv5lsl9m1j2j 
        foreign key (current_address_id) 
        references address;

    alter table employee 
        add constraint FK_hm0g01yk4of86mk55mgxnpywd 
        foreign key (permanent_address_id) 
        references address;

    alter table office_address 
        add constraint FK_km9txalyxqbmspxt0p3ukinvm 
        foreign key (id_address) 
        references address;

    alter table office_address 
        add constraint FK_aygttokbemi0chl7si9hq000 
        foreign key (id_company) 
        references company;

    alter table state 
        add constraint FK_mnlclb4b80osphgasbdmdwokg 
        foreign key (id_country) 
        references country;
