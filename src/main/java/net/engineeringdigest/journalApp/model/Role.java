package net.engineeringdigest.journalApp.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "roles")
public class Role {
    @Id
    private String name;
    private LocalDateTime createdAt;
}
