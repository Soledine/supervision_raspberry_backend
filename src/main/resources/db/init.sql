drop table if exists mesure_cpu ;

CREATE TABLE mesure_cpu (
	id serial primary key,
	time timestamp not null,
	cpu_load decimal(5,2) not null
);
