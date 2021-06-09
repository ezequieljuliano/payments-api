CREATE TABLE customer (
	id bigserial NOT NULL,
	"name" varchar(255) NOT NULL,
	CONSTRAINT pk_customer PRIMARY KEY (id)
);