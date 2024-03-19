package com.app.manage_ressource.dto.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@NoArgsConstructor @AllArgsConstructor
public class TypeRessourceDtoRequest {
    private UUID id;
    private String type;
    private boolean  isActive;

}
