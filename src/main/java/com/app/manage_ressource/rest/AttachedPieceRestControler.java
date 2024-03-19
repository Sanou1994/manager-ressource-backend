package com.app.manage_ressource.rest;
import com.app.bank.dto.request.AttachedPieceDtoRequest;
import com.app.manage_ressource.entities.Reponse;
import com.app.manage_ressource.services.AttachedPieceService;
import com.app.manage_ressource.services.IFileService;
import com.app.manage_ressource.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
public class AttachedPieceRestControler {
	@Autowired
	private AttachedPieceService attachedPieceService;

	@Autowired
	private IFileService fileService;

	@PostMapping(Utility.ATTACHED_PIECES)
	public Reponse getAddAttachedPiece(@RequestBody AttachedPieceDtoRequest agency){
		Reponse resultatCreation = attachedPieceService.create(agency);
		return resultatCreation;
    }
	@PostMapping(Utility.UPDATE_ATTACHED_PIECE)
	public Reponse update(@RequestBody AttachedPieceDtoRequest agency){
		Reponse resultatCreation = attachedPieceService.update(agency);
		return resultatCreation;
	}

	@GetMapping(Utility.GET_ATTACHED_PIECE_BY_ID)
	public Reponse getAttachedPiece(@PathVariable(value = "id") UUID droitID){
		Reponse	userUpdate =attachedPieceService.getById(droitID);
		return userUpdate ;
	}


	@GetMapping(Utility.ATTACHED_PIECES)
	public Reponse getAllAttachedPieces(){
		Reponse	userUpdate =attachedPieceService.getAll();
		return userUpdate ;
	}

}
