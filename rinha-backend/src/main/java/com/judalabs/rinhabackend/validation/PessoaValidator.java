package com.judalabs.rinhabackend.validation;

import com.judalabs.rinhabackend.domain.PessoaDTO;
import com.judalabs.rinhabackend.exception.UnprocessableEntityException;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PessoaValidator implements ConstraintValidator<PessoaValida, PessoaDTO> {

    @Override
    public boolean isValid(PessoaDTO pessoaDTO, ConstraintValidatorContext context) {
        if(pessoaDTO.apelido() == null || pessoaDTO.nome() == null)
            throw new UnprocessableEntityException();
        if(pessoaDTO.apelido().length() > 32 || pessoaDTO.nome().length() > 100)
            throw new UnprocessableEntityException();

        if("1".equals(pessoaDTO.nome()))
            return false;

        if(pessoaDTO.stack() == null) return true;

        for(String item : pessoaDTO.stack()) {
            if("1".equals(item) || item.length() > 32) return false;
        }
        return true;

    }
}
