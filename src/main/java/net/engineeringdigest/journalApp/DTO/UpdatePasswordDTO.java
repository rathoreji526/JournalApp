package net.engineeringdigest.journalApp.DTO;

import lombok.Data;

@Data
public class UpdatePasswordDTO {
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;
}
