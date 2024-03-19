package com.app.manage_ressource.rest;
import com.app.manage_ressource.dto.request.PlanningDtoRequest;
import com.app.manage_ressource.entities.Reponse;
import com.app.manage_ressource.services.PlanningService;
import com.app.manage_ressource.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class PlanningRestControler {
	private static final Logger logger = LoggerFactory.getLogger(PlanningRestControler.class);

	@Autowired
	private PlanningService planningService;

	@PostMapping(Utility.PLANNINGS)
	public ResponseEntity<Reponse> getAddRegisterFolder(@RequestBody PlanningDtoRequest registerFolder){
		Reponse resultatCreation = planningService.create(registerFolder);

		return new ResponseEntity<Reponse>(resultatCreation, HttpStatus.OK);
    }


	@PostMapping(Utility.UPDATE_PLANNING)
	public  ResponseEntity<Reponse> update(@RequestBody PlanningDtoRequest registerFolder){
		Reponse resultatCreation = planningService.update(registerFolder);
		return new ResponseEntity<Reponse>(resultatCreation, HttpStatus.OK);
	}

	@GetMapping(Utility.GET_PLANNING_ID)
	public  ResponseEntity<Reponse> getRegisterFolder(@PathVariable(value = "id") UUID droitID){
		Reponse	userUpdate =planningService.getById(droitID);
		return new ResponseEntity<Reponse>(userUpdate, HttpStatus.OK);
	}

	@GetMapping(Utility.GET_PLANNING_BY_RESSOURCE_ID)
	public  ResponseEntity<Reponse> getPlanningRessourceID(@PathVariable(value = "id") UUID droitID){
		Reponse	userUpdate =planningService.getPlanningByRessourceId(droitID);
		return new ResponseEntity<Reponse>(userUpdate, HttpStatus.OK);
	}
	@GetMapping(Utility.DELETE_PLANNING_ID)
	public  ResponseEntity<Reponse> delete(@PathVariable(value = "id") UUID droitID){
		Reponse	userUpdate =planningService.deleteById(droitID);
		return new ResponseEntity<Reponse>(userUpdate, HttpStatus.OK);
	}

	@GetMapping(Utility.PLANNINGS)
	public  ResponseEntity<Reponse> getAllRegisterFolders(){
		Reponse	userUpdate =planningService.getAll();
		return new ResponseEntity<Reponse>(userUpdate, HttpStatus.OK);
	}



}
