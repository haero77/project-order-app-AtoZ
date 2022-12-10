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

create table orders
(
    order_id       varchar(50) primary key,
    member_id      varchar(50) not null references member (member_id),
    menu_id        varchar(50) not null references menu (menu_id),
    store_id       varchar(50) not null references store (store_id),
    order_quantity bigint      not null,
    order_amount   bigint      not null,
    order_status   varchar(50) not null,
    created_at     timestamp   not null,
    cancelled_at   timestamp
);