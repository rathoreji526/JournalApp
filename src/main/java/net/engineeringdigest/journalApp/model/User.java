package net.engineeringdigest.journalApp.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "user")
@NoArgsConstructor
public class User {

    @Id
    private ObjectId id;
    @NonNull
    @Indexed(unique = true)
    private String username;
    @NonNull
    private String fullName;
    private Date DOB;
    @NonNull
    private String password;
    @NonNull
    @Indexed(unique = true)
    private String email;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @DBRef
    private List<JournalEntry> journalEntries = new ArrayList<>();
    @DBRef
    private List<String> roles = new ArrayList<>();
}
