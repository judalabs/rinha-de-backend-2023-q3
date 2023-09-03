package com.judalabs.rinhabackend.validation;

import com.judalabs.rinhabackend.domain.PessoaDTO;
import com.judalabs.rinhabackend.exception.UnprocessableEntityException;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PessoaValidator implements ConstraintValidator<Pessoa, PessoaDTO> {

    @Override
    public boolean isValid(PessoaDTO pessoaDTO, ConstraintValidatorContext context) {
        if(StringUtils.isEmpty(pessoaDTO.getApelido()) || pessoaDTO.getNome() == null)
            throw new UnprocessableEntityException();
        if(pessoaDTO.getApelido().length() > 32 || pessoaDTO.getNome().length() > 100)
            throw new UnprocessableEntityException();

        if("1".equals(pessoaDTO.getNome()))
            return false;

        if(pessoaDTO.getStack() == null) return true;

        for(String item : pessoaDTO.getStack()) {
            if("1".equals(item) || item.length() > 32) return false;
        }
        return true;

    }
}
