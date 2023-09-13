package com.judalabs.rinhabackend.infra;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.judalabs.rinhabackend.domain.PessoaDTO;

@Service
public class BatchInsertService {

    private static final String sql = "INSERT INTO pessoa (id, nome, apelido, nascimento, stack) VALUES (?, ?, ?, ?, ?)";
    private final JdbcTemplate jdbcTemplate;

    public BatchInsertService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Lazy
    public void salvaSeEstaCheio(ValueOperations<Boolean, List<PessoaDTO>> valueOperations) {
        final List<PessoaDTO> pessoaDTOS = valueOperations.get(true);
        if(pessoaDTOS != null && pessoaDTOS.size() == 500) {
            extracted(pessoaDTOS);
            valueOperations.set(true, new ArrayList<>());
        }
    }

    private void extracted(List<PessoaDTO> pessoaDTOS) {
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                PessoaDTO objeto = pessoaDTOS.get(i);
                ps.setObject(1, objeto.id());
                ps.setString(2, objeto.nome());
                ps.setString(3, objeto.apelido());
                ps.setObject(4, objeto.nascimento());
                ps.setString(5, PessoaDTO.getStackString(objeto));
            }

            @Override
            public int getBatchSize() {
                return pessoaDTOS.size();
            }
        });
    }
}
