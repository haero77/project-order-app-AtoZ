create table member
(
    member_id   varchar(50) primary key,
    password    varchar(50) not null,
    member_name varchar(50) not null
);

create table store
(
    store_id             varchar(50) primary key,
    store_name           varchar(50) not null,
    minimum_order_amount bigint      not null
);

create table menu
(
    menu_id     varchar(50) primary key,
    menu_name   varchar(50) not null,
    price       bigint      not null,
    description varchar(100),
    store_id    varchar(50) not null,
    foreign key (store_id) references store (store_id),
    check (price >= 0)
);