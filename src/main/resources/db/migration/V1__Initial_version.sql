create table hotel
(
    hotel_id       int primary key auto_increment,
    hotel_name     varchar(50)  not null,
    address        varchar(120) not null,
    contact        varchar(15)  not null,
    email          varchar(50)  not null,
    check_in_time  time,
    check_out_time time,
    created_at     datetime default current_timestamp,
    updated_at     datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


create table hotel_images
(
    id         int primary key auto_increment,
    imagepath  varchar(40) not null,
    hotel_id   int         not null,
    created_at datetime default current_timestamp,
    updated_at datetime default current_timestamp on update now(),
    foreign key (hotel_id) references hotel (hotel_id)
);


create table user
(
    user_id    int primary key auto_increment,
    first_name varchar(50) not null,
    last_name  varchar(50),
    email      varchar(60) not null unique,
    contact    varchar(15) not null,
    gender     Enum('Male','Female','Other') default 'Male',
    passwords  varchar(15),
    roles      Enum('Admin','User') default 'User',
    is_active  boolean  default true,
    profilePic varchar(70),
    created_at DateTime default current_timestamp,
    updated_at DateTime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

create table room
(
    room_id         int primary key auto_increment,
    room_number     int unique not null,
    room_type       ENUM('DELUXE','SINGLEBED','DOUBLEBED','TRIPLE','QUEEN','KING') not null,
    capacity        int        not null,
    price_per_night float      not null,
    is_active       boolean  default true,
    created_at      DateTime default current_timestamp,
    updated_at      DateTime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

create table room_images
(
    id         int primary key auto_increment,
    imagepath  varchar(40) not null,
    room_id    int         not null,
    created_at datetime default current_timestamp,
    updated_at datetime default current_timestamp on update now(),
    foreign key (room_id) references room (room_id)
);


create table booking
(
    booking_id        int primary key auto_increment,
    user_id           int      not null,
    room_number       int      not null,
    check_in          DateTime not null,
    check_out         DateTime not null,
    total_amount      float    not null,
    booking_status    Enum('CONFIRMED','CANCELLATION','COMPLETED') not null,
    cancellation_date Date,
    refund_amount     float,
    created_at        datetime default current_timestamp,
    updated_at        datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


create table category
(
    category_id   int primary key auto_increment,
    category_name varchar(30) not null unique,
    created_at    datetime default current_timestamp
);


create table amenities
(
    amenity_id   int primary key auto_increment,
    amenity_name varchar(40) not null unique,
    category_id  int         not null,
    created_at   datetime default current_timestamp,
    FOREIGN KEY (category_id) REFERENCES category (category_id) ON DELETE CASCADE
);


create table guest_details
(
    guest_id        int primary key auto_increment,
    booking_id      int         not null,
    guest_name      varchar(70) not null,
    age             int         not null,
    id_type         Enum('AADHAR CARD','PAN CARD','VOTER CARD','LICENSE') default 'Aadhar card' not null,
    id_proof_number varchar(18) not null,
    created_at      datetime default current_timestamp,
    updated_at      DateTime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    foreign key (booking_id) references booking (booking_id)
);


create table payment
(
    payment_id   int primary key auto_increment,
    booking_id   int      not null,
    bill_amount  float    not null,
    payment_date datetime not null,
    payment_mode Enum('CREDIT CARD','CASH','DEBIT CARD','UPI'),
    created_at   datetime default current_timestamp,
    updated_at   datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES booking (booking_id)
);


create table room_amenities
(
    room_id    int not null,
    amenity_id int not null,
    created_at datetime default current_timestamp,
    foreign key (room_id) references room (room_id),
    foreign key (amenity_id) references amenities (amenity_id)
);


create table services
(
    service_id          int primary key auto_increment,
    service_description varchar(100) not null,
    service_cost        float        not null,
    created_at          datetime default current_timestamp,
    updated_at          datetime default current_timestamp on update now()
);


create table booking_services
(
    booking_Id int not null,
    service_Id int not null,
    foreign key (booking_id) references booking (booking_id),
    foreign key (service_id) references services (service_id)
);


create table review
(
    review_id  int primary key auto_increment,
    user_id    int          not null,
    booking_id int          not null,
    feedback   varchar(120) not null,
    rating     tinyint      not null,
    created_at datetime default current_timestamp,
    updated_at datetime default current_timestamp on update now(),
    foreign key (user_id) references user (user_id),
    foreign key (booking_id) references booking (booking_id)
);
