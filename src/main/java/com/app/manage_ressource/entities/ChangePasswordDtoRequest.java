package com.app.manage_ressource.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data @AllArgsConstructor @NoArgsConstructor
public class ChangePasswordDtoRequest
{
	private UUID id;
	private String newPassword;
}
