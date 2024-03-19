package com.app.manage_ressource.dto.request;


import com.app.manage_ressource.entities.UtilisateurEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data @AllArgsConstructor @NoArgsConstructor
public class UserDtoRequest
{
    private UUID ID;
    private String lastname;
    private String firstname;
    private String adress;
    private String email;
    private String password;
    private String phone;
    private UtilisateurEnum role;


}
