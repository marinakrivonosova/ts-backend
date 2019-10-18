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

-- object: public."USERS" | type: TABLE --
-- DROP TABLE IF EXISTS public."USERS" CASCADE;
CREATE TABLE public."USERS"(
	user_id varchar(32) NOT NULL,
	name varchar(64) NOT NULL,
	surname varchar(64) NOT NULL,
	birth_date date NOT NULL,
	email varchar(64) NOT NULL,
	hashed_password varchar(32) NOT NULL,
	phone_number varchar(32) NOT NULL,
	CONSTRAINT "USERS_pk" PRIMARY KEY (user_id)

);
-- ddl-end --
ALTER TABLE public."USERS" OWNER TO postgres;
-- ddl-end --

-- object: public."GOODS" | type: TABLE --
-- DROP TABLE IF EXISTS public."GOODS" CASCADE;
CREATE TABLE public."GOODS"(
	goods_id varchar(32) NOT NULL,
	title varchar(256) NOT NULL,
	price decimal(10,2) NOT NULL,
	weight smallint NOT NULL,
	volume smallint NOT NULL,
	count smallint NOT NULL,
	category_id varchar(32) NOT NULL,
	CONSTRAINT "GOODS_pk" PRIMARY KEY (goods_id)

);
-- ddl-end --
ALTER TABLE public."GOODS" OWNER TO postgres;
-- ddl-end --

-- object: public."CATEGORIES" | type: TABLE --
-- DROP TABLE IF EXISTS public."CATEGORIES" CASCADE;
CREATE TABLE public."CATEGORIES"(
	category_id varchar(32) NOT NULL,
	name varchar(256),
	CONSTRAINT "CATEGORIES_pk" PRIMARY KEY (category_id)

);
-- ddl-end --
ALTER TABLE public."CATEGORIES" OWNER TO postgres;
-- ddl-end --

-- object: public."GOODS_ATTRIBUTES" | type: TABLE --
-- DROP TABLE IF EXISTS public."GOODS_ATTRIBUTES" CASCADE;
CREATE TABLE public."GOODS_ATTRIBUTES"(
	attribute_id varchar(32) NOT NULL,
	goods_id varchar(32) NOT NULL,
	name varchar(32) NOT NULL,
	value varchar(32) NOT NULL,
	CONSTRAINT "GOOD_ATTRIBUTES_pk" PRIMARY KEY (attribute_id)

);
-- ddl-end --
ALTER TABLE public."GOODS_ATTRIBUTES" OWNER TO postgres;
-- ddl-end --

-- object: public."ORDERS" | type: TABLE --
-- DROP TABLE IF EXISTS public."ORDERS" CASCADE;
CREATE TABLE public."ORDERS"(
	order_id varchar(32) NOT NULL,
	user_id varchar(32) NOT NULL,
	address varchar(256) NOT NULL,
	payment_method varchar(32) NOT NULL,
	payment_status varchar(32) NOT NULL,
	delivery_method varchar(32) NOT NULL,
	order_status varchar(32) NOT NULL,
	comments varchar(1000),
	CONSTRAINT "ORDERS_pk" PRIMARY KEY (order_id)

);
-- ddl-end --
ALTER TABLE public."ORDERS" OWNER TO postgres;
-- ddl-end --

-- object: public."LINE_ITEMS" | type: TABLE --
-- DROP TABLE IF EXISTS public."LINE_ITEMS" CASCADE;
CREATE TABLE public."LINE_ITEMS"(
	line_item_id varchar(32) NOT NULL,
	order_id varchar(32) NOT NULL,
	good_id varchar(32) NOT NULL,
	count smallint NOT NULL,
	price smallint NOT NULL,
	CONSTRAINT "LINE_ITEMS_pk" PRIMARY KEY (line_item_id)

);
-- ddl-end --
ALTER TABLE public."LINE_ITEMS" OWNER TO postgres;
-- ddl-end --

-- object: goods_category_id_fk | type: CONSTRAINT --
-- ALTER TABLE public."GOODS" DROP CONSTRAINT IF EXISTS goods_category_id_fk CASCADE;
ALTER TABLE public."GOODS" ADD CONSTRAINT goods_category_id_fk FOREIGN KEY (category_id)
REFERENCES public."CATEGORIES" (category_id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: goods_attributes_goods_id_fk | type: CONSTRAINT --
-- ALTER TABLE public."GOODS_ATTRIBUTES" DROP CONSTRAINT IF EXISTS goods_attributes_goods_id_fk CASCADE;
ALTER TABLE public."GOODS_ATTRIBUTES" ADD CONSTRAINT goods_attributes_goods_id_fk FOREIGN KEY (goods_id)
REFERENCES public."GOODS" (goods_id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: orders_user_id_fk | type: CONSTRAINT --
-- ALTER TABLE public."ORDERS" DROP CONSTRAINT IF EXISTS orders_user_id_fk CASCADE;
ALTER TABLE public."ORDERS" ADD CONSTRAINT orders_user_id_fk FOREIGN KEY (user_id)
REFERENCES public."USERS" (user_id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: line_items_order_id_fk | type: CONSTRAINT --
-- ALTER TABLE public."LINE_ITEMS" DROP CONSTRAINT IF EXISTS line_items_order_id_fk CASCADE;
ALTER TABLE public."LINE_ITEMS" ADD CONSTRAINT line_items_order_id_fk FOREIGN KEY (order_id)
REFERENCES public."ORDERS" (order_id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: line_items_good_id_fk | type: CONSTRAINT --
-- ALTER TABLE public."LINE_ITEMS" DROP CONSTRAINT IF EXISTS line_items_good_id_fk CASCADE;
ALTER TABLE public."LINE_ITEMS" ADD CONSTRAINT line_items_good_id_fk FOREIGN KEY (good_id)
REFERENCES public."GOODS" (goods_id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --


