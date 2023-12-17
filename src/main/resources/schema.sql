drop table if exists cameras cascade;
create table cameras
(
    id         serial,
    nasa_id    integer not null,
    name       varchar(64) not null,
    created_at timestamp not null default now(),
    constraint pk_cameras_id primary key (id)
);

drop table if exists pictures cascade;
create table pictures
(
    id         serial,
    nasa_id    integer   not null,
    img_src    varchar(255)   not null,
    camera_id  integer   not null,
    created_at timestamp not null default now(),
    constraint pk_pictures_id primary key (id),
    constraint fk_pictures_cameras_id foreign key (camera_id) REFERENCES cameras (id)
);