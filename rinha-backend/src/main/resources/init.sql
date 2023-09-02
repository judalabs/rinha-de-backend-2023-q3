DROP TABLE IF EXISTS public.pessoa;

create extension pgcrypto;

CREATE TABLE public.pessoa (
	id uuid PRIMARY KEY  DEFAULT gen_random_uuid(),
	nome VARCHAR(100) NOT NULL,
	apelido VARCHAR(32) NOT NULL,
	nascimento TIMESTAMP NOT NULL,
	stack VARCHAR(1024),
	busca_completa TEXT GENERATED ALWAYS as (
	    lower(nome || ' ' || apelido || ' ' || COALESCE(stack,''))
	) stored
);
CREATE EXTENSION PG_TRGM;
CREATE INDEX CONCURRENTLY IF NOT EXISTS pessoas_busca_completa_idx ON pessoa USING GIST (busca_completa GIST_TRGM_OPS(SIGLEN=128));
CREATE UNIQUE INDEX pessoa_apelido_idx ON pessoa(apelido);
