package io.felipepoliveira.senaikeep.ws.rest.v1.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.felipepoliveira.senaikeep.excp.UnauthorizedException;
import io.felipepoliveira.senaikeep.excp.ValidationException;
import io.felipepoliveira.senaikeep.models.UserModel;
import io.felipepoliveira.senaikeep.ws.rest.AbstractRestController;
import io.felipepoliveira.senaikeep.ws.rest.v1.models.JwtAuthClientModel;
import io.felipepoliveira.senaikeep.ws.rest.v1.services.AuthService;
import io.felipepoliveira.senaikeep.ws.rest.v1.utils.AuthClientProvider;

@RestController
@RequestMapping(value = "/rest/1.0/auth")
@SuppressWarnings("rawtypes")
public class AuthController extends AbstractRestController {
	
	@Autowired
	private AuthClientProvider authClientProvider;
	
	@Autowired
	private AuthService authService;
	
	@PostMapping(value = "/jwt")
	public ResponseEntity authenticateWithJwt(@RequestBody @Valid UserModel user, BindingResult bindingResult) {
		try {
			JwtAuthClientModel jwtAuthClient =  authService.authenticateWithJWT(user, bindingResult);
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(jwtAuthClient);
			
			
		} catch (ValidationException e) {
			return ResponseEntity
					.status(HttpStatus.UNPROCESSABLE_ENTITY)
					.body(this.bindingResultAsMap(bindingResult));
			
		} catch (UnauthorizedException e) {
			return ResponseEntity
					.status(HttpStatus.UNAUTHORIZED)
					.build();
			
		} catch (Exception e) {
			e.printStackTrace();
			
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.build();
		}
	}
	
	@PatchMapping(value = "/jwt")
	public ResponseEntity renewJwtMinimumRequiredDate() {
		try {
			authService.renewJwtMinimumRequiredDate(authClientProvider.getAuthClient());
			
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
}
