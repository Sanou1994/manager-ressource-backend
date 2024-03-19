package com.app.manage_ressource.rest;
import com.app.manage_ressource.dto.request.RessourceDtoRequest;
import com.app.manage_ressource.dto.request.TypeRessourceDtoRequest;
import com.app.manage_ressource.entities.Reponse;
import com.app.manage_ressource.services.RessourceService;
import com.app.manage_ressource.services.TypeRessourceService;
import com.app.manage_ressource.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;

@RestController
public class TypeRessourceRestControler {
	private static final Logger logger = LoggerFactory.getLogger(TypeRessourceRestControler.class);

	@Autowired
	private TypeRessourceService typeRessourceService;

	@PostMapping(Utility.TYPERESSOURCES)
	public ResponseEntity<Reponse> getAddRegisterFolder(@RequestBody TypeRessourceDtoRequest registerFolder){
		Reponse resultatCreation = typeRessourceService.create(registerFolder);

		return new ResponseEntity<Reponse>(resultatCreation, HttpStatus.OK);
    }


	@PostMapping(Utility.UPDATE_TYPERESSOURCE)
	public  ResponseEntity<Reponse> update(@RequestBody TypeRessourceDtoRequest registerFolder){
		Reponse resultatCreation = typeRessourceService.update(registerFolder);
		return new ResponseEntity<Reponse>(resultatCreation, HttpStatus.OK);
	}

	@GetMapping(Utility.GET_TYPERESSOURCE_BY_ID)
	public  ResponseEntity<Reponse>getRegisterFolder(@PathVariable(value = "id") UUID droitID){
		Reponse	userUpdate =typeRessourceService.getById(droitID);
		return new ResponseEntity<Reponse>(userUpdate, HttpStatus.OK);
	}


	@GetMapping(Utility.TYPERESSOURCES)
	public  ResponseEntity<Reponse> getAllRegisterFolders(
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(required = false) String type
	){
		Reponse	userUpdate =typeRessourceService.getAll( type, page, size);
		return new ResponseEntity<Reponse>(userUpdate, HttpStatus.OK);
	}


	@GetMapping(Utility.TYPERESSOURCE_ALL)
	public  ResponseEntity<Reponse> getAlls(){

		Reponse	userUpdate =typeRessourceService.getAll();
		return new ResponseEntity<Reponse>(userUpdate, HttpStatus.OK);
	}

}
