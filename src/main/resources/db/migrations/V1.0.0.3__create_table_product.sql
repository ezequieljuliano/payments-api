CREATE TABLE product (
	id bigserial NOT NULL,
	file varchar(255) NULL,
	name varchar(255) NULL,
	price numeric(19,2) NOT NULL,
	payment_id int8 NULL,
	CONSTRAINT pk_product PRIMARY KEY (id),
	CONSTRAINT fk_payment FOREIGN KEY (payment_id) REFERENCES payment(id) ON UPDATE CASCADE ON DELETE CASCADE
);