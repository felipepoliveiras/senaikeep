package io.felipepoliveira.senaikeep.services;

import org.springframework.validation.BindingResult;

import io.felipepoliveira.senaikeep.excp.ValidationException;

public class AbstractService {
	
	/**
	 * Verify if the given binding result contains validation errors. If positive, it throws an {@link ValidationException}
	 * @param bindingResult - The verified binding result
	 * @throws ValidationException
	 */
	public void validate(BindingResult bindingResult) throws ValidationException {
		this.validate(bindingResult, new String[0]);
	}
	
	/**
	 * Verify if the given binding result contains validation errors. If positive, it throws an {@link ValidationException}
	 * @param bindingResult - The verified binding result
	 * @param fields - The fields to validate [optional]
	 * @throws ValidationException
	 */
	public void validate(BindingResult bindingResult, String...fields) throws ValidationException {
		//If does not has errors return nothing
		if(!bindingResult.hasErrors()) {
			return;
		}
		
		//If any fields was specified to validate, throw exception, otherwise check only specific fields for errors
		if(fields.length == 0) {
			throw new ValidationException("Errors was found in validation.");
		}else {
			for (String field : fields) {
				if(bindingResult.hasFieldErrors(field)) {
					throw new ValidationException("An validation error was found for field " + field);
				}
			}
		}
	}
}
