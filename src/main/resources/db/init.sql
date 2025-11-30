drop table if exists mesure_cpu ;
drop table if exists mesure_memoire ;

CREATE TABLE mesure_cpu (
	id serial primary key,
	time timestamp not null,
	cpu_load decimal(5,2) not null
);

CREATE TABLE mesure_memoire (
	id serial primary key,
	time timestamp not null,
	memory_available decimal(5,2) not null
);
