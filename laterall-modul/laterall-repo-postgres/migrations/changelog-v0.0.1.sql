--liquibase formatted sql

--changeset u1406:1 labels:v0.0.1
CREATE TYPE "task_visibilities_type" AS ENUM ('public', 'owner', 'group');

CREATE TYPE "task_groups_type" AS ENUM ('other', 'work', 'home', 'children', 'car');

CREATE TYPE "task_importance_type" AS ENUM ('low', 'medium', 'high');


CREATE TABLE "tasks" (
	"id" text primary key constraint tasks_id_length_ctr check (length("id") < 64),
	"title" text constraint tasks_title_length_ctr check (length(title) < 128),
	"description" text constraint tasks_description_length_ctr check (length(title) < 4096),
	"visibility" task_visibilities_type not null,
	"owner_id" text not null constraint tasks_owner_id_length_ctr check (length(id) < 64),
	"product_id" text constraint tasks_product_id_length_ctr check (length(id) < 64),
	"lock" text not null constraint tasks_lock_length_ctr check (length(id) < 64),
	"importance" task_importance_type not null,
	"group" task_groups_type not null,
	"priority" integer,
	"taskstart" text,
	"taskend" text,
	"taskappend" text
);

CREATE INDEX tasks_owner_id_idx on "tasks" using hash ("owner_id");

CREATE INDEX tasks_product_id_idx on "tasks" using hash ("product_id");

CREATE INDEX tasks_visibility_idx on "tasks" using hash ("visibility");
