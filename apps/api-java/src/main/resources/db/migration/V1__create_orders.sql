create table if not exists orders (
    id uuid primary key,
    customer varchar(120) not null,
    amount integer not null check (amount > 0),
    status varchar(30) not null,
    created_at timestamp with time zone not null
);

create index if not exists idx_orders_created_at on orders (created_at desc);
