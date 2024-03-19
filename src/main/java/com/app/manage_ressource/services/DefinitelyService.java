package com.app.manage_ressource.services;

import com.app.manage_ressource.dto.request.DefinitelyDtoRequest;
import com.app.manage_ressource.dto.request.UnavailabilityDtoRequest;
import com.app.manage_ressource.dto.request.UnavailabilityRestitutionDtoRequest;
import com.app.manage_ressource.dto.response.DefinitelyDtoResponse;
import com.app.manage_ressource.dto.response.UnavailabilityDtoResponse;
import com.app.manage_ressource.entities.*;
import com.app.manage_ressource.repositories.IDaoDefinitely;
import com.app.manage_ressource.repositories.IDaoRessource;
import com.app.manage_ressource.repositories.IDaoUnavailability;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class DefinitelyService implements CrudService <DefinitelyDtoRequest>
{
    private static final Logger logger = LoggerFactory.getLogger(DefinitelyService.class);
    @Autowired
    private IDaoDefinitely definitelyRepository;
    @Autowired
    private IDaoRessource daoRessource;

    @Autowired(required=true)
    private ModelMapper modelMapper;
    @Override
    public Reponse create(DefinitelyDtoRequest obj) {

        Reponse reponse = new Reponse();
        try
        {
            Definitely attachedPieceConverted =modelMapper.map(obj, Definitely.class);
        daoRessource.findById(obj.getRessourceID()).ifPresentOrElse(
                ressource -> {
                    if(ressource.getCurrentCount() >= attachedPieceConverted.getCount()){
                        ressource.setStatus( (ressource.getCurrentCount() == attachedPieceConverted.getCount()) ? false :true);
                        ressource.setDemandCount(ressource.getDemandCount()+1);
                        ressource.setCurrentCount((int) (ressource.getCurrentCount()-attachedPieceConverted.getCount()));
                        daoRessource.save(ressource);
                        attachedPieceConverted.setStatus(true);
                        Definitely userSave = definitelyRepository.save(attachedPieceConverted);
                        reponse.setData(modelMapper.map(userSave, DefinitelyDtoResponse.class));
                        reponse.setMessage("Cet enregistrement a reussi avec succès");
                        logger.error("Cet enregistrement a reussi avec succès");
                        reponse.setCode(200);

                    }else{
                        logger.error(" impossible de faire Cet enregistrement : ");
                        reponse.setCode(201);
                        reponse.setMessage("impossible de faire Cet enregistrement");
                    }

                },
                () -> {
                    logger.error(" impossible de faire Cet enregistrement");
                    reponse.setCode(201);
                    reponse.setMessage("impossible de faire Cet enregistrement");

                }
        );




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
    public Reponse update(DefinitelyDtoRequest obj) {
        Reponse reponse = new Reponse();
        try
        {

            if(obj.getId() != null)
            {
                Optional<Definitely> userById = definitelyRepository.findById(obj.getId());


                userById.get().setStatus(true);
                Definitely userSave = definitelyRepository.save(userById.get());
                DefinitelyDtoResponse agencyDtoResponse=modelMapper.map(userSave, DefinitelyDtoResponse.class);
                reponse.setData(agencyDtoResponse);
                reponse.setMessage("Cette modification a été reussie avec succès");
                reponse.setCode(200);
                logger.error(" Cette modification a été reussie avec succès  " );


            }
            else {
                logger.error(" Cette ressource n'existe pas  ");
                reponse.setMessage(" Cette ressource n'existe pas");
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
            List<DefinitelyDtoResponse> users = definitelyRepository.findAll()
                    .stream()
                    .map( v->{

                          var attached= modelMapper.map(v, DefinitelyDtoResponse.class);
                           return attached;
                            }).collect(Collectors.toList());
            reponse.setData(users);
            reponse.setMessage("Listes des ressources");
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
            Optional<Definitely> user = definitelyRepository.findById(ID);


            if(user.isPresent())
            {
                DefinitelyDtoResponse userConverted =modelMapper.map(user.get(), DefinitelyDtoResponse.class);
                reponse.setData(userConverted);
                reponse.setMessage("Cette ressource a été retrouvé avec succès");
                reponse.setCode(200);

            }
            else
            {
                reponse.setMessage("Cette insdisponibilité n'existe pas");
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

    public Reponse getDefinitelyByRessourceID(UUID ressourceID,UUID userID,String role,int page,int size) {
        int skipCount = (page - 1) * size;
        AtomicInteger my_size= new AtomicInteger(0);
        HashMap<String, Object> datas  = new HashMap<String, Object>();
        Reponse reponse = new Reponse();
        try
        {
            List<DefinitelyDtoResponse> dtos = definitelyRepository.findByRessourceID(ressourceID)
                    .stream()
                    .filter(m->role.equals(UtilisateurEnum.ADMINISTRATEUR.name())
                            || (role.equals(UtilisateurEnum.UTILISATEUR.name()) && m.getTakerIdentificant().compareTo(userID)==0)
                    )
                    .peek(o-> my_size.getAndIncrement())
                    .map(p->modelMapper.map(p, DefinitelyDtoResponse.class) )
                    .sorted(Comparator.comparing(DefinitelyDtoResponse::getCreatedOn).reversed())
                    //  .skip(skipCount)
                    //  .limit(size)
                    .collect(Collectors.toList());

            datas.put("totals",my_size);
            datas.put("data", dtos);
            reponse.setData(dtos);
            reponse.setMessage("Cette demande a reussi succès");
            reponse.setCode(200);
        }
        catch (Exception e) {
            logger.error(" une exception est survenue "+e.getMessage());
            reponse.setMessage("Une erreur interne est survenue");
            reponse.setCode(500);

        }

        return reponse;
    }
}
