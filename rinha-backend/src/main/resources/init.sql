DROP TABLE IF EXISTS public.pessoa;

CREATE TABLE public.pessoa (
	id uuid PRIMARY KEY NOT NULL,
	nome VARCHAR(100) NOT NULL,
	apelido VARCHAR(32) NOT NULL,
	nascimento TIMESTAMP NOT NULL,
	stack VARCHAR(1024),
	busca_completa TEXT GENERATED ALWAYS as (
	    lower(nome || ' ' || apelido || ' '|| stack)
	) stored
);
CREATE EXTENSION PG_TRGM;
CREATE INDEX CONCURRENTLY IF NOT EXISTS pessoas_busca_completa_idx ON pessoa USING GIST (busca_completa GIST_TRGM_OPS(SIGLEN=128));
CREATE UNIQUE INDEX pessoa_apelido_idx ON pessoa(apelido);


--CREATE TABLE public.pessoa (
--	id uuid NOT NULL,
--	nome VARCHAR(100) NOT NULL,
--	apelido  VARCHAR(32) PRIMARY KEY NOT NULL,
--	nascimento TIMESTAMP NOT NULL,
--	stack VARCHAR(1024),
--	busca_completa TEXT GENERATED ALWAYS as (
--	    lower(replace(stack, ',', ' ') || ' ' || nome || ' ' || apelido )
--	) stored
--);
--CREATE EXTENSION PG_TRGM;
--CREATE INDEX CONCURRENTLY IF NOT EXISTS pessoas_busca_completa_idx ON pessoa USING GIN (busca_completa gin_trgm_ops) ;
-- CREATE UNIQUE INDEX pessoa_apelido_idx ON pessoa(apelido);