package com.app.manage_ressource.repositories;

import com.app.manage_ressource.entities.Definitely;
import com.app.manage_ressource.entities.Unavailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IDaoDefinitely extends JpaRepository<Definitely, UUID> {
    List<Definitely> findByRessourceID(UUID ressourceID);

    List<Definitely> findByTakerIdentificantAndRessourceID(UUID dentifiant,UUID ressourceID);
}
