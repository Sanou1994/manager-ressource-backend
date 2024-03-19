package com.app.manage_ressource.services;

import com.app.manage_ressource.dto.request.TypeRessourceDtoRequest;
import com.app.manage_ressource.dto.response.TypeRessourceDtoResponse;
import com.app.manage_ressource.entities.Reponse;
import com.app.manage_ressource.entities.TypeRessource;
import com.app.manage_ressource.repositories.IDaoPlanning;
import com.app.manage_ressource.repositories.IDaoTypeRessource;
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
public class TypeRessourceService implements CrudService <TypeRessourceDtoRequest>
{
    private static final Logger logger = LoggerFactory.getLogger(TypeRessourceService.class);
    @Autowired
    private IDaoTypeRessource typeRessourceRepository;
    @Autowired
    private IDaoPlanning planningRepository;

    @Autowired(required=true)
    private ModelMapper modelMapper;
    @Override
    public Reponse create(TypeRessourceDtoRequest obj) {
        Reponse reponse = new Reponse();

        try
        {
            TypeRessource attachedPieceConverted =modelMapper.map(obj, TypeRessource.class);
            attachedPieceConverted.setActive(true);
            TypeRessource userSave = typeRessourceRepository.save(attachedPieceConverted);
            reponse.setData(modelMapper.map(userSave, TypeRessourceDtoResponse.class));
            reponse.setMessage("Ce type de ressource a été enregistrée avec succès");
            logger.info("Ce  type de ressource a été enregistrée avec succès ");
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
    public Reponse update(TypeRessourceDtoRequest obj) {
        Reponse reponse = new Reponse();
        try
        {

            if(obj.getId() != null)
            {
                Optional<TypeRessource> userById = typeRessourceRepository.findById(obj.getId());
                if(obj.getType() != null && !obj.getType().equals(userById.get().getType()))
                {
                    userById.get().setType(obj.getType());
                }


                userById.get().setActive(true);
                TypeRessource userSave = typeRessourceRepository.save(userById.get());
                TypeRessourceDtoResponse agencyDtoResponse=modelMapper.map(userSave, TypeRessourceDtoResponse.class);
                reponse.setData(agencyDtoResponse);
                reponse.setMessage("Ce type de ressource a été modifiée avec succès");
                reponse.setCode(200);
                logger.error(" Ce type de ressource a été modifiée avec succès  " +userById.get().getType());


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
            List<TypeRessourceDtoResponse> users = typeRessourceRepository.findByIsActive(true)
                    .stream()
                    .map( v->modelMapper.map(v, TypeRessourceDtoResponse.class)).collect(Collectors.toList());
            reponse.setData(users);
            reponse.setMessage("Listes des type de ressources");
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
            List<TypeRessourceDtoResponse> users = typeRessourceRepository.findAll()
                    .stream()
                    .peek(o-> my_size.getAndIncrement())
                    .filter(v ->type == null || (type != null && type.length() >0 && v.getId().compareTo(UUID.fromString(type)) ==0))
                    .sorted(Comparator.comparing(TypeRessource::getType))
                    .map( v-> modelMapper.map(v, TypeRessourceDtoResponse.class))
                    .skip(skipCount)
                    .limit(size)
                    .collect(Collectors.toList());

            datas.put("totals",my_size);
            datas.put("data", users);
            reponse.setData(datas);
            reponse.setMessage("Listes des types de  ressources");
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
            Optional<TypeRessource> user = typeRessourceRepository.findById(ID);


            if(user.isPresent())
            {
                TypeRessourceDtoResponse userConverted =modelMapper.map(user.get(), TypeRessourceDtoResponse.class);

                reponse.setData(userConverted);
                reponse.setMessage("Ce type de ressource a été retrouvé avec succès");
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
