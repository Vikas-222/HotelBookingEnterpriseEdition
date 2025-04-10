CREATE TABLE room_service_charge (room_type ENUM('DELUXE','SINGLEBED','DOUBLEBED','TRIPLE','QUEEN','KING') PRIMARY KEY,
charge_per_night FLOAT NOT NULL);