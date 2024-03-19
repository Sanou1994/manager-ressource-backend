package com.app.manage_ressource.repositories;
import com.app.manage_ressource.entities.Ressource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IDaoRessource extends JpaRepository<Ressource, UUID> {
    List<Ressource> findByStatus(boolean status);
    

}
