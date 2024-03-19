package com.app.manage_ressource.rest;
import com.app.manage_ressource.dto.request.DefinitelyDtoRequest;
import com.app.manage_ressource.dto.request.UnavailabilityDtoRequest;
import com.app.manage_ressource.dto.request.UnavailabilityRestitutionDtoRequest;
import com.app.manage_ressource.entities.Reponse;
import com.app.manage_ressource.services.DefinitelyService;
import com.app.manage_ressource.services.UnavailabilityService;
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

@RestController
public class DefinitelyRestControler {
	private static final Logger logger = LoggerFactory.getLogger(DefinitelyRestControler.class);

	@Autowired
	private DefinitelyService definitelyService;

	@PostMapping(Utility.DEFINITELY)
	public ResponseEntity<Reponse> getAddRegisterFolder(@RequestBody DefinitelyDtoRequest registerFolder){
		Reponse resultatCreation = definitelyService.create(registerFolder);
		return new ResponseEntity<Reponse>(resultatCreation, HttpStatus.OK);
    }

	@PostMapping(Utility.UPDATE_DEFINITELY)
	public  ResponseEntity<Reponse> update(@RequestBody DefinitelyDtoRequest registerFolder){
		Reponse resultatCreation = definitelyService.update(registerFolder);
		return new ResponseEntity<Reponse>(resultatCreation, HttpStatus.OK);
	}

	@GetMapping(Utility.GET_DEFINITELY_BY_ID)
	public  ResponseEntity<Reponse> getRegisterFolder(@PathVariable(value = "id") UUID droitID){
		Reponse	userUpdate =definitelyService.getById(droitID);
		return new ResponseEntity<Reponse>(userUpdate, HttpStatus.OK);
	}
	@GetMapping(Utility.GET_DEFINITELY_BY_RESSOURCE)
	public  ResponseEntity<Reponse> getDefinitelyByRessourceID(
			ServletResponse servletResponse,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size,
			@PathVariable(value = "id") UUID droitID){
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
		String role=httpServletResponse.getHeader("role");
		String userID=httpServletResponse.getHeader("userID");
		Reponse	userUpdate =definitelyService.getDefinitelyByRessourceID(droitID,UUID.fromString(userID),role,page,size);
		return new ResponseEntity<Reponse>(userUpdate, HttpStatus.OK);
	}

	@GetMapping(Utility.DEFINITELY)
	public  ResponseEntity<Reponse> getAllRegisterFolders(){
		Reponse	userUpdate =definitelyService.getAll();
		return new ResponseEntity<Reponse>(userUpdate, HttpStatus.OK);
	}



}
