package com.app.manage_ressource.rest;
import com.app.manage_ressource.dto.request.RessourceDtoRequest;
import com.app.manage_ressource.dto.request.UnavailabilityDtoRequest;
import com.app.manage_ressource.dto.request.UnavailabilityRestitutionDtoRequest;
import com.app.manage_ressource.entities.Reponse;
import com.app.manage_ressource.services.RessourceService;
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
public class UnavailabilityRestControler {
	private static final Logger logger = LoggerFactory.getLogger(UnavailabilityRestControler.class);

	@Autowired
	private UnavailabilityService unavailabilityService;

	@PostMapping(Utility.UNAVAILABILITIES)
	public ResponseEntity<Reponse> getAddRegisterFolder(@RequestBody UnavailabilityDtoRequest registerFolder){
		Reponse resultatCreation = unavailabilityService.create(registerFolder);

		return new ResponseEntity<Reponse>(resultatCreation, HttpStatus.OK);
    }
	@PostMapping(Utility.GET_UNAVAILABILITIE_RESTITUTION)
	public ResponseEntity<Reponse> restitution(@RequestBody UnavailabilityRestitutionDtoRequest restitution){
		Reponse resultatCreation = unavailabilityService.restitution(restitution);

		return new ResponseEntity<Reponse>(resultatCreation, HttpStatus.OK);
	}



	@PostMapping(Utility.UPDATE_UNAVAILABILITIE)
	public  ResponseEntity<Reponse> update(@RequestBody UnavailabilityDtoRequest registerFolder){
		Reponse resultatCreation = unavailabilityService.update(registerFolder);
		return new ResponseEntity<Reponse>(resultatCreation, HttpStatus.OK);
	}

	@GetMapping(Utility.GET_UNAVAILABILITIE_ID)
	public  ResponseEntity<Reponse> getRegisterFolder(@PathVariable(value = "id") UUID droitID){
		Reponse	userUpdate =unavailabilityService.getById(droitID);
		return new ResponseEntity<Reponse>(userUpdate, HttpStatus.OK);
	}
	@GetMapping(Utility.GET_UNAVAILABILITIE_BY_RESSOURCE_ID)
	public  ResponseEntity<Reponse> getUnavailabilityByRessourceID(
			ServletResponse servletResponse,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size,
			@PathVariable(value = "id") UUID droitID){
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
		String role=httpServletResponse.getHeader("role");
		String userID=httpServletResponse.getHeader("userID");
		Reponse	userUpdate =unavailabilityService.getUnavailabilityByRessourceID(droitID,UUID.fromString(userID),role,page,size);
		return new ResponseEntity<Reponse>(userUpdate, HttpStatus.OK);
	}

	@GetMapping(Utility.UNAVAILABILITIES)
	public  ResponseEntity<Reponse> getAllRegisterFolders(){
		Reponse	userUpdate =unavailabilityService.getAll();
		return new ResponseEntity<Reponse>(userUpdate, HttpStatus.OK);
	}



}
