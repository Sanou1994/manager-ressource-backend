package com.app.manage_ressource.dto.response;
import com.app.manage_ressource.entities.UtilisateurEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;
import java.util.UUID;


@Data @AllArgsConstructor @NoArgsConstructor
public class UserDtoResponse
{

    private UUID ID;
    private String lastname;
    private String firstname;
    private String adress;
    private String email;
    private String password;
    private String phone;
    private boolean status;
    private UtilisateurEnum role;
    private Date createdOn;
    private Date updatedOn;

}