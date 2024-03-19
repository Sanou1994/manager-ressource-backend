package com.app.manage_ressource.dto.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@NoArgsConstructor @AllArgsConstructor
public class ChangeStatusDtoRequest {
    private UUID stepID;
    private UUID nextStepID;
    private UUID statusID;
    private UUID registerFolderID;
    private UUID attachedPieceID;
    private UUID typeRegisterFolderID;
    private UUID userGivenAgreementID;
    private String comment;
    private String title;


}
