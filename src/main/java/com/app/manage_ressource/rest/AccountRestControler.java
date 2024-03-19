package com.app.manage_ressource.rest;
import com.app.manage_ressource.dto.request.UserDtoRequest;
import com.app.manage_ressource.entities.ChangePasswordDtoRequest;
import com.app.manage_ressource.entities.Login;
import com.app.manage_ressource.entities.Reponse;
import com.app.manage_ressource.entities.UtilisateurEnum;
import com.app.manage_ressource.services.UserService;
import com.app.manage_ressource.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class AccountRestControler {
	@Autowired
	private UserService accountService;


	@PostMapping(Utility.USERS)
	public Reponse getAddUser(@RequestBody UserDtoRequest user){
		Reponse resultatCreation = accountService.create(user);
		return resultatCreation;
    }

	@PostMapping(Utility.DO_LOGIN)
	public Reponse Login(@RequestBody Login login){

		Reponse resultatCreation = accountService.login_in(login);

		return resultatCreation;
	}
	@PostMapping(Utility.UPDATE_PASSWORD)
	public Reponse updatePwd(@PathVariable(value = "id") UUID userID ,@RequestBody ChangePasswordDtoRequest changePasswordDtoRequest){
		changePasswordDtoRequest.setId(userID);
		Reponse resultatCreation = accountService.changePassword(changePasswordDtoRequest);

		return resultatCreation;
	}
	@PostMapping(Utility.UPDATE_USER)
	public Reponse updateUser( @RequestBody UserDtoRequest user){
		Reponse updateDroit = accountService.update(user);
		return updateDroit;
    }

	@GetMapping(Utility.USERS)
	public Reponse geUsers(){
		Reponse resultatCreation = accountService.getAll();
		return resultatCreation;
	}
	@GetMapping(Utility.USER_ALLS)
	public Reponse geUserAll(
			@RequestParam(defaultValue = "20", required = false) int size,
			@RequestParam(defaultValue = "1", required = false) int page,
			@RequestParam(defaultValue = "0", required = false) UtilisateurEnum role,
			@RequestParam(defaultValue = "true", required = false) boolean active){
		Reponse resultatCreation = accountService.getAll(page, size, active, role);
		return resultatCreation;
	}
	@GetMapping(Utility.DELETE_USER_BY_ID)
	public Reponse lockUser(@PathVariable(value = "id") UUID droitID){
		Reponse	userUpdate =accountService.lockUser(droitID);
		return userUpdate ;
	}




}
