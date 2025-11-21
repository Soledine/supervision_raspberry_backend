drop table if exists mesure_cpu ;

CREATE TABLE mesure_cpu (
	id serial primary key,
	cpu_load decimal(3,2) not null
);
