package com.judalabs.rinhabackend.infra;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.judalabs.rinhabackend.domain.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, UUID> {

    @Query(nativeQuery = true,
            value = "SELECT DISTINCT p.* FROM pessoas p " +
                    "LEFT JOIN pessoas_stacks ps ON p.id = ps.pessoa_id " +
                    "WHERE p.apelido ILIKE CONCAT('%', :searchTerm, '%') OR " +
                    "p.nome ILIKE CONCAT('%', :termo, '%') OR " +
                    "ps.stack ILIKE CONCAT('%', :termo, '%') " +
                    "LIMIT 50")
    List<Pessoa> buscarPorTermo(@Param("termo") String termo);

}
