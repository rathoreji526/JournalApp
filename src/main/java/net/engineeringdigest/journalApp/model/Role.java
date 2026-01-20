package net.engineeringdigest.journalApp.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "roles")
public class Role {
    private String roleName;
}
