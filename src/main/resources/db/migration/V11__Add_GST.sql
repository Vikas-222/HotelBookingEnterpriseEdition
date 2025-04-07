CREATE TABLE gst_rates(id INT PRIMARY KEY AUTO_INCREMENT, min_price DECIMAL(10, 2) NOT NULL,
                 max_price DECIMAL(10, 2) NOT NULL, tax_rate DECIMAL(5, 2) NOT NULL);

Alter table booking add column gstRate float not null after booking_status;