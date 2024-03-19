package com.app.manage_ressource.repositories;
import com.app.manage_ressource.entities.Utilisateur;
import com.app.manage_ressource.entities.UtilisateurEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IDaoUser extends JpaRepository<Utilisateur, UUID> {
    Optional<Utilisateur> findByEmail(String email);
    List<Utilisateur> findByEmailOrPhone(String email, String phone);
    List<Utilisateur> findByStatusAndRole(Boolean status, UtilisateurEnum role);

    Optional<Utilisateur> findByPhone(String phone);
    List<Utilisateur> findByStatus(Boolean status);


}
