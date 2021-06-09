CREATE TABLE payment (
	id bigserial NOT NULL,
	"date" timestamp NOT NULL,
	customer_id int8 NOT NULL,
	CONSTRAINT pk_payment PRIMARY KEY (id),
	CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customer(id)
);