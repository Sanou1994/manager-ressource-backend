package com.app.manage_ressource.services;

import com.app.manage_ressource.dto.request.UnavailabilityDtoRequest;
import com.app.manage_ressource.dto.request.UnavailabilityRestitutionDtoRequest;
import com.app.manage_ressource.dto.response.RessourceDtoResponse;
import com.app.manage_ressource.dto.response.UnavailabilityDtoResponse;
import com.app.manage_ressource.entities.*;
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
public class UnavailabilityService implements CrudService <UnavailabilityDtoRequest>
{
    private static final Logger logger = LoggerFactory.getLogger(UnavailabilityService.class);
    @Autowired
    private IDaoUnavailability unavailabilityRepository;
    @Autowired
    private IDaoRessource daoRessource;

    @Autowired(required=true)
    private ModelMapper modelMapper;
    @Override
    public Reponse create(UnavailabilityDtoRequest obj) {

        Reponse reponse = new Reponse();
        try
        {
        Unavailability attachedPieceConverted =modelMapper.map(obj, Unavailability.class);
        daoRessource.findById(obj.getRessourceID()).ifPresentOrElse(
                ressource -> {
                    if(ressource.getCurrentCount() >= attachedPieceConverted.getCount()){
                        ressource.setDemandCount(ressource.getDemandCount()+1);
                        ressource.setCurrentCount((int) (ressource.getCurrentCount()-attachedPieceConverted.getCount()));
                        daoRessource.save(ressource);
                        attachedPieceConverted.setStatus(true);
                        attachedPieceConverted.setEtat(RestitutionEnum.NON_RESTITUTION);
                        Unavailability userSave = unavailabilityRepository.save(attachedPieceConverted);
                        reponse.setData(modelMapper.map(userSave,UnavailabilityDtoResponse.class));
                        reponse.setMessage("Cette insdisponibilité a été enregistrée avec succès");
                        logger.error("Cette insdisponibilité a été enregistrée avec succès ");
                        reponse.setCode(200);

                    }else{
                        logger.error(" impossible de faire cette demande : ");
                        reponse.setCode(201);
                        reponse.setMessage("impossible de faire cette demande");
                    }

                },
                () -> {
                    logger.error(" impossible de faire cette demande");
                    reponse.setCode(201);
                    reponse.setMessage("impossible de faire cette demande");

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
    public Reponse update(UnavailabilityDtoRequest obj) {
        Reponse reponse = new Reponse();
        try
        {

            if(obj.getId() != null)
            {
                Optional<Unavailability> userById = unavailabilityRepository.findById(obj.getId());


                userById.get().setStatus(true);
                Unavailability userSave = unavailabilityRepository.save(userById.get());
                UnavailabilityDtoResponse agencyDtoResponse=modelMapper.map(userSave, UnavailabilityDtoResponse.class);
                reponse.setData(agencyDtoResponse);
                reponse.setMessage("Cette insdisponibilité a été modifiée avec succès");
                reponse.setCode(200);
                logger.error(" Cette insdisponibilité a été modifiée avec succès  " );


            }
            else {
                logger.error(" Cette insdisponibilité n'existe pas  ");
                reponse.setMessage(" Cette insdisponibilité n'existe pas");
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
            List<UnavailabilityDtoResponse> users = unavailabilityRepository.findAll()
                    .stream()
                    .map( v->{

                          var attached= modelMapper.map(v, UnavailabilityDtoResponse.class);
                           return attached;
                            }).collect(Collectors.toList());
            reponse.setData(users);
            reponse.setMessage("Listes des insdisponibilités");
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
            Optional<Unavailability> user = unavailabilityRepository.findById(ID);


            if(user.isPresent())
            {
                UnavailabilityDtoResponse userConverted =modelMapper.map(user.get(), UnavailabilityDtoResponse.class);
                reponse.setData(userConverted);
                reponse.setMessage("Cette insdisponibilité a été retrouvé avec succès");
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

    public Reponse getUnavailabilityByRessourceID(UUID ressourceID,UUID userID,String role,int page,int size) {
        int skipCount = (page - 1) * size;
        AtomicInteger my_size= new AtomicInteger(0);
        HashMap<String, Object> datas  = new HashMap<String, Object>();
        Reponse reponse = new Reponse();
        try
        {
            List<UnavailabilityDtoResponse> dtos = unavailabilityRepository.findByRessourceID(ressourceID)
            .stream()
            .filter(m->role.equals(UtilisateurEnum.ADMINISTRATEUR.name())
                            || (role.equals(UtilisateurEnum.UTILISATEUR.name()) && m.getTakerIdentificant().compareTo(userID)==0)
             )
            .peek(o-> my_size.getAndIncrement())
            .map(p->modelMapper.map(p, UnavailabilityDtoResponse.class) )
            .sorted(Comparator.comparing(UnavailabilityDtoResponse::getCreatedOn).reversed())
          //  .skip(skipCount)
          //  .limit(size)
            .collect(Collectors.toList());

            datas.put("totals",my_size);
            datas.put("data", dtos);
            reponse.setData(dtos);
                reponse.setMessage("Cette insdisponibilité a été retrouvé avec succès");
                reponse.setCode(200);
        }
        catch (Exception e) {
            logger.error(" une exception est survenue "+e.getMessage());
            reponse.setMessage("Une erreur interne est survenue");
            reponse.setCode(500);

        }

        return reponse;
    }

    public Reponse restitution(UnavailabilityRestitutionDtoRequest restitution) {
        Reponse reponse = new Reponse();
        try
        {
            unavailabilityRepository.findById(restitution.getId()).ifPresentOrElse(
                    unavailability -> {
                        daoRessource.findById(unavailability.getRessourceID()).ifPresentOrElse(
                                ressource -> {
                                    if(restitution.getCount() <= unavailability.getCount())
                                    {
                                        unavailability.setEtat( (unavailability.getCount() ==  restitution.getCount()) ? RestitutionEnum.TOTAL_RESTITUTION : RestitutionEnum.PARTIEL_RESTITUTION);
                                        ressource.setDemandCount( (unavailability.getCount() ==  restitution.getCount()) ? ressource.getDemandCount()-1  : ressource.getDemandCount());
                                        unavailability.setCount(unavailability.getCount() -restitution.getCount());
                                        unavailability.setCount_given(unavailability.getCount_given() +restitution.getCount());
                                        unavailability.setDate_restitution(restitution.getDate_restitution());
                                        ressource.setCurrentCount((int) (ressource.getCurrentCount()+restitution.getCount()));
                                        unavailabilityRepository.save(unavailability);
                                        daoRessource.save(ressource);
                                        reponse.setData(ressource);
                                        reponse.setMessage("Cette remise a été reussi avec succès");
                                        reponse.setCode(200);

                                    }
                                    else
                                    {
                                        logger.error(" impossible de faire cette remise");
                                        reponse.setCode(201);
                                        reponse.setMessage("impossible de faire cette remise");

                                    }},() ->{
                                    logger.error(" impossible de faire cette remise");
                                    reponse.setCode(201);
                                    reponse.setMessage("impossible de faire cette remise");
                                }
                        );

                    },() -> {
                        logger.error(" impossible de faire cette remise");
                        reponse.setCode(201);
                        reponse.setMessage("impossible de faire cette remise");

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
}
