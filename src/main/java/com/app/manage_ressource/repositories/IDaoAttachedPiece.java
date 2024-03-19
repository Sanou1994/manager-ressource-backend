package com.app.manage_ressource.repositories;
import com.app.manage_ressource.entities.AttachedPiece;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IDaoAttachedPiece extends JpaRepository<AttachedPiece, UUID> {

}
