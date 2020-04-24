create table faculty
(
    id          bigint auto_increment
        primary key,
    given_name  varchar(255)                        not null,
    family_name varchar(255)                        not null,
    created_at  timestamp default CURRENT_TIMESTAMP not null,
    updated_at  timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP
);


create table student
(
    id          bigint auto_increment
        primary key,
    given_name  varchar(255)                        not null,
    family_name varchar(255)                        not null,
    enroll_in   int                                 not null,
    created_at  timestamp default CURRENT_TIMESTAMP not null,
    updated_at  timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP
);


create table course
(
    id            bigint auto_increment
        primary key,
    course_prefix varchar(8)                          not null,
    course_num    smallint                            not null,
    name          varchar(255)                        not null,
    max_enroll    int                                 not null,
    instructor_id bigint                              not null,
    created_at    timestamp default CURRENT_TIMESTAMP null,
    updated_at    timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    constraint class_faculty_id_fk
        foreign key (instructor_id) references faculty (id)
            on update cascade on delete cascade
);

create table enrollment
(
    id         bigint auto_increment
        primary key,
    year       int                                 not null,
    student_id bigint                              not null,
    course_id  bigint                              not null,
    created_at timestamp default CURRENT_TIMESTAMP not null,
    updated_at timestamp default CURRENT_TIMESTAMP not null,
    constraint enroll_list_course_id_fk
        foreign key (course_id) references course (id)
            on update cascade on delete cascade,
    constraint enroll_list_student_id_fk
        foreign key (student_id) references student (id)
            on update cascade on delete cascade
);


