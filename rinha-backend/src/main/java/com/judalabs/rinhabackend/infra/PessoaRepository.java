package com.judalabs.rinhabackend.infra;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.judalabs.rinhabackend.domain.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, UUID> {

    @Query(nativeQuery = true,
            value = "SELECT p.* FROM pessoa p " +
                    "WHERE p.apelido ILIKE CONCAT('%', :termo, '%') OR " +
                    "p.nome ILIKE CONCAT('%', :termo, '%') OR " +
                    "p.stack ILIKE CONCAT('%', :termo, '%') " +
                    "LIMIT 50")
    List<Pessoa> buscarPorTermo(@Param("termo") String termo);

}
