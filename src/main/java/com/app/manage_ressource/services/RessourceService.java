package com.app.manage_ressource.services;

import com.app.bank.dto.request.AttachedPieceDtoRequest;
import com.app.manage_ressource.dto.request.RessourceDtoRequest;
import com.app.manage_ressource.dto.response.AttachedPieceDtoResponse;
import com.app.manage_ressource.dto.response.PlanningDtoResponse;
import com.app.manage_ressource.dto.response.RessourceDtoResponse;
import com.app.manage_ressource.entities.*;
import com.app.manage_ressource.repositories.*;
import org.jvnet.hk2.internal.Utilities;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class RessourceService implements CrudService <RessourceDtoRequest>
{
    private static final Logger logger = LoggerFactory.getLogger(RessourceService.class);
    @Autowired
    private IDaoRessource ressourceRepository;
    @Autowired
    private IDaoDefinitely definitelyRepository;
    @Autowired
    private IDaoUnavailability unavailabilityRepository;
    @Autowired
    private IDaoPlanning planningRepository;

    @Autowired(required=true)
    private ModelMapper modelMapper;
    @Override
    public Reponse create(RessourceDtoRequest obj) {
        Reponse reponse = new Reponse();

        try
        {

            Ressource attachedPieceConverted =modelMapper.map(obj, Ressource.class);
            attachedPieceConverted.setStatus(true);
            attachedPieceConverted.setCurrentCount(obj.getCount());
            Ressource userSave = ressourceRepository.save(attachedPieceConverted);
        reponse.setData(modelMapper.map(userSave, RessourceDtoResponse.class));
        reponse.setMessage("Cette ressource a été enregistrée avec succès");
        logger.error("Cette ressource a été enregistrée avec succès ");
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
    public Reponse update(RessourceDtoRequest obj) {
        Reponse reponse = new Reponse();
        try
        {

            if(obj.getId() != null)
            {

                int countUnavailability=  unavailabilityRepository.findByRessourceID(obj.getId()).size();
                int countDefinitely=definitelyRepository.findByRessourceID(obj.getId()).size();

                Optional<Ressource> userById = ressourceRepository.findById(obj.getId());
                if(obj.getCategory() != null && !obj.getCategory().equals(userById.get().getCategory()))
                {
                    userById.get().setCategory(obj.getCategory());
                }
                if(obj.getCode() != null && !obj.getCode().equals(userById.get().getCode()))
                {
                    userById.get().setCode(obj.getCode());
                }
                if(obj.getMarque() != null && !obj.getMarque().equals(userById.get().getMarque()))
                {
                    userById.get().setMarque(obj.getMarque());
                }
                if(countUnavailability ==0 && countDefinitely==0 && obj.getMount() != 0 && obj.getMount() != userById.get().getMount())
                {
                    userById.get().setMount(obj.getMount());
                }
                if( countUnavailability ==0 && countDefinitely==0 && obj.isPaidByDays() != userById.get().isPaidByDays())
                {
                    userById.get().setPaidByDays(obj.isPaidByDays());
                }
                if( countUnavailability ==0 && countDefinitely==0 && obj.isPaidByHours() != userById.get().isPaidByHours())
                {
                    userById.get().setPaidByHours(obj.isPaidByHours());
                }
                if(countUnavailability ==0 && countDefinitely==0 && obj.isPaidByGroups() != userById.get().isPaidByGroups())
                {
                    userById.get().setPaidByGroups(obj.isPaidByGroups());
                }

                if( countUnavailability ==0 && countDefinitely==0 &&obj.getCount() != 0 && obj.getCount() != userById.get().getCount())
                {

                userById.get().setCurrentCount(userById.get().getCurrentCount() + (obj.getCount() - userById.get().getCount()));
                userById.get().setCount(obj.getCount());

                }
                if(obj.getDescription() != null && !obj.getDescription().equals(userById.get().getDescription()))
                {
                    userById.get().setDescription(obj.getDescription());
                }
                userById.get().setStatus(true);
                Ressource userSave = ressourceRepository.save(userById.get());
                RessourceDtoResponse agencyDtoResponse=modelMapper.map(userSave, RessourceDtoResponse.class);
                reponse.setData(agencyDtoResponse);
                reponse.setMessage( "Cette ressource a été modifiée avec succès");
                reponse.setCode(200);
                logger.error(" Cette ressource a été modifiée avec succès  " +userById.get().getCategory());


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
    @Transactional
    public Reponse getAll()
    {
        Reponse reponse = new Reponse();
        try
        {
            List<RessourceDtoResponse> users = ressourceRepository.findByStatus(true)
                    .stream()
                    .map( v->modelMapper.map(v, RessourceDtoResponse.class)).collect(Collectors.toList());
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
    public Reponse getAll(String type,int page,int size)
    {
        int skipCount = (page - 1) * size;
        AtomicInteger my_size= new AtomicInteger(0);
        HashMap<String, Object> datas  = new HashMap<String, Object>();
        Reponse reponse = new Reponse();
        try
        {
            List<RessourceDtoResponse> users = ressourceRepository.findAll()
                    .stream()
                    .peek(o-> my_size.getAndIncrement())
                    .filter(v ->type == null || (type != null && type.length() >0 && v.getId().compareTo(UUID.fromString(type)) ==0))
                    .sorted(Comparator.comparing(Ressource::getCreatedOn).reversed())
                    .map( v-> modelMapper.map(v, RessourceDtoResponse.class))
                    .skip(skipCount)
                    .limit(size)
                    .collect(Collectors.toList());

            datas.put("totals",my_size);
            datas.put("data", users);
            reponse.setData(datas);
            reponse.setMessage("Listes des  ressources");
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
            Optional<Ressource> user = ressourceRepository.findById(ID);


            if(user.isPresent())
            {
                RessourceDtoResponse userConverted =modelMapper.map(user.get(), RessourceDtoResponse.class);
                List<PlanningDtoResponse> res = planningRepository.findByRessourceId(ID)
                        .stream()
                        .map( p->{
                            var attached= modelMapper.map(p, PlanningDtoResponse.class);
                            return attached;
                        }).collect(Collectors.toList());
                userConverted.setPlannings(res);
                reponse.setData(userConverted);
                reponse.setMessage("Cette ressource a été retrouvé avec succès");
                reponse.setCode(200);

            }
            else
            {
                reponse.setMessage("Cette ressource n'existe pas");
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

    public Reponse getAllReservations() {
        Reponse reponse = new Reponse();
        try
        {
            List<RessourceDtoResponse> users = ressourceRepository.findAll()
                    .stream()
                    .filter(d-> d.isTemporaly() && d.isProgrammable())
                    .map( v->
                    {
                        var attached= modelMapper.map(v, RessourceDtoResponse.class);
                        var res = planningRepository.findByRessourceId(v.getId())
                                .stream().mapToDouble(Planning::getMount).sum();
                        attached.setMount(res);
                        attached.setReservationTotalCount((int) planningRepository.findByRessourceId(v.getId())
                                .stream().count());
                        return attached;
                    }).collect(Collectors.toList());
            reponse.setData(users);
            reponse.setMessage("Listes des ressources avec les reservations");
            reponse.setCode(200);


        }
        catch (Exception e) {
            logger.error(" une exception est survenue "+e.getMessage());
            reponse.setMessage("Une erreur interne est survenue");
            reponse.setCode(500);

        }

        return reponse;
    }

    public Reponse getRessourceByUserId(UUID droitID,String role, int page,int size){
        int skipCount = (page - 1) * size;
        AtomicInteger my_size= new AtomicInteger(0);
        HashMap<String, Object> datas  = new HashMap<String, Object>();
        Reponse reponse = new Reponse();

        try
        {
            List<RessourceDtoResponse> users = ressourceRepository.findAll()
                    .stream()
                    .filter(v ->{
                    int countUnavailability=  unavailabilityRepository.findByTakerIdentificantAndRessourceID(droitID,v.getId()).size();
                    int countDefinitely=definitelyRepository.findByTakerIdentificantAndRessourceID(droitID,v.getId()).size();

                    return  ( role.equals(UtilisateurEnum.ADMINISTRATEUR.name()) || countUnavailability >0 || countDefinitely >0  ) ? true :false;})
                     .peek(o-> my_size.getAndIncrement())
                    .sorted(Comparator.comparing(Ressource::getCreatedOn).reversed())
                    .map( v->
                            {
                                int countUnavailability=  unavailabilityRepository.findByTakerIdentificantAndRessourceID(droitID,v.getId()).size();
                                int countDefinitely=definitelyRepository.findByTakerIdentificantAndRessourceID(droitID,v.getId()).size();
                                var ress= modelMapper.map(v, RessourceDtoResponse.class);
                                ress.setCount(role.equals(UtilisateurEnum.UTILISATEUR.name()) ? (countUnavailability+countDefinitely) : ress.getCount() );
                                return  ress ;
                            } )
                    .skip(skipCount)
                    .limit(size)
                    .collect(Collectors.toList());

            datas.put("totals",my_size);
            datas.put("data", users);
            reponse.setData(datas);
            reponse.setMessage("Listes des  ressources");
            reponse.setCode(200);


        }
        catch (Exception e) {
            logger.error(" une exception est survenue "+e.getMessage());
            reponse.setMessage("Une erreur interne est survenue");
            reponse.setCode(500);

        }

        return reponse;
    }

    public Reponse delete(UUID ID){
        Reponse reponse = new Reponse();
        try
        {
            Optional<Ressource> user = ressourceRepository.findById(ID);
            if(user.isPresent())
            {
                planningRepository.findByRessourceId(ID).stream().forEach(p->planningRepository.delete(p));
                unavailabilityRepository.findByRessourceID(ID).stream().forEach(p->unavailabilityRepository.delete(p));
                definitelyRepository.findByRessourceID(ID).stream().forEach(p->definitelyRepository.delete(p));
                ressourceRepository.delete(user.get());
                reponse.setData(true);
                reponse.setMessage("Cette ressource a été bien supprimé avec succès");
                reponse.setCode(200);

            }
            else
            {
                reponse.setMessage("Cette ressource n'existe pas");
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
