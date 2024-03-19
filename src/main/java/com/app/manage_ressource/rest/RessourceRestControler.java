package com.app.manage_ressource.rest;
import com.app.manage_ressource.dto.request.RessourceDtoRequest;
import com.app.manage_ressource.entities.Reponse;
import com.app.manage_ressource.services.RessourceService;
import com.app.manage_ressource.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
@CrossOrigin(origins = "*")
@RestController
public class RessourceRestControler {
	private static final Logger logger = LoggerFactory.getLogger(RessourceRestControler.class);

	@Autowired
	private RessourceService ressourceService;

	@PostMapping(Utility.RESSOURCES)
	public ResponseEntity<Reponse> getAddRegisterFolder(@RequestBody RessourceDtoRequest registerFolder){
		Reponse resultatCreation = ressourceService.create(registerFolder);

		return new ResponseEntity<Reponse>(resultatCreation, HttpStatus.OK);
    }


	@PostMapping(Utility.UPDATE_RESSOURCE)
	public  ResponseEntity<Reponse> update(@RequestBody RessourceDtoRequest registerFolder){
		Reponse resultatCreation = ressourceService.update(registerFolder);
		return new ResponseEntity<Reponse>(resultatCreation, HttpStatus.OK);
	}

	@GetMapping(Utility.GET_RESSOURCE_BY_ID)
	public  ResponseEntity<Reponse>getRegisterFolder(@PathVariable(value = "id") UUID droitID){
		Reponse	userUpdate =ressourceService.getById(droitID);
		return new ResponseEntity<Reponse>(userUpdate, HttpStatus.OK);
	}
	@GetMapping(Utility.GET_RESSOURCE_BY_USER_ID)
	public  ResponseEntity<Reponse>getRessourceByUserID(
			ServletResponse servletResponse,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size){
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
		String role=httpServletResponse.getHeader("role");
		String userID=httpServletResponse.getHeader("userID");
		Reponse	userUpdate =ressourceService.getRessourceByUserId(UUID.fromString(userID),role,page,size);
		return new ResponseEntity<Reponse>(userUpdate, HttpStatus.OK);
	}


	@GetMapping(Utility.RESSOURCES)
	public  ResponseEntity<Reponse> getAllRegisterFolders(
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(required = false) String type
	){
		Reponse	userUpdate =ressourceService.getAll( type, page, size);
		return new ResponseEntity<Reponse>(userUpdate, HttpStatus.OK);
	}

	@GetMapping(Utility.RESSOURCES_WITH_RESERVATION)
	public  ResponseEntity<Reponse> getAll(){
		Reponse	userUpdate =ressourceService.getAllReservations();
		return new ResponseEntity<Reponse>(userUpdate, HttpStatus.OK);
	}
	@GetMapping(Utility.RESSOURCES_SAMPLE)
	public  ResponseEntity<Reponse> getAllSample(){
		Reponse	userUpdate =ressourceService.getAll();
		return new ResponseEntity<Reponse>(userUpdate, HttpStatus.OK);
	}

	@GetMapping(Utility.RESSOURCES_DELETE)
	public  ResponseEntity<Reponse>delete(@PathVariable(value = "id") UUID droitID){
		Reponse	userUpdate =ressourceService.delete(droitID);
		return new ResponseEntity<Reponse>(userUpdate, HttpStatus.OK);
	}
}
