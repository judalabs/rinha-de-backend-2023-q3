DROP TABLE IF EXISTS public.pessoa;

CREATE TABLE public.pessoa (
	id uuid PRIMARY KEY NOT NULL,
	nome VARCHAR(100) NOT NULL,
	apelido VARCHAR(32) NOT NULL,
	nascimento TIMESTAMP NOT NULL,
	stack VARCHAR(1024),
	busca_completa TEXT GENERATED ALWAYS as (
	    lower(nome || apelido || stack)
	) stored
);
CREATE EXTENSION PG_TRGM;
CREATE INDEX CONCURRENTLY IF NOT EXISTS pessoas_busca_completa_idx ON pessoa USING GIST (busca_completa GIST_TRGM_OPS(SIGLEN=64));
CREATE UNIQUE INDEX pessoa_apelido_idx ON pessoa(apelido);
