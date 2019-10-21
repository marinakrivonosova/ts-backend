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
	first_name varchar(64) NOT NULL,
	last_name varchar(64) NOT NULL,
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

-- object: products_category_id_fk | type: CONSTRAINT --
-- ALTER TABLE public."products" DROP CONSTRAINT IF EXISTS products_category_id_fk CASCADE;
ALTER TABLE public."products" ADD CONSTRAINT products_category_id_fk FOREIGN KEY (category_id)
REFERENCES public."categories" (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;
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

-- object: public."payment_methods" | type: TABLE --
-- DROP TABLE IF EXISTS public."payment_methods" CASCADE;
CREATE TABLE public."payment_methods"(
	id varchar(32) NOT NULL,
	payment_method varchar(256),
	CONSTRAINT "payment_methods_pk" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE public."payment_methods" OWNER TO postgres;
-- ddl-end -

-- object: public."payment_statuses" | type: TABLE --
-- DROP TABLE IF EXISTS public."payment_statuses" CASCADE;
CREATE TABLE public."payment_statuses"(
	id varchar(32) NOT NULL,
	payment_status varchar(256),
	CONSTRAINT "payment_statuses_pk" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE public."payment_statuses" OWNER TO postgres;
-- ddl-end -

-- object: public."delivery_methods" | type: TABLE --
-- DROP TABLE IF EXISTS public."delivery_methods" CASCADE;
CREATE TABLE public."delivery_methods"(
	id varchar(32) NOT NULL,
	delivery_method varchar(256),
	CONSTRAINT "delivery_methods_pk" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE public."delivery_methods" OWNER TO postgres;
-- ddl-end -

-- object: public."order_statuses" | type: TABLE --
-- DROP TABLE IF EXISTS public."order_statuses" CASCADE;
CREATE TABLE public."order_statuses"(
	id varchar(32) NOT NULL,
	order_status varchar(256),
	CONSTRAINT "order_statuses_pk" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE public."order_statuses" OWNER TO postgres;
-- ddl-end -

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

-- object: product_attributes_product_id_fk | type: CONSTRAINT --
-- ALTER TABLE public."product_attributes" DROP CONSTRAINT IF EXISTS product_attributes_product_id_fk CASCADE;
ALTER TABLE public."product_attributes" ADD CONSTRAINT product_attributes_product_id_fk FOREIGN KEY (product_id)
REFERENCES public."products" (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: public."orders" | type: TABLE --
-- DROP TABLE IF EXISTS public."orders" CASCADE;
CREATE TABLE public."orders"(
	id varchar(32) NOT NULL,
	user_id varchar(32) NOT NULL,
	address varchar(256) NOT NULL,
	payment_method_id varchar(32) NOT NULL,
	payment_status_id varchar(32) NOT NULL,
	delivery_method_id varchar(32) NOT NULL,
	status_id varchar(32) NOT NULL,
	CONSTRAINT "orders_pk" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE public."orders" OWNER TO postgres;
-- ddl-end --

-- object: orders_user_id_fk | type: CONSTRAINT --
-- ALTER TABLE public."orders" DROP CONSTRAINT IF EXISTS orders_user_id_fk CASCADE;
ALTER TABLE public."orders" ADD CONSTRAINT orders_user_id_fk FOREIGN KEY (user_id)
REFERENCES public."users" (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object:orders_status_fk | type: CONSTRAINT --
-- ALTER TABLE public."orders" DROP CONSTRAINT IF EXISTS orders_status_fk CASCADE;
ALTER TABLE public."orders" ADD CONSTRAINT orders_status_fk FOREIGN KEY (status_id)
REFERENCES public."order_statuses" (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object:orders_delivery_method_fk | type: CONSTRAINT --
-- ALTER TABLE public."orders" DROP CONSTRAINT IF EXISTS orders_delivery_method_fk CASCADE;
ALTER TABLE public."orders" ADD CONSTRAINT orders_delivery_method_fk FOREIGN KEY (delivery_method_id)
REFERENCES public."delivery_methods" (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object:orders_payment_status_fk | type: CONSTRAINT --
-- ALTER TABLE public."orders" DROP CONSTRAINT IF EXISTS orders_payment_status_fk CASCADE;
ALTER TABLE public."orders" ADD CONSTRAINT orders_payment_status_fk FOREIGN KEY (payment_status_id)
REFERENCES public."payment_statuses" (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object:orders_payment_status_fk | type: CONSTRAINT --
-- ALTER TABLE public."orders" DROP CONSTRAINT IF EXISTS orders_payment_status_fk CASCADE;
ALTER TABLE public."orders" ADD CONSTRAINT orders_payment_status_fk FOREIGN KEY (payment_status_id)
REFERENCES public."payment_statuses" (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;
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

-- object: line_items_product_id_fk | type: CONSTRAINT --
-- ALTER TABLE public."line_items" DROP CONSTRAINT IF EXISTS line_items_product_id_fk CASCADE;
ALTER TABLE public."line_items" ADD CONSTRAINT line_items_product_id_fk FOREIGN KEY (product_id)
REFERENCES public."products" (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: line_items_order_id_fk | type: CONSTRAINT --
-- ALTER TABLE public."line_items" DROP CONSTRAINT IF EXISTS line_items_order_id_fk CASCADE;
ALTER TABLE public."line_items" ADD CONSTRAINT line_items_order_id_fk FOREIGN KEY (order_id)
REFERENCES public."orders" (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: public."order_comments" | type: TABLE --
-- DROP TABLE IF EXISTS public."order_comments" CASCADE;
CREATE TABLE public."order_comments"(
	id varchar(32) NOT NULL,
	order_id varchar(32) NOT NULL,
	comment varchar(1000) NOT NULL,
	CONSTRAINT "order_comments_pk" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE public."order_comments" OWNER TO postgres;
-- ddl-end --

-- object:order_comments_order_id_fk | type: CONSTRAINT --
-- ALTER TABLE public."order_comments" DROP CONSTRAINT IF EXISTS order_comments_order_id_fk CASCADE;
ALTER TABLE public."order_comments" ADD CONSTRAINT order_comments_order_id_fk FOREIGN KEY (order_id)
REFERENCES public."orders" (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --