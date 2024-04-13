package com.app.manage_ressource.services;

import com.app.manage_ressource.dto.request.PlanningDtoRequest;
import com.app.manage_ressource.dto.request.RessourceDtoRequest;
import com.app.manage_ressource.dto.response.PlanningDtoResponse;
import com.app.manage_ressource.dto.response.RessourceDtoResponse;
import com.app.manage_ressource.entities.Planning;
import com.app.manage_ressource.entities.Reponse;
import com.app.manage_ressource.entities.Ressource;
import com.app.manage_ressource.repositories.IDaoPlanning;
import com.app.manage_ressource.repositories.IDaoRessource;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PlanningService implements CrudService <PlanningDtoRequest>
{
    private static final Logger logger = LoggerFactory.getLogger(PlanningService.class);
    @Autowired
    private IDaoPlanning planningRepository;
    @Autowired
    private IDaoRessource ressourceRepository;
    @Autowired(required=true)
    private ModelMapper modelMapper;
    @Override
    public Reponse create(PlanningDtoRequest obj) {
        Reponse reponse = new Reponse();

        try
        {
            if(obj.getRessourceId() !=null ) {
                Ressource ressource = ressourceRepository.findById(obj.getRessourceId()).get();
                Planning planningConverted =modelMapper.map(obj, Planning.class);
                planningConverted.setActive(true);
                long duration=planningConverted.getEndDate() -planningConverted.getStartDate();
                double hours= (duration/3600000.00);
                double days= (hours/24.00);

                if(ressource.isPaidByDays())
                {
                    planningConverted.setMount(Math.round(ressource.getMount()*days));
                }
                else if(ressource.isPaidByHours())
                {
                    planningConverted.setMount(Math.round(ressource.getMount()*hours));

                }
                else {
                    planningConverted.setMount(0);
                }


                Planning planningSave = planningRepository.save(planningConverted);
                reponse.setData(modelMapper.map(planningSave, PlanningDtoResponse.class));
                reponse.setMessage("Ce planning a été enregistré avec succès");
                logger.error("Ce planning a été enregistré avec succès ");
                reponse.setCode(200);


            }
            else
            {
                logger.error(" Veuillez choisir une ressource ");
                reponse.setCode(201);
                reponse.setMessage("Veuillez choisir une ressource  !");
            }



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
    public Reponse update(PlanningDtoRequest obj) {
        Reponse reponse = new Reponse();
        try
        {

            if(obj.getId() != null && obj.getRessourceId() !=null )
            {
                Ressource ressource= ressourceRepository.findById(obj.getRessourceId()).get();
                Optional<Planning> userById = planningRepository.findById(obj.getId());

                    userById.get().setActive(true);
                    if(obj.getStartDate() != 0 && obj.getStartDate() != userById.get().getStartDate())
                    {
                        userById.get().setStartDate(obj.getStartDate());
                    }
                    if(obj.getEndDate() != 0 && obj.getEndDate() != userById.get().getEndDate())
                    {
                        userById.get().setEndDate(obj.getEndDate());
                    }
                    if(obj.getMount() != 0 && obj.getMount() != userById.get().getMount())
                    {
                        userById.get().setMount(obj.getMount());
                    }
                    if(obj.getTarget() != null && !obj.getTarget().equals(userById.get().getTarget()))
                    {
                        userById.get().setTarget(obj.getTarget());
                    }
                    if(obj.getRegisterId() != null && obj.getRegisterId().compareTo(userById.get().getRegisterId()) ==0)
                    {
                        userById.get().setRegisterId(obj.getRegisterId());
                    }
                    long duration=userById.get().getEndDate() -userById.get().getStartDate();
                    double hours= (duration/3600000.00);
                    double days= (hours/24.00);

                    if(ressource.isPaidByDays())
                    {
                        userById.get().setMount(Math.round(ressource.getMount()*days));
                    }
                    else if(ressource.isPaidByHours())
                    {
                        userById.get().setMount(Math.round(ressource.getMount()*hours));

                    }
                    else {
                        userById.get().setMount(0);
                    }
                    Planning userSave = planningRepository.save(userById.get());
                    PlanningDtoResponse agencyDtoResponse=modelMapper.map(userSave, PlanningDtoResponse.class);
                    reponse.setData(agencyDtoResponse);
                    reponse.setMessage("Ce planning a été modifié avec succès");
                    reponse.setCode(200);
                    logger.error(" Ce planning a été modifié avec succès  ");
                }


            else {
                logger.error("Ce planning n'existe pas  ");
                reponse.setMessage("Ce planning n'existe pas");
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
            List<PlanningDtoResponse> users = planningRepository.findAll()
                    .stream()
                    .map( v->{
                       //  var rep=fileService.loadFileAsResource(v.getFilename());

                          var attached= modelMapper.map(v, PlanningDtoResponse.class);
                        //    attached.setResource( rep.getCode() == 200 ? (Resource) rep.getData() : null );
                           return attached;
                            }).collect(Collectors.toList());
            reponse.setData(users);
            reponse.setMessage("Listes des plannings");
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
            Optional<Planning> user = planningRepository.findById(ID);


            if(user.isPresent())
            {
                PlanningDtoResponse userConverted =modelMapper.map(user.get(), PlanningDtoResponse.class);
                reponse.setData(userConverted);
                reponse.setMessage("Ce planning a été retrouvé avec succès");
                reponse.setCode(200);

            }
            else
            {
                reponse.setMessage("Ce planning n'existe pas");
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
    public Reponse deleteById(UUID ID) {
        Reponse reponse = new Reponse();
        try
        {
            Optional<Planning> user = planningRepository.findById(ID);
            if(user.isPresent())
            {
                planningRepository.delete(user.get());
                reponse.setData(true);
                reponse.setMessage("Ce planning a été supprimé");
                logger.error(" Ce planning a été supprimé :  "+user.get().getTarget());
                reponse.setCode(200);

            }
            else
            {
                reponse.setMessage("Ce planning n'existe plus ");
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
    public Reponse getPlanningByRessourceId(UUID ID) {
        Reponse reponse = new Reponse();
        try
        {
            List<PlanningDtoResponse> users = planningRepository.findByRessourceId(ID)
                    .stream()
                    .map( v->{
                        var attached= modelMapper.map(v, PlanningDtoResponse.class);
                        return attached;
                    }).collect(Collectors.toList());
            reponse.setData(users);
            reponse.setMessage("Listes des plannings par ressources");
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
