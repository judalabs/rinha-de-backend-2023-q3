package com.judalabs.rinhabackend.infra;

import java.util.UUID;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.judalabs.rinhabackend.domain.Pessoa;

import reactor.core.publisher.Flux;

@Repository
public interface PessoaRepository extends ReactiveCrudRepository<Pessoa, UUID> {

    @Query(value = " SELECT p.* FROM pessoa p " +
                    " WHERE p.busca_completa LIKE CONCAT('%', :termo, '%')" +
                    " LIMIT 50")
    Flux<Pessoa> buscarPorTermo(@Param("termo") String termo);

}
