DROP TABLE IF EXISTS public.pessoa;

CREATE TABLE public.pessoa (
	id uuid PRIMARY KEY NOT NULL,
	nome varchar(100) NOT NULL,
	apelido varchar(32) NOT NULL,
	nascimento timestamp NOT NULL,
	stack varchar NULL
);

CREATE UNIQUE INDEX pessoa_apelido_idx ON pessoa(apelido);
