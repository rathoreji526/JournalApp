package net.engineeringdigest.journalApp.DTO;

import lombok.Data;
import lombok.NonNull;
import net.engineeringdigest.journalApp.model.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Data
public class UserDTO {
    private String username;
    private String password;
    private String email;
    private String phone;
}
