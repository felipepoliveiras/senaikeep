package io.felipepoliveira.senaikeep.ws.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public abstract class AbstractRestController {
	
	public URI uri(String path) {
		try {
			return new URI(path);
		} catch (URISyntaxException e) {
			return null;
		}
	}
	
	public Map<String, String> bindingResultAsMap(BindingResult bindingResult){
		Map<String, String> map = new HashMap<>();
		for (FieldError fieldError: bindingResult.getFieldErrors()) {
			map.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		
		return map;
	}
}
