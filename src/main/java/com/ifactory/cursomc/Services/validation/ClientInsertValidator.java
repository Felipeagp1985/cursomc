package com.ifactory.cursomc.Services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.ifactory.cursomc.Services.validation.utils.BR;
import com.ifactory.cursomc.domain.Cliente;
import com.ifactory.cursomc.domain.enums.TipoCliente;
import com.ifactory.cursomc.dto.ClienteNewDTO;
import com.ifactory.cursomc.repositories.ClienteRepository;
import com.ifactory.cursomc.resources.exception.FieldMessage;

public class ClientInsertValidator implements ConstraintValidator<ClientInsert, ClienteNewDTO> {

	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClientInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();
		
		if(objDto.getTipoCliente().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCnpjOuCpf())) {
			list.add(new FieldMessage("cnpjOuCpf","CPF inválido"));
		}
		
		if(objDto.getTipoCliente().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCnpjOuCpf())) {
			list.add(new FieldMessage("cnpjOuCpf","CNPJ inválido"));
		}
		
		Cliente aux = repo.findByEmail(objDto.getEmail());
		
		if(aux != null) {
			list.add(new FieldMessage("email", "Email já existe"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
