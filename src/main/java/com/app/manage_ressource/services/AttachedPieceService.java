package com.app.manage_ressource.services;

import com.app.bank.dto.request.AttachedPieceDtoRequest;
import com.app.manage_ressource.dto.response.AttachedPieceDtoResponse;
import com.app.manage_ressource.entities.AttachedPiece;
import com.app.manage_ressource.entities.Reponse;
import com.app.manage_ressource.repositories.IDaoAttachedPiece;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AttachedPieceService implements CrudService <AttachedPieceDtoRequest>
{
    private static final Logger logger = LoggerFactory.getLogger(AttachedPieceService.class);
    @Autowired
    private IDaoAttachedPiece attachedPieceRepository;
    @Autowired
    private IFileService fileService;
    @Autowired(required=true)
    private ModelMapper modelMapper;
    @Override
    public Reponse create(AttachedPieceDtoRequest obj) {
        Reponse reponse = new Reponse();

        try
        {

        AttachedPiece attachedPieceConverted =modelMapper.map(obj, AttachedPiece.class);
        attachedPieceConverted.setActive(true);
        AttachedPiece userSave = attachedPieceRepository.save(attachedPieceConverted);
        reponse.setData(modelMapper.map(userSave, AttachedPieceDtoResponse.class));
        reponse.setMessage("Cette pièce a été enregistrée avec succès");
        logger.error("Cette pièce a été enregistrée avec succès ");
        reponse.setCode(200);
        }
        catch (Exception e)
        {
            logger.error(" une exception est survenue "+e.getMessage());
            reponse.setCode(500);
            reponse.setMessage("Un problème de serveur  !");
        }

        return reponse ;
    }

    @Override
    public Reponse update(AttachedPieceDtoRequest obj) {
        Reponse reponse = new Reponse();
        try
        {

            if(obj.getId() != null)
            {
                Optional<AttachedPiece> userById = attachedPieceRepository.findById(obj.getId());
                if(obj.getTitle() != null && !obj.getTitle().equals(userById.get().getTitle()))
                {
                    userById.get().setTitle(obj.getTitle());
                }

                if(obj.getRegisterFolderID() != null && obj.getRegisterFolderID().compareTo(userById.get().getRegisterFolderID()) != 0)
                {
                    userById.get().setRegisterFolderID(obj.getRegisterFolderID());
                }

                userById.get().setActive(true);
                AttachedPiece userSave = attachedPieceRepository.save(userById.get());
                AttachedPieceDtoResponse agencyDtoResponse=modelMapper.map(userSave, AttachedPieceDtoResponse.class);
                reponse.setData(agencyDtoResponse);
                reponse.setMessage("Cette pièce a été modifiée avec succès");
                reponse.setCode(200);
                logger.error(" Cette pièce a été modifiée avec succès  " +userById.get().getTitle());


            }
            else {
                logger.error(" Cette pièce n'existe pas  ");
                reponse.setMessage(" Cette pièce n'existe pas");
                reponse.setCode(201);
            }


        }
        catch (Exception e)
        {
            logger.error(" une exception est survenue "+e.getMessage());
            reponse.setCode(500);
            reponse.setMessage("Une erreur interne est survenue coté serveur  !");
        }

        return reponse ;
    }

    @Override
    public Reponse getAll() {
        Reponse reponse = new Reponse();
        try
        {
            List<AttachedPieceDtoResponse> users = attachedPieceRepository.findAll()
                    .stream()
                    .map( v->{
                       //  var rep=fileService.loadFileAsResource(v.getFilename());

                          var attached= modelMapper.map(v, AttachedPieceDtoResponse.class);
                        //    attached.setResource( rep.getCode() == 200 ? (Resource) rep.getData() : null );
                           return attached;
                            }).collect(Collectors.toList());
            reponse.setData(users);
            reponse.setMessage("Listes des pièces");
            reponse.setCode(200);


        }
        catch (Exception e) {
            logger.error(" une exception est survenue "+e.getMessage());
            reponse.setMessage("Une erreur interne est survenue");
            reponse.setCode(500);

        }

        return reponse;
    }

    @Override
    public Reponse getById(UUID ID) {
        Reponse reponse = new Reponse();
        try
        {
            Optional<AttachedPiece> user = attachedPieceRepository.findById(ID);


            if(user.isPresent())
            {
                AttachedPieceDtoResponse userConverted =modelMapper.map(user.get(), AttachedPieceDtoResponse.class);
                reponse.setData(userConverted);
                reponse.setMessage("Cette pièce a été retrouvé avec succès");
                reponse.setCode(200);

            }
            else
            {
                reponse.setMessage("Cet avis n'existe pas");
                reponse.setCode(201);

            }


        }
        catch (Exception e) {
            logger.error(" une exception est survenue "+e.getMessage());
            reponse.setMessage("Une erreur interne est survenue");
            reponse.setCode(500);

        }

        return reponse;
    }
}
