-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- pgModeler  version: 0.9.1
-- PostgreSQL version: 10.0
-- Project Site: pgmodeler.io
-- Model Author: ---


-- Database creation must be done outside a multicommand file.
-- These commands were put in this file only as a convenience.
-- -- object: new_database | type: DATABASE --
-- -- DROP DATABASE IF EXISTS new_database;
-- CREATE DATABASE new_database;
-- -- ddl-end --
-- 

-- object: public."users" | type: TABLE --
-- DROP TABLE IF EXISTS public."users" CASCADE;
CREATE TABLE public."users"(
	id varchar(32) NOT NULL,
	name varchar(64) NOT NULL,
	surname varchar(64) NOT NULL,
	birthdate date NOT NULL,
	email varchar(64) NOT NULL,
	password varchar(32) NOT NULL,
	phone varchar(32) NOT NULL,
	CONSTRAINT "users_pk" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE public."users" OWNER TO postgres;
-- ddl-end --

-- object: public."products" | type: TABLE --
-- DROP TABLE IF EXISTS public."products" CASCADE;
CREATE TABLE public."products"(
	id varchar(32) NOT NULL,
	name varchar(256) NOT NULL,
	description varchar(256),
	price decimal(10,2) NOT NULL,
	weight smallint NOT NULL,
	volume smallint NOT NULL,
	count smallint NOT NULL,
	category_id varchar(32) NOT NULL,
	CONSTRAINT "products_pk" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE public."products" OWNER TO postgres;
-- ddl-end --

-- object: public."categories" | type: TABLE --
-- DROP TABLE IF EXISTS public."categories" CASCADE;
CREATE TABLE public."categories"(
	id varchar(32) NOT NULL,
	name varchar(256),
	description varchar(256),
	CONSTRAINT "categories_pk" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE public."categories" OWNER TO postgres;
-- ddl-end --

-- object: public."products_attributes" | type: TABLE --
-- DROP TABLE IF EXISTS public."products_attributes" CASCADE;
CREATE TABLE public."product_attributes"(
	id varchar(32) NOT NULL,
	name varchar(32) NOT NULL,
	value varchar(32) NOT NULL,
	product_id varchar(32) NOT NULL,
	CONSTRAINT "product_attributes_pk" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE public."products_attributes" OWNER TO postgres;
-- ddl-end --

-- object: public."orders" | type: TABLE --
-- DROP TABLE IF EXISTS public."orders" CASCADE;
CREATE TABLE public."orders"(
	id varchar(32) NOT NULL,
	user_id varchar(32) NOT NULL,
	address varchar(256) NOT NULL,
	payment_method varchar(32) NOT NULL, -- the payment methods have to be listed in a predefined table and there should be a foreign key to the "payment_methods" table
	payment_status varchar(32) NOT NULL, -- the payment statuses have to be listed in a predefined table and there has to be a foreign key to the "payment_statuses" table
	delivery_method varchar(32) NOT NULL, -- the delivery methods have to be listed in a predefined table and there should be a foreign key to the "delivery_methods" table
	status varchar(32) NOT NULL, -- the statuses have to be listed in a predefined table and there has to be a foreign key to the "order_statuses" table
	-- comments varchar(1000), -- the comments have to be presented by a many-to-many relationship with an order
	CONSTRAINT "orders_pk" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE public."orders" OWNER TO postgres;
-- ddl-end --

-- object: public."line_items" | type: TABLE --
-- DROP TABLE IF EXISTS public."line_items" CASCADE;
CREATE TABLE public."line_items"(
	id varchar(32) NOT NULL,
	order_id varchar(32) NOT NULL,
	product_id varchar(32) NOT NULL,
	count smallint NOT NULL,
	price smallint NOT NULL,
	CONSTRAINT "line_items_pk" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE public."line_items" OWNER TO postgres;
-- ddl-end --

-- object: products_category_id_fk | type: CONSTRAINT --
-- ALTER TABLE public."products" DROP CONSTRAINT IF EXISTS products_category_id_fk CASCADE;
ALTER TABLE public."products" ADD CONSTRAINT products_category_id_fk FOREIGN KEY (category_id)
REFERENCES public."categories" (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: product_attributes_product_id_fk | type: CONSTRAINT --
-- ALTER TABLE public."product_attributes" DROP CONSTRAINT IF EXISTS product_attributes_product_id_fk CASCADE;
ALTER TABLE public."product_attributes" ADD CONSTRAINT product_attributes_product_id_fk FOREIGN KEY (product_id)
REFERENCES public."products" (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: orders_user_id_fk | type: CONSTRAINT --
-- ALTER TABLE public."orders" DROP CONSTRAINT IF EXISTS orders_user_id_fk CASCADE;
ALTER TABLE public."orders" ADD CONSTRAINT orders_user_id_fk FOREIGN KEY (user_id)
REFERENCES public."users" (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: line_items_order_id_fk | type: CONSTRAINT --
-- ALTER TABLE public."line_items" DROP CONSTRAINT IF EXISTS line_items_order_id_fk CASCADE;
ALTER TABLE public."line_items" ADD CONSTRAINT line_items_order_id_fk FOREIGN KEY (order_id)
REFERENCES public."orders" (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: line_items_product_id_fk | type: CONSTRAINT --
-- ALTER TABLE public."line_items" DROP CONSTRAINT IF EXISTS line_items_product_id_fk CASCADE;
ALTER TABLE public."line_items" ADD CONSTRAINT line_items_product_id_fk FOREIGN KEY (product_id)
REFERENCES public."products" (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --


