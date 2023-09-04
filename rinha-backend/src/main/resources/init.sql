DROP TABLE IF EXISTS public.pessoa;

CREATE TABLE IF NOT EXISTS public.pessoa (
	id uuid PRIMARY KEY  DEFAULT gen_random_uuid(),
	nome VARCHAR(100) NOT NULL,
	apelido VARCHAR(32) NOT NULL,
	nascimento TIMESTAMP NOT NULL,
	stack VARCHAR(1024),
	busca_completa TEXT GENERATED ALWAYS as (
	    lower(nome || ' ' || apelido || ' ' || COALESCE(stack,''))
	) stored
);
CREATE EXTENSION IF NOT EXISTS PG_TRGM;
CREATE INDEX CONCURRENTLY IF NOT EXISTS pessoas_busca_completa_idx ON pessoa USING GIST (busca_completa GIST_TRGM_OPS(SIGLEN=128));
CREATE UNIQUE INDEX IF NOT EXISTS pessoa_apelido_idx ON pessoa(apelido);
