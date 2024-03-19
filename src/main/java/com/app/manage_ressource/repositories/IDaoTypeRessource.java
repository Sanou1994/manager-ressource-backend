package com.app.manage_ressource.repositories;
import com.app.manage_ressource.entities.TypeRessource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IDaoTypeRessource extends JpaRepository<TypeRessource, UUID> {
List<TypeRessource> findByIsActive(boolean status);
}
