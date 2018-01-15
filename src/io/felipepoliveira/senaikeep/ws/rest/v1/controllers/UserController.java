package io.felipepoliveira.senaikeep.ws.rest.v1.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.felipepoliveira.senaikeep.excp.ConflictException;
import io.felipepoliveira.senaikeep.excp.ValidationException;
import io.felipepoliveira.senaikeep.models.UserModel;
import io.felipepoliveira.senaikeep.services.UserService;
import io.felipepoliveira.senaikeep.ws.rest.AbstractRestController;

@RestController
@RequestMapping(value = "/rest/1.0/users")
@SuppressWarnings("rawtypes")
public class UserController extends AbstractRestController{
	
	@Autowired
	private UserService userService;
	
	/**
	 * 
	 * <h2>Possible responses</h2>
	 * <table border="1">
	 * <tr>
	 * 	<th>Response code</th>
	 * 	<th>Description</th>
	 * </tr>
	 * <tbody>
	 * 	<tr>
	 * 		<td>201</td>
	 * 		<td>If created successfully</td>
	 * 	</tr>
	 *	<tr>
	 * 		<td>409</td>
	 * 		<td>If the email is already in use</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>422</td>
	 * 		<td>If the user attributes has validation errors</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>500</td>
	 * 		<td>If not expected error occurs in the server</td>
	 * 	</tr>
	 * </tbody>
	 * </table>
	 * @param user
	 * @param bindingResult
	 * @return
	 */
	@PostMapping
	public ResponseEntity register(@RequestBody @Valid UserModel user, BindingResult bindingResult) {
		try {
			userService.register(user, bindingResult);
			
			return ResponseEntity
					.created(null)
					.build();
			
		} catch (ValidationException e) {
			return ResponseEntity
					.status(HttpStatus.UNPROCESSABLE_ENTITY)
					.body(this.bindingResultAsMap(bindingResult));
			
		} catch (ConflictException e) {
			return ResponseEntity
					.status(HttpStatus.CONFLICT)
					.body(this.bindingResultAsMap(bindingResult));
			
		}catch (Exception e) {
			e.printStackTrace();
			
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.build();
		}
	}
}
