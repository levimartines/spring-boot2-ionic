package com.levimartines.cursomc.service.validation;

import com.levimartines.cursomc.bean.ClienteNewBean;
import com.levimartines.cursomc.enums.TipoCliente;
import com.levimartines.cursomc.handler.FieldMessage;
import com.levimartines.cursomc.repository.ClienteRepository;
import com.levimartines.cursomc.service.validation.utils.BR;
import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewBean> {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void initialize(ClienteInsert ann) {
    }

    @Override
    public boolean isValid(ClienteNewBean objDto, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR
            .isValidCPF(objDto.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
        }

        if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR
            .isValidCNPJ(objDto.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
        }

        if (clienteRepository.findByEmail(objDto.getEmail())!=null) {
            list.add(new FieldMessage("email", "Email já existente"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMsg())
                .addPropertyNode(e.getFieldName())
                .addConstraintViolation();
        }
        return list.isEmpty();
    }
}