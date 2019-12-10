package com.levimartines.cursomc.service.validation;

import com.levimartines.cursomc.bean.ClienteBean;
import com.levimartines.cursomc.handler.FieldMessage;
import com.levimartines.cursomc.model.Cliente;
import com.levimartines.cursomc.repository.ClienteRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

@Slf4j
public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteBean> {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void initialize(ClienteUpdate ann) {
    }

    @Override
    public boolean isValid(ClienteBean objBean, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Long uriId = Long.parseLong(map.get("id"));

        Cliente cliente = clienteRepository.findByEmail(objBean.getEmail());
        if (clienteRepository.findByEmail(objBean.getEmail())!=null
        && !cliente.getId().equals(uriId)) {
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